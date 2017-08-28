package model.solver.SWPOldVer.Tiep;
import java.util.Vector;

import model.solver.SWPOldVer.Tiep.Hitori;

public class StandardCyclePattern {
	public Vector<String> duongdi = new Vector<String>();
	public Vector<String> duongbien = new Vector<String>();
	public Vector<String> diemdadi = new Vector<String>();
	public int index = 0;
    public void exe(int row, int col, Hitori hitori){
    	if(hitori.hitori[row][col].paint == true) paint(row, col, hitori);// khi tô đen 1 ô.
    	else unpaint(row, col, hitori);// khi unpaint 1 ô.
    	remove1();
    }
    public void paint(int row, int col, Hitori hitori){
    	
    	hitori.hitori[row][col].undefined = false;
        if(row>0){
        	if(hitori.hitori[row-1][col].undefined==true){
	            hitori.hitori[row-1][col].paint = false;
	            hitori.hitori[row-1][col].undefined = false;
        	}
        }
        if(row<hitori.hitori.length-1){
        	if(hitori.hitori[row+1][col].undefined==true){
	            hitori.hitori[row+1][col].paint = false;
	            hitori.hitori[row+1][col].undefined = false;
        	}
        }
        if(col>0){
        	if(hitori.hitori[row][col-1].undefined==true){
	            hitori.hitori[row][col-1].paint = false;
	            hitori.hitori[row][col-1].undefined = false;
        	}
        }
        if(col<hitori.hitori.length-1){
        	if(hitori.hitori[row][col+1].undefined==true){
	            hitori.hitori[row][col+1].paint = false;
	            hitori.hitori[row][col+1].undefined = false;
        	}
        }
        
        if(row>0){
	            check(row-1, col, hitori);
        }
        if(row<hitori.hitori.length-1){
	            check(row+1, col, hitori);
        }
        if(col>0){
	            check(row, col-1, hitori);
        }
        if(col<hitori.hitori.length-1){
	            check(row, col+1, hitori);
        }
    }
    
    public void unpaint(int row, int col, Hitori hitori){// gọi khi unpaint một ô.
    	hitori.hitori[row][col].undefined = false;
    	check(row, col, hitori);
    }
    
    public void checkRow(int row, int col, Hitori hitori){
    	
    	for(int i=0; i<hitori.size; i++){
    		if(hitori.hitori[row][i].value == hitori.hitori[row][col].value 
    				&& hitori.hitori[row][i].undefined==true && i!= col){
    			hitori.hitori[row][i].paint=true;
    			paint(row, i, hitori);
    		}
    	}
    }
    
    public void checkCol(int row, int col, Hitori hitori){
    	for(int i=0; i<hitori.size; i++){
    		if(hitori.hitori[i][col].value == hitori.hitori[row][col].value 
    				&& hitori.hitori[i][col].undefined==true && i!= row){
    			hitori.hitori[i][col].paint=true;
    			paint(i, col, hitori);
    		}
    	}
    }
    
    public void check(int row, int col, Hitori hitori){
    	if(hitori.hitori[row][col].multiple == 1){
    		checkRow(row, col, hitori);
    	}
    	else if(hitori.hitori[row][col].multiple == 2){
    		checkCol(row, col, hitori);
    	}
    	else if(hitori.hitori[row][col].multiple == 3){
    		checkRow(row, col, hitori);
    		checkCol(row, col, hitori);
    	}
    }
    
    public boolean nonLoop(int row, int col, Hitori hitori){
    	if(hitori.hitori[row][col].paint == true){
    		duongdi.addElement(String.valueOf(row*hitori.size+col));
    		if(row==0 || col==0 || row==hitori.size-1 || col == hitori.size-1){
    			duongbien.addElement(String.valueOf(row*hitori.size+col));
    		}
    		if(duongbien.size()>=2){
    			//System.out.println("hehe");
    			return false;
    		}
    		if(kiemtra((row+1)*hitori.size+col+1)==true && row < hitori.size-1 && col < hitori.size-1){
    			if(nonLoop(row+1, col+1, hitori)==false) return false;
    		}
    		if(kiemtra((row+1)*hitori.size+col-1)==true && row < hitori.size-1 && col > 0){
    			if(nonLoop(row+1, col-1, hitori)==false) return false;
    		}
    		if(kiemtra((row-1)*hitori.size+col+1)==true && row > 0 && col < hitori.size-1){
    			if(nonLoop(row-1, col+1, hitori)==false) return false;
    		}
    		if(kiemtra((row-1)*hitori.size+col-1)==true && row > 0 && col > 0){
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
    	index = 0;
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
    
    public boolean nonLoop1(int i, int j, Hitori hitori){
    	int size = hitori.size;
    	int row = j/size;
    	int col = j%size;
    	if(kiemtra1(j)==false) return false;
    	if(hitori.hitori[row][col].paint==true){
    		diemdadi.addElement(String.valueOf(j));
    	}
    	else return true;
    	if(row < size-1 && col < size-1){
			if(hitori.hitori[row+1][col+1].paint==true && ((row+1)*size + col+1)!=i){
				if(nonLoop1(j, (row+1)*size+col+1, hitori)==false) return false;
			}
    	}
    	if(row < size-1 && col > 0){
			if(hitori.hitori[row+1][col-1].paint==true && ((row+1)*size + col-1)!=i){
				if(nonLoop1(j, (row+1)*size+col-1, hitori)==false) return false;
			}
    	}
    	if(row > 0 && col < size-1){
			if(hitori.hitori[row-1][col+1].paint==true && ((row-1)*size + col+1)!=i){
				if(nonLoop1(j, (row-1)*size+col+1, hitori)==false) return false;
			}
    	}
    	if(row > 0 && col > 0){
			if(hitori.hitori[row-1][col-1].paint==true && ((row-1)*size + col-1)!=i){
				if(nonLoop1(j, (row-1)*size+col-1, hitori)==false) return false;
			}
    	}
    	return true;
    }
}
