/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Spring 2017
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	public static int levelsDFS;
	public static ArrayList<String> ladder;
	public static ArrayList<String> finalPath;
	public static ArrayList<String> visited;
	public static String startWord, endWord;
	
	static Neighbors neighbors;
	// static variables and constants only here.
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out;			// default to Stdout
		}
		// TODO methods to read in words, output ladder
		initialize();
		ladder = parse(kb);
		
		while (!ladder.isEmpty()){
			ArrayList<String> ladderDFS = getWordLadderDFS(ladder.get(0),ladder.get(2));
		//	ArrayList<String> ladderBFS = getWordLadderBFS(ladder.get(0),ladder.get(2));
			
            printLadder(ladderDFS);
           // printLadder(ladderBFS);
			ladder = parse(kb);
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		neighbors = new Neighbors();
		levelsDFS = 0;
	}
	
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> parseLadder=new ArrayList<String>();
		String word = keyboard.next();
		if(word.equals("/quit")){
			return parseLadder;
		}
		else{
			parseLadder.add(word);
			parseLadder.add("2");
			parseLadder.add(keyboard.next());
		}
		return parseLadder;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		startWord = start.toUpperCase();
		endWord = end.toUpperCase();
		
		ArrayList<String> neighborsList = new ArrayList<String>();
		neighborsList = neighbors.findNeighbors(startWord, dict, visited);
		
		//base case (end adjacent to calling word)
		if (neighborsList.contains(endWord)){
			finalPath.add(endWord);
			finalPath.add(startWord);
			if (levelsDFS == 0){
				Collections.reverse(finalPath);
				return finalPath;
			}
		}
		
		if(neighborsList.isEmpty()){
			if (levelsDFS == 0){
				finalPath.add(startWord);
				finalPath.add(endWord);
				return finalPath;
			}
			return null;
		}
		
		visited.add(startWord);
		ArrayList<String> path = new ArrayList<String>();
		for(String eachWord: neighborsList){
			levelsDFS++;
			path = getWordLadderDFS(eachWord, endWord);
			levelsDFS--;
			if (path == null){
				visited.add(eachWord);

			}
/*			else { // Found a working path
				break;
			}*/
		}
		
		path.add(start);
		if (levelsDFS == 0) { // Finished ladder
			startWord = start;
			endWord = path.get(0);
			visited.clear();
			Collections.reverse(path);
		}
		return path;	
		
	}
		
		
	
	
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.get(0).equals(startWord) && ladder.get(1).equals(endWord)) {
            System.out.println("no word ladder can be found between " + startWord.toLowerCase() + " and " + endWord.toLowerCase() + ".");
            return;
        }
        

		System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + startWord.toLowerCase() + " and " + endWord.toLowerCase() + ".");
        for (int i = 0; i < ladder.size(); i++) {
            System.out.println(ladder.get(i).toLowerCase());
        }
        return;
	}
	// TODO
	// Other private static methods here
}
