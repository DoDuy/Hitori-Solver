package model.solver;

import java.io.File;
import java.util.ArrayList;
import model.Config;
import model.SatSolver.ISatSolver;
import model.lib.Judge;
import model.lib.StopWatch;

/**
 * not finish
 * done fake Search With Pattern 11h28 12/11/2015
 * create on 12/11/2015
 * Encoding any Pattern
 * @author doduy
 */
public final class PatternEncoding1v0 extends ASolver{
    protected int NumberOfClauses;
    protected int NumberOfVars;
    protected int white[][];
    protected long timer;
    protected StopWatch sWatch;
    protected ISatSolver solver;
    
    public PatternEncoding1v0(int n, int m, int[][] b) {
        super(n, m, b);
        //NumberOfVars = 0;
        NumberOfClauses = 0;
        sWatch = new StopWatch();
        solver = (new Judge()).getSatSolver(Config.SELECTED_SATSOLVER);
        //encodeVars
        white = new int[Rows][Columns];
        int i,j;
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                this.white[i][j] = i*Columns+j+1;
        NumberOfVars = Rows*Columns;
    }

    private void Pattern1(){
        if(valueOfCell[0][0] == valueOfCell[0][1]) solver.addAClause(white[1][0]);
        if(valueOfCell[0][0] == valueOfCell[1][0]) solver.addAClause(white[0][1]);
        if(valueOfCell[1][0] == valueOfCell[1][1]) solver.addAClause(white[0][1]);
        if(valueOfCell[0][1] == valueOfCell[1][1]) solver.addAClause(white[1][0]);
        
        if(valueOfCell[Rows-1][0] == valueOfCell[Rows-2][0]) solver.addAClause(white[Rows-1][1]);
        if(valueOfCell[Rows-1][0] == valueOfCell[Rows-1][1]) solver.addAClause(white[Rows-2][0]);
        if(valueOfCell[Rows-1][1] == valueOfCell[Rows-2][1]) solver.addAClause(white[Rows-2][0]);
        if(valueOfCell[Rows-2][0] == valueOfCell[Rows-2][1]) solver.addAClause(white[Rows-1][1]);
        
        if(valueOfCell[0][Columns-1] == valueOfCell[0][Columns-2]) solver.addAClause(white[1][Columns-1]);
        if(valueOfCell[0][Columns-1] == valueOfCell[1][Columns-1]) solver.addAClause(white[0][Columns-2]);
        if(valueOfCell[0][Columns-2] == valueOfCell[1][Columns-2]) solver.addAClause(white[1][Columns-1]);
        if(valueOfCell[1][Columns-1] == valueOfCell[1][Columns-2]) solver.addAClause(white[0][Columns-2]);
        
        if(valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-2][Columns-1]) solver.addAClause(white[Rows-1][Columns-2]);
        if(valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-1][Columns-2]) solver.addAClause(white[Rows-2][Columns-1]);
        if(valueOfCell[Rows-1][Columns-2] == valueOfCell[Rows-2][Columns-2]) solver.addAClause(white[Rows-2][Columns-1]);
        if(valueOfCell[Rows-2][Columns-1] == valueOfCell[Rows-2][Columns-2]) solver.addAClause(white[Rows-1][Columns-2]);
    }
    
    private void Pattern2(){
        int i,j,k;
        int[][] flag = new int[Rows][Columns];
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
                flag[i][j] = -1;
        
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++){
                if(i+1 < Rows && valueOfCell[i][j] == valueOfCell[i+1][j])
                    for(k = 0;k<Rows;k++)
                        if( k!= i && k!= i+1 && valueOfCell[k][j] == valueOfCell[i][j] && flag[k][j] == -1){
                            flag[k][j] = 1;
                            solver.addAClause(-1*white[k][j]);
                        }
                if(j+1 < Columns && valueOfCell[i][j] == valueOfCell[i][j+1])
                    for(k = 0;k<Columns;k++) 
                        if( k!= j && k!= j+1 && valueOfCell[i][k] == valueOfCell[i][j] && flag[i][k] == -1){
                            flag[i][k] = 1;
                            solver.addAClause(-1*white[i][k]);
                        }
            }
    }
    
    private void Pattern3(){
        int i,j;
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
                if((i+1 < Rows && i-1 >= 0 && valueOfCell[i+1][j] == valueOfCell[i-1][j])
                || (j+1 < Columns && j-1 >= 0 && valueOfCell[i][j-1] == valueOfCell[i][j+1]))
                    solver.addAClause(white[i][j]);
    }
    
    private void Pattern4(){
        if(valueOfCell[0][0] == valueOfCell[0][1] && valueOfCell[0][0] == valueOfCell[1][0])
            solver.addAClause(-1*white[0][0]);
        if(valueOfCell[Rows-1][0] == valueOfCell[Rows-1][1] && valueOfCell[Rows-1][0] == valueOfCell[Rows-2][0])
            solver.addAClause(-1*white[Rows-1][0]);
        if(valueOfCell[0][Columns-1] == valueOfCell[1][Columns-1] && valueOfCell[0][Columns-1] == valueOfCell[0][Columns-2])
            solver.addAClause(-1*white[0][Columns-1]);
        if(valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-2][Columns-1] && valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-1][Columns-2])
            solver.addAClause(-1*white[Rows-1][Columns-1]);
    }
    
    private void Pattern5(){
        // -b1 & -b2 & -b3 ... -> a 
        int i,j,k;
        ArrayList<Integer> list = new ArrayList<>();
        
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++){
                list.add(white[i][j]);
                for(k = 0;k<Rows;k++)
                    if(k != i && valueOfCell[k][j] == valueOfCell[i][j])
                        list.add(white[k][j]);
                for(k = 0;k<Columns;k++) 
                    if(k != j && valueOfCell[i][k] == valueOfCell[i][j])
                        list.add(white[i][k]);
                    
                int len = list.size();
                int[] ints = new int[len];
                for(k=0; k < len; k++)
                   ints[k] = list.get(k);
                solver.addAClause(ints);

                list.clear();
            }
    }
    
    private void Pattern6_8(){
        int i,j,k;
        boolean[][] isAblePaint = new boolean[Rows][Columns];
        for(i = 0; i < Rows; i++)
            for(j = 0;j < Columns; j++)
                isAblePaint[i][j] = false;
        
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns-1; j++)              
                for(k = j+1; k < Columns; k++)          
                    if(valueOfCell[i][j] == valueOfCell[i][k]){
                        solver.addAClause(-1*white[i][j],-1*white[i][k]);
                        isAblePaint[i][j] = true;
                        isAblePaint[i][k] = true;
                    }
        
        for(j = 0; j < Columns; j++)
            for(i = 0; i < Rows-1; i++)
                for(k = i+1; k < Rows; k++)
                    if(valueOfCell[i][j] == valueOfCell[k][j]){
                        solver.addAClause(-1*white[i][j],-1*white[k][j]);
                        isAblePaint[i][j] = true;
                        isAblePaint[k][j] = true;
                    }
        
        for(i = 0; i<Rows; i++)
            for(j = 0; j<Columns; j++)
                if(isAblePaint[i][j]){           
                    if((i-1>=0)&&isAblePaint[i-1][j])
                        solver.addAClause(white[i][j],white[i-1][j]);
                    if((j-1>=0)&&isAblePaint[i][j-1])
                        solver.addAClause(white[i][j],white[i][j-1]);
                }
    }
    
    private void Pattern7(){
        int i,j;
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
                if(i-1 >= 0 && i+1 < Rows && j-1 >= 0 && j+1 < Columns)
                    solver.addAClause(white[i-1][j],white[i+1][j],white[i][j-1],white[i][j+1]);
    }
    
    private boolean Encode(){
        Pattern1();
        Pattern2();
        Pattern3();
        Pattern4();
        Pattern5();
        Pattern6_8();
        Pattern7();
        NumberOfClauses = solver.getNumberOfClauses();
        return solver.finishAddAClause() && solver.addVarsAndClauses(NumberOfVars);
    }
    
    private int SatSolverRun(){
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
    
    private void setSatSolverRt(ISatSolver solver){
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
    
    private boolean decode(int[] arr){
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
    
    private boolean InsertAClause(){
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
    public int Solve() {
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
    public int SolveMore() {
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