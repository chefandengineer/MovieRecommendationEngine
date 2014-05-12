package edu.cmu.andrew.neighbor;

import edu.cmu.andrew.data.DataMatrix;
import edu.cmu.andrew.data.UserBasedDataMatrix;
import edu.cmu.andrew.similarity.*;

import java.io.File;
import java.util.List;
import java.util.Collections;
import edu.cmu.andrew.data.*;
import java.util.ArrayList;
/**
 * this class calculate the neighbor for a given user or movie
 * it is based on the query, it takes the K neighbor that has the rate
 * for that movie
 * @author tao
 *
 */
public class KNearestUserNeighbor implements Neighbor {

	private List<Similarity> kNearestNeighbor;
	private List<Similarity> similarityVector;
	private int k;
	private DataMatrix dataMatrix;

	public KNearestUserNeighbor(int k, List<Similarity> similarity,
			DataMatrix dataMatrix) {
		this.similarityVector = similarity;
		this.k = k;
		this.dataMatrix = dataMatrix;
		Collections.sort(similarityVector);
		this.kNearestNeighbor = similarityVector;
	}

	public void print() {
		for (Similarity similarity : kNearestNeighbor) {
			System.out.println(similarity.getSimilarity());
		}
	}

	public List<Similarity> getKNearestNeighborSimilarityList(){
		if(similarityVector.size() <= k){
			return similarityVector;
		}else{
			return similarityVector.subList(0, k+1);
		}
	}
	@Override
	public List<Similarity> getKNearestNeighborSimilarityList(int movieID) {
		List<Similarity> kNearestNeighborSimilarity = new ArrayList<Similarity>();
		if(similarityVector.size() <= k){
			return similarityVector;
		}else{
			for(Similarity similarity : similarityVector){
				int userID = similarity.getSubjectID();
				//	System.out.println("USERID " + userID);
					SubjectVector userVector = dataMatrix.getSubjectVector(userID);
					Rating rating = userVector.getSubjectRating(movieID);
					if (rating != null) {
						kNearestNeighborSimilarity.add(similarity);
						if(kNearestNeighborSimilarity.size() > k){
							break;
						}
					}
			}
			return kNearestNeighborSimilarity;
		}
	}
	public List<Rating> getKNearestNeighborRatingList(int movieID) {
		List<Rating> neighborListForThisMovie = new ArrayList<Rating>();
		for (Similarity similarity : similarityVector) {
			int userID = similarity.getSubjectID();
		//	System.out.println("USERID " + userID);
			SubjectVector userVector = dataMatrix.getSubjectVector(userID);
			Rating rating = userVector.getSubjectRating(movieID);
			if (rating != null) {
				neighborListForThisMovie.add(rating);
				if(neighborListForThisMovie.size() > k){
					break;
				}
			}
		}
		return neighborListForThisMovie;
	}

	public void setkNearestNeighbor(List<Similarity> kNearestNeighbor) {
		this.kNearestNeighbor = kNearestNeighbor;
	}
}
