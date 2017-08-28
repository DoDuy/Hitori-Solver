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
 * SatSolver 'Lingeling' write file 'cnf' before solve
 * @author DoDuy
 */
public class LingelingSolver implements ISatSolver{
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
    public LingelingSolver(){
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
            Process process = new ProcessBuilder(Config.PATH_LINGELING, this.urlin).start();
            InputStream is = process.getInputStream();
   	
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br1 = new BufferedReader(isr);
            String prline;
            
            int[] numbers = null;
            int index = 0;
            while ((prline = br1.readLine()) != null){
                if(prline.contains("UNSATISFIABLE")){
                    this.isSat = false;
                    return true;
                }
                if(prline.contains("SATISFIABLE")){
                    this.isSat = true;
                }
                if(prline.contains("variables,") && prline.contains("clauses,")){
                    String[] str = prline.trim().split(" ");
                    nVar = Integer.parseInt(str[2]);
                    nConstraints = Integer.parseInt(str[4]);
                    //for(String s:str)System.out.println(s);
                    numbers = new int[nVar];
                }
                if(prline.startsWith("v")) {
                    String[] strnumbers = prline.trim().split(" +");
                    int len = strnumbers.length;
                    for(int i=1; i<len; i++)
                        if(!"0".equals(strnumbers[i]))
                            numbers[index++] = Integer.parseInt(strnumbers[i]);
                } 
            }
            this.result = numbers;
            if(!sWatch.Stop()) {
                mgError = Config.STROUT_ERROR_STOPWATCH;
                return false;
            }
            this.time = sWatch.getNanoTime();
            return true;
        } catch (IOException e) {
            mgError = "IOException, sorry!<br>Maybe not found Lingeling";
        }
        return false;
    }
}