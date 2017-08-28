package model.solver;

import java.io.*;
import model.Config;
import model.SatSolver.ISatSolver;

/**
 * 12/10/2015
 * Connectivity Encoding use Zones and patterns before encode
 * @author DoDuy
 */
public final class ConnectivityEncoding2v1 extends ASolverSATE{
    private final int flag[][];
    private final boolean isBlackFix[][];
    private long timeForSearch;
    
    private int nrOfCellNotFix;
    private boolean[][] notFix;
    private int[][] zones;
    private int[][][][] path;
    public ConnectivityEncoding2v1(int n, int m, int b[][]){
        super(n,m,b);
        flag = new int[Rows][Columns];
        isBlackFix = new boolean[Rows][Columns];
        int j;
        for(int i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
            {
                flag[i][j] = -1;
                isBlackFix[i][j] = false;
            }
    }
    
    private void DoWithPatternEasy(){
        //Pattern 1
        if(valueOfCell[0][0] == valueOfCell[0][1]) flag[1][0] = 0;
        if(valueOfCell[0][0] == valueOfCell[1][0]) flag[0][1] = 0;
        if(valueOfCell[1][0] == valueOfCell[1][1]) flag[0][1] = 0;
        if(valueOfCell[0][1] == valueOfCell[1][1]) flag[1][0] = 0;
        
        if(valueOfCell[Rows-1][0] == valueOfCell[Rows-2][0]) flag[Rows-1][1] = 0;
        if(valueOfCell[Rows-1][0] == valueOfCell[Rows-1][1]) flag[Rows-2][0] = 0;
        if(valueOfCell[Rows-1][1] == valueOfCell[Rows-2][1]) flag[Rows-2][0] = 0;
        if(valueOfCell[Rows-2][0] == valueOfCell[Rows-2][1]) flag[Rows-1][1] = 0;
        
        if(valueOfCell[0][Columns-1] == valueOfCell[0][Columns-2]) flag[1][Columns-1] = 0;
        if(valueOfCell[0][Columns-1] == valueOfCell[1][Columns-1]) flag[0][Columns-2] = 0;
        if(valueOfCell[0][Columns-2] == valueOfCell[1][Columns-2]) flag[1][Columns-1] = 0;
        if(valueOfCell[1][Columns-1] == valueOfCell[1][Columns-2]) flag[0][Columns-2] = 0;
        
        if(valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-2][Columns-1]) flag[Rows-1][Columns-2] = 0;
        if(valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-1][Columns-2]) flag[Rows-2][Columns-1] = 0;
        if(valueOfCell[Rows-1][Columns-2] == valueOfCell[Rows-2][Columns-2]) flag[Rows-2][Columns-1] = 0;
        if(valueOfCell[Rows-2][Columns-1] == valueOfCell[Rows-2][Columns-2]) flag[Rows-1][Columns-2] = 0;
        
        //Pattern 2
        int i,j,k;
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++){
                if(i+1 < Rows && valueOfCell[i][j] == valueOfCell[i+1][j])
                    for(k = 0;k<Rows;k++)
                        if( k!= i && k!= i+1 && valueOfCell[k][j] == valueOfCell[i][j])
                            flag[k][j]=1;
                if(j+1 < Columns && valueOfCell[i][j] == valueOfCell[i][j+1])
                    for(k = 0;k<Columns;k++) 
                        if( k!= j && k!= j+1 && valueOfCell[i][k] == valueOfCell[i][j])
                            flag[i][k]=1;
            }
        
        //Pattern 3
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
                if((i+1 < Rows && i-1 >= 0 && valueOfCell[i+1][j] == valueOfCell[i-1][j])
                || (j+1 < Columns && j-1 >= 0 && valueOfCell[i][j-1] == valueOfCell[i][j+1]))
                    flag[i][j] = 0;
        
        //Parttern 4
        if(valueOfCell[0][0] == valueOfCell[0][1] && valueOfCell[0][0] == valueOfCell[1][0]) flag[0][0] = 1;
        if(valueOfCell[Rows-1][0] == valueOfCell[Rows-1][1] && valueOfCell[Rows-1][0] == valueOfCell[Rows-2][0]) flag[Rows-1][0] = 1;
        if(valueOfCell[0][Columns-1] == valueOfCell[1][Columns-1] && valueOfCell[0][Columns-1] == valueOfCell[0][Columns-2]) flag[0][Columns-1] = 1;
        if(valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-2][Columns-1] && valueOfCell[Rows-1][Columns-1] == valueOfCell[Rows-1][Columns-2]) flag[Rows-1][Columns-1] = 1;
    }
    
    private void DoWithPatternHard(){
        int i,j,k;
        boolean change = true;
        while(change){
            change = false;
            //Pattern 2
            for(i = 0;i < Rows ; i++)
                for(j = 0;j < Columns ; j++)
                    if(flag[i][j] == 1){
                        if(i-1 >= 0 && flag[i-1][j] == -1) {flag[i-1][j] = 0; change = true;}
                        if(i+1 < Rows && flag[i+1][j] == -1) {flag[i+1][j] = 0; change = true;}
                        if(j-1 >= 0 && flag[i][j-1] == -1) {flag[i][j-1] = 0; change = true;}
                        if(j+1 < Columns && flag[i][j+1] == -1) {flag[i][j+1] = 0; change = true;}
                    }
            //Pattern 3
            for(i = 0;i < Rows ; i++)
                for(j = 0;j < Columns ; j++)
                    if(flag[i][j] == 0 && i-1 >= 0 && i+1 < Rows && j-1 >= 0 && j+1 < Columns)
                    {
                        if(flag[i-1][j] == 1 && flag[i+1][j] == 1){
                           if(flag[i][j-1] == 1 && flag[i][j+1] == -1){flag[i][j+1] = 0; change = true;}
                           if(flag[i][j+1] == 1 && flag[i][j-1] == -1){flag[i][j-1] = 0; change = true;}
                        }
                        if(flag[i][j-1] == 1 && flag[i][j+1] == 1){
                            if(flag[i-1][j] == 1 && flag[i+1][j] == -1){flag[i+1][j] = 0; change = true;}
                            if(flag[i+1][j] == 1 && flag[i-1][j] == -1){flag[i-1][j] = 0; change = true;}
                        }
                    }
            //Pattern 4
            for(i = 0;i < Rows ; i++)
                for(j = 0;j < Columns ; j++)
                    if(flag[i][j]==0){
                        for(k=0;k<Rows;k++)
                            if(i!=k && valueOfCell[i][j]==valueOfCell[k][j] && flag[k][j] == -1)
                            {
                                flag[k][j] = 1;
                                change = true;
                            }
                        for(k=0;k<Columns;k++)
                            if(j!=k && valueOfCell[i][j]==valueOfCell[i][k] && flag[i][k] == -1)
                            {
                                flag[i][k] = 1;
                                change = true;
                            }
                    }
            //Pattern 1
            boolean[][] isSingle = new boolean[Rows][Columns];
            for(i = 0; i < Rows; i++)                       
                for(j = 0; j < Columns; j++)
                    isSingle[i][j] = true;
            for(i = 0; i < Rows; i++)                       
                for(j = 0; j < Columns-1; j++)            
                    for(k = j+1; k < Columns; k++)          
                        if(valueOfCell[i][j] == valueOfCell[i][k] && flag[i][j] != 1 && flag[i][k] != 1){ 
                            isSingle[i][j] = false;
                            isSingle[i][k] = false;
                        }

            for(j = 0; j < Columns; j++)
                for(i = 0; i < Rows-1; i++)
                    for(k = i+1; k < Rows; k++)
                        if(valueOfCell[i][j] == valueOfCell[k][j] && flag[i][j] != 1 && flag[k][j] != 1){
                            isSingle[i][j] = false;
                            isSingle[k][j] = false;
                        }

            for(i = 0; i < Rows; i++)
                for(j = 0; j<Columns; j++)
                    if(isSingle[i][j] && flag[i][j] == -1) 
                    {
                        flag[i][j] = 0;
                        change = true;
                    }
        }
    }
    
    @Override
    protected void encodeVar(){
        int i,j,k,h;
        notFix = new boolean[Rows][Columns];
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns; j++)
            {
                notFix[i][j] = flag[i][j] == -1;
                isBlackFix[i][j] = flag[i][j] == 1;
            }
        
        //var of board
        NumberOfVars = 0;
        white = new int[Rows][Columns];
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                if(notFix[i][j]){
                    NumberOfVars++;
                    white[i][j] = NumberOfVars;
                }
        nrOfCellNotFix = NumberOfVars;
        //zones
        zones = new int[Rows][Columns];
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                zones[i][j] = 0;
        int zoneNumber = 0;
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                if(notFix[i][j] && zones[i][j] == 0) {
                    zoneNumber++;
                    findZone(i,j,zoneNumber);
                }
        
        //set path
        path = new int[Rows][Columns][Rows][Columns];
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                for(k = 0; k < Rows; k++)
                    for(h = 0; h < Columns; h++){
                        path[i][j][k][h] = 0;
                    }
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                for(k = 0; k < Rows; k++)
                    for(h = 0; h < Columns; h++)
                        if(zones[i][j] != 0 && zones[i][j] == zones[k][h] && path[i][j][k][h] == 0){
                            NumberOfVars++;
                            path[i][j][k][h] = NumberOfVars;
                        }
    }
    
    private void findZone(int i, int j, int zoneNumber){
        this.zones[i][j] = zoneNumber;
        if(i+1 < Rows && j+1 < Columns && (notFix[i+1][j+1] || isBlackFix[i+1][j+1]) && zones[i+1][j+1] == 0) findZone(i+1,j+1,zoneNumber);
        if(i-1 >= 0 && j+1 < Columns && (notFix[i-1][j+1] || isBlackFix[i-1][j+1]) && zones[i-1][j+1] == 0) findZone(i-1,j+1,zoneNumber);
        if(i+1 < Rows && j-1 >=0 && (notFix[i+1][j-1] || isBlackFix[i+1][j-1]) && zones[i+1][j-1] == 0) findZone(i+1,j-1,zoneNumber);
        if(i-1 >= 0 && j-1 >= 0 && (notFix[i-1][j-1] || isBlackFix[i-1][j-1]) && zones[i-1][j-1] == 0) findZone(i-1,j-1,zoneNumber);
    }
    
    private boolean InMatrix(int x,int y){
        return x>=0 && x<Rows && y>=0 && y<Columns;
    }
    
    private boolean Diff(int x,int y,int a,int b){
        return x!=a || y!=b;
    }
    
    //CNF Rule 1
    @Override
    protected void CnfRule1(){
        int i,j,k;
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns-1; j++)              
                for(k = j+1; k < Columns; k++)          
                    if(valueOfCell[i][j] == valueOfCell[i][k] && notFix[i][j] && notFix[i][k])
                        solver.addAClause(-1*white[i][j],-1*white[i][k]);
        
        for(j = 0; j < Columns; j++)
            for(i = 0; i < Rows-1; i++)
                for(k = i+1; k < Rows; k++)
                    if(valueOfCell[i][j] == valueOfCell[k][j] && notFix[i][j] && notFix[k][j])
                        solver.addAClause(-1*white[i][j],-1*white[k][j]);
    }
    
    //CNF Rule 2
    @Override
    protected void CnfRule2(){
        for(int i = 0; i<Rows; i++)
            for(int j = 0; j<Columns; j++)
                if(isBlackFix[i][j] || notFix[i][j]){           
                    if(i-1>=0 && (isBlackFix[i-1][j] || notFix[i-1][j]))
                        solver.addAClause(white[i][j],white[i-1][j]);
                    if(j-1>=0 && (isBlackFix[i][j-1] || notFix[i][j-1]))
                        solver.addAClause(white[i][j],white[i][j-1]);
                }
    }
    
    //Rule 3
    @Override
    protected void CnfRule3(){
        int i,j,k,h;
        //-Path(x,y,x,y);
        //-white(x,y) && -white(x+1,y+1) <=> Path(x,y,x+1,y+1) || Path(x+1,y+1,x,y)
        //-white(x,y) && -white(x+1,y-1) <=> Path(x,y,x+1,y-1) || Path(x+1,y-1,x,y)
        //(x,y) Bien thi di vao trong
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++) if(notFix[i][j])
                solver.addAClause(-path[i][j][i][j]);
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++) if(zones[i][j] != 0){
                if(InMatrix(i+1,j+1) && zones[i+1][j+1] != 0){
                    if(i==0 || i==Rows-1 || j==0 || j==Columns-1){
                        if(notFix[i][j] && notFix[i+1][j+1])  solver.addAClause(white[i][j],white[i+1][j+1],path[i][j][i+1][j+1]);
                        else if(notFix[i][j]) solver.addAClause(white[i][j],path[i][j][i+1][j+1]);
                        else if(notFix[i+1][j+1])  solver.addAClause(white[i+1][j+1],path[i][j][i+1][j+1]);
                        else solver.addAClause(path[i][j][i+1][j+1]);
                    }
                    if(i+1==0 || i+1==Rows-1 || j+1==0 || j+1==Columns-1){
                        if(notFix[i][j] && notFix[i+1][j+1]) solver.addAClause(white[i][j],white[i+1][j+1],path[i+1][j+1][i][j]);
                        else if(notFix[i][j]) solver.addAClause(white[i][j],path[i+1][j+1][i][j]);
                        else if(notFix[i+1][j+1]) solver.addAClause(white[i+1][j+1],path[i+1][j+1][i][j]);
                        else solver.addAClause(path[i+1][j+1][i][j]);
                    }
                    if(i>0 && i<Rows-1 && j>0 && j<Columns-1 && i+1>0 && i+1<Rows-1 && j+1>0 && j+1<Columns-1)
                    {
                        if(notFix[i][j] && notFix[i+1][j+1]) solver.addAClause(white[i][j],white[i+1][j+1],path[i][j][i+1][j+1],path[i+1][j+1][i][j]);
                        else if(notFix[i][j]) solver.addAClause(white[i][j],path[i][j][i+1][j+1],path[i+1][j+1][i][j]);
                        else if(notFix[i+1][j+1]) solver.addAClause(white[i+1][j+1],path[i][j][i+1][j+1],path[i+1][j+1][i][j]);
                        else solver.addAClause(path[i][j][i+1][j+1],path[i+1][j+1][i][j]);
                    }
                }
                
                if(InMatrix(i+1,j-1) && zones[i+1][j-1] != 0){
                    if(i==0 || i==Rows-1 || j==0 || j==Columns-1){
                        if(notFix[i][j] && notFix[i+1][j-1]) solver.addAClause(white[i][j],white[i+1][j-1],path[i][j][i+1][j-1]);
                        else if(notFix[i][j]) solver.addAClause(white[i][j],path[i][j][i+1][j-1]);
                        else if(notFix[i+1][j-1]) solver.addAClause(white[i+1][j-1],path[i][j][i+1][j-1]);
                        else solver.addAClause(path[i][j][i+1][j-1]);
                    }
                    if(i+1==0 || i+1==Rows-1 || j-1==0 || j-1==Columns-1){
                        if(notFix[i][j] && notFix[i+1][j-1]) solver.addAClause(white[i][j],white[i+1][j-1],path[i+1][j-1][i][j]);
                        else if(notFix[i][j]) solver.addAClause(white[i][j],path[i+1][j-1][i][j]);
                        else if(notFix[i+1][j-1]) solver.addAClause(white[i+1][j-1],path[i+1][j-1][i][j]);
                        else solver.addAClause(path[i+1][j-1][i][j]);
                    }
                    if(i>0 && i<Rows-1 && j>0 && j<Columns-1 && i+1>0 && i+1<Rows-1 && j-1>0 && j-1<Columns-1){
                        if(notFix[i][j] && notFix[i+1][j-1]) solver.addAClause(white[i][j],white[i+1][j-1],path[i][j][i+1][j-1],path[i+1][j-1][i][j]);
                        else if(notFix[i][j]) solver.addAClause(white[i][j],path[i][j][i+1][j-1],path[i+1][j-1][i][j]);
                        else if(notFix[i+1][j-1]) solver.addAClause(white[i+1][j-1],path[i][j][i+1][j-1],path[i+1][j-1][i][j]);
                        else solver.addAClause(path[i][j][i+1][j-1],path[i+1][j-1][i][j]);
                    }
                }
            }
        
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)if(zones[i][j] != 0){
                if(InMatrix(i+1,j+1) && InMatrix(i+1,j-1) && zones[i+1][j+1] != 0 && zones[i+1][j-1] != 0)
                    solver.addAClause(-path[i+1][j+1][i][j],-path[i+1][j-1][i][j]);
                if(InMatrix(i+1,j+1) && InMatrix(i-1,j+1) && zones[i+1][j+1] != 0 && zones[i-1][j+1] != 0)
                    solver.addAClause(-path[i+1][j+1][i][j],-path[i-1][j+1][i][j]);
                if(InMatrix(i+1,j+1) && InMatrix(i-1,j-1) && zones[i+1][j+1] != 0 && zones[i-1][j-1] != 0)
                    solver.addAClause(-path[i+1][j+1][i][j],-path[i-1][j-1][i][j]);
                if(InMatrix(i+1,j-1) && InMatrix(i-1,j-1) && zones[i+1][j-1] != 0 && zones[i-1][j-1] != 0)
                    solver.addAClause(-path[i+1][j-1][i][j],-path[i-1][j-1][i][j]);
                if(InMatrix(i+1,j-1) && InMatrix(i-1,j+1) && zones[i+1][j-1] != 0 && zones[i-1][j+1] != 0)
                    solver.addAClause(-path[i+1][j-1][i][j],-path[i-1][j+1][i][j]);
                if(InMatrix(i-1,j+1) && InMatrix(i-1,j-1) && zones[i-1][j+1] != 0 && zones[i-1][j-1] != 0)
                    solver.addAClause(-path[i-1][j+1][i][j],-path[i-1][j-1][i][j]);
            }
        
        //Path(x,y,a,b) && Path(a,b,a+1,b+1)=> Path(x,y,a+1,b+1) && 
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                if (zones[i][j] != 0)
                    for(k=0;k<Rows;k++)
                        for(h=0;h<Columns;h++)
                            if(Diff(i,j,k,h) && zones[k][h] == zones[i][j]){  
                                if(InMatrix(k+1,h+1) && zones[k+1][h+1] == zones[i][j])
                                    solver.addAClause(-path[i][j][k][h],-path[k][h][k+1][h+1],path[i][j][k+1][h+1]);
                                if(InMatrix(k+1,h-1) && zones[k+1][h-1] == zones[i][j])
                                    solver.addAClause(-path[i][j][k][h],-path[k][h][k+1][h-1],path[i][j][k+1][h-1]);
                                if(InMatrix(k-1,h+1) && zones[k-1][h+1] == zones[i][j])
                                    solver.addAClause(-path[i][j][k][h],-path[k][h][k-1][h+1],path[i][j][k-1][h+1]);
                                if(InMatrix(k-1,h-1) && zones[k-1][h-1] == zones[i][j])
                                    solver.addAClause(-path[i][j][k][h],-path[k][h][k-1][h-1],path[i][j][k-1][h-1]);
                            }
    }
   
    @Override
    protected boolean InsertAClause(){
        NumberOfClauses++;
        int i,j;
        int len = 0;
        for (i=0;i<Rows;i++)
            for (j=0;j<Columns;j++)
                if(notFix[i][j]) len++;
        int[] arr = new int[len];
        len = 0;
        for (i=0;i<Rows;i++)
            for (j=0;j<Columns;j++)
                if(notFix[i][j]){
                    if(result[i][j]) arr[len++] = -1*white[i][j];
                    else arr[len++] = white[i][j];
                }
        return solver.addAClauseToMore(NumberOfVars, arr);
    }
    
    @Override
    protected boolean decode(int[] arr){
        int len = arr.length;
        int d,i,j,k;
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                result[i][j] = flag[i][j] != 1;
        for (d = 0; d < len; d++){
            k = arr[d];
            if(k==0 || k> nrOfCellNotFix || k<-1*nrOfCellNotFix) break;
            for(i = 0; i < Rows; i++)
                for(j = 0; j < Columns; j++)
                    if(white[i][j] == -k) {
                        result[i][j] = false;
                        break;
                    }
        }
        return true;
    }
    
    @Override
    public int Solve(){
        if(!sWatch.Start()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        DoWithPatternEasy();
        DoWithPatternHard();
        if(!sWatch.Stop()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        timeForSearch = sWatch.getNanoTime();
        if(!sWatch.Start()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        Encode();
        if(!sWatch.Stop()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        timer = sWatch.getNanoTime();
        return SatSolverRun();
    }
    
    @Override
    protected void setSatSolverRt(ISatSolver solver){
        SatSolverRt =  "<html>Count Variables: " + NumberOfVars 
                     + "<br>Count Clauses: " + NumberOfClauses 
                     + "<br><br>Number of Variables: " + solver.getnVar()
                     + "<br>Number of Constraints: " + solver.getnConstraints()
                     + "<br><br>Number of cell not fix: " + nrOfCellNotFix
                     + "<br><br>File Cnf size: " + String.format("%.3f",new File(Config.PATH_FILE_CNF).length()*1.0/1024) + " Kb"
                     + "<br><br>Time search pattern : " + String.format("%.9f",timeForSearch*0.000000001) + " s"
                     + "<br>Time create file Cnf : " + String.format("%.9f",timer*0.000000001) + " s"
                     + "<br>Time SatSolver run : " + String.format("%.9f",solver.getTimeNs()*0.000000001) + " s"
                     + "<br>Time all ....................: " + String.format("%.9f",(solver.getTimeNs()+timer+timeForSearch)*0.000000001) + " s"
                     + "<html>";
        timeCounter = (solver.getTimeNs()+timer+timeForSearch)*0.000000001;
    }
}
