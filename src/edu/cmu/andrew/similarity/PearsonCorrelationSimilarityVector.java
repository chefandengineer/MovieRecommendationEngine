package edu.cmu.andrew.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cmu.andrew.data.DataMatrix;
import edu.cmu.andrew.data.Rating;
import edu.cmu.andrew.data.SubjectVector;
/**
 * PCC similarity metrics
 * @author tao
 *
 */
public class PearsonCorrelationSimilarityVector implements SimilarityVector{
	public static Map<Integer, ArrayList<Similarity>> similarityMetrics = new HashMap<Integer, ArrayList<Similarity>>();
	//minimum common subject that two item or users must share
	public static final int MIN_COMMON = 1;
	//minimum number of common movies that might not jepordize the effect of the similarity
	public static final int MIN_EFFECT_COMMON = 4;
	/**
	 * Compute the similarity given the userID and the dataMatrix
	 * @param userID
	 */
	public List<Similarity> computeSimilarity(DataMatrix matrix, int targetID) {
		List<Similarity> similarityVector = new ArrayList<Similarity>();
		// check if the similarity vector is cashed
		if (DotProductSimilarityVector.similarityMetrics.containsKey(targetID)) {
			similarityVector = DotProductSimilarityVector.similarityMetrics.get(targetID);
			return similarityVector;
		}
		List<SubjectVector> subjectVectorList = matrix.getSubjectRatingVectors();
		SubjectVector targetRatingVector = matrix.getSubjectVector(targetID);
		for (SubjectVector neighborRatingVector : subjectVectorList) {
			// Avoid calculating with the same user
			if (neighborRatingVector.getRatingList().size() <= 1) {
				continue;
			}
			int neighborID = neighborRatingVector.getSubjectID();
			double similarity = 0;
			double product = 0;
			double targetVectorSum = 0;
			double neighborVectorSum = 0;
			
			double targetAvg = targetRatingVector.getAvgRating();
			if (neighborID != targetID) {
				// statistic variable
				List<Rating> targetRatingList = targetRatingVector.getRatingList();
				// Generate the DotProductSimilarity vector
				List<Rating> neighborRatingList = neighborRatingVector.getRatingList();
				
				//averageRating
				double neighborAvg = neighborRatingVector.getAvgRating();

				int neighborIndex = 0;
				int targetIndex = 0;
				// Compute the dot similarity between the target user and
				// current user in the vector
				int commonMovie = 0;

				while (neighborIndex < neighborRatingList.size()
						&& targetIndex < targetRatingList.size()) {
					// get the rating and movie ID
					Rating neighborRating = neighborRatingList.get(neighborIndex);
					Rating targetRating = targetRatingList.get(targetIndex);
					int neighborMovieID = neighborRating.getMovieID();
					int targetMovieID = targetRating.getMovieID();

					// Only when they are rating the same movieID
					if (neighborMovieID == targetMovieID) {
						double neighborRate = neighborRating.getRate();
						double targetRate = targetRating.getRate();
						product += (neighborRate - neighborAvg) * (targetRate - targetAvg);
						targetVectorSum += (targetRate - targetAvg) * (targetRate - targetAvg);
						neighborVectorSum += (neighborRate - neighborAvg) * (neighborRate - neighborAvg);						
						neighborIndex++;
						targetIndex++;
						commonMovie++;
					} else if (neighborMovieID < targetMovieID) {
						neighborIndex++;
					} else {
						targetIndex++;
					}
				}
				if (commonMovie < MIN_COMMON) {
					continue;
				}
				//the minimum number that can take effect
				int effectiveCommon = Math.min(MIN_EFFECT_COMMON,commonMovie);
				double normOfUserVector = Math.sqrt(targetVectorSum);
				double normOfNeighborVector = Math.sqrt(neighborVectorSum);
				double lengthEffect = (double)effectiveCommon /(double)MIN_EFFECT_COMMON; 
				similarity = lengthEffect * product / (double)(normOfUserVector * normOfNeighborVector);
				UserAndUserSimilarity userSimilarity = new UserAndUserSimilarity(neighborID, similarity);
				similarityVector.add(userSimilarity);
			}
		}
		DotProductSimilarityVector.similarityMetrics.put(targetID,
				(ArrayList<Similarity>) similarityVector);
		return similarityVector;
	}
}
