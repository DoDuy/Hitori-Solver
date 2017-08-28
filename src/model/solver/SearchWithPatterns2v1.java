package model.solver;

import model.Config;
import model.check.QuickChecker;
import model.lib.StopWatch;

/**
 * Search with pattern v2
 * @author DoDuy
 */
public final class SearchWithPatterns2v1 extends ASolver{//Test Search Pattern
    private final int flag[][];
    private final QuickChecker check;
    private final int[][] data;
    private int notSet;
    private int[] x;
    private int[] y;
    
    private int nbOfWhite;
    private int nbOfBlack;
    
    private long time1;
    private long time2;
    
    private final StopWatch sWatch;
    
    public SearchWithPatterns2v1(int n, int m, int b[][]){
        super(n,m,b);
        flag = new int[Rows][Columns];
        int j;
        for(int i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
            {
                flag[i][j] = -1;
            }
        check = new QuickChecker();
        data = new int[Rows][Columns];
        nbOfWhite = 0;
        nbOfBlack = 0;
        sWatch = new StopWatch();
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
    
    private int find1stvalueOfCellnotSet(){
        for(int i = 0; i < notSet; i++)
            if(flag[x[i]][y[i]] == -1) return i;
        return -1;
    }
    
    private boolean Find1stSolution(){
        if(!check.CheckRule(data, flag, Rows, Columns)) return false;
        int inCircle = find1stvalueOfCellnotSet();
        if(inCircle == -1) return true;
        
        int[] tmp = new int[notSet];
        int i;
        for(i = 0; i<notSet; i++)
            tmp[i] = flag[x[i]][y[i]];
        flag[x[inCircle]][y[inCircle]] = 0;
        DoWithPatternHard();
        if(Find1stSolution()) return true;
        for(i = 0; i<notSet; i++)
            flag[x[i]][y[i]] = tmp[i];
        
        flag[x[inCircle]][y[inCircle]] = 1;
        DoWithPatternHard();
        if(Find1stSolution()) return true;
        for(i = 0; i<notSet; i++)
            flag[x[i]][y[i]] = tmp[i];
        
        return false;
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
        time1 = sWatch.getNanoTime();
        int i,j,k;
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++){
                if(flag[i][j] == 0) nbOfWhite++;
                if(flag[i][j] == 1) nbOfBlack++;
            }
        notSet = Rows*Columns - nbOfWhite - nbOfBlack;
        x = new int[notSet];
        y = new int[notSet];
        k = 0;
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++)
                if(flag[i][j] == -1){
                    x[k] = i;
                    y[k] = j;
                    k++;
                }
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns; j++)
                data[i][j] = valueOfCell[i][j];
        if(!sWatch.Start()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        if(!Find1stSolution()) return 0;
        if(!sWatch.Stop()) {
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        time2 = sWatch.getNanoTime();
        decode();
        return 1;
    } 
    
    private void decode(){
        SatSolverRt = "<html>"
                +"all cell: "+Rows*Columns
                +"<br><br>number of white: "+nbOfWhite
                +"<br>number of black: "+nbOfBlack
                +"<br><br>cells not set: "+notSet
                +"<br><br>Time search: "+String.format("%.9f",time1*0.000000001) + " s"
                +"<br>Time BnSt: "+String.format("%.9f",time2*0.000000001) + " s"
                +"<br><br>Time all: "+String.format("%.9f",(time1+time2)*0.000000001) + " s"
                +"<html>";
        int i, j;
        for(i = 0;i < Rows ; i++)
            for(j = 0;j < Columns ; j++){
                if(flag[i][j] == 0) result[i][j] = true;
                if(flag[i][j] == 1) result[i][j] = false;
            }
        timeCounter = (time1+time2)*0.000000001;
    }
    
    @Override
    public int SolveMore(){
        return 0;
    }
}