package model.solver.SWPOldVer.Tiep;

import model.solver.SWPOldVer.Tiep.Hitori;

public class Pattern5 {
	Hitori hitori;
    public Pattern5(Hitori hitori){
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
    
    public void pattern5(){
    	int size = hitori.hitori.length;
    	init();
    	for(int i=0; i<size; i++){
    		if(hitori.hitori[0][i].value== hitori.hitori[1][i].value)
    			if(i+1<size)
    				if(hitori.hitori[0][i+1].value==hitori.hitori[1][i+1].value){
    					if(i-1>=0)
    						hitori.hitori[0][i-1].paint=false;
    					if(i+2<size)
    						hitori.hitori[0][i+2].paint=false;
    				}
    		
    		if(size-1>=0)
    			if(hitori.hitori[size-1][i].value == hitori.hitori[size-2][i].value)
    				if(i+1<size)
    					if(hitori.hitori[size-1][i+1].value == hitori.hitori[size-2][i+1].value){
    						if(i-1>=0)
    							hitori.hitori[size-1][i-1].paint=false;
    						if(i+2<size)
    							hitori.hitori[size-1][i+2].paint=false;
    					}
    		
    		if(hitori.hitori[i][0].value== hitori.hitori[i][1].value)
    			if(i+1<size)
    				if(hitori.hitori[i+1][0].value==hitori.hitori[i+1][1].value){
    					if(i-1>=0)
    						hitori.hitori[i-1][0].paint=false;
    					if(i+2<size)
    						hitori.hitori[i+2][0].paint=false;
    				}
    		
    		if(size-1>=0)
    			if(hitori.hitori[i][size-1].value == hitori.hitori[i][size-2].value)
    				if(i+1<size)
    					if(hitori.hitori[i+1][size-1].value == hitori.hitori[i+1][size-2].value){
    						if(i-1>=0)
    							hitori.hitori[i-1][size-1].paint=false;
    						if(i+2<size)
    							hitori.hitori[i+2][size-1].paint=false;
    					}
    	}
    }

}
