package model.lib;

import model.SatSolver.*;

/**
 *
 * @author root
 */
public class Judge {
    public static final int SAT4J = 1;
    public static final int SAT4J_QUICKSOLVE = 2;
    public static final int SAT4J_INPROCESS = 3;
    public static final int MINISAT = 4;
    public static final int GLUEMINISAT = 5;
    public static final int LINGELING = 6;
    public ISatSolver getSatSolver(int s){
        switch(s){
            case SAT4J:
                return new Sat4jSolver();
            case SAT4J_QUICKSOLVE:
                return new Sat4jSolverQuickSolve();
            case SAT4J_INPROCESS:
                return new Sat4jSolverInProcess();
            case MINISAT:
                return new MinisatSolver();
            case GLUEMINISAT:
                return new GlueminisatSolver();
            case LINGELING:
                return new LingelingSolver();
            default:
                return null;
        }
    }
}
