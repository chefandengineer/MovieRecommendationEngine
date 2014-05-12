package edu.cmu.andrew.data;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * This class represents the user vector, per each user, a list of rating is
 * generated
 * 
 * @author tao
 * 
 */
public class UserVector implements SubjectVector {
	private int userID;
	private List<Rating> ratingList;
	private List<Integer> movieIDList;
	private double avgRating;
	public double standardDeviation;

	/**
	 * Construct a user vector
	 * 
	 * @param line
	 */
	public UserVector(String line) {
		// Get the vector array
		// The format of the vector would be UserID movieID:Rating
		String[] vectorArr = line.split("\\s");
		this.userID = Integer.parseInt(vectorArr[0]);
		this.ratingList = new ArrayList<Rating>();
		this.movieIDList = new ArrayList<Integer>();
		this.avgRating = Double.parseDouble(vectorArr[vectorArr.length - 1]);
		double deviation = 0;
		// Adding the rates to the ratingList
		for (int i = 1; i < vectorArr.length - 2; i++) {
			String[] entryArr = vectorArr[i].split(":");
			int movieID = Integer.parseInt(entryArr[0]);
			double rate = Double.parseDouble(entryArr[1]);
			deviation += (rate - avgRating) * (rate - avgRating);
			Rating newRate = new Rating(movieID, rate);
			ratingList.add(newRate);
			movieIDList.add(movieID);
		}
		this.standardDeviation = Math.sqrt(deviation);

		// Set up the normalized Rating
		for (Rating rating : ratingList) {
			double rate = rating.getRate();
			double normalizedRate = (rate - avgRating) / standardDeviation;
			normalizedRate = normalizedRate * 5;
			rating.setNormalizedRate(normalizedRate);
		}
	}

	public double getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}

	public Rating getSubjectRating(int subjectID) {
		int index = movieIDList.indexOf(subjectID);
		if (index != -1) {
			return ratingList.get(index);
		}
		return null;
	}

	public int getSubjectID() {
		return userID;
	}

	public void setSubjectID(int userID) {
		this.userID = userID;
	}

	public List<Rating> getRatingList() {
		return ratingList;
	}

	public void setRatingList(List<Rating> ratingList) {
		this.ratingList = ratingList;
	}
}
