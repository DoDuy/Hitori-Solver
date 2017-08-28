/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.solver.SWPOldVer.Tiep;
import java.io.Serializable;

import model.solver.SWPOldVer.Tiep.CellHitori;
/**
 *
 * @author tiept_000
 */
public class Hitori  implements Serializable{
    public CellHitori hitori[][];
    public int size;
    public Hitori(Hitori hitori1){
    	//this.hitori = hitori.hitori.clone();
    	this.size = hitori1.size;
    	hitori = new CellHitori[size][size];
    	for(int i=0; i<size; i++){
    		for(int j=0; j<size; j++){
    			hitori[i][j] = hitori1.hitori[i][j];
    		}
    	}
    }
    public Hitori(int size, int[][] matrix){
    	this.size = size;
        hitori = new CellHitori[size][size];
        for(int i=0; i< size; i++){
            for(int j=0; j< size; j++){
                hitori[i][j] = new CellHitori();
                hitori[i][j].value = matrix[i][j];
            }
        }
    } 
    public void print(){
    	for(int i=0; i<size; i++){
    		for(int j=0; j<size; j++){
    			if(hitori[i][j].paint==true) System.out.print(hitori[i][j].value+"   ");
    			else System.out.print("-"+hitori[i][j].value+"  ");
    		}
    		System.out.println();
    	}
    }
    
}
