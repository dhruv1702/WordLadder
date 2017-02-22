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
	//public static ArrayList<String> finalPath;
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
			ArrayList<String> ladderBFS = getWordLadderBFS(ladder.get(0),ladder.get(2));
			
            printLadder(ladderDFS);
            printLadder(ladderBFS);
			ladder = parse(kb);
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
		neighbors = new Neighbors();
		visited = new ArrayList<String>();
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
		String next= keyboard.next();
		if(word.equals("/quit")||next.equals("/quit")){
			return parseLadder;
		}
		else{
			parseLadder.add(word);
			parseLadder.add("0");
			parseLadder.add(next);
		}
		return parseLadder;
	}
	

    public static ArrayList<String> getWordLadderDFS(String start, String end) {
    	start = start.toUpperCase();
		end = end.toUpperCase();
		Set<String> dict = makeDictionary();
		ArrayList<String> neighborsList = new ArrayList<String>();
		neighborsList = neighbors.findNeighbors(start, dict, visited);
		
		// base case (end adjacent to calling word)
		if (neighborsList.contains(end)) {
			ArrayList<String> finalPath = new ArrayList<String>();
			finalPath.add(end);
			finalPath.add(start);
			if (levelsDFS == 0) {
				startWord = start;
				endWord = finalPath.get(0);
				Collections.reverse(finalPath);
			}
			return finalPath;
		}
		
		// no path
		if (neighborsList.isEmpty()) {
			if (levelsDFS == 0) {
				startWord = start;
				endWord = end;
				ArrayList<String> emptyArray = new ArrayList<String>();
				emptyArray.add(startWord);
				emptyArray.add(endWord);

				return emptyArray;
			}
			return null;
		}
		
		visited.add(start);
		ArrayList<String> path = new ArrayList<String>();
		for (String eachWord : neighborsList) {
			levelsDFS++;
			path = getWordLadderDFS(eachWord, end);
			levelsDFS--;
			
			if (path == null) {
				visited.add(eachWord);
				continue;
			}
			else{
				break;
			}
		}
		if (path == null) {
			if (levelsDFS == 0) {
				startWord = start;
				endWord = end;
				ArrayList<String> emptyArray = new ArrayList<String>();
				emptyArray.add(startWord);
				emptyArray.add(endWord);
				return emptyArray;
			}
			return null;
		}
		path.add(start);
		if (levelsDFS == 0) {
			startWord = start;
			endWord = path.get(0);
			Collections.reverse(path);
			visited.clear();
		}
		return path;
    }
    
	
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {

		ArrayList<String> result=new ArrayList<String>();
    	Queue<String> frontier=new LinkedList<String>();
		ArrayList<String> wordList=new ArrayList<String>();
		Set<String> dict = makeDictionary();
		ArrayList<String> black=new ArrayList<String>();
		String nextWord;
		startWord=start.toUpperCase();
		endWord=end.toUpperCase();
		
		frontier.add(startWord);
		while(frontier.size()!=0){
			nextWord=frontier.element();
			if(nextWord.equals(end)){
				return result;
			}
			wordList=checkDictBFS(nextWord,endWord,wordList);
				for(int j=0;j<wordList.size();j++){
					String word=wordList.get(j);
					if(dict.contains(word)&&(!frontier.contains(word))&&(!black.contains(word))){
						if(word.equals(endWord)){
							black.add(frontier.remove());
							String last=word;
							result.add(last);
							while(!last.equals(startWord)){
								if((neighbors.isNeighbor(last, black.get(black.size()-1)))&&(!result.contains(black.get(black.size()-1)))){
									last=black.remove(black.size()-1);
									result.add(0,last);
								}
								else{
									black.remove(black.size()-1);
								}
							}
							for(int k=result.size()-1;k>1;k--){
								for(int l=0;l<k-1;l++){
									if(neighbors.isNeighbor(result.get(l), result.get(k))){
										for(int count=l+1;count<k;count++){
											result.remove(l+1);
										}
										k=result.size()-1;
										l=0;
									}
								}
							}
							return result;
						}
						frontier.add(word);
					}
				}
				black.add(frontier.remove());
				wordList.clear();
		}
		result.add(startWord);
		result.add(endWord);
		return result;
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
		if ((ladder.get(0).equals(startWord) && ladder.get(1).equals(endWord))||(ladder.isEmpty())) {
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
	
	private static ArrayList<String> checkDictBFS(String newWord,String end,ArrayList<String> wordList){	
		 for(int i=0;i<newWord.length();i++){
				for(char letter='A';letter<='Z';letter++){
					if(newWord.charAt(i)==letter){
						letter++;
		 			}
					else{
						String tempWord=newWord.substring(0, i)+letter+newWord.substring(i+1);
	 					wordList.add(tempWord);
					}
				}
		 }
		 return wordList;
	}
}