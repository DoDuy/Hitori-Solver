package model.SatSolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import model.Config;
import model.lib.StopWatch;

/**
 * SatSolver SAT4J write file 'cnf' before solve and call in a process
 * @author DoDuy
 */
public class Sat4jSolverInProcess implements ISatSolver{
    private String urlin = "";
    private String mgError = "";
    private boolean isSat = false;
    private int[] result = null;
    private long time = 0;
    private int nConstraints = 0;
    private int nVar = 0;
    private final StopWatch sWatch;
    private FileWriter writer;
    
    private int NumberOfClauses;
    public Sat4jSolverInProcess(){
        this.urlin = Config.PATH_FILE_CNF;
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
        try {
            Process process = new ProcessBuilder("java","-jar",Config.PATH_SAT4J_JAR_FILE, this.urlin).start();
            InputStream is = process.getInputStream();
   	
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br1 = new BufferedReader(isr);
            String prline;

            while ((prline = br1.readLine()) != null) {
                if(prline.contains("UNSATISFIABLE")){
                    this.isSat = false;
                    return true;
                }
                if(prline.contains("SATISFIABLE")){
                    this.isSat = true;
                }
                if(prline.contains("declared #vars")){
                    String[] str = prline.trim().split(" +");
                    nVar = Integer.parseInt(str[str.length-1]);
                }
                if(prline.contains("#constraints")){
                    String[] str = prline.trim().split(" +");
                    nConstraints = Integer.parseInt(str[str.length-1]);
                }
                if(prline.startsWith("v")) {
                    String[] strnumbers = prline.trim().split(" ");
                    int len = strnumbers.length-1;
                    int[] numbers = new int[len];
                    for(int i=1; i<len; i++)
                        numbers[i-1] = Integer.parseInt(strnumbers[i]);
                    this.result = numbers;
                } 
            }
            if(!sWatch.Stop()) {
                mgError = Config.STROUT_ERROR_STOPWATCH;
                return false;
            }
            this.time = sWatch.getNanoTime();
            return true;
        } catch (IOException e) {
            mgError = "IOException, sorry!<br>Maybe not found sat4j.jar";
        }
        return false;
    }
}