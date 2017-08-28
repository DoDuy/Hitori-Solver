package model.solver.SWPOldVer.Tiep;

import java.io.Serializable;

/**
 *
 * @author tiept_000
 */
public class CellHitori implements Serializable{
    public int value;
    public boolean paint;
    public boolean undefined;
    public int multiple;
    public CellHitori(){
        value = 0;
        paint = false;
        undefined = true;
        multiple = 0;
    }
}
