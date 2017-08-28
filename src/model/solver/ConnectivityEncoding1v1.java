package model.solver;

/**
 * 12/10/2015
 * Connectivity Encoding use Zones
 * @author DoDuy
 */
public final class ConnectivityEncoding1v1 extends ASolverSATE{
    private int maxVarInBorad;
    private boolean[][] notFix;
    private int[][] zones;
    private int[][][][] path;
    public ConnectivityEncoding1v1(int n, int m, int b[][]){
        super(n,m,b);
    }
    
    @Override
    protected void encodeVar(){
        int i,j,k,h;
        notFix = new boolean[Rows][Columns];
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns; j++)
                notFix[i][j] = false;
        for(i = 0; i < Rows; i++)                       
            for(j = 0; j < Columns-1; j++)            
                for(k = j+1; k < Columns; k++)          
                    if(valueOfCell[i][j] == valueOfCell[i][k]){ 
                        notFix[i][j] = true;
                        notFix[i][k] = true;
                    }

        for(j = 0; j < Columns; j++)
            for(i = 0; i < Rows-1; i++)
                for(k = i+1; k < Rows; k++)
                    if(valueOfCell[i][j] == valueOfCell[k][j]){
                        notFix[i][j] = true;
                        notFix[k][j] = true;
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
        maxVarInBorad = NumberOfVars;
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
        if(i+1 < Rows && j+1 < Columns && notFix[i+1][j+1] && zones[i+1][j+1] == 0) findZone(i+1,j+1,zoneNumber);
        if(i-1 >= 0 && j+1 < Columns && notFix[i-1][j+1] && zones[i-1][j+1] == 0) findZone(i-1,j+1,zoneNumber);
        if(i+1 < Rows && j-1 >=0 && notFix[i+1][j-1] && zones[i+1][j-1] == 0) findZone(i+1,j-1,zoneNumber);
        if(i-1 >= 0 && j-1 >= 0 && notFix[i-1][j-1] && zones[i-1][j-1] == 0) findZone(i-1,j-1,zoneNumber);
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
                    if(valueOfCell[i][j] == valueOfCell[i][k])
                        solver.addAClause(-1*white[i][j],-1*white[i][k]);
        
        for(j = 0; j < Columns; j++)
            for(i = 0; i < Rows-1; i++)
                for(k = i+1; k < Rows; k++)
                    if(valueOfCell[i][j] == valueOfCell[k][j])
                        solver.addAClause(-1*white[i][j],-1*white[k][j]);
    }
    
    //CNF Rule 2
    @Override
    protected void CnfRule2(){   
        for(int i = 0; i<Rows; i++)
            for(int j = 0; j<Columns; j++)
                if(notFix[i][j]){           
                    if(i-1>=0 && notFix[i-1][j])
                        solver.addAClause(white[i][j],white[i-1][j]);
                    if(j-1>=0 && notFix[i][j-1])
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
            for(j=0;j<Columns;j++) if(notFix[i][j]){
                solver.addAClause(-path[i][j][i][j]);
                
                if(InMatrix(i+1,j+1) && notFix[i+1][j+1]){
                    if(i==0 || i==Rows-1 || j==0 || j==Columns-1){
                        solver.addAClause(white[i][j],white[i+1][j+1],path[i][j][i+1][j+1]);
                    }else if(i+1==0 || i+1==Rows-1 || j+1==0 || j+1==Columns-1){
                        solver.addAClause(white[i][j],white[i+1][j+1],path[i+1][j+1][i][j]);
                    }else
                    {
                        solver.addAClause(white[i][j],white[i+1][j+1],path[i][j][i+1][j+1],path[i+1][j+1][i][j]);
                    }
                }
                
                if(InMatrix(i+1,j-1) && notFix[i+1][j-1]){
                    if(i==0 || i==Rows-1 || j==0 || j==Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j-1],path[i][j][i+1][j-1]);
                    if(i+1==0 || i+1==Rows-1 || j-1==0 || j-1==Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j-1],path[i+1][j-1][i][j]);
                    if(i>0 && i<Rows-1 && j>0 && j<Columns-1 && i+1>0 && i+1<Rows-1 && j-1>0 && j-1<Columns-1)
                        solver.addAClause(white[i][j],white[i+1][j-1],path[i][j][i+1][j-1],path[i+1][j-1][i][j]);
                }
            }
        
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)if(notFix[i][j]){
                if(InMatrix(i+1,j+1) && InMatrix(i+1,j-1) && notFix[i+1][j+1] && notFix[i+1][j-1])
                    solver.addAClause(-path[i+1][j+1][i][j],-path[i+1][j-1][i][j]);
                if(InMatrix(i+1,j+1) && InMatrix(i-1,j+1) && notFix[i+1][j+1] && notFix[i-1][j+1])
                    solver.addAClause(-path[i+1][j+1][i][j],-path[i-1][j+1][i][j]);
                if(InMatrix(i+1,j+1) && InMatrix(i-1,j-1) && notFix[i+1][j+1] && notFix[i-1][j-1])
                    solver.addAClause(-path[i+1][j+1][i][j],-path[i-1][j-1][i][j]);
                if(InMatrix(i+1,j-1) && InMatrix(i-1,j-1) && notFix[i+1][j-1] && notFix[i-1][j-1])
                    solver.addAClause(-path[i+1][j-1][i][j],-path[i-1][j-1][i][j]);
                if(InMatrix(i+1,j-1) && InMatrix(i-1,j+1) && notFix[i+1][j-1] && notFix[i-1][j+1])
                    solver.addAClause(-path[i+1][j-1][i][j],-path[i-1][j+1][i][j]);
                if(InMatrix(i-1,j+1) && InMatrix(i-1,j-1) && notFix[i-1][j+1] && notFix[i-1][j-1])
                    solver.addAClause(-path[i-1][j+1][i][j],-path[i-1][j-1][i][j]);
            }
        
        //Path(x,y,a,b) && Path(a,b,a+1,b+1)=> Path(x,y,a+1,b+1) && 
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                for(k=0;k<Rows;k++)
                    for(h=0;h<Columns;h++)
                        if(Diff(i,j,k,h) && zones[k][h] == zones[i][j] && zones[i][j] != 0){  
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
        for (d = 0; d < len; d++){
            k = arr[d];
            if(k==0 || k> maxVarInBorad || k<-1*maxVarInBorad) break;
            for(i = 0; i < Rows; i++)
                for(j = 0; j < Columns; j++)
                    if(white[i][j] == -k) {
                        result[i][j] = false;
                        break;
                    }
        }
        return true;
    }
}
