package model.solver;

import java.io.*;
import model.Config;
import model.SatSolver.ISatSolver;

/**
 * Connectivity Encoding plus Search with Pattern before Write Clauses (test speed of this)
 * @author DoDuy
 */
public final class ConnectivityEncoding2v0 extends ASolverSATE{
    private final int flag[][];
    private final boolean isSingle[][];
    protected final boolean isAblePaint[][];
    
    private long timeForSearch;

    public ConnectivityEncoding2v0(int n, int m, int b[][]){
        super(n,m,b);
        flag = new int[Rows][Columns];
        isSingle = new boolean[Rows][Columns];
        int j;
        isAblePaint = new boolean[Rows][Columns];
        for(int i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
            {
                flag[i][j] = -1;
                isSingle[i][j] = true; 
                isAblePaint[i][j] = false;
            }
    }
    
    private boolean InMatrix(int x,int y){
        return x>=0 && x<Rows && y>=0 && y<Columns;
    }
    
    private boolean Diff(int x,int y,int a,int b){
        return x!=a || y!=b;
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
                if(InMatrix(i+1,j) && valueOfCell[i][j] == valueOfCell[i+1][j])
                    for(k = 0;k<Rows;k++) 
                        if( k!= i && k!= i+1 && valueOfCell[k][j] == valueOfCell[i][j])
                            flag[k][j]=1;
                if(InMatrix(i,j+1) && valueOfCell[i][j] == valueOfCell[i][j+1])
                    for(k = 0;k<Columns;k++) 
                        if( k!= j && k!= j+1 && valueOfCell[i][k] == valueOfCell[i][j])
                            flag[i][k]=1;
            }
        
        //Pattern 3
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
                if((InMatrix(i+1,j) && InMatrix(i-1,j) && valueOfCell[i+1][j] == valueOfCell[i-1][j])
                || (InMatrix(i,j+1) && InMatrix(i,j-1) && valueOfCell[i][j-1] == valueOfCell[i][j+1]))
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
        //Pattern 2
        while(change){
            change = false;
            for(i = 0;i < Rows ; i++)
                for(j = 0;j < Columns ; j++)
                    if(flag[i][j]==1){
                        if(InMatrix(i-1,j) && flag[i-1][j]!=0) {flag[i-1][j]=0;change = true;}
                        if(InMatrix(i+1,j) && flag[i+1][j]!=0) {flag[i+1][j]=0;change = true;}
                        if(InMatrix(i,j-1) && flag[i][j-1]!=0) {flag[i][j-1]=0;change = true;}
                        if(InMatrix(i,j+1) && flag[i][j+1]!=0) {flag[i][j+1]=0;change = true;}
                    }
            //Pattern 3
            for(i = 0;i < Rows ; i++)
                for(j = 0;j < Columns ; j++)
                    if(flag[i][j] == 0 && InMatrix(i-1,j) && InMatrix(i+1,j) && InMatrix(i,j-1) && InMatrix(i,j+1))
                    {
                        if(flag[i-1][j] == 1 && flag[i+1][j] == 1){
                           if(flag[i][j-1] == 1 && flag[i][j+1] != 0){flag[i][j+1] = 0;change = true;}
                           if(flag[i][j+1] == 1 && flag[i][j-1] != 0){flag[i][j-1] = 0;change = true;}
                        }
                        if(flag[i][j-1] == 1 && flag[i][j+1] == 1){
                            if(flag[i-1][j] == 1 && flag[i+1][j] != 0){flag[i+1][j] = 0;change = true;}
                            if(flag[i+1][j] == 1 && flag[i-1][j] != 0){flag[i-1][j] = 0;change = true;}
                        }
                    }
            //Pattern 4
            for(i = 0;i < Rows ; i++)
                for(j = 0;j < Columns ; j++)
                    if(flag[i][j]==0){
                        for(k=0;k<Rows;k++)
                            if(i!=k && valueOfCell[i][j]==valueOfCell[k][j] && flag[k][j] != 1)
                            {
                                flag[k][j] = 1;
                                change = true;
                            }
                        for(k=0;k<Columns;k++)
                            if(j!=k && valueOfCell[i][j]==valueOfCell[i][k] && flag[i][k] != 1)
                            {
                                flag[i][k] = 1;
                                change = true;
                            }
                    }
            //Pattern 1
            for(i = 0; i < Rows; i++)                       
                for(j = 0; j < Columns-1; j++)            
                    for(k = j+1; k < Columns; k++)          
                        if(valueOfCell[i][j] == valueOfCell[i][k] && flag[i][j]!=1 && flag[i][k]!=1){ 
                            isSingle[i][j] = false;
                            isSingle[i][k] = false;
                        }

            for(j = 0; j < Columns; j++)
                for(i = 0; i < Rows-1; i++)
                    for(k = i+1; k < Rows; k++)
                        if(valueOfCell[i][j] == valueOfCell[k][j] && flag[i][j]!=1 && flag[k][j]!=1){
                            isSingle[i][j] = false;
                            isSingle[k][j] = false;
                        }

            for(i = 0; i < Rows; i++)
                for(j = 0; j<Columns; j++)
                    if(isSingle[i][j] && flag[i][j]==-1) 
                    {
                        flag[i][j] = 0;
                        change = true;
                    }
        }
    }
    
    //CNF Rule 1
    @Override
    protected void CnfRule1(){
        int i,j,k;
        
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns; j++)
            {
                if(flag[i][j] == 0)
                    solver.addAClause(white[i][j]);
                if(flag[i][j] == 1)
                    solver.addAClause(-1*white[i][j]);
            }
        
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns-1; j++)              
                for(k = j+1; k < Columns; k++)          
                    if(valueOfCell[i][j] == valueOfCell[i][k]){ 
                    //tren mot hang khong the co 2 valueOfCell cung mot gia tri
                        solver.addAClause(-1*white[i][j],-1*white[i][k]);
                        isAblePaint[i][j] = true;
                        isAblePaint[i][k] = true;
                    }
        
        for(j = 0; j < Columns; j++)
            for(i = 0; i < Rows-1; i++)
                for(k = i+1; k < Rows; k++)
                    if(valueOfCell[i][j] == valueOfCell[k][j]){
                    //Mot cot khong the co 2 o cung gia tri
                        solver.addAClause(-1*white[i][j],-1*white[k][j]);
                        isAblePaint[i][j] = true;
                        isAblePaint[k][j] = true;
                    }
        
        //Tranh truong hop nhieu O khong nhat thiet phai xoa ma van xoa
        for(i = 0; i < Rows; i++)
            for(j = 0; j<Columns; j++)
                if(!isAblePaint[i][j])
                    solver.addAClause(white[i][j]);
    }
    
    //CNF Rule 2
    @Override
    protected void CnfRule2(){
        for(int i = 0; i<Rows; i++)
            for(int j = 0; j<Columns; j++)
                if(isAblePaint[i][j]){           
                    if((i-1>=0)&&isAblePaint[i-1][j])
                        solver.addAClause(white[i][j],white[i-1][j]);
                    if((j-1>=0)&&isAblePaint[i][j-1])
                        solver.addAClause(white[i][j],white[i][j-1]);
                }
    }
    
    private int[][][][] SetIndexOfVar(){
        int[][][][] IndexOfVar = new int[Rows][Columns][Rows][Columns];
        int i,j,k,h;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                for(k=0;k<Rows;k++)
                    for(h=0;h<Columns;h++){
                        IndexOfVar[i][j][k][h] = 0;
                    }
        int count = Rows*Columns;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                for(k=0;k<Rows;k++)
                    for(h=0;h<Columns;h++)
                        if(isAblePaint[i][j] && isAblePaint[k][h] && IndexOfVar[i][j][k][h] == 0){
                            count++;
                            IndexOfVar[i][j][k][h] = count;
                        }
        NumberOfVars = count;
        
        return IndexOfVar;
    }
    
    //Rule 3
    @Override
    protected void CnfRule3(){
        int[][][][] IndexOfVar = SetIndexOfVar();
        int i,j,k,h;
        //-Path(x,y,x,y);
        //-white(x,y) && -white(x+1,y+1) <=> Path(x,y,x+1,y+1) || Path(x+1,y+1,x,y)
        //-white(x,y) && -white(x+1,y-1) <=> Path(x,y,x+1,y-1) || Path(x+1,y-1,x,y)
        //(x,y) Bien thi di vao trong
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++) if(isAblePaint[i][j]){
                solver.addAClause(-IndexOfVar[i][j][i][j]);
                
                if(InMatrix(i+1,j+1) && isAblePaint[i+1][j+1]){
                    if(i==0 || i==Rows-1 || j==0 || j==Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j+1],IndexOfVar[i][j][i+1][j+1]);
                    if(i+1==0 || i+1==Rows-1 || j+1==0 || j+1==Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j+1],IndexOfVar[i+1][j+1][i][j]);
                    if(i>0 && i<Rows-1 && j>0 && j<Columns-1 && i+1>0 && i+1<Rows-1 && j+1>0 && j+1<Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j+1],IndexOfVar[i][j][i+1][j+1],IndexOfVar[i+1][j+1][i][j]);
                }
                
                if(InMatrix(i+1,j-1) && isAblePaint[i+1][j-1]){
                    if(i==0 || i==Rows-1 || j==0 || j==Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j-1],IndexOfVar[i][j][i+1][j-1]);
                    if(i+1==0 || i+1==Rows-1 || j-1==0 || j-1==Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j-1],IndexOfVar[i+1][j-1][i][j]);
                    if(i>0 && i<Rows-1 && j>0 && j<Columns-1 && i+1>0 && i+1<Rows-1 && j-1>0 && j-1<Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j-1],IndexOfVar[i][j][i+1][j-1],IndexOfVar[i+1][j-1][i][j]);
                }
            }
        
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)if(isAblePaint[i][j]){
                if(InMatrix(i+1,j+1) && InMatrix(i+1,j-1) && isAblePaint[i+1][j+1] && isAblePaint[i+1][j-1])
                    solver.addAClause(-IndexOfVar[i+1][j+1][i][j],-IndexOfVar[i+1][j-1][i][j]);
                if(InMatrix(i+1,j+1) && InMatrix(i-1,j+1) && isAblePaint[i+1][j+1] && isAblePaint[i-1][j+1])
                    solver.addAClause(-IndexOfVar[i+1][j+1][i][j],-IndexOfVar[i-1][j+1][i][j]);
                if(InMatrix(i+1,j+1) && InMatrix(i-1,j-1) && isAblePaint[i+1][j+1] && isAblePaint[i-1][j-1])
                    solver.addAClause(-IndexOfVar[i+1][j+1][i][j],-IndexOfVar[i-1][j-1][i][j]);
                if(InMatrix(i+1,j-1) && InMatrix(i-1,j-1) && isAblePaint[i+1][j-1] && isAblePaint[i-1][j-1])
                    solver.addAClause(-IndexOfVar[i+1][j-1][i][j],-IndexOfVar[i-1][j-1][i][j]);
                if(InMatrix(i+1,j-1) && InMatrix(i-1,j+1) && isAblePaint[i+1][j-1] && isAblePaint[i-1][j+1])
                    solver.addAClause(-IndexOfVar[i+1][j-1][i][j],-IndexOfVar[i-1][j+1][i][j]);
                if(InMatrix(i-1,j+1) && InMatrix(i-1,j-1) && isAblePaint[i-1][j+1] && isAblePaint[i-1][j-1])
                    solver.addAClause(-IndexOfVar[i-1][j+1][i][j],-IndexOfVar[i-1][j-1][i][j]);
            }
        
        //Path(x,y,a,b) => -Path(a,b,x,y) 
        //Path(x,y,a,b) && Path(a,b,a+1,b+1)=> Path(x,y,a+1,b+1) && 
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                for(k=0;k<Rows;k++)
                    for(h=0;h<Columns;h++)
                        if(Diff(i,j,k,h) && isAblePaint[i][j] && isAblePaint[k][h]){  
                            if(InMatrix(k+1,h+1) && isAblePaint[k+1][h+1])
                                solver.addAClause(-IndexOfVar[i][j][k][h],-IndexOfVar[k][h][k+1][h+1],IndexOfVar[i][j][k+1][h+1]);
                            if(InMatrix(k+1,h-1) && isAblePaint[k+1][h-1])
                                solver.addAClause(-IndexOfVar[i][j][k][h],-IndexOfVar[k][h][k+1][h-1],IndexOfVar[i][j][k+1][h-1]);
                            if(InMatrix(k-1,h+1) && isAblePaint[k-1][h+1])
                                solver.addAClause(-IndexOfVar[i][j][k][h],-IndexOfVar[k][h][k-1][h+1],IndexOfVar[i][j][k-1][h+1]);
                            if(InMatrix(k-1,h-1) && isAblePaint[k-1][h-1])
                                solver.addAClause(-IndexOfVar[i][j][k][h],-IndexOfVar[k][h][k-1][h-1],IndexOfVar[i][j][k-1][h-1]);
                        }
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
                     + "<br><br>File Cnf size: " + String.format("%.3f",new File(Config.PATH_FILE_CNF).length()*1.0/1024) + " Kb"
                     + "<br><br>Time search pattern : " + String.format("%.9f",timeForSearch*0.000000001) + " s"
                     + "<br>Time create file Cnf : " + String.format("%.9f",timer*0.000000001) + " s"
                     + "<br>Time SatSolver run : " + String.format("%.9f",solver.getTimeNs()*0.000000001) + " s"
                     + "<br>Time all ....................: " + String.format("%.9f",(solver.getTimeNs()+timer+timeForSearch)*0.000000001) + " s"
                     + "<html>";
        timeCounter = (solver.getTimeNs()+timer+timeForSearch)*0.000000001;
    }
}
