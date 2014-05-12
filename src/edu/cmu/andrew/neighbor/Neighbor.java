package edu.cmu.andrew.neighbor;

import java.util.List;
import edu.cmu.andrew.data.*;
import edu.cmu.andrew.similarity.*;

public interface Neighbor {
	public List<Similarity> getKNearestNeighborSimilarityList(int movieID);
	public List<Rating> getKNearestNeighborRatingList(int subjectID);
	public List<Similarity> getKNearestNeighborSimilarityList();
}
