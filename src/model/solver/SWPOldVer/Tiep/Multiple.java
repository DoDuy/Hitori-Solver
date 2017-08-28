package model.solver.SWPOldVer.Tiep;

import java.util.Vector;

import model.solver.SWPOldVer.Tiep.Hitori;

public class Multiple {
	public Vector<String> muti = new Vector<String>();
    public void exe(Hitori hitori){
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
                //if(r==4 && c==7) System.out.print("trantiep"+test1);
                if(test==true && test1==true){
                    hitori.hitori[r][c].multiple = 3;
                    muti.addElement(String.valueOf(r*hitori.size+c));
                }
                else if(test == true){
                    hitori.hitori[r][c].multiple = 1;
                    muti.addElement(String.valueOf(r*hitori.size+c));
                }
                else if(test1 == true){
                    hitori.hitori[r][c].multiple = 2;
                    muti.addElement(String.valueOf(r*hitori.size+c));
                }
            }
        }
    }
    public void print(){
    	for(int i=0; i<muti.size(); i++){
    		System.out.println(muti.get(i));
    	}
    }
}
