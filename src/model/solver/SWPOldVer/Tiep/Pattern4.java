package model.solver.SWPOldVer.Tiep;

import model.solver.SWPOldVer.Tiep.Hitori;

public class Pattern4 {
	Hitori hitori;
    public Pattern4(Hitori hitori){
        this.hitori = hitori;
    }
    
    public void init(){
    	int size = hitori.hitori.length;
    	for(int i = 0; i<size ; i++){
    		for(int j = 0; j<size; j++){
    			hitori.hitori[i][j].paint = false;
    			hitori.hitori[i][j].undefined = false;
    		}
    	}
    }
    
    public void pattern4(){
    	int size = hitori.hitori.length;
    	init();
    	int counter = 0;
    	for(int i=0; i<size; i++){
    		for(int j=0; j<size; j++){
    			if(i==1 || i==size-2)
    				if(j+1<size && hitori.hitori[i][j].value == hitori.hitori[i][j+1].value){
    					if(i-1>=0 && hitori.hitori[i-1][j].value == hitori.hitori[i-1][j+1].value){
    						if(i==1){
    							if(j-1 >=0)
    								hitori.hitori[i-1][j-1].paint=false;
    							if(j+2<size)
    								hitori.hitori[i-1][j+2].paint=false;
    						}
    						counter++;
    					}
    					
    					if(i+1<size && hitori.hitori[i+1][j].value == hitori.hitori[i+1][j+1].value){
    						if(i==size-2){
    							if(j-1 >=0)
    								hitori.hitori[i+1][j-1].paint=false;
    							if(j+2<size)
    								hitori.hitori[i+1][j+2].paint=false;
    						}
    						counter++;
    					}
    					
    					if(counter ==2){
    						if(j-1 >=0)
								hitori.hitori[i][j-1].paint=false;
							if(j+2<size)
								hitori.hitori[i][j+2].paint=false;
    					}
    				}
    			counter = 0;
    			
    			
    			if(j==1 || j==size-2)
    				if(i+1<size && hitori.hitori[i][j].value == hitori.hitori[i+1][j].value){
    					if(j-1>=0 && hitori.hitori[i][j-1].value == hitori.hitori[i+1][j-1].value){
    						if(j==1){
    							if(i-1 >=0)
    								hitori.hitori[i-1][j-1].paint=false;
    							if(i+2<size)
    								hitori.hitori[i+2][j-1].paint=false;
    						}
    						counter++;
    					}
    					
    					if(j+1<size && hitori.hitori[i][j+1].value == hitori.hitori[i+1][j+1].value){
    						if(j==size-2){
    							if(i-1 >=0)
    								hitori.hitori[i-1][j+1].paint=false;
    							if(i+2<size)
    								hitori.hitori[i+2][j+1].paint=false;
    						}
    						counter++;
    					}
    					
    					if(counter ==2){
    						if(i-1 >=0)
								hitori.hitori[i-1][j].paint=false;
							if(i+2<size)
								hitori.hitori[i+2][j].paint=false;
    					}
    				}
    			counter = 0;
    		}
    	}
    }

}
