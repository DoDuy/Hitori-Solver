/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.solver.SWPOldVer.Tiep;
import model.solver.SWPOldVer.Tiep.Hitori;
/**
 *
 * @author tiept_000
 */
public class Parttent6 {
    Hitori hitori;
    public Parttent6(Hitori hitori){
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
    public void exeParttent6(){
        int size = hitori.hitori.length;
        for(int r=0; r< size; r++){
            for(int c=0; c< size-1; c++){
                if(hitori.hitori[r][c].multiple>0 && hitori.hitori[r][c+1].multiple>0 && hitori.hitori[r][c].value == hitori.hitori[r][c+1].value){
                    for(int i=0; i< size; i++){
                        if(i==0 && hitori.hitori[r][i].value == hitori.hitori[r][c].value && hitori.hitori[r][i].value != hitori.hitori[r][i+1].value){
                            hitori.hitori[r][i].paint = true;
                            
                            unpaint(r,i);
                        }
                        else if(i==size-1 && hitori.hitori[r][i].value == hitori.hitori[r][c].value && hitori.hitori[r][i].value != hitori.hitori[r][i-1].value){
                            hitori.hitori[r][i].paint = true;
                            unpaint(r,i);
                        }
                        else if(i>0 && i< size-1 && hitori.hitori[r][i].value == hitori.hitori[r][c].value && hitori.hitori[r][i].value != hitori.hitori[r][i-1].value && hitori.hitori[r][i].value != hitori.hitori[r][i+1].value){
                            hitori.hitori[r][i].paint = true;
                            unpaint(r,i);
                        }
                    }
                    
                }
                if(hitori.hitori[c][r].multiple>0 && hitori.hitori[c+1][r].multiple>0 && hitori.hitori[c][r].value == hitori.hitori[c+1][r].value){
                    for(int i=0; i< size && i!= c && i != c+1 && i!=c-1 && i!= c+2; i++){
                        if(i==0 && hitori.hitori[i][r].value == hitori.hitori[c][r].value && hitori.hitori[c][r].value != hitori.hitori[i+1][r].value){
                            hitori.hitori[i][r].paint = true;
                            unpaint(i,r);
                        }
                        else if(i==size-1 && hitori.hitori[i][r].value == hitori.hitori[c][r].value && hitori.hitori[c][r].value != hitori.hitori[i-1][r].value){
                            hitori.hitori[i][r].paint = true;
                            unpaint(i,r);
                        }
                        else if(i>0 && i< size-1 && hitori.hitori[i][r].value == hitori.hitori[c][r].value && hitori.hitori[c][r].value != hitori.hitori[i-1][r].value && hitori.hitori[i][r].value != hitori.hitori[i+1][r].value){
                            hitori.hitori[i][r].paint = true;
                            unpaint(i, r);
                        }
                    }
                    
                }
            }
        }
        System.out.print("ok  ");
     }
     public boolean findCellRow(int row, int col){
         if(col==0){
             
         }
         return false;
     }
      public void exeParttent7(){//chua giai quyet voi cac canh va goc
        int size = hitori.hitori.length;
        for(int r=1; r< size-1; r++){
            for(int c=1; c< size-1; c++){
                if(hitori.hitori[r][c].paint==false){
                    
                    if(hitori.hitori[r+1][c].value == hitori.hitori[r-1][c].value 
                            &&hitori.hitori[r+1][c].paint == true && hitori.hitori[r-1][c].paint == true){
                        
                        if(hitori.hitori[r][c-1].value == hitori.hitori[r][c+1].value 
                            &&hitori.hitori[r][c-1].multiple >0){
                            
                            for(int i=0; i<size; i++){
                                if(hitori.hitori[r][c-1].value == hitori.hitori[r][i].value && i != c && i!=c-1 && i!=c+1){
                                    System.out.println(r+" "+c);
                                     hitori.hitori[r][i].paint = true;
                                     unpaint(r, i);
                                     
                                }
                            }
                            
                        }
                    }
                    
                    if(hitori.hitori[r][c+1].value == hitori.hitori[r][c-1].value 
                            &&hitori.hitori[r][c+1].paint == true && hitori.hitori[r][c-1].paint == true){
                        
                        if(hitori.hitori[r-1][c].value == hitori.hitori[r+1][c].value 
                            &&hitori.hitori[r-1][c].multiple >0){
                            
                            for(int i=0; i<size; i++){
                                if(hitori.hitori[r-1][c].value == hitori.hitori[i][c].value && i != r && i!=r-1 && i!=r+1){
                                    
                                     hitori.hitori[i][c].paint = true;
                                     unpaint(i, c);
                                     
                                }
                            }
                            
                        }
                    }
                    
                }
            }
        }
        
      }
      public void exeParttent8(){//chua giai quyet voi cac canh va goc
            int size = hitori.hitori.length;
            for(int c=0; c< size-2; c++){
                    if(hitori.hitori[1][c].multiple==3){
                        if(hitori.hitori[0][c].value == hitori.hitori[1][c].value &&
                                hitori.hitori[1][c+1].value == hitori.hitori[1][c].value){
                            System.out.print("trantiep");
                            if(hitori.hitori[0][c+2].paint==true){
                                hitori.hitori[1][c].paint = true;
                                unpaint(1, c);
                            }
                        }
                    }
            }
            
            for(int c=2; c< size; c++){
                    if(hitori.hitori[1][c].multiple==3){
                        if(hitori.hitori[0][c].value == hitori.hitori[1][c].value &&
                                hitori.hitori[1][c-1].value == hitori.hitori[1][c].value){
                            System.out.print("trantiep");
                            if(hitori.hitori[0][c-2].paint==true){
                                hitori.hitori[1][c].paint = true;
                                unpaint(1, c);
                            }
                        }
                    }
            }
            
            for(int c=0; c< size-2; c++){
                    if(hitori.hitori[c][1].multiple==3){
                        if(hitori.hitori[c][0].value == hitori.hitori[c][1].value &&
                                hitori.hitori[c+1][1].value == hitori.hitori[c][1].value){
                            System.out.print("trantiep");
                            if(hitori.hitori[c+2][0].paint==true){
                                hitori.hitori[c][1].paint = true;
                                unpaint(c, 1);
                            }
                        }
                    }
            }
            
            for(int c=2; c< size; c++){
                    if(hitori.hitori[c][1].multiple==3){
                        if(hitori.hitori[c][0].value == hitori.hitori[c][1].value &&
                                hitori.hitori[c-1][1].value == hitori.hitori[c][1].value){
                            System.out.print("trantiep");
                            if(hitori.hitori[c-2][0].paint==true){
                                hitori.hitori[c][1].paint = true;
                                unpaint(c, 1);
                            }
                        }
                    }
            }
            
            
      }
}
     
