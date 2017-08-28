package model.solver.SWPOldVer.Tiep;

import java.util.Vector;

import model.solver.SWPOldVer.Tiep.Hitori;

public class backtracking {
	public Vector<String> muti;
	public int index = 1;
	public Hitori hitori;
	private StandardCyclePattern standard = new StandardCyclePattern();
	private part2 part;
	public backtracking(Vector<String> muti, Hitori hitori){
		this.muti = muti;
		this.hitori = (Hitori) ObjectCopier.copy(hitori);
		part = new part2(muti, hitori);
	}
	
	public boolean checksSolved(Hitori hitori){
		for(int i=0; i<muti.size(); i++){
			int row = Integer.parseInt(muti.get(i))/hitori.size;
			int col = Integer.parseInt(muti.get(i))%hitori.size;
			standard.remove1();
			if(hitori.hitori[row][col].undefined==true || standard.nonLoop1(-1,row*hitori.size + col, hitori)==false
					|| standard.nonLoop(row, col, hitori)==false){
				return false;
			}
		}
		return true;
	}
	
	public boolean check(Hitori hitori){
		for(int i=0; i<muti.size(); i++){
			int row = Integer.parseInt(muti.get(i))/hitori.size;
			int col = Integer.parseInt(muti.get(i))%hitori.size;
			standard.remove1();
			if(standard.nonLoop1(-1,row*hitori.size + col, hitori)==false
					|| standard.nonLoop(row, col, hitori)==false){
				return false;
			}
		}
		return true;
	}
	
	public void exe1(){
		
		Hitori backup = (Hitori) ObjectCopier.copy(hitori);
		boolean flag = true;
		while(flag == true){
			flag = false;
			
			for(int i=0; i<muti.size(); i++){
				int row = Integer.parseInt(muti.get(i))/hitori.size;
				int col = Integer.parseInt(muti.get(i))%hitori.size;
				if(hitori.hitori[row][col].undefined==true){
					hitori.hitori[row][col].paint = true;
					standard.exe(row, col, hitori);
					part.hitori = (Hitori) ObjectCopier.copy(hitori);
					part.exe();
					hitori = (Hitori) ObjectCopier.copy(part.hitori);
					exe1();
					if(checksSolved(hitori)==true){
						break;
					}
					boolean kt = check(hitori);
					if(kt == true){
						hitori = (Hitori) ObjectCopier.copy(backup);
					}
					else{
						hitori = (Hitori) ObjectCopier.copy(backup);
						hitori.hitori[row][col].paint = false;
						standard.exe(row, col, hitori);
						backup = (Hitori) ObjectCopier.copy(hitori);
						flag = true;
						i=0;
						break;
					}
					
				}
			}
		}
	}
			
}
