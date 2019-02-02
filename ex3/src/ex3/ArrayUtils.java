package ex3;

import java.util.Arrays;

public class ArrayUtils {
	
	public static void transposeSquareMatrix(int[][] m) {
		int len = m.length;
		int temp;
		for(int i=0; i < len; i++) {
			for(int j=i+1; j < len; j++) {
				temp = m[i][j];
				m[i][j] = m[j][i];
				m[j][i] = temp;
			}
		}
	}
	private static void printArr(int[] arr) {
		for(int i:arr) {
			System.out.print(i);
		}
	}
	
	public static void printMat(int[][] mat) {
		for(int[] i:mat) {
			ArrayUtils.printArr(i);
			System.out.println();
		}
	}
	
	public static int[][] transposeMatrix(int[][] m){
		int columnLen = m.length;
		if(columnLen == 0) {return m;}
		int rowLen = m[0].length;
		if(rowLen == 0) {return m;}
		
		if(rowLen > columnLen) {
			m = Arrays.copyOf(m, rowLen);
			for(int j=columnLen; j < rowLen; j++) {
				m[j] = new int[rowLen];
			}
			transposeSquareMatrix(m);
			for(int i = 0; i < rowLen; i++) {
				m[i] = Arrays.copyOf(m[i], columnLen);
			}
		}else{
			for(int i = 0; i < columnLen; i++) {
				m[i] = Arrays.copyOf(m[i], columnLen);
			}
			transposeSquareMatrix(m);
			m = Arrays.copyOf(m, rowLen);
		}
		
		return m;
	}
	
	public static int[] shiftArrayCyclic(int[] array, int move, char direction)
	{
		
		if (!(direction == 'R' | direction == 'L') | move < 1) {
			return array;
		}
		int len = array.length;
		move = move % len;
		if(direction=='R') {move = len - move;}
		for(int i = 0; i < move; i++) {
			int temp = array[0];
			for(int j = 0; j < len-1; j++) {
				array[j] = array[j+1];
			}
			array[len-1] = temp;
		}
		return array;
		
	}
	
	public static int alternateSum(int[] array) {
		int len = array.length;
		if(len == 0) {return 0;}
		int maxSum = array[0];
		for(int i = 0; i < len; i++) {
			int currentSum = array[i];
			for(int j = i+1; j < len; j++) {
				if((j-i) % 2 == 0) {currentSum += array[j];}
				else {currentSum -= array[j];}
				if(maxSum < currentSum) {maxSum = currentSum;}
			}
		}
		return maxSum;
		
	}
	
	public static int findPath(int[][] m, int i, int j)
	{
		int len = m.length;
		int [] pathArr = new int[len];
		pathArr[i] = 1;
		for(int ind = 0; ind < len; ind++) {
			if(pathArr[ind] == 1) {
				for(int ind2 = 0; ind2 < len; ind2++) {
					if(m[ind][ind2] == 1 & !(pathArr[ind2]==2)) {pathArr[ind2] = 1;}
					if(pathArr[j] == 1) {return 1;}
				}
				
				pathArr[ind] = 2;
			}
		}
		return 0;
		
	}

}

