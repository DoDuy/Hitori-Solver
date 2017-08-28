package model.solver;

/**
 * Connect from 1st Cell don't use variable k 
 * @author DoDuy
 */
public final class ConnecFrom1stCell2v0 extends ASolverSATE{
    protected final boolean isAblePaint[][];
    public ConnecFrom1stCell2v0(int n, int m, int b[][]){
        super(n,m,b);
        isAblePaint = new boolean[Rows][Columns];
        for(int i = 0; i < n; i++)
            for(int j = 0;j < m; j++)
                isAblePaint[i][j] = false;
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
    
    private int[][][][] SetPath(){
        final int[][][][] path = new int[Rows][Columns][Rows][Columns];
        int i,j,k,h;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                for(k=0;k<Rows;k++)
                    for(h=0;h<Columns;h++){
                        path[i][j][k][h] = Rows*Columns+1;
                    }
        int count = Rows*Columns+1;
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
            {
                if(i+1 < Rows){
                    count++;
                    path[i][j][i+1][j] = count;
                    count++;
                    path[i+1][j][i][j] = count;
                }
                if(j+1 < Columns){
                    count++;
                    path[i][j][i][j+1] = count;
                    count++;
                    path[i][j+1][i][j] = count;
                }
            }
        NumberOfVars = count;
        
        return path;
    }
    
    //Rule 3
    @Override
    protected void CnfRule3(){
        int i,j;
        int[][][][] path = SetPath();
        //While(a,b) && While(a,b+1) => -(path(a,b,a,b+1) && path(a,b+1,a,b))
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
            {
                if(i+1 < Rows)
                    solver.addAClause(-1*white[i][j],-1*white[i+1][j],-1*path[i][j][i+1][j],-1*path[i+1][j][i][j]);
                if(j+1 < Columns)
                    solver.addAClause(-1*white[i][j],-1*white[i][j+1],-1*path[i][j][i][j+1],-1*path[i][j+1][i][j]);
            }
        //while(a,b) => Path(a+1,b,a,b) || Path(a,b+1,a,b) || Path(a-1,b,a,b) || Path(a,b-1,a,b)
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                if(i != 0 || (j != 0 && j != 1))
                {
                    int len = 0;
                    if (i-1>=0) len++;
                    if (i+1<Rows) len++;
                    if (j-1>=0) len++;
                    if (j+1<Columns) len++;
                    int[] ints = new int[len+1];
                    len = 0;
                    ints[len++] = -1*white[i][j];
                    if(i+1 < Rows) ints[len++] = path[i+1][j][i][j];
                    if(i-1 >= 0) ints[len++] = path[i-1][j][i][j];
                    if(j+1 < Columns) ints[len++] = path[i][j+1][i][j];
                    if(j-1 >= 0) ints[len++] = path[i][j-1][i][j];
                    solver.addAClause(ints);
                }
        //-white(a,b) => -Path(a,b,a+1,b) && -Path(a,b,a+1,b) && -Path(a,b,a+1,b) && -Path(a,b,a+1,b)
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
            {
                if(i+1 < Rows)
                    solver.addAClause(white[i][j],-1*path[i][j][i+1][j]);
                if(i-1 >= 0)
                    solver.addAClause(white[i][j],-1*path[i][j][i-1][j]);
                if(j+1 < Columns)
                    solver.addAClause(white[i][j],-1*path[i][j][i][j+1]);
                if(j-1 >= 0)
                    solver.addAClause(white[i][j],-1*path[i][j][i][j-1]);
            }
    }
}