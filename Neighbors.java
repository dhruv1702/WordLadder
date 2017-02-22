package assignment3;

import java.util.ArrayList;
import java.util.Set;



public class Neighbors {
	
	/**
	 * checks whether two equal length words are only one character apart
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isNeighbor(String start, String end){
		boolean oneCharDiff = false;
		for (int i = 0; i < start.length(); i++){
			if (start.charAt(i) != end.charAt(i)){
				if (oneCharDiff){
					return false;
				}
				else{
					oneCharDiff = true;
				}
			}
		}
		if (oneCharDiff){
			return true;
		}
		return false;
	}
	
	
	/**
	 * returns ArrayList of all words from dict that are one character apart from start
	 * @param start
	 * @param dict
	 * @param visited
	 * @return
	 */
	public ArrayList<String> findNeighbors(String start, Set<String> dict, ArrayList<String> visited){
		ArrayList<String> wordList = new ArrayList<String>();
		 for (int i=0;i<start.length();i++){
				for (char letter = 'A'; letter <= 'Z'; letter++){
					if (start.charAt(i) == letter){
						letter++;
		 			}
					else{
						String tempWord = start.substring(0, i) + letter + start.substring(i+1);
	 					wordList.add(tempWord);
					}
				}
		 }

		ArrayList<String> neighborList = new ArrayList<String>();
		for (String s: wordList){
			if(dict.contains(s) && !visited.contains(s)) {
				neighborList.add(s);
			}
		}
		return neighborList;
	}
	
}
