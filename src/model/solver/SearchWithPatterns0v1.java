package model.solver;

import model.Config;
import model.lib.StopWatch;
import model.solver.SWPOldVer.Anh.*;

/**
 * 4/11/2015
 * @author doduy
 */
public final class SearchWithPatterns0v1 extends ASolver{
    
    public SearchWithPatterns0v1(int n, int m, int[][] b) {
        super(n, m, b);
    }

    @Override
    public int Solve() {
        StopWatch sWatch = new StopWatch();
        if(!sWatch.Start()){
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        hitoriSolver hs = new hitoriSolver();
        hs.ReadData(Rows,valueOfCell);
        hs.Solve();
        this.result = hs.Print();
        if(!sWatch.Stop()){
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        timeCounter = sWatch.getNanoTime()*0.000000001;
        SatSolverRt = "Time all: " + timeCounter;
        return 1;
    }

    @Override
    public int SolveMore() {
        return 0;
    }
}
