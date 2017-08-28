package model.SatSolver;

/**
 * 14/10/2015
 * @author root
 */
public interface ISatSolver {
    public void addAClause(int... clause);
    public boolean finishAddAClause();
    public boolean addAClauseToMore(int v,int... clause);
    public boolean addVarsAndClauses(int v);
    public boolean Solve();
    public String getMgError();
    public boolean getIsSat();
    public long getTimeNs();
    public int getnConstraints();
    public int getnVar();
    public int getNumberOfClauses();
    public int[] getResult();
}
