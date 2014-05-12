package edu.cmu.andrew.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cmu.andrew.data.DataMatrix;
import edu.cmu.andrew.data.Rating;
import edu.cmu.andrew.data.SubjectVector;
import edu.cmu.andrew.data.UserBasedDataMatrix;
import edu.cmu.andrew.data.UserVector;

public class CosineSimilarityVector implements SimilarityVector {
	public static Map<Integer, ArrayList<Similarity>> similarityMetrics = new HashMap<Integer, ArrayList<Similarity>>();
	public static final int MIN_COMMON = 1;

	/**
	 * Compute the similarity given the userID and the dataMatrix
	 * 
	 * @param userID
	 */
	public List<Similarity> computeSimilarity(DataMatrix matrix, int userID) {
		List<Similarity> similarityVector = new ArrayList<Similarity>();

		// check if the similarity vector is cashed
		if (DotProductSimilarityVector.similarityMetrics.containsKey(userID)) {
			similarityVector = DotProductSimilarityVector.similarityMetrics
					.get(userID);
			return similarityVector;
		}

		List<SubjectVector> userRatingList = matrix.getSubjectRatingVectors();

		for (SubjectVector userVector : userRatingList) {
			// Avoid calculating with the same user
			if (userVector.getRatingList().size() <= 1) {
				continue;
			}

			int currentUserID = userVector.getSubjectID();

			double similarity = 0;
			double sumOfProduct = 0;
			double sumOfUserVector = 0;
			double sumOfNeighborVector = 0;
			
			if (currentUserID != userID) {
				// stats variable
				UserVector neighborUserVector = (UserVector) matrix
						.getSubjectVector(userID);
				List<Rating> neighborRatingList = neighborUserVector
						.getRatingList();
				// Generate the DotProductSimilarity vector
				List<Rating> currentRatingList = userVector.getRatingList();

				int neighborIndex = 0;
				int currentIndex = 0;
				// Compute the dot similarity between the target user and
				// the
				// current user in the vector
				int commonMovie = 0;

				while (neighborIndex < neighborRatingList.size()
						&& currentIndex < currentRatingList.size()) {
					// get the rating and movie ID
					Rating neighborRating = neighborRatingList
							.get(neighborIndex);
					Rating currentRating = currentRatingList.get(currentIndex);
					int neighborMovieID = neighborRating.getMovieID();
					int currentMovieID = currentRating.getMovieID();

					// Only when they are rating the same movieID
					if (neighborMovieID == currentMovieID) {
						double neighborRate = neighborRating.getRate();
						double currentRate = currentRating.getRate();
						sumOfProduct += neighborRate * currentRate;
						sumOfUserVector += currentRate * currentRate;
						sumOfNeighborVector += neighborRate * neighborRate;

						// update the index
						neighborIndex++;
						currentIndex++;
						commonMovie++;
					} else if (neighborMovieID < currentMovieID) {
						double neighborRate = neighborRating.getRate();
						sumOfNeighborVector += neighborRate * neighborRate;
						neighborIndex++;
					} else {
						double currentRate = currentRating.getRate();
						sumOfUserVector += currentRate * currentRate;
						currentIndex++;
					}
				}
				while (neighborIndex < neighborRatingList.size()) {
					Rating neighborRating = neighborRatingList
							.get(neighborIndex);
					double neighborRate = neighborRating.getRate();
					sumOfNeighborVector += neighborRate * neighborRate;
					neighborIndex++;
				}
				while (currentIndex < currentRatingList.size()) {
					Rating currentRating = currentRatingList.get(currentIndex);
					double currentRate = currentRating.getRate();
					sumOfUserVector += currentRate * currentRate;
					currentIndex++;
				}

				if (commonMovie < MIN_COMMON) {
					continue;
				}
				sumOfUserVector = Math.sqrt(sumOfUserVector);
				sumOfNeighborVector = Math.sqrt(sumOfNeighborVector);
				similarity = sumOfProduct / (sumOfUserVector * sumOfNeighborVector);
				UserAndUserSimilarity userSimilarity = new UserAndUserSimilarity(currentUserID, similarity);
				similarityVector.add(userSimilarity);
			}
		}
		DotProductSimilarityVector.similarityMetrics.put(userID,
				(ArrayList<Similarity>) similarityVector);
		return similarityVector;
	}
}
