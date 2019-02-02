package il.ac.tau.cs.sw1.ex5;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14000;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] vocabulary = new  String[MAX_VOCABULARY_SIZE];
		Scanner vocabularyScanner = new Scanner(new File(fileName));
		int vocIndex = 0;
		while (vocabularyScanner.hasNext()){
			String currWord = convertWord(vocabularyScanner.next());
			if(currWord != null && !contain(vocabulary, currWord)) {
				vocabulary[vocIndex++] = currWord;
			}
			if(vocIndex == MAX_VOCABULARY_SIZE) {break;}			
		}
		if (vocIndex == MAX_VOCABULARY_SIZE) {return vocabulary;}
		return Arrays.copyOfRange(vocabulary, 0, vocIndex);
	}
	
	private String convertWord(String word) { // converts the word, or gives back null if the word isn't a legal one 
		char[] charArr = word.toCharArray();
		boolean onlyDigit = true;
		for(char letter: charArr) {
			if(Character.isAlphabetic(letter)) {return word.toLowerCase();}
			if(!Character.isDigit(letter)){onlyDigit = false;}
		}
		if(onlyDigit) {return SOME_NUM;}
		else {return null;}
	}
	
	private boolean contain(String[] array, String str) { // check if a string array contain a specific array
		char[] strCharArr = str.toCharArray();
		for(String currString: array) {
			char[] currCharArr = currString.toCharArray();
			if(strCharArr.length != currCharArr.length) {continue;}
			for(int i=0; i < strCharArr.length; i++) {
				if(strCharArr[i] != currCharArr[i]) {break;}
				if(i == strCharArr.length -1) {return true;}
			}
		}
		return false;
	}
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		// replace with your code
		return null;

	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		// add your code here
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		// add your code here
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		// replace with your code
		return 0;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		// replace with your code
		return 0;
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		// replace with your code
		return null;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		// replace with your code
		return false;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = 0, otherwise
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		// replace with your code
		return 0.;
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		// replace with your code
		return null;
	}
	
}
