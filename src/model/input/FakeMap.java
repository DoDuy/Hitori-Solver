package model.input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Config;

/**
 * 2/11/2015
 * Create map in this class
 * @author doduy
 */
public class FakeMap {
	public int[][] matrix;
	public int rows;
        public int columns;
	public int total;
	Random rn;
        private final int frPer;
        private final int toPer;
	public FakeMap(int r, int c, int frPer, int toPer){
                rn = new Random();
		matrix = new int[r][c];
		rows = r;
                columns = c;
		for(int i=0; i<r; i++){
			for(int j=0; j<c; j++){
				matrix[i][j] = 0;
			}
		}
		this.frPer = frPer;
                this.toPer = toPer;
	}
	public int[][] createPainted(){
		total = rn.nextInt(((rows*columns)*(toPer - frPer))/100) + ((rows*columns)*frPer)/100;
		int[][] re = new int[rows][columns];
		Rulers p = new Rulers();
		for(int i=0; i<total; i++){
                    while(true){
//                        int[][] flag = new int[rows][columns];
//                        int cter = 0;
//                        if(cter >= rows*columns - i - 1) {
//                            i-=2;
//                            break;
//                        }
                        int x = rn.nextInt(rows-1);
                        int y = rn.nextInt(columns-1);
                        p.remove1();
                        if(re[x][y]==0 && p.checkRuler12(re, x, y)==true){//flag[x][y] != 0 && 
                            re[x][y] = -1;
                            if( p.nonLoop(x, y, re) == true && p.nonLoop1(-1, x*columns+y, re)==true) break;
                            else re[x][y] = 0;
//                            flag[x][y] = 1;
//                            cter++; 
                        }
                    }
		}
		//if(p.checkRuler12(matrix)==false) return createPainted();
		return re;
	}
        
        public int getNrOfBlackCell(){
            return total;
        }
	
	public int randomN(int n){
		Random rand = new Random(); 
		int value = rand.nextInt(n)+1;
		return value;
	}
	public void print(){
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				System.out.print(matrix[i][j]+"  ");
			}
			System.out.println();
		}
	}
	public int[][] createSpace(int n, int max){
		int[][] matrix = new int[n][n];
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
					while(true){
						matrix[i][j]=randomN(n);
						int index = 0;
						for(int k=0; k<j; k++){
							if(matrix[i][k]==matrix[i][j]) index++;
						}
						int index1 = 0;
						for(int k=0; k<i; k++){
							if(matrix[k][j]==matrix[i][j]) index1++;
						}
						if(index<max && index1 < max) break;
					}
					if(j<n-1) System.out.print(matrix[i][j]+" ");
					else System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
		return matrix;
	}
	
	public int[][] createSpace(){
		int[][] matrix = new int[rows][columns];
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				matrix[i][j] = 0;
			}
		}
		for(int i=0; i<rows; i++){
			int k=1;
			for(int j=i; j<columns; j++){
					matrix[i][j] = k;
					k++;
			}
			for(int j=0; j<i; j++){
				matrix[i][j] = k;
				k++;
			}
		}
		for(int i=0; i<rows; i++){
			int index = randomN(rows);
			if(index!=i){
				for(int j=0; j<columns; j++){
					int mid = matrix[i][j];
					matrix[i][j] = matrix[index-1][j];
					matrix[index-1][j] = mid;
				}
			}
		}
		
		for(int i=0; i<rows; i++){
			int index = randomN(columns);
			if(index!=i){
				for(int j=0; j<columns; j++){
					int mid = matrix[j][i];
					matrix[j][i] = matrix[j][index-1];
					matrix[j][index-1] = mid;
				}
			}
		}
		for(int i=0; i<rows; i++){
			for(int j=0; j<columns; j++){
				if(this.matrix[i][j]<0){
					while(true){
						int index = randomN(rows);
						if(index!=matrix[i][j]){
							matrix[i][j] = index;
							break;
						}
					}
				}
			}
		}
		
		return matrix;
	}
	
	private void writeToFile(int[][] matrix, long index) throws IOException, ArrayIndexOutOfBoundsException{
            try{
                try (FileWriter fw = new FileWriter(Config.PATH_CREATE_MAP_DATA+"/"+rows+"x"+columns+"_"+index); 
                 BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(1 + "\n");
                    bw.write("[1]" + "\n");
                    for(int i=0; i<rows; i++){
                            String row = "";
                            for(int j=0; j<columns; j++){
                                    row += matrix[i][j]+" ";
                            }
                            bw.write(row.trim() + "\n");
                    }
                    bw.flush();
                }
                }catch (Exception e){
                        e.getMessage();
                }


        }
        
        public int[][] createMap(){
            matrix = createPainted();
            return createSpace();
        }
        
        public void saveMap(int[][] matrix){
            try {
                writeToFile(matrix, System.currentTimeMillis());
            } catch (IOException | ArrayIndexOutOfBoundsException ex) {
                Logger.getLogger(FakeMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
	public int[][] createAndSave(int nrMap){
            FileWriter fw = null; 
            int[][] ma = null;
            try {
                fw = new FileWriter(Config.PATH_CREATE_MAP_DATA+"/"+rows+"x"+columns+"_"+System.currentTimeMillis());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(nrMap + "\n");
                for(int k = 0; k < nrMap; k++){
                    matrix = createPainted();
                    ma = createSpace();
                    bw.write("["+(k+1)+"]" + "\n");
                    for(int i=0; i<rows; i++){
                            String row = "";
                            for(int j=0; j<columns; j++){
                                    row += ma[i][j]+" ";
                            }
                            bw.write(row.trim() + "\n");
                    }
                    
                } 
                bw.flush();
            } catch (IOException ex) {
                Logger.getLogger(FakeMap.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(FakeMap.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return ma;
	}
}
