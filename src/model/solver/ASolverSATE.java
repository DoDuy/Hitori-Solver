package model.solver;

import java.io.File;
import model.Config;
import model.SatSolver.ISatSolver;
import model.lib.Judge;
import model.lib.StopWatch;

/**
 *
 * @author DoDuy
 */
public abstract class ASolverSATE extends ASolver{
    protected int NumberOfClauses;
    protected int NumberOfVars;
    protected int white[][];
    protected long timer;
    protected StopWatch sWatch;
    protected ISatSolver solver;
    protected ASolverSATE(int n, int m, int b[][]){
        super(n,m,b);
        NumberOfVars = 0;
        NumberOfClauses = 0;
        sWatch = new StopWatch();
        solver = (new Judge()).getSatSolver(Config.SELECTED_SATSOLVER);
    }
    
    public int getNrOfClauses(){
        return NumberOfClauses;
    }
    
    public int getNrOfVars(){
        return NumberOfVars;
    }
    
    protected void encodeVar(){
        white = new int[Rows][Columns];
        int i,j;
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                this.white[i][j] = i*Columns+j+1;
    }
    
    protected int SatSolverRun(){
        boolean isOk = solver.Solve();
        if(isOk){
            this.setSatSolverRt(solver);
            if(solver.getIsSat() == false) return 0;
            this.decode(solver.getResult());
            return 1;
        }
        else{
            SatSolverRt = solver.getMgError();
            return -1;
        }
    }
    
    protected void setSatSolverRt(ISatSolver solver){
        SatSolverRt =  "<html>Count Variables: " + NumberOfVars 
                     + "<br>Count Clauses: " + NumberOfClauses 
                     + "<br><br>Number of Variables: " + solver.getnVar()
                     + "<br>Number of Constraints: " + solver.getnConstraints()
                     + "<br><br>File Cnf size: " + String.format("%.3f",new File(Config.PATH_FILE_CNF).length()*1.0/1024) + " Kb"
                     + "<br><br>Time create file Cnf : " + String.format("%.9f",timer*0.000000001) + " s"
                     + "<br>Time SatSolver run : " + String.format("%.9f",solver.getTimeNs()*0.000000001) + " s"
                     + "<br>Time all ....................: " + String.format("%.9f",(solver.getTimeNs()+timer)*0.000000001) + " s"
                     + "<html>";
        timeCounter = (solver.getTimeNs()+timer)*0.000000001;
    }
    
    protected boolean decode(int[] arr){
        int len = arr.length;
        int k;
        for (int i = 0; i < len; i++){
            k = arr[i];
            if(k==0 || k>Rows*Columns || k<-1*Rows*Columns) break;
            if(k>0) result[(k-1)/Columns][(k-1)%Columns]=true;
            if(k<0) result[(-k-1)/Columns][(-k-1)%Columns]=false;
        }
        return true;
    }
    
    protected abstract void CnfRule1();
    protected abstract void CnfRule2();
    protected abstract void CnfRule3();
    protected boolean Encode(){
        encodeVar();
        CnfRule1();
        CnfRule2();
        CnfRule3();
        NumberOfClauses = solver.getNumberOfClauses();
        return solver.finishAddAClause() && solver.addVarsAndClauses(NumberOfVars);
    }
    
    protected boolean InsertAClause(){
        NumberOfClauses++;
        int i,j;
        int[] arr = new int[Rows*Columns];
        for (i=0;i<Rows;i++)
            for (j=0;j<Columns;j++)
                if(result[i][j]) arr[i*Columns + j] = -1*white[i][j];
                else arr[i*Columns + j] = white[i][j];
        return solver.addAClauseToMore(NumberOfVars, arr);
    }
    @Override
    public int Solve(){
        if(!sWatch.Start()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        if(!Encode()){
            SatSolverRt = solver.getMgError();
            return -1;
        }
        if(!sWatch.Stop()){
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        timer = sWatch.getNanoTime();
        return SatSolverRun();
    }
    
    @Override
    public int SolveMore(){
        if(!sWatch.Start()){
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        InsertAClause();
        if(!sWatch.Stop()){
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        timer = sWatch.getNanoTime();
        return SatSolverRun();
    }
}