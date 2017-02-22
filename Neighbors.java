/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Dhruv Verma
 * dv7229
 * 16230
 * Daniel Laveman
 * DEL824
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/dhruv1702/WordLadder
 * Spring 2017
 */


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
	 * returns ArrayList of all words from dict that are one character apart from start (removes words that change the same char as start)
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
		
		if(!visited.isEmpty()){
			String lastVisited = visited.get(visited.size()-1);
			int indexChanged = 0;
			for (int i = 0; i < lastVisited.length(); i++){
				if(start.charAt(i) != lastVisited.charAt(i)){
					indexChanged = i;
				}
			}
			
			ArrayList<String> toRemove = new ArrayList<String>();
			for (String all: neighborList){
				if (all.charAt(indexChanged) != start.charAt(indexChanged)){
					toRemove.add(all);
				}
			}
			neighborList.removeAll(toRemove);
		}
		return neighborList;

	}
	
}
