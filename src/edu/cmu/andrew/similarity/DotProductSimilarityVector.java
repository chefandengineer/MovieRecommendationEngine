package edu.cmu.andrew.similarity;
import java.util.List;
import java.util.ArrayList;
import edu.cmu.andrew.data.*;
import java.util.Map;
import java.util.HashMap;

public class DotProductSimilarityVector implements SimilarityVector {
	public static Map<Integer, ArrayList<Similarity>> similarityMetrics = new HashMap<Integer, ArrayList<Similarity>>();
	public static final int MIN_COMMON = 1;
	/**
	 * Compute the similarity given the userID and the dataMatrix
	 * 
	 * @param userID
	 */
	public List<Similarity> computeSimilarity(DataMatrix matrix, int targetID) {
		List<Similarity> similarityVector = new ArrayList<Similarity>();

		// check if the similarity vector is cashed
		if (DotProductSimilarityVector.similarityMetrics.containsKey(targetID)) {
			similarityVector = DotProductSimilarityVector.similarityMetrics.get(targetID);
			return similarityVector;
		}
		List<SubjectVector> userRatingList = matrix.getSubjectRatingVectors();
		
		for (SubjectVector userVector : userRatingList) {
			// Avoid calculating with the same user
			if (userVector.getRatingList().size() <= 1) {
				continue;
			}
			int currentUserID = userVector.getSubjectID();
			if (currentUserID != targetID) {
				double similarity = 0;
				SubjectVector neighborUserVector = matrix
						.getSubjectVector(targetID);
				List<Rating> targetRatingList = neighborUserVector
						.getRatingList();
				// Generate the DotProductSimilarity vector
				List<Rating> currentRatingList = userVector.getRatingList();
				int targetIndex = 0;
				int currentIndex = 0;
				// Compute the dot similarity between the target user and
				// current user in the vector
				int commonMovie = 0;
				// long start4 = System.currentTimeMillis();
				while (targetIndex < targetRatingList.size()
						&& currentIndex < currentRatingList.size()) {
					// get the rating and movie ID
					Rating targetRating = targetRatingList.get(targetIndex);
					Rating currentRating = currentRatingList.get(currentIndex);
					int targetMovieID = targetRating.getMovieID();
					int currentMovieID = currentRating.getMovieID();

					// Only when they are rating the same movieID
					if (targetMovieID == currentMovieID) {
						double targetRate = targetRating.getRate();
						double currentRate = currentRating.getRate();
						similarity += targetRate * currentRate;
						targetIndex++;
						currentIndex++;
						commonMovie++;
					} else if (targetMovieID < currentMovieID) {
						targetIndex++;
					} else {
						currentIndex++;
					}
				}
				if (commonMovie < MIN_COMMON) {
					continue;
				}
				UserAndUserSimilarity userSimilarity = new UserAndUserSimilarity(
						currentUserID, similarity);
				similarityVector.add(userSimilarity);
			}
		}
		DotProductSimilarityVector.similarityMetrics.put(targetID,(ArrayList<Similarity>) similarityVector);
		return similarityVector;
	}
}                                  