package edu.cmu.andrew.recommender;

import edu.cmu.andrew.neighbor.*;
import edu.cmu.andrew.data.*;


/**
 * This class represents a recommender
 * @author Tao Jiang
 *
 */
public interface Recommender {	
	public double recommendWeightSumRating(Neighbor neighbor,DataMatrix dataMatrix, int userID, int recommendMovieID);
	public double recommendMeanRating(Neighbor neighbor, DataMatrix dataMatrix,int userID, int qMovieID);
	public double recommendCustomRating(Neighbor neighbor, DataMatrix dataMatrix, int userID, int recommendMovieID);
	public double recommendNormalizedMeanRating(Neighbor neighbor, DataMatrix dataMatrix,int userID, int recommendMovieID);
	public double recommendWeightSumNormalizedRating(Neighbor neighbor,DataMatrix dataMatrix, int userID, int recommendMovieID);
}

