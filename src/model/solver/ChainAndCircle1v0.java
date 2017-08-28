package model.solver;

import java.util.*;

/**
 * Chain and Circle
 * @author DoDuy
 */
public final class ChainAndCircle1v0 extends ASolverSATE{
    protected final boolean isAblePaint[][];
    public ChainAndCircle1v0(int n, int m, int[][] b) {
        super(n, m, b);
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
    
    //Tim nhung vong trong bang
    private void FindCycle(int x,int y,ArrayList<Integer> cycle){
        if (    !(cycle.size()!=1 || y>(cycle.get(0)-1)%Columns)
            ||  !(x>=0 && x<Rows && y>=0 && y<Columns)
            ||  !isAblePaint[x][y]
            ||  white[x][y] < cycle.get(0)
            ||  cycle.indexOf(white[x][y]) >= 0
            ) return;
        
        
        int a,b,c=0;
        int k[] = new int[3];
        
        for(a = -1; a < 2; a=a+2)
            for(b = -1; b < 2; b=b+2)
                if(x+a>=0 && x+a<Rows && y+b>=0 && y+b<Columns && !cycle.get(cycle.size()-1).equals(white[x+a][y+b])){
                    k[c] = cycle.indexOf(white[x+a][y+b]);
                    c++;
                }
        
        for(int i = 0;i < c;i++)
            if(k[i] > 0) return;
        for(int i = 0;i < c;i++)
            if(k[i] == 0) {
                int[] ints = new int[cycle.size()+1];
                for(int j=0, len = cycle.size(); j < len; j++)
                   ints[j] = cycle.get(j);
                ints[cycle.size()] = white[x][y];
                solver.addAClause(ints);
                return;
            }
        
        
        //Tiep tuc tim voi cac o tiep theo
        cycle.add(white[x][y]);
        for(a = -1; a < 2; a=a+2)
            for(b = -1; b < 2; b=b+2)              
                FindCycle(x+a,y+b,cycle);
        cycle.remove(cycle.indexOf(white[x][y]));
    }
    
    private void FindChain(int x,int y,ArrayList<Integer> chain){
        if( !(x>=0 && x<Rows && y>=0 && y<Columns)
        ||  !isAblePaint[x][y]
        ||  chain.indexOf(white[x][y]) >= 0            
        ) return;
        
        int a,b;       
        for(a = -1; a < 2; a=a+2)
            for(b = -1; b < 2; b=b+2)
                if(x+a>=0 && x+a<Rows && y+b>=0 && y+b<Columns && !chain.get(chain.size()-1).equals(white[x+a][y+b]))
                    if(chain.indexOf(white[x+a][y+b])>=0) return;
        
        if((x==0 || y==0 || x==Rows-1 || y==Columns-1) &&  white[x][y] > chain.get(0)){
            int[] ints = new int[chain.size()+1];
            for(int i=0, len = chain.size(); i < len; i++)
               ints[i] = chain.get(i);
            ints[chain.size()] = white[x][y];
            solver.addAClause(ints);
            return;
        }
          
        chain.add(white[x][y]);
        for(a = -1; a < 2; a=a+2)
            for(b = -1; b < 2; b=b+2)
                FindChain(x+a,y+b,chain);
        chain.remove(chain.indexOf(white[x][y]));
    }
    
    private void FindCycleTmp(int x,int y,ArrayList<Integer> cycle){
        if(!isAblePaint[x][y]) return;
        int a,b;
        cycle.add(white[x][y]);
        for(a = -1; a < 2; a=a+2)
            for(b = -1; b < 2; b=b+2)                            
                FindCycle(x+a,y+b,cycle);
        cycle.remove(cycle.indexOf(white[x][y]));
    }
    
    private void FindChainTmp(int x,int y,ArrayList<Integer> chain){
        if(!isAblePaint[x][y]) return;
        int a,b;
        chain.add(white[x][y]);
        for(a = -1; a < 2; a=a+2)
            for(b = -1; b < 2; b=b+2)                            
                FindChain(x+a,y+b,chain);
        chain.remove(chain.indexOf(white[x][y]));
    }
    
    //Rule 3
    @Override
    protected void CnfRule3(){
        int i,j;
        ArrayList<Integer> cycle = new ArrayList<>();
        //bat dau tim cycle
        for(i = 0;i<Rows;i++)
            for(j = 0;j<Columns;j++)
                FindCycleTmp(i,j,cycle);
        
        //tim chain voi nhung O bat dau o bien cua bang
        for(i = 0; i< Rows; i++) {
            FindChainTmp(i,0,cycle);
            FindChainTmp(i,Columns-1,cycle);
        }
        for(j = 1; j< Columns-1; j++) {
            FindChainTmp(0,j,cycle);
            FindChainTmp(Rows-1,j,cycle);
        }
        NumberOfVars = Rows*Columns;
    }
}