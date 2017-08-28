package model.check;

/**
 *
 * @author DoDuy
 */
public class QuickChecker {
    private int[][] data;
    private int[][] paint;
    private int Rows, Columns;
    
    /* luat 1*/
    private boolean CheckRule1() {
        int i, j,k;

        /* In rows */
        for (k = 0; k < Rows; k++) {
            for (i = 0; i < Columns; i++) {
                for (j = i + 1; j < Columns; j++) {
                    if (data[k][i] == data[k][j] && paint[k][i] == 0 && paint[k][j] == 0) 
                        return false;
                }
            }
        }
        /*In colums*/
        for (k = 0; k < Columns; k++) {
            for (i = 0; i < Rows; i++) {
                for (j = i + 1; j < Rows; j++) {
                    if (data[i][k] == data[j][k] && paint[i][k] == 0 && paint[j][k] == 0)
                        return false;
                }
            }
        }
        return true;
    }
    
    /* luat 2*/
    private boolean CheckRule2() {
        int i,j;
        for (i = 0;i < Rows; i++) {
            for (j = 0; j < Columns; j++) {
                if (i+1<Rows && paint[i][j] == 1 && paint[i+1][j] == 1)
                    return false;
                if (j+1<Columns && paint[i][j] == 1 && paint[i][j + 1] == 1)
                    return false;
            }
        }
        return true;
    }
    
    private void Find(int x,int y,boolean arr[][]){
        if(paint[x][y] == 1 || arr[x][y] == true) return;
        arr[x][y] = true;
        if(x+1<Rows) Find(x+1,y,arr);
        if(x-1>=0) Find(x-1,y,arr);
        if(y+1<Columns) Find(x,y+1,arr);
        if(y-1>=0) Find(x,y-1,arr);
    }
    
    /* luat 3*/
    private boolean CheckRule3() {
        final boolean[][] arr = new boolean[Rows][Columns];
        int i,j;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                arr[i][j] = false;
        if(paint[0][0] != 1) Find(0,0,arr);
            else Find(0,1,arr);
        boolean bo = true;
        for(i = 0; i < Rows; i++)
            for(j = 0; j < Columns; j++)
                if((paint[i][j] == 1 && arr[i][j] == true)
                || (paint[i][j] != 1 && arr[i][j] == false)) 
                    bo = false;
        return bo;
    }
    
    public boolean CheckRule(int[][] data, int[][] paint, int x, int y){
        this.data = data;
        this.paint = paint;
        Rows = x;
        Columns = y;
        if(!CheckRule1()) return false;
        if(!CheckRule2()) return false;
        return CheckRule3();
    }
}
