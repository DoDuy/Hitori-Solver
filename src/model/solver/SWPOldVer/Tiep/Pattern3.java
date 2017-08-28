package model.solver.SWPOldVer.Tiep;
import model.solver.SWPOldVer.Tiep.Hitori;

public class Pattern3 {
	Hitori hitori;
    public Pattern3(Hitori hitori){
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
    
    public void pattern3(){
    	int size = hitori.hitori.length;
    	init();
    	if(hitori.hitori[0][0].value == hitori.hitori[0][1].value
    			&& hitori.hitori[1][0].value == hitori.hitori[1][1].value ||
    			hitori.hitori[0][0].value == hitori.hitori[1][0].value
    			&& hitori.hitori[0][1].value == hitori.hitori[1][1].value){
    		hitori.hitori[0][0].paint = true;
    		hitori.hitori[1][1].paint = true;
    		
    	}
    	
    	if(hitori.hitori[size-1][0].value == hitori.hitori[size-1][1].value
    			&& hitori.hitori[size-2][0].value == hitori.hitori[size-2][1].value ||
    			hitori.hitori[size-1][0].value == hitori.hitori[size-2][0].value
    			&& hitori.hitori[size-1][1].value == hitori.hitori[size-2][1].value){
    		hitori.hitori[size-1][0].paint = true;
    		hitori.hitori[size-2][1].paint = true;
    		
    	}
    	
    	if(hitori.hitori[0][size-2].value == hitori.hitori[0][size-1].value
    			&& hitori.hitori[1][size-2].value == hitori.hitori[1][size-1].value ||
    			hitori.hitori[0][size-2].value == hitori.hitori[1][size-2].value
    			&& hitori.hitori[0][size-1].value == hitori.hitori[1][size-1].value){
    		hitori.hitori[0][size-1].paint = true;
    		hitori.hitori[1][size-2].paint = true;
    		
    	}
    	
    	if(hitori.hitori[size-1][size-1].value == hitori.hitori[size-1][size-2].value
    			&& hitori.hitori[size-2][size-1].value == hitori.hitori[size-2][size-2].value ||
    			hitori.hitori[size-1][size-1].value == hitori.hitori[size-2][size-1].value
    			&& hitori.hitori[size-1][size-2].value == hitori.hitori[size-2][size-2].value){
    		hitori.hitori[size-1][size-1].paint = true;
    		hitori.hitori[size-2][size-2].paint = true;
    		
    	}
    	System.out.print("ok  ");
    }
}
