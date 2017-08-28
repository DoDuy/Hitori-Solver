package model.solver.SWPOldVer.Anh;

import java.io.File;
import java.util.Scanner;

public class hitoriJudge {

	final static String inputFileName = new String(
			"D:/eclipse/workplace/uet-oop/src/hitori/hitori.in");
	final static String input2FileName = new String(
			"D:/eclipse/workplace/uet-oop/src/hitori/hitori.out");
	final static int MAXN = 30;

	
	private static Scanner input;
	private static Scanner input2;
	private static int A[][] = new int[MAXN][MAXN];
	private static int check[][] = new int[MAXN][MAXN];
	private static int n;
	private static boolean col[][] = new boolean[MAXN][MAXN];
	private static boolean row[][] = new boolean[MAXN][MAXN];	
	private static boolean mark[][] = new boolean[MAXN][MAXN];	
	private static pair root = new pair(0,0);
	private static pair Q[] = new pair[MAXN*MAXN];
	
	private static void OpenFile() throws Exception {
		File inputFile = new File(inputFileName);
		input = new Scanner(inputFile);
		File input2File = new File(input2FileName);
		input2 = new Scanner(input2File);
	}
	
	private static void ReadData() throws Exception{
		n = input.nextInt();
		input.nextLine();
		String level = input.nextLine();
		int code = input.nextInt();
		input.nextLine();
		level = input2.nextLine();
		level = input2.nextLine();
		level = input2.nextLine();
		
		
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				A[i][j] = input.nextInt();				
			}
			if (input.hasNextLine())
				input.nextLine();
		}
		
		for (int i=1;i<=n;i++){
			for (int j=1;j<=n;j++) {
				check[i][j] = input2.nextInt();
			}
			if (input2.hasNextLine()) input2.nextLine();
		}
	}
	
	private static boolean Intable(int i, int j) {
		return (i >= 1 && j >= 1 && i <= n && j <= n);
	}
	
	private static boolean CheckRule1(){
		for (int i=1;i<=n;i++)
			for (int j=1;j<=n;j++)
				if (check[i][j]==2){
					int x=A[i][j];
					if (col[j][x] || row [i][x]) return false;
					col[j][x]=true;
					row[i][x]=true;
				}
		return true;
	}
	
	private static boolean CheckRule2(){
		for (int i=1;i<=n;i++)
			for (int j=1;j<=n;j++)
				if (check[i][j]==1){
					for (int x = -1; x <= 1; x++)
						for (int y = -1; y <= 1; y++)
							if ((x==0 || y==0) && x!=y){
								int u=i+x;
								int v=j+y;
								if (Intable(u,v) && check[u][v]==1) return false;
							}
				}
		return true;
	}

	private static int BFS(){
		pair u = new pair(0,0);
		pair v = new pair(0,0);
		int d=1;
		int c=1;
		Q[d]=root;
		mark[root.x][root.y]=true;
		while (d<=c) {
			u=Q[d];
			d++;
			for (int x = -1; x <= 1; x++)
				for (int y = -1; y <= 1; y++)
					if ((x==0 || y==0) && x!=y){
						v= new pair(0,0);
						v.x=u.x+x;
						v.y=u.y+y;
						if (Intable(v.x,v.y) && check[v.x][v.y]==2 && !mark[v.x][v.y]){
							c++;
							Q[c]=v;
							mark[v.x][v.y]=true;
						}
					}
		}
		//System.out.println(c);
		return c;
	}
	
	private static boolean CheckRule3(){
		int Count=0;		
		for (int i=1;i<=n;i++)
			for (int j=1;j<=n;j++)
				if (check[i][j]==2){
					Count++;
					root.x=i; 
					root.y=j;
				}
		//System.out.println(root.x+" "+root.y+ " "+ Count + BFS());
		if (Count==BFS()) return true; else return false;
	}
	
	private static boolean Check(){ 
		if (CheckRule1() && CheckRule2() && CheckRule3()) return true; else return false;
	}
	
	private static void Process(){
		if (Check()) System.out.println("OK"); else System.out.println("Not OK");
	}
	
//	public static void main(String agrs[]) throws Exception{
//		OpenFile();
//		ReadData();
//		Process();
//	}
	
}
