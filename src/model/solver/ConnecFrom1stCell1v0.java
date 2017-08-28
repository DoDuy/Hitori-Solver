package model.solver;

/**
 * Connect from 1st Cell
 * @author DoDuy
 */
public final class ConnecFrom1stCell1v0 extends ASolverSATE{
    protected final boolean isAblePaint[][];
    public ConnecFrom1stCell1v0(int n, int m, int b[][]){
        super(n,m,b);
        isAblePaint = new boolean[Rows][Columns];
        for(int i = 0; i < n; i++)
            for(int j = 0;j < m; j++)
                isAblePaint[i][j] = false;
    }
    
    private int Path(int k,int x,int y){
        return ((k+1)*Rows*Columns+white[x][y]);
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
    
    //Rule 3
    @Override
    protected void CnfRule3(){
        int i,j,k;
        // PATH(0,0,0) <-> Index(0,0)
        solver.addAClause(-Path(0,0,0),white[0][0]);
        solver.addAClause(Path(0,0,0),-white[0][0]);
        
        // No one else has path of length 0
        for (i=0;i<Rows;i++) for (j=0;j<Columns;j++) if (i!=0 || j!=0)
            solver.addAClause(-Path(0,i,j));
        
        // PATH(1,0,1) <-> Index(0,1)
        solver.addAClause(-Path(1,0,1),white[0][1]);
        solver.addAClause(Path(1,0,1),-white[0][1]);
        
        for (k=1;k<Rows*Columns-1;k++) for (i=0;i<Rows;i++) for(j=0;j<Columns;j++)
            if (!(k==1 && i==0 && j==1)){
                //PATH(k,i,j) <-> Index(i,j) & (PATH(k-1,i-1,j) v PATH(k-1,i+1,j) v PATH(k-1,i,j-1) v PATH(k-1,i,j+1)
                solver.addAClause(-Path(k,i,j),white[i][j]);
                int len = 0;
                if (i-1>=0) len++;
                if (i+1<Rows) len++;
                if (j-1>=0) len++;
                if (j+1<Columns) len++;
                int[] ints = new int[len+1];
                len = 0;
                ints[len++] = -Path(k,i,j);
                if (i-1>=0) ints[len++] = Path(k-1,i-1,j);
                if (i+1<Rows) ints[len++] = Path(k-1,i+1,j);
                if (j-1>=0) ints[len++] = Path(k-1,i,j-1);
                if (j+1<Columns) ints[len++] = Path(k-1,i,j+1);
                solver.addAClause(ints);
                
                if (i-1>=0 && i-1<Rows)
                    solver.addAClause(-white[i][j],-Path(k-1,i-1,j),Path(k,i,j));
                if (i+1>=0 && i+1<Rows)
                    solver.addAClause(-white[i][j],-Path(k-1,i+1,j),Path(k,i,j));
                if (j-1>=0 && j-1<Columns)
                    solver.addAClause(-white[i][j],-Path(k-1,i,j-1),Path(k,i,j));
                if (j+1>=0 && j+1<Columns)
                    solver.addAClause(-white[i][j],-Path(k-1,i,j+1),Path(k,i,j));
            }
        
        for (i=0;i<Rows;i++) for(j=0;j<Columns;j++) {
        // Index(i,j) -> exists k PATH(k,i,j)
            int[] ints = new int[Rows*Columns];
            ints[0] = -white[i][j];
            for (k=0;k<Rows*Columns-1;k++)
                ints[k+1] = Path(k,i,j);
            solver.addAClause(ints);
        }
        
        NumberOfVars = Rows*Columns+Rows*Columns*Rows*Columns;
    }
}