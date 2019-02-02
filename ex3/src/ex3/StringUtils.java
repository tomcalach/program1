package ex3;
import java.util.Arrays;

public class StringUtils {
	
	public static String findSortedSequence(String str){
		
		String[] wordArr = str.split(" ");
		int len = wordArr.length;
		int[] indexes = {0,0};
		
		upperloop:
		for(int i=0; i < len; i++) {
			for (int j=i+1; j < len; j++) {
				if (wordArr[j-1].compareTo(wordArr[j]) > 0){
					if ((j-1-i) >= (indexes[1]-indexes[0])) {
						indexes[0] = i;
						indexes[1] = j-1;
					}
					break;
				}
				if(j+1 == len) {
					indexes[0] = i;
					indexes[1] = j;
					break upperloop;
				}
			}
			}
		if(indexes[1]-indexes[0] == 0) {indexes[0]= len-1; indexes[1]= len-1;}
		return String.join(" ", Arrays.copyOfRange(wordArr, indexes[0], indexes[1]+1));	
	}
	
	public static String[] change10To(String[] arr, String delimeter) {
		for(int x=0; x < arr.length; x++) {
			if(arr[x] == "10") {
				arr[x] = delimeter;
			}
		}
		return arr;
	}

	public static String parityXorStrings(String a, String b) {
		String[] ansArr = new String[a.length()];
		String[] letters = a.split("");
		int ind = 0;
		int countLetter;
		
		upperloop:
		for(String letter:letters) {
			if(!(ansArr[ind] == null)) {ind++; continue;}
			countLetter = 0;
			for(int i = ind; i < a.length(); i++) {
				if(a.substring(i, i+1).equals(letter)) {
					countLetter++;
					ansArr[i] = "10";
				}
			}
			if(countLetter % 2 == 0){
				ansArr = StringUtils.change10To(ansArr, "");
				ind++;
				continue upperloop;
			}else {
				countLetter = 0;
				for(int j = 0; j < b.length(); j++) {
					if(b.substring(j, j+1).equals(letter)) {
						countLetter++;
					}
				}
			}
				if(countLetter > 0 && (countLetter % 2 == 0)) {
					ansArr = StringUtils.change10To(ansArr, letter);
				}else {
					ansArr = StringUtils.change10To(ansArr, "");
				}
				ind++;
				
		}
		return String.join("", ansArr);
		
	}
	
	public static boolean isAnagram(String a, String b)
	{
		String[] arrA = a.replaceAll("\\s+", "").split("");
		String[] arrB = b.replaceAll("\\s+", "").split("");
		if(arrA.length != arrB.length) {return false;}
		int indA = 0;
		int indB = 0;
		for(String letterA:arrA) {
			if(indB == arrB.length) {return false;}
			indB = 0;
			for(String letterB:arrB) {
				if(letterB != null && letterA.equals(letterB)) {
					arrA[indA] = null;
					arrB[indB] = null;
					indA++;
					break;
				}
				indB++;
			}
		}
		return ((indA > indB && indA == arrA.length)? true:false);
		
	}
}