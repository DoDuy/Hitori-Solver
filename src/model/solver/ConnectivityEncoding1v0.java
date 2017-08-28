package model.solver;

/**
 * Connectivity Encoding 
 * @author DoDuy
 */
public final class ConnectivityEncoding1v0 extends ASolverSATE{
    protected final boolean isAblePaint[][];
    public ConnectivityEncoding1v0(int n, int m, int b[][]){
        super(n,m,b);
        isAblePaint = new boolean[Rows][Columns];
        for(int i = 0; i < n; i++)
            for(int j = 0;j < m; j++)
                isAblePaint[i][j] = false;
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
                    solver.addAClause(-IndexOfVar[i-1][j+1][i][j], -IndexOfVar[i-1][j-1][i][j]);
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
}
