package edu.cmu.andrew.similarity;

import java.util.List;
import edu.cmu.andrew.data.DataMatrix;
import java.util.Map;
/**
 * interface for user similarity vector
 * @author tao
 *
 */
public interface SimilarityVector {
	public List<Similarity> computeSimilarity(DataMatrix matrix, int userID);
	//public List<Similarity> getSimilarityVector();
}
