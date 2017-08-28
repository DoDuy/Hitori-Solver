package model.solver;

import model.Config;
import model.lib.StopWatch;
import model.solver.SWPOldVer.Tiep.*;

/**
 * 4/11/2015
 * @author doduy
 */
public final class SearchWithPatterns0v0 extends ASolver{
    
    public SearchWithPatterns0v0(int n, int m, int[][] b) {
        super(n, m, b);
    }

    @Override
    public int Solve() {
        StopWatch sWatch = new StopWatch();
        if(!sWatch.Start()){
            SatSolverRt = Config.STROUT_ERROR_STOPWATCH;
            return -1;
        }
        
        Hitori hitori = new Hitori(Rows, valueOfCell);
        Multiple muti = new Multiple();
        StandardCyclePattern standard = new StandardCyclePattern();

        Parttent6 parttent = new Parttent6(hitori);
        Parttent1 parttent1 = new Parttent1(hitori);
        Parttent2 parttent2 = new Parttent2(hitori);
        Pattern3 pt1 = new Pattern3(hitori);
        Pattern4 pt = new Pattern4(hitori);
        muti.exe(hitori);
        //parttent1.exeParttent();
        //parttent2.exeParttent();
                //parttent.exeParttent6();
        //parttent.exeParttent7();
        //parttent.exeParttent8();
        backtracking backtrack = new backtracking(muti.muti, hitori);
        backtrack.exe1();
        hitori = (Hitori) ObjectCopier.copy(backtrack.hitori);
        
        for(int i=0; i<Rows; i++)
            for(int j=0; j<Columns; j++)
                this.result[i][j] = !hitori.hitori[i][j].paint;
        
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
