package model.input;

import java.util.Vector;

public class Rulers {
	public Vector<String> duongdi = new Vector<>();
	public Vector<String> duongbien = new Vector<>();
	public Vector<String> diemdadi = new Vector<>();
	public boolean checkRuler12(int[][] matrix, int x, int y){
		if(x<matrix.length && matrix[x+1][y]<0) return false;
		if(x>0 && matrix[x-1][y]<0) return false;
		if(y<matrix.length && matrix[x][y+1]<0) return false;
		return !(y>0 && matrix[x][y-1]<0);
	}
	
	public boolean nonLoop(int row, int col, int[][] hitori){
    	if(hitori[row][col]<0){
    		duongdi.addElement(String.valueOf(row*hitori.length+col));
    		if(row==0 || col==0 || row==hitori.length-1 || col == hitori.length-1){
    			duongbien.addElement(String.valueOf(row*hitori.length+col));
    		}
    		if(duongbien.size()>=2){
    			//System.out.println("hehe");
    			return false;
    		}
    		if(kiemtra((row+1)*hitori.length+col+1)==true && row < hitori.length-1 && col < hitori.length-1){
    			if(nonLoop(row+1, col+1, hitori)==false) return false;
    		}
    		if(kiemtra((row+1)*hitori.length+col-1)==true && row < hitori.length-1 && col > 0){
    			if(nonLoop(row+1, col-1, hitori)==false) return false;
    		}
    		if(kiemtra((row-1)*hitori.length+col+1)==true && row > 0 && col < hitori.length-1){
    			if(nonLoop(row-1, col+1, hitori)==false) return false;
    		}
    		if(kiemtra((row-1)*hitori.length+col-1)==true && row > 0 && col > 0){
    			if(nonLoop(row-1, col-1, hitori)==false) return false;
    		}
    		return true;
    	}
    	return true;
    }
    public boolean kiemtra(int i){
    	for(int j=0; j<duongdi.size(); j++){
    		if(duongdi.get(j).equals(String.valueOf(i)) == true){
    			return false;
    		}
    	}
    	return true;
    }
    
    public boolean kiemtra1(int i){
    	for(int j=0; j<diemdadi.size(); j++){
    		if(diemdadi.get(j).equals(String.valueOf(i)) == true){
    			return false;
    		}
    	}
    	return true;
    }
    
    public void remove1(){
    	if(diemdadi.size() > 0){
    		diemdadi.removeAllElements();
    	}
    	if(duongdi.size() > 0){
    		duongdi.removeAllElements();
    	}
    	
    	if(duongbien.size() > 0){
    		duongbien.removeAllElements();
    	}
    }
    
    public boolean nonLoop1(int i, int j, int[][] hitori){
    	int size = hitori.length;
    	int row = j/size;
    	int col = j%size;
    	if(kiemtra1(j)==false) return false;
    	if(hitori[row][col]<0){
    		diemdadi.addElement(String.valueOf(j));
    	}
    	else return true;
    	if(row < size-1 && col < size-1){
			if(hitori[row][col]<0 && ((row+1)*size + col+1)!=i){
				if(nonLoop1(j, (row+1)*size+col+1, hitori)==false) return false;
			}
    	}
    	if(row < size-1 && col > 0){
			if(hitori[row][col]<0 && ((row+1)*size + col-1)!=i){
				if(nonLoop1(j, (row+1)*size+col-1, hitori)==false) return false;
			}
    	}
    	if(row > 0 && col < size-1){
			if(hitori[row][col]<0 && ((row-1)*size + col+1)!=i){
				if(nonLoop1(j, (row-1)*size+col+1, hitori)==false) return false;
			}
    	}
    	if(row > 0 && col > 0){
			if(hitori[row][col]<0 && ((row-1)*size + col-1)!=i){
				if(nonLoop1(j, (row-1)*size+col-1, hitori)==false) return false;
			}
    	}
    	return true;
    }
}
