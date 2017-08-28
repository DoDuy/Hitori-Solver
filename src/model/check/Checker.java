package model.check;

/**
 * 
 * @author DoDuy
 */
public class Checker {
    private int[][] data;
    private int[][] paint;
    private boolean[][] result;
    private int Rows, Columns;
    
    public boolean[][] getResult(){
        return result;
    }
    
    /* luat 1*/
    private boolean CheckRule1(boolean arr[][]) {
        int i, j,k;

        /* In rows */
        for (k = 0; k < Rows; k++) {
            for (i = 0; i < Columns; i++) {
                for (j = i + 1; j < Columns; j++) {
                    if (data[k][i] == data[k][j] && paint[k][i] != 1 && paint[k][j] != 1) {
                        arr[k][i] = true;
                        arr[k][j] = true;
                        return false;
                    }
                }
            }
        }
        /*In colums*/
        for (k = 0; k < Columns; k++) {
            for (i = 0; i < Rows; i++) {
                for (j = i + 1; j < Rows; j++) {
                    if (data[i][k] == data[j][k] && paint[i][k] != 1 && paint[j][k] != 1) {
                        arr[i][k] = true;
                        arr[j][k] = true;
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    /* luat 2*/
    private boolean CheckRule2(boolean[][] arr) {
        int i,j;
        /* Xet rows */
        for (i = 0;i < Rows; i++) {
            for (j = 0; j < Columns; j++) {
                if (i+1<Rows && paint[i][j] == 1 && paint[i+1][j] == 1) {
                    arr[i][j] = true;
                    arr[i+1][j] = true;
                    return false;

                }
                if (j+1<Columns && paint[i][j] == 1 && paint[i][j + 1] == 1) {
                    arr[i][j] = true;
                    arr[i][j+1] = true;
                    return false;

                }
            }
        }
        return true;
    }
    
    private void Find(int x,int y,boolean arr[][]){
        if(paint[x][y] == 1 || arr[x][y] == true) return;
        arr[x][y] = true;
        if(x+1>=0 && x+1<Rows) Find(x+1,y,arr);
        if(x-1>=0 && x-1<Rows) Find(x-1,y,arr);
        if(y+1>=0 && y+1<Columns) Find(x,y+1,arr);
        if(y-1>=0 && y-1<Columns) Find(x,y-1,arr);
    }
    
    /* luat 3*/
    private boolean CheckRule3(boolean[][] arr) {
        if(paint[0][0] != 1) Find(0,0,arr);
            else Find(0,1,arr);
        boolean bo = true;
        int i,j;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                if((paint[i][j]== 1 && arr[i][j] == true)
                || (paint[i][j]!= 1 && arr[i][j] == false)) 
                    bo = false;
        return bo;
    }
    
    public boolean CheckRule(int[][] data, int[][] paint, int x, int y){
        this.data = data;
        Rows = x;
        Columns = y;
        this.paint = paint;
        boolean[][] White = new boolean[Rows][Columns];
        int i,j;
        for(i=0;i<Rows;i++)
            for(j=0;j<Columns;j++)
                White[i][j] = false;
        
        if(CheckRule1(White) == false){
            result = White;
            return false;
        }
        if(CheckRule2(White) == false){
            result = White;
            return false;
        }
        if(CheckRule3(White) == false){
            result = White;
            return false;
        }
        return true;
    }
}
