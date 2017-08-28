/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.solver.SWPOldVer.Tiep;
import model.solver.SWPOldVer.Tiep.Hitori;
/**
 *

 */
public class Parttent2 {
    Hitori hitori;
    public Parttent2(Hitori hitori){
        this.hitori = hitori;
    }
    public void exe(){
        int size = hitori.hitori.length;
        for(int r=0; r< size; r++){
            for(int c=0; c< size; c++){
                boolean test = false;
                boolean test1 = false;
                for(int i=0; i<size; i++){
                    if(i!=c && hitori.hitori[r][i].value==hitori.hitori[r][c].value){
                        test=true;
                        break;
                    }
                }
                for(int i=0; i<size; i++){
                    if(i!=r && hitori.hitori[i][c].value==hitori.hitori[r][c].value){
                        test1=true;
                        break;
                    }
                }
                if(test==true && test1==true){
                    hitori.hitori[r][c].multiple = 3;
                }
                else if(test == true){
                    hitori.hitori[r][c].multiple = 1;
                }
                else if(test1 == true){
                    hitori.hitori[r][c].multiple = 2;
                }
            }
        }
    }
    public void unpaint(int row, int col){
    	hitori.hitori[row][col].undefined = false;
        if(row>0){
            hitori.hitori[row-1][col].paint = false;
            hitori.hitori[row-1][col].undefined = false;
        }
        if(row<hitori.hitori.length-1){
            hitori.hitori[row+1][col].paint = false;
            hitori.hitori[row+1][col].undefined = false;
        }
        if(col>0){
            hitori.hitori[row][col-1].paint = false;
            hitori.hitori[row][col-1].undefined = false;
        }
        if(col<hitori.hitori.length-1){
            hitori.hitori[row][col+1].paint = false;
            hitori.hitori[row][col+1].undefined = false;
        }
    }
    public void exeParttent(){
        int size = hitori.hitori.length;
        for(int r=0; r< size; r++){
            for(int c=0; c< size; c++){
            	if(r==0 && c==0){
            		if(hitori.hitori[r][c].multiple>0 && hitori.hitori[r][c+1].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r][c+1].value){
                		if(hitori.hitori[r+1][c].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r+1][c].value){
                			hitori.hitori[r][c].paint = true;
                            unpaint(r,c); 
                		}   
                	}	
            	}
            	if(r==size-1 && c==0){
            		if(hitori.hitori[r][c].multiple>0 && hitori.hitori[r][c+1].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r][c+1].value){
                		if( hitori.hitori[r-1][c].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r-1][c].value){
                			hitori.hitori[r][c].paint = true;
                            unpaint(r,c); 
                		}   
                	}	
            	}
            	if(r==size-1 && c==size-1){
            		if(hitori.hitori[r][c].multiple>0 && hitori.hitori[r][c-1].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r][c-1].value){
                		if( hitori.hitori[r-1][c].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r-1][c].value){
                			hitori.hitori[r][c].paint = true;
                            unpaint(r,c); 
                		}   
                	}	
            	}
            	if(r==0 && c==size-1){
            		if(hitori.hitori[r][c].multiple>0 && hitori.hitori[r][c-1].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r][c-1].value){
                		if( hitori.hitori[r+1][c].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r+1][c].value){
                			hitori.hitori[r][c].paint = true;
                            unpaint(r,c); 
                		}   
                	}	
            	}
            	
            }
        }
        System.out.print("ok  ");
        
                
     }
}
     
