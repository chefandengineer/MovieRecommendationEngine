package edu.cmu.andrew.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class defines the user based data matrix
 * @author Tao Jiang
 *
 */
public class UserBasedDataMatrix implements DataMatrix{
	
	private List<SubjectVector> userRatingVectors;
	private Map<Integer,Integer> userIDRatingVectorTable;

	public UserBasedDataMatrix(){
		this.userRatingVectors = new ArrayList<SubjectVector>();
		this.userIDRatingVectorTable = new HashMap<Integer,Integer>();
	}
	/**
	 * Load the file from the data File, constructing the the user vector
	 */
	public void loadData(File dataFile) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(dataFile));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		try {
			String line = null;
			int counter = 0;
			while((line = reader.readLine()) != null){
				UserVector userVector = new UserVector(line);
				userRatingVectors.add(userVector);
				int userID = userVector.getSubjectID();
				userIDRatingVectorTable.put(userID, counter++);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * Returns the user vector
	 * @param userID
	 * @return userVector
	 */
	public SubjectVector getSubjectVector(int userID){
			if(userIDRatingVectorTable == null){
				System.out.println("it's null");
			}
			if(!userIDRatingVectorTable.containsKey(userID)){
				System.out.println("it does not contain");
			}
			int userVectorIndex = userIDRatingVectorTable.get(userID);
			SubjectVector userVector = userRatingVectors.get(userVectorIndex);
			return userVector;
	}
	/**
	 * 
	 * @return
	 */
	public List<SubjectVector> getSubjectRatingVectors() {
		return userRatingVectors;
	}
	public void setUserRatingVectors(List<SubjectVector> userRatingVectors) {
		this.userRatingVectors = userRatingVectors;
	}
	public Map<Integer, Integer> getUserIDRatingVectorTable() {
		return userIDRatingVectorTable;
	}
	public void setUserIDRatingVectorTable(
			Map<Integer, Integer> userIDRatingVectorTable) {
		this.userIDRatingVectorTable = userIDRatingVectorTable;
	}
	
	
	public static void main(String[] args){
		DataMatrix userbasedDataMatrix = new UserBasedDataMatrix();
		userbasedDataMatrix.loadData(new File("user_movie_rating_list.txt"));
		
	}
}
