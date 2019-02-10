package il.ac.tau.cs.sw1.ex5;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
	
	private boolean contain(String[] array, String str) { // check if a string array contain a specific string
		for(String currString: array) {
			if (str.equals(currString)) {
				return true;
			}
		}
		return false;
	}
	
	private int containIndex(String[] array, String str) { // check if a string array contain a specific string
		int ind = 0;
		for(String currString: array) {
			if (str.equals(currString)) {
				return ind;
			}
			ind++;
		}
		return -1;
	}
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		Scanner scanner = new Scanner(new File(fileName));
		int [][] countArray = new int[vocabulary.length][vocabulary.length];
		
		while (scanner.hasNextLine()){
			String[] line = scanner.nextLine().split(" ");
			for(int i = 0; i < line.length-1; i++) {
				String word1 = convertWord(line[i]);
				if(word1 != null) {
					int word1Ind = containIndex(vocabulary, word1);
					if(word1Ind > -1) {
						String word2 = convertWord(line[i+1]);
						if(word2 != null) {
							int word2Ind = containIndex(vocabulary, word2);
							if(word2Ind > -1) {
								countArray[word1Ind][word2Ind]++;
								continue;
							}
						}
						i++;
					}
				}
			}
		}
		return countArray;
	}
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		PrintWriter vocWriter = new PrintWriter(fileName+ ".voc", "UTF-8");
		vocWriter.println(mVocabulary.length + " words");
		int ind = 0;
		for(String word: mVocabulary) {
			vocWriter.println(ind + "," + word);
			ind++;
		}
		vocWriter.close();
		
		PrintWriter countsWriter = new PrintWriter(fileName+ ".counts", "UTF-8");
		for(int i = 0; i < mBigramCounts.length; i++) {
			for(int j = 0; j < mBigramCounts.length; j++) {
				if(mBigramCounts[i][j] > 0) {
					countsWriter.println(i + "," + j + ":" + mBigramCounts[i][j]);
				}
			}
		}
		countsWriter.close();
	}
	
	private int countLinesInFile(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		int count = 0; 
		while (scanner.hasNextLine()) {
			scanner.nextLine();
			count++;
		}
		return count;
	}
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		String vocFileName = fileName +".voc";
		int lines = countLinesInFile(vocFileName) - 1;
		Scanner vocScanner = new Scanner(new File(vocFileName));
		this.mVocabulary = new String[lines];
		int currLine = 0;
		vocScanner.nextLine();
		while (vocScanner.hasNextLine()) {
			String word = vocScanner.nextLine();
			mVocabulary[currLine] = word.substring(2);
			currLine++;
		}
		Scanner countsScanner = new Scanner(new File(fileName + ".counts"));
		this.mBigramCounts = new int[lines][lines];
		while (countsScanner.hasNextLine()) {
			String lineOfText = countsScanner.nextLine();
			int i = Integer.parseInt(lineOfText.substring(0,1));
			int j = Integer.parseInt(lineOfText.substring(2,3));
			int howMany = Integer.parseInt(lineOfText.substring(4));
			mBigramCounts[i][j] = howMany;
		}
	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: word is in lowercase
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		return containIndex(mVocabulary, word);
	}
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int word1Ind = getWordIndex(word1);
		int word2Ind = getWordIndex(word2);
		if(word1Ind > -1 && word2Ind > -1) {
			return mBigramCounts[word1Ind][word2Ind];
		}
		return 0;
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int lineIndex = getWordIndex(word);
		int[] wordArrLine = mBigramCounts[lineIndex];
		int maxFreq = 0;
		int MaxWordIndex = -1;
		int ind = 0;
		for(int currFreq: wordArrLine) {
			if(currFreq > maxFreq) {
				maxFreq = currFreq;
				MaxWordIndex = ind;
			}
			ind++;
		}
		if(MaxWordIndex == -1) {return null;}
		return mVocabulary[MaxWordIndex];
	}
	
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String[] sentenceArr = sentence.split(" ");
		String firstWord = sentenceArr[0];
		for(String secondWord: Arrays.copyOfRange(sentenceArr, 1, sentenceArr.length)) {
			if(getBigramCount(firstWord, secondWord) == 0){return false;} 
			firstWord = secondWord;
		}
		return true;
	}
	
	private static double squareSumSquart(int[] vector) {
		int sum = 0;
		for(int currInt: vector) {
			sum += currInt * currInt;
		}
		return Math.sqrt(sum);
	}
	
	private static int productSum(int[] vector1, int[] vector2) {
		int sum = 0;
		for(int i = 0; i < vector1.length; i++) {
			sum += vector1[i] * vector2[i];
		}
		return sum;
	}
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = 0, otherwise
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		double ans = 0;
		int numerator = productSum(arr1, arr2);
		if(numerator != 0) {
			ans = (double) numerator / (squareSumSquart(arr1) * squareSumSquart(arr2));
		}
		
		return ans;
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		int wordIndex = getWordIndex(word);
		int[] wordVector = mBigramCounts[wordIndex];
		double maxRelation = 0;
		int closestWordInd = wordIndex;
		for(int ind = 0; ind < mBigramCounts.length; ind++) {
			if(ind == wordIndex) {continue;}
			int[] currWordVector = mBigramCounts[ind];
			double cosineSim = calcCosineSim(wordVector, currWordVector);
			if(cosineSim > maxRelation) {
				maxRelation = cosineSim;
				closestWordInd = ind;
			}
		}
		return mVocabulary[closestWordInd];
	}
}
