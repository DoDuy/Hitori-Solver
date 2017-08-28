package model.input;

/**
 * 
 * @author DoDuy
 */
import java.io.*;
import java.util.Scanner;
import model.Config;

public class input {
    private int mapIndex;
    private int mapMax;
    private int currentRows;
    private int currentCols;
    
    public input(){
        currentRows = 0;
        currentCols = 0;
    }
    
    public int getMapIndex(){
        return this.mapIndex;
    }
    
    public int getMapMax(){
        return this.mapMax;
    }
    
    public int[][] getinput(int Rows,int Columns) throws IOException{
        if(Rows!=currentRows || Columns!=currentCols){
            currentRows = Rows;
            currentCols = Columns;
            mapIndex = 0;
        }
        int[][] value = new int [Rows][Columns];
        String st = Config.PATH_MAP+"/"+Rows+"x"+Columns+".ma";
        Scanner scan = new Scanner(new File(st));
        int i,j;
        mapMax=scan.nextInt();
//        Random random = new Random();
//        mapIndex=random.nextInt(mapMax)+1;
        mapIndex = mapIndex%mapMax+1;
        String ranma = "["+mapIndex+"]";
        
        while(scan.hasNext())
            if(scan.next().startsWith(ranma)){        
                for (i=0;i<Rows;i++)
                    for (j=0;j<Columns;j++)
                        value[i][j] = scan.nextInt();
                break;
            }
        return value;
	}       
}