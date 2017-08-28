package model.solver;

/**
 *
 * @author DoDuy
 */
public abstract class ASolver {
    protected final int Rows,Columns;
    protected int valueOfCell[][];
    protected boolean[][] result;
    protected String SatSolverRt;
    protected double timeCounter;
    
    protected ASolver(int n, int m, int b[][]){
        Rows = n;
        Columns = m;
        valueOfCell = b;
        result = new boolean[n][m];
        for(int i = 0; i < n; i++)
            for(int j = 0;j < m; j++)
                result[i][j] = true;
        SatSolverRt = "";
        timeCounter = -1;
    }
    
    public String getSatSolverRt(){
        return SatSolverRt;
    }
    
    public double getTime(){
        return timeCounter;
    }
        
    public boolean[][] getResult(){
        return result;
    }
    
    public abstract int Solve();
    public abstract int SolveMore();
}
