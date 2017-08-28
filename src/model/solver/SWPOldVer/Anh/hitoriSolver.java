package model.solver.SWPOldVer.Anh;

import java.util.*;
import java.io.*;

class pair {
	public int x;
	public int y;

	public pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class hitoriSolver {

//	final static String inputFileName = Config.PATH_FILE_CNF;
//	final static String outputFileName = Config.PATH_FILE_CNF_OUT;
	final int MAXN = 50;

	private int A[][] = new int[MAXN][MAXN];
	private int check[][] = new int[MAXN][MAXN];
	private int n;
	private int nPair = 0;
	private pair B[] = new pair[MAXN * MAXN];
	private boolean solved = false;
	private int P[] = new int[MAXN * MAXN];
	private Scanner input;
	private FileWriter output;
	private String level = "";
	private int code=0;

	/*
	 * private static void print(int A[][]){ for (int i=1;i<=n;i++){ for (int
	 * j=1;j<=n;j++) System.out.print(A[i][j]+" "); System.out.println(""); } }
	 */

	private void OpenFile(){
//		File inputFile = new File(inputFileName);
//		input = new Scanner(inputFile);
//		File outputFile = new File(outputFileName);
//		output = new FileWriter(outputFile);
	}

	public void ReadData(int si, int[][] arr){
//		n = input.nextInt();
//		input.nextLine();
//		level = input.nextLine();
//		code = input.nextInt();
//		input.nextLine();
//
//		for (int i = 1; i <= n; i++) {
//			for (int j = 1; j <= n; j++) {
//				A[i][j] = input.nextInt();
//				 //if (input.hasNextLine()) input.nextLine();
//			}
//			if (input.hasNextLine()) input.nextLine();
//		}
            n = si;
            for (int i = 1; i <= n; i++)
                for (int j = 1; j <= n; j++)
                        A[i][j] = arr[i-1][j-1];
	}

	public boolean[][] Print(){
//            for (int i = 1; i <= n; i++) {
//                    for (int j = 1; j <= n; j++)
//                            System.out.print(check[i][j] + " ");
//                    System.out.print("\n");
//            }
            boolean[][] re = new boolean[n][n];
            for (int i = 1; i <= n; i++) 
                for (int j = 1; j <= n; j++)
                    if(check[i][j] == 2) re[i-1][j-1] = true;
                    else re[i-1][j-1] = false;
            
            return re;
	}

	private void RowB(int i, int j) {
		for (int k = 1; k <= n; k++)
			if (k != j && k != j + 1 && A[i][k] == A[i][j])
				check[i][k] = 1;
	}

	private void ColB(int i, int j) {
		for (int k = 1; k <= n; k++)
			if (k != i && k != i + 1 && A[k][j] == A[i][j])
				check[k][j] = 1;
	}

	private void BasicSearch() {
		// Rule A
		if (A[1][1] == A[1][2])
			check[2][1] = 2;
		if (A[1][n] == A[1][n - 1])
			check[2][n] = 2;
		if (A[n][n] == A[n][n - 1])
			check[n - 1][n] = 2;
		if (A[n][1] == A[n][2])
			check[n - 1][1] = 2;
		if (A[2][1] == A[2][2])
			check[1][2] = 2;
		if (A[2][n] == A[2][n - 1])
			check[1][n - 1] = 2;
		if (A[n - 1][n] == A[n - 1][n - 1])
			check[n][n - 1] = 2;
		if (A[n - 1][1] == A[n - 1][2])
			check[n][2] = 2;
		if (A[1][1] == A[2][1])
			check[1][2] = 2;
		if (A[n][1] == A[n - 1][1])
			check[n][2] = 2;
		if (A[n][n] == A[n - 1][n])
			check[n][n - 1] = 2;
		if (A[1][n] == A[2][n])
			check[1][n - 1] = 2;
		if (A[1][2] == A[2][2])
			check[2][1] = 2;
		if (A[n][2] == A[n - 1][2])
			check[n - 1][1] = 2;
		if (A[n][n - 1] == A[n - 1][n - 1])
			check[n - 1][n] = 2;
		if (A[1][n - 1] == A[2][n - 1])
			check[2][n] = 2;
		// Rule B
		for (int i = 1; i < n; i++)
			for (int j = 1; j < n; j++) {
				if (A[i][j] == A[i][j + 1])
					RowB(i, j);
				if (A[i][j] == A[i + 1][j])
					ColB(i, j);
			}
		// Rule C
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				if (A[i][j - 1] == A[i][j + 1])
					check[i][j] = 2;
				if (A[i - 1][j] == A[i + 1][j])
					check[i][j] = 2;
			}
		// Rule D
		if (A[1][1] == A[1][2] && A[1][1] == A[2][1])
			check[1][1] = 1;
		if (A[1][n] == A[1][n - 1] && A[1][n] == A[2][n])
			check[1][n] = 1;
		if (A[n][1] == A[n][2] && A[n][1] == A[n - 1][1])
			check[n][1] = 1;
		if (A[n][n] == A[n][n - 1] && A[n][n] == A[n - 1][n])
			check[n][n] = 1;
	}

	private boolean Check(int u, int v) {
		int c = 0;
		for (int k = 1; k <= n; k++) {
			if (A[u][k] == A[u][v] && check[u][k] != 1)
				c++;
			if (A[k][v] == A[u][v] && check[k][v] != 1)
				c++;
		}
		if (c == 2)
			return true;
		else
			return false;
	}

	private boolean check1(int i, int j) {
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++)
				if ((x == 0 || y == 0) && x != y) {
					int u = i + x;
					int v = j + y;
					if (Intable(u, v) && check[u][v] == 1)
						return false;
				}
		return true;
	}

	private boolean ImplementH(int i, int j) {
		for (int k = 1; k <= n; k++) {

			if (A[i][k] == A[i][j] && k != j && check[i][k] == 0) {
				if (connected(i, k) || !check1(i, k))
					return false;
				check[i][k] = 1;
				connect(i, k);
			}
			if (A[k][j] == A[i][j] && k != i && check[k][j] == 0) {
				if (connected(k, j) || !check1(k, j))
					return false;
				check[k][j] = 1;
				connect(k, j);
			}
		}
		return true;
	}

	private boolean check2(int i, int j) {
		for (int k = 1; k <= n; k++) {
			if (k != j && check[i][k] == 2 && A[i][k] == A[i][j])
				return false;
			if (k != i && check[k][j] == 2 && A[k][j] == A[i][j])
				return false;
		}
		return true;
	}

	private boolean RuleE() {
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (check[i][j] == 1) {
					if (!check2(i + 1, j) && Intable(i + 1, j))
						return false;
					if (!check2(i - 1, j) && Intable(i - 1, j))
						return false;
					if (!check2(i, j + 1) && Intable(i, j + 1))
						return false;
					if (!check2(i, j - 1) && Intable(i, j - 1))
						return false;

					check[i + 1][j] = 2;
					check[i - 1][j] = 2;
					check[i][j + 1] = 2;
					check[i][j - 1] = 2;
				}
		return true;
	}

	private boolean RuleF() {
		for (int k = 0; k <= n + 1; k++) {
			check[0][k] = 1;
			check[n + 1][k] = 1;
			check[k][0] = 1;
			check[k][n + 1] = 1;
		}
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				int c = 0;
				for (int x = -1; x <= 1; x++)
					for (int y = -1; y <= 1; y++)
						if ((x == 0 || y == 0) && x != y) {
							int u = i + x;
							int v = j + y;
							if (check[u][v] == 1)
								c++;
						}
				if (c == 3)
					for (int x = -1; x <= 1; x++)
						for (int y = -1; y <= 1; y++)
							if ((x == 0 || y == 0) && x != y) {
								int u = i + x;
								int v = j + y;
								if (check[u][v] == 0) {
									if (!check2(u, v))
										return false;
									check[u][v] = 2;
								}
							}
			}
		return true;
	}

	private boolean RuleG() {
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (Check(i, j) && check[i][j] == 0) {
					if (!check2(i, j))
						return false;
					check[i][j] = 2;
				}
		return true;
	}

	private boolean RuleH() {
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (check[i][j] == 2) {
					if (!ImplementH(i, j))
						return false;
				}
		return true;
	}

	private int NumOfZero() {
		int res = 0;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (check[i][j] == 0)
					res++;
		return res;
	}

	private boolean AdvanceSearch2(){
		if (!RuleE())
			return false;
		if (!RuleF())
			return false;
		if (!RuleG())
			return false;
		if (!RuleH())
			return false;
		return true;
	}

	private boolean AdvanceSearch(){
		while (true) {
			int st = NumOfZero();
			if (!RuleE())
				return false;
			if (!RuleF())
				return false;
			if (!RuleG())
				return false;
			if (!RuleH())
				return false;
			int fi = NumOfZero();
			if (st == fi)
				return true;
		}
	}

	private void Try(int k){
		if (k == nPair + 1) {
			solved = true;
			return;
		}
		int x = B[k].x;
		int y = B[k].y;
		if (check[x][y] == 0) {
			int backup[][] = new int[MAXN][MAXN];
			int backupP[] = new int[MAXN * MAXN];
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++)
					backup[i][j] = check[i][j];
			for (int i = 0; i <= code(n + 1, n + 1); i++)
				backupP[i] = P[i];

			if (check2(x, y)) {
				check[x][y] = 2;
				if (AdvanceSearch())
					Try(k + 1);
				if (solved)
					return;
				for (int i = 1; i <= n; i++)
					for (int j = 1; j <= n; j++)
						check[i][j] = backup[i][j];
				for (int i = 0; i <= code(n + 1, n + 1); i++)
					P[i] = backupP[i];
			}
			if (!connected(x, y) && check1(x, y)) {
				check[x][y] = 1;
				connect(x, y);
				if (AdvanceSearch()) {
					Try(k + 1);
				}
				if (solved)
					return;
				for (int i = 1; i <= n; i++)
					for (int j = 1; j <= n; j++)
						check[i][j] = backup[i][j];
				for (int i = 0; i <= code(n + 1, n + 1); i++)
					P[i] = backupP[i];
			}
			check[x][y] = 0;
		} else {
			Try(k + 1);
		}
	}

	private int code(int i, int j) {
		return i * (n + 2) + j;
	}

	private boolean inTable(int i, int j) {
		return (i >= 0 && j >= 0 && i <= n + 1 && j <= n + 1);
	}

	private boolean Intable(int i, int j) {
		return (i >= 1 && j >= 1 && i <= n && j <= n);
	}

	private int findSet(int i) {
		if (P[i] < 0)
			return i;
		else {
			P[i] = findSet(P[i]);
			return P[i];
		}
	}

	private void Union(int i, int j) {
		int u = findSet(i);
		int v = findSet(j);
		if (u == v)
			return;
		if (P[u] < P[v]) {
			P[u] = P[u] + P[v];
			P[v] = u;
		} else {
			P[v] = P[v] + P[u];
			P[u] = v;
		}
	}

	private void connect(int i, int j) {
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++)
				if (x != 0 || y != 0) {
					int u = i + x;
					int v = j + y;
					if (inTable(u, v) && check[u][v] == 1) {
						int s = code(i, j);
						int t = code(u, v);
						Union(s, t);
					}
				}
	}

	private boolean connected(int i, int j) {
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++)
				for (int x1 = -1; x1 <= 1; x1++)
					for (int y1 = -1; y1 <= 1; y1++)
						if ((x != 0 || y != 0) && ((x1 != 0 || y1 != 0))
								&& (x != x1 || y != y1)) {
							int u = i + x;
							int v = j + y;
							int u1 = i + x1;
							int v1 = j + y1;
							if (inTable(u, v) && inTable(u1, v1)
									&& check[u][v] == 1 && check[u1][v1] == 1
									&& (Intable(u, v) || Intable(u1, v1))) {
								int s = code(u1, v1);
								int t = code(u, v);
								s = findSet(s);
								t = findSet(t);
								if (s == t)
									return true;
							}
						}
		return false;
	}

	private void Init() {
		for (int k = 0; k <= code(n + 1, n + 1); k++)
			P[k] = -1;
		for (int k = 0; k <= n + 1; k++) {
			check[0][k] = 1;
			check[n + 1][k] = 1;
			check[k][0] = 1;
			check[k][n + 1] = 1;
		}
		for (int i = 0; i <= n + 1; i++)
			for (int j = 0; j <= n + 1; j++)
				if (check[i][j] == 1)
					connect(i, j);
	}

	private void Recursion(){
		Init();
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (check[i][j] == 0) {
					nPair++;
					B[nPair] = new pair(i, j);
				}
		Try(1);
	}

	public void Solve(){
		//int start = (int) System.currentTimeMillis();
		BasicSearch();
		Init();
		AdvanceSearch2();
		Recursion();
//		int finish = (int) System.currentTimeMillis();
//		int timer = finish - start;
//		output.write("Time: "+timer+" milis\n");
//		output.write("Level: "+level+ "\n");
//		output.write("Code: "+code+"\n");
	}

//	public static void main(String agrs[]) throws Exception {
//		OpenFile();
//		ReadData();
//		Solve();
//		Print(check);
//
//		input.close();
//		output.close();
//	}

}
