package edu.cmu.andrew.recommender;

import edu.cmu.andrew.data.DataMatrix;
import edu.cmu.andrew.data.UserBasedDataMatrix;
import edu.cmu.andrew.neighbor.Neighbor;
import java.io.File;
import java.util.List;
import edu.cmu.andrew.similarity.*;
import java.util.Map;
import java.util.HashMap;
import edu.cmu.andrew.data.*;

public class UserBasedRecommender implements Recommender{

	Map<String, Double> recommendedList = null;

	public UserBasedRecommender() {
		recommendedList = new HashMap<String, Double>();
	}
	/**
	 * This recommendation method takes the weight of each neighbor
	 */
	public double recommendWeightSumRating(Neighbor neighbor,
			DataMatrix dataMatrix, int userID, int recommendMovieID) {
		List<Similarity> neighborSimilarityList = neighbor.getKNearestNeighborSimilarityList();
		double recommendRating = 0;
		double totalSimilarity = 0;

		// iterating the neighbor to get the similarity of each
		for (Similarity similarity : neighborSimilarityList) {
			int neighborUserID = similarity.getSubjectID();
			double similarityValue = similarity.getSimilarity();
			SubjectVector neighborUserVector = (UserVector)dataMatrix.getSubjectVector(neighborUserID);
			Rating rating = neighborUserVector
					.getSubjectRating(recommendMovieID);
			// This neighbor has rated the movie
			if (rating != null) {
				double ratingValue = rating.getRate();
				recommendRating += ratingValue * similarityValue;
				totalSimilarity += similarityValue;
			}
		}
		if (totalSimilarity == 0 || recommendRating == 0) {
			return 0;
		}
		recommendRating = recommendRating / totalSimilarity;
		if (recommendRating < 1){
			recommendRating = Math.round(recommendRating + 0.5);
		}
		if(recommendRating == Double.NaN){
			System.err.println("NaN");
		}
		return recommendRating;
	}
	/**
	 * This recommendation method takes the weight of each neighbor
	 */
	public double recommendWeightSumNormalizedRating(Neighbor neighbor,
			DataMatrix dataMatrix, int userID, int recommendMovieID) {
		List<Similarity> neighborSimilarityList = neighbor.getKNearestNeighborSimilarityList();
		double recommendRating = 0;
		double totalSimilarity = 0;

		// iterating the neighbor to get the similarity of each
		for (Similarity similarity : neighborSimilarityList) {
			int neighborUserID = similarity.getSubjectID();
			double similarityValue = similarity.getSimilarity();
			SubjectVector neighborUserVector = (UserVector)dataMatrix.getSubjectVector(neighborUserID);
			Rating rating = neighborUserVector
					.getSubjectRating(recommendMovieID);
			// This neighbor has rated the movie
			if (rating != null) {
				double ratingValue = rating.getNormalizedRate();
				recommendRating += ratingValue * similarityValue;
				totalSimilarity += similarityValue;
			}
		}
		if (totalSimilarity == 0 || recommendRating == 0) {
			return 0;
		}
		recommendRating = recommendRating / totalSimilarity;
		if (recommendRating < 1){
			recommendRating = Math.round(recommendRating + 0.5);
		}
		return recommendRating;
	}
	/**
	 * 
	 */
	public double recommendCustomRating(Neighbor neighbor, DataMatrix dataMatrix, int userID, int recommendMovieID){
		List<Similarity> neighborSimilarityList = neighbor.getKNearestNeighborSimilarityList();
		double recommendRating = 0;
		double totalSimilarity = 0;
		UserVector targetUserVector = (UserVector)dataMatrix.getSubjectVector(userID);
		double targetAvgRating = targetUserVector.getAvgRating();
		// iterating the neighbor to get the similarity of each
		for (Similarity similarity : neighborSimilarityList) {
			int neighborUserID = similarity.getSubjectID();
			double similarityValue = similarity.getSimilarity();
			if(Double.isInfinite(similarityValue) || similarityValue == 0){
				continue;
			}
			SubjectVector neighborUserVector = dataMatrix.getSubjectVector(neighborUserID);
			Rating rating = neighborUserVector.getSubjectRating(recommendMovieID);
			double neighborAvg = neighborUserVector.getAvgRating();
			// This neighbor has rated the movie
			if (rating != null) { 
				double ratingValue = rating.getRate();
				recommendRating += (ratingValue - neighborAvg) * similarityValue;
				totalSimilarity += similarityValue;
			}
		}
		if (totalSimilarity == 0 || recommendRating == 0) {
			return 0;
		}
		
		if(Double.isNaN(recommendMovieID) || Double.isNaN(totalSimilarity)){
			return targetAvgRating;
		}
		recommendRating = recommendRating / totalSimilarity;
		recommendRating = targetAvgRating + recommendRating;
		if (recommendRating < 1){
			recommendRating = (double) 1;
		}
		return recommendRating;
	}
	/**
	 * Scoring the subject using the mean rating
	 */
	public double recommendMeanRating(Neighbor neighbor, DataMatrix dataMatrix,
			int userID, int qMovieID) {
		List<Rating> neighborSimilarityList = neighbor
				.getKNearestNeighborRatingList(qMovieID);
		double recommendRating = 0;
		int counter = 0;
		// After getting the neighbor list
		for (Rating rating : neighborSimilarityList) {
			counter++;
			double thisRating = rating.getRate();
			recommendRating += thisRating;
		}
		// If no neighbor in the K nearest neighborhood has rated the movie
		if (recommendRating == 0) {
			return 0;
		}
		recommendRating = recommendRating / (double) counter;
		double score = 0;
		//round the score to 1 if it is less than 1
		if (recommendRating < 1){
			score = Math.round(recommendRating + 0.5);
		}
		return score;
	}

	/**
	 * Recommend with normalized user and movie rating from the data set
	 * 
	*/
	public double recommendNormalizedMeanRating(Neighbor neighbor, DataMatrix dataMatrix,
			int userID, int qMovieID) {
		List<Rating> neighborSimilarityList = neighbor
				.getKNearestNeighborRatingList(qMovieID);
		double recommendRating = 0;
		int counter = 0;
		// After getting the neighbor list
		for (Rating rating : neighborSimilarityList) {
			counter++;
			double thisRating = rating.getNormalizedRate();
			recommendRating += thisRating;
		}
		// If no neighbor in the K nearest neighborhood has rated the movie
		if (recommendRating == 0) {
			return 0;
		}
		recommendRating = recommendRating / (double) counter;
		long score = 0;
		//round the score to 1 if it is less than 1
		if (recommendRating < 1){
			score = Math.round(recommendRating + 0.5);
		}else{
			score = Math.round(recommendRating);
		}
		return score;
	}
}