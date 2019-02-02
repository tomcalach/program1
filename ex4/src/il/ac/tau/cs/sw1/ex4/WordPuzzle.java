package il.ac.tau.cs.sw1.ex4;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	public static final int MAX_VOCABULARY_SIZE = 3000;
	
	
	public static String[] scanVocabulary(Scanner scanner){          // Q - 1
		String text = "";
		while (scanner.hasNextLine()) {
			text += scanner.nextLine().toLowerCase() + ' ';
		}
		String[] arr = text.split("\\p{javaWhitespace}+");
		Arrays.sort(arr);
		if(arr.length > MAX_VOCABULARY_SIZE) {
			return Arrays.copyOfRange(arr, 0, MAX_VOCABULARY_SIZE-1);
		}
		return arr;
	}
	
	public static boolean isInVocabulary(String[] vocabulary, String word){ // Q - 2
		for(String checkWord: vocabulary) {
			if(word.equals(checkWord)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isLegalPuzzleStructure(char[] puzzle){  // Q - 3
		int countHidden = 0;
		for (char i: puzzle) {
			if(!(Character.isLowerCase(i) || i == HIDDEN_CHAR)) {
				return false;
			}
			if(i == HIDDEN_CHAR) {countHidden++;}
		}
		if(countHidden == 0 ) {return false;}
		return true;
	}
	
	public static int countHiddenInPuzzle(char[] puzzle){ // Q - 4
		int count = 0;
		for (char chr: puzzle) {
			if(chr == HIDDEN_CHAR) {
				count++;
			}
		}
		return count;
	}
	
	public static boolean checkSolution(char[] puzzle, String word){ // Q - 5
		if(puzzle.length != word.length()) {
			return false;
		}
		for (int i = 0; i < word.length(); i++) {
			String letter = word.substring(i, i+1);
			if (puzzle[i] == HIDDEN_CHAR) {
				if(new String(puzzle).contains(letter)) {
					return false;
				}
			}else if (letter.charAt(0) != puzzle[i]) {
				return false;
		}
			
		}
		return true;
	}
	
	public static String[] getSolution(char[] puzzle, String[] vocabulary){ // Q - 6
		String[] posibleAns = new String[vocabulary.length];
		int countAns=0;
		for (String word: vocabulary) {
			if(checkSolution(puzzle, word)) {
				posibleAns[countAns++] = word;
			}
		}
		if(countAns > 0 ) {
			return Arrays.copyOfRange(posibleAns, 0, countAns);
		}
		return null;
	}
	
	public static int applyGuess(char guess, String solution, char[] puzzle){ // Q - 7
		int countHits = 0;
		for(int i = 0 ; i < puzzle.length; i++) {
			if(puzzle[i] == HIDDEN_CHAR && guess == solution.charAt(i)) {
				countHits++;
				puzzle[i] = guess;
			}
		}
		return countHits;
	}

	public static void main(String[] args) throws Exception{ //Q - 8
		Scanner vocScanner = new Scanner(new File(args[0])); 
		String[] vocabulary = scanVocabulary(vocScanner); // make vocabulary
		printReadVocabulary(args[0], vocabulary.length); // prints the number of words and file name
		printSettingsMessage(); // prints setting message
		
		Scanner puzzleScanner = null;
		char[] puzzle = null;
		String[] solutionArr = null;
		while(puzzleScanner == null || !isLegalPuzzleStructure(puzzle) || solutionArr == null) {
			if (puzzleScanner != null) {printIllegalPuzzleMessage(); }
			printEnterPuzzleMessage(); // asks the user to insert a puzzle
			puzzleScanner = new Scanner(System.in); // read a puzzle from user
			puzzle = puzzleScanner.nextLine().toCharArray(); // changing the user input into a puzzle format
			solutionArr = getSolution(puzzle, vocabulary); // checks how many solutions are there
		}
		String solution;
		if(solutionArr.length > 1) { // if there is more than 1 optional solution, let the user decide
			printIllegalSolutionsNumberMessage(solutionArr);
			Scanner solutionNum = new Scanner(System.in);
			solution = solutionArr[solutionNum.nextInt()-1];
		} else {solution = solutionArr[0];} // if not use the only solution
		
		
		printGameStageMessage(); // prints game stage
		int tries = 3;
		int hidden = 0;
		for(char letter: puzzle) {
			if(letter == '_') {tries++; hidden++;}
		}
		
		while(true) {
			// game round
			printPuzzle(puzzle);
			printEnterYourGuessMessage();
			Scanner guessScanner = new Scanner(System.in);
			char guess = guessScanner.next().charAt(0);
			int hits = applyGuess(guess, solution, puzzle);
			hidden -= hits;
			--tries;
			if(hidden == 0) { printWinMessage(); break;}
			else if(hits > 0) {printCorrectGuess(tries);}
			else {printWrongGuess(tries);}
			if(tries == 0) {printGameOver(); break;}
		}
	}


	/*************************************************************/
	/*********************  Don't change this ********************/
	/*************************************************************/
	
	public static void printReadVocabulary(String vocabularyFileName, int numOfWords){
		System.out.println("Read " + numOfWords + " words from " + vocabularyFileName);
	}

	public static void printSettingsMessage(){
		System.out.println("--- Settings stage ---");
	}
	
	public static void printEnterPuzzleMessage(){
		System.out.println("Enter your puzzle:");
	}
	
	public static void printIllegalPuzzleMessage(){
		System.out.println("Illegal puzzle, try again!");
	}
	
	public static void printIllegalSolutionsNumberMessage(String[] solutions){
		System.out.println("Puzzle doesn't have a single solution, choose a solution!");
		for(int i = 0; i< solutions.length; i++)
		{
			System.out.format("%d	"+solutions[i]+"%n", i+1);
		}
	}
	
	
	public static void printGameStageMessage(){
		System.out.println("--- Game stage ---");
	}
	
	public static void printPuzzle(char[] puzzle){
		System.out.println(puzzle);
	}
	
	public static void printEnterYourGuessMessage(){
		System.out.println("Enter your guess:");
	}
	
	public static void printCorrectGuess(int attemptsNum){
		System.out.println("Correct Guess, " + attemptsNum + " guesses left");
	}
	
	public static void printWrongGuess(int attemptsNum){
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left");
	}

	public static void printWinMessage(){
		System.out.println("Congratulations! You solved the puzzle");
	}
	
	public static void printGameOver(){
		System.out.println("Game over!");
	}

}
