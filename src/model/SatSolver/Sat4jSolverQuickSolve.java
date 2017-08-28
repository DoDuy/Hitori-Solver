package model.SatSolver;

import model.Config;
import model.lib.StopWatch;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

/**
 * SatSolver SAT4J don't write file 'cnf'
 * @author DoDuy
 */
public class Sat4jSolverQuickSolve implements ISatSolver{
    private long timeoutset = 0;
    private String mgError = "";
    private boolean isSat = false;
    private int[] result = null;
    private long time = 0;
    private int nConstraints = 0;
    private int nVar = 0;
    private final StopWatch sWatch;
    private final ISolver solver;
    
    private int NumberOfClauses;
    public Sat4jSolverQuickSolve(){
        this.timeoutset = Config.TIMEOUT_SAT4J;
        sWatch = new StopWatch();
        solver = SolverFactory.newDefault();
        NumberOfClauses = 0;
    }
    
    @Override
    public void addAClause(int... clause) {
        try {
            solver.addClause(new VecInt(clause));
            NumberOfClauses++;
        } catch (ContradictionException ex) {
            mgError = "ContradictionException while add a clause!";
        }
    }
    
    @Override
    public boolean finishAddAClause(){
        return true;
    }
    
    @Override
    public boolean addAClauseToMore(int v,int... clause) {
        try {
            solver.addClause(new VecInt(clause));
            NumberOfClauses++;
        } catch (ContradictionException ex) {
            mgError = "ContradictionException while addAClauseToMore!";
            return false;
        }
        return true;
    }
    
    @Override
    public boolean addVarsAndClauses(int v){
//        solver.newVar(c);
//        solver.setExpectedNumberOfClauses(c);
        return true;
    }
    
    @Override
    public String getMgError(){
        return this.mgError;
    }
    
    @Override
    public boolean getIsSat(){
        return this.isSat;
    }
    
    @Override
    public long getTimeNs(){
        return this.time;
    }
    
    @Override
    public int getnConstraints(){
        return this.nConstraints;
    }
    
    @Override
    public int getnVar(){
        return this.nVar;
    }
    
    @Override
    public int[] getResult(){
        return this.result;
    }
    
    @Override
    public int getNumberOfClauses() {
        return NumberOfClauses;
    }
    
    @Override
    public boolean Solve(){
        if(!sWatch.Start()) {
            mgError = Config.STROUT_ERROR_STOPWATCH;
            return false;
        }
        solver.setTimeoutMs(this.timeoutset);
        try {
            IProblem problem = solver;
            if (problem.isSatisfiable()) {
                this.result = problem.model();
                this.isSat = true;
            } else {
                this.isSat = false;
            }
            this.nConstraints = solver.nConstraints();
            this.nVar = solver.nVars();
            if(!sWatch.Stop()) {
                mgError = Config.STROUT_ERROR_STOPWATCH;
                return false;
            }
            this.time = sWatch.getNanoTime();
            return true;
        } catch (TimeoutException e) {
            mgError = "Timeout, sorry!";
        }
        return false;
    }
}