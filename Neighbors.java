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
	public ArrayList<String> getNeighbors(String start, Set<String> dict, ArrayList<String> visited){
		ArrayList<String> neighbors = new ArrayList<String>();
		for (String s: dict){
			if(isNeighbor(start,s) && !visited.contains(s)) {
				neighbors.add(s);
			}
		}
	
		return neighbors;
	}
	
}
