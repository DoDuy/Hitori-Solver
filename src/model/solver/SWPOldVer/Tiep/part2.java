package model.solver.SWPOldVer.Tiep;

import java.util.Vector;

import model.solver.SWPOldVer.Tiep.Hitori;

public class part2 {
	public Vector<String> muti;
	public Hitori hitori;
	private StandardCyclePattern standard = new StandardCyclePattern();
	public part2(Vector<String> muti, Hitori hitori){
		this.muti = muti;
		this.hitori = (Hitori) ObjectCopier.copy(hitori);
	}
	public void exe(){
		Hitori backup = (Hitori) ObjectCopier.copy(hitori);
		boolean flag = true;
		while(flag==true){
			flag = false;
			for(int i=0; i<muti.size(); i++){
				int row = Integer.parseInt(muti.get(i))/hitori.size;
				int col = Integer.parseInt(muti.get(i))%hitori.size;
				if(hitori.hitori[row][col].undefined==true){
					
					hitori.hitori[row][col].paint = true;
					standard.exe(row, col, hitori);
					standard.remove1();
					boolean kt = standard.nonLoop(row, col, hitori);
					boolean kt1 = standard.nonLoop1(-1,row*hitori.size + col, hitori);
					
					if(kt == true && kt1 == true){
						hitori = (Hitori) ObjectCopier.copy(backup);
					}
					else{
						hitori = (Hitori) ObjectCopier.copy(backup);
						hitori.hitori[row][col].paint = false;
						standard.exe(row, col, hitori);
						backup = (Hitori) ObjectCopier.copy(hitori);
						i=0;
						flag = true;
						break;
					}
				}
			}
		}
	}
}
