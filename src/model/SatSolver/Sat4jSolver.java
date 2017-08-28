package model.SatSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import model.Config;
import model.lib.StopWatch;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/**
 * SatSolver SAT4J write file 'cnf' before solve
 * @author DoDuy
 */
public class Sat4jSolver implements ISatSolver{
    private String url = "";
    private long timeoutset = 0;
    private String mgError = "";
    private boolean isSat = false;
    private int[] result = null;
    private long time = 0;
    private int nConstraints = 0;
    private int nVar = 0;
    private final StopWatch sWatch;
    private FileWriter writer;
    
    private int NumberOfClauses;
    public Sat4jSolver(){
        this.url = Config.PATH_FILE_CNF;
        this.timeoutset = Config.TIMEOUT_SAT4J;
        sWatch = new StopWatch();
        try {
            writer = new FileWriter( new File(Config.PATH_FILE_CNF));
            writer.write(String.format("%"+ Config.HEADER_LENGTH_FILE_CNF +"s", " ")+"\n");
        } catch (IOException ex) {
            mgError = "IOException in file cnf!";
            System.out.print(mgError);
        }
        NumberOfClauses = 0;
    }
    
    @Override
    public void addAClause(int... clause) {
        try {
            if(clause.length == 0 || writer == null) return;
            for(int cl : clause)
                writer.write(cl+" ");
            writer.write("0\n");
            NumberOfClauses++;
        } catch (IOException ex) {
            mgError = "IOException in file cnf!";
        }
    }
    
    @Override
    public boolean finishAddAClause(){
        try {
            writer.close();
        } catch (IOException ex) {
            mgError = "IOException in file cnf!";
            return false;
        }
        return true;
    }
    
    @Override
    public boolean addAClauseToMore(int v,int... clause) {
        NumberOfClauses++;
        String header = "p cnf "+v+" "+NumberOfClauses;
        if(header.length() > Config.HEADER_LENGTH_FILE_CNF){
            mgError = "Header too long!<br>Repair file config and try agian.";
            return false;
        }
        try{
            RandomAccessFile f = new RandomAccessFile(new File(Config.PATH_FILE_CNF), "rw");
            f.seek(0); // to the beginning
            f.writeBytes(header);
            f.seek(f.length());
            for(int cl : clause)
                f.write((cl+" ").getBytes());
            f.write("0\n".getBytes());
        } catch (IOException ex) {
            mgError = "IOException in file cnf!";
            return false;
        }
        return true;
    }
    
    @Override
    public boolean addVarsAndClauses(int v){
        String header = "p cnf "+v+" "+NumberOfClauses;
        if(header.length() > Config.HEADER_LENGTH_FILE_CNF){
            mgError = "Header too long!<br>Repair file config and try agian.";
            return false;
        }
        try {
            RandomAccessFile f = new RandomAccessFile(new File(Config.PATH_FILE_CNF), "rw");
            f.seek(0); // to the beginning
            f.writeBytes(header);
        } catch (Exception ex) {
            mgError = "RandomAccessFile IOException in file cnf!";
            return false;
        }
        return true;
    }
    
    @Override
    public String getMgError(){
        return this.mgError;
    }
    
    @Override
    public boolean getIsSat(){
        return this.isSat;
    }
    
    @Override
    public long getTimeNs(){
        return this.time;
    }
    
    @Override
    public int getnConstraints(){
        return this.nConstraints;
    }
    
    @Override
    public int getnVar(){
        return this.nVar;
    }
    
    @Override
    public int[] getResult(){
        return this.result;
    }
    
    @Override
    public int getNumberOfClauses() {
        return NumberOfClauses;
    }
    
    @Override
    public boolean Solve(){
        if(!sWatch.Start()) {
            mgError = Config.STROUT_ERROR_STOPWATCH;
            return false;
        }
        ISolver solver = SolverFactory.newLight();
        solver.setTimeoutMs(this.timeoutset);
        solver.setDBSimplificationAllowed(true);
        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance(this.url);
            if (problem.isSatisfiable()) {
                this.result = problem.model();
                this.isSat = true;
            } else {
                this.isSat = false;
            }
            this.nConstraints = solver.nConstraints();
            this.nVar = solver.nVars();
            if(!sWatch.Stop()) {
                mgError = Config.STROUT_ERROR_STOPWATCH;
                return false;
            }
            this.time = sWatch.getNanoTime();
            return true;
        } catch (FileNotFoundException e) {
            mgError = "The file cannot be found";
        } catch (ParseFormatException e) {
            mgError = "An error occurs during parsing";
            e.printStackTrace();
        } catch (IOException e) {
            mgError = "An I/O error occurs";
        } catch (ContradictionException e) {
            mgError = "Unsatisfiable (trivial)!";
        } catch (TimeoutException e) {
            mgError = "Timeout, sorry!";
        }
        return false;
    }
}