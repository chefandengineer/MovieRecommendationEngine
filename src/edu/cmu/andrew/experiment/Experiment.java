package edu.cmu.andrew.experiment;

import java.io.File;
import edu.cmu.andrew.data.DataMatrix;
import edu.cmu.andrew.data.UserBasedDataMatrix;
import edu.cmu.andrew.neighbor.KNearestUserNeighbor;
import edu.cmu.andrew.neighbor.Neighbor;
import edu.cmu.andrew.recommender.Recommender;
import edu.cmu.andrew.recommender.UserBasedRecommender;
import edu.cmu.andrew.similarity.SimilarityVector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import edu.cmu.andrew.similarity.*;

public class Experiment {
	ArrayList<String> queryPairs = new ArrayList<String>();

	public void loadQueryData(String file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(file)));
			String line = null;
			String movieID = null;
			while ((line = reader.readLine()) != null) {
				// check if it is fa userID
				if (line.matches(".*:$")) {
					System.out.println(line);
					movieID = line;
					continue;
				}
				queryPairs.add(movieID + line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void executeExperiment(int experimentNum) {
		// Experiment 1. user-user similarity metric and weighted sum
		Recommender userbasedRecommender = new UserBasedRecommender();
		DataMatrix userMatrix = new UserBasedDataMatrix();
		userMatrix.loadData(new File("user_movie_rating_list.txt"));
		// File writer for output the file
		BufferedWriter weightedMeanWriter = null;
		// Experiment 1, using a dot product similarity and weighted training
		if (experimentNum == 1) {
			System.out.println("This is experiement 1");
			String experimentOneOuputFile = "Experiment1.txt";
			try {
				weightedMeanWriter = new BufferedWriter(new FileWriter(
						new File(experimentOneOuputFile)));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			SimilarityVector similarityVector = new PearsonCorrelationSimilarityVector();
			int lastMovieID = Integer.parseInt(queryPairs.get(0).split(":")[0]);
			try {
				weightedMeanWriter.write(String.valueOf(lastMovieID) + ":");
				weightedMeanWriter.newLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			for (String queryPair : queryPairs) {
				String[] queryPairArr = queryPair.split(":");
				int currentMovieID = Integer.parseInt(queryPairArr[0]);
				if (lastMovieID != currentMovieID) {
					try {
						weightedMeanWriter.write(String.valueOf(currentMovieID)
								+ ":");
						weightedMeanWriter.newLine();
						lastMovieID = currentMovieID;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				int currentUserID = Integer.parseInt(queryPairArr[1]);
				List<Similarity> similairityVector = similarityVector
						.computeSimilarity(userMatrix, currentUserID);
				Neighbor knearestUserNeighbor = new KNearestUserNeighbor(4000,
						similairityVector, userMatrix);
				double meanRating = userbasedRecommender.recommendCustomRating(
						knearestUserNeighbor, userMatrix, currentUserID,
						currentMovieID);
				try {
						weightedMeanWriter.write(String.valueOf(meanRating));
					weightedMeanWriter.newLine();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
		if (experimentNum == 2) {
			System.out.println("This is experiment 2");
			String experimentThreeOuputFile = "Experiment2.txt";
			try {
				weightedMeanWriter = new BufferedWriter(new FileWriter(
						new File(experimentThreeOuputFile)));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			SimilarityVector similarityVector = new CosineSimilarityVector();
			int lastMovieID = Integer.parseInt(queryPairs.get(0).split(":")[0]);
			try {
				weightedMeanWriter.write(String.valueOf(lastMovieID) + ":");
				weightedMeanWriter.newLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			for (String queryPair : queryPairs) {
				String[] queryPairArr = queryPair.split(":");
				int currentMovieID = Integer.parseInt(queryPairArr[0]);
				if (lastMovieID != currentMovieID) {
					try {
						weightedMeanWriter.write(String.valueOf(currentMovieID)
								+ ":");
						weightedMeanWriter.newLine();
						lastMovieID = currentMovieID;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				int currentUserID = Integer.parseInt(queryPairArr[1]);
				List<Similarity> similairityVector = similarityVector
						.computeSimilarity(userMatrix, currentUserID);
				Neighbor knearestUserNeighbor = new KNearestUserNeighbor(400,
						similairityVector, userMatrix);
				double meanRating = userbasedRecommender.recommendCustomRating(
						knearestUserNeighbor, userMatrix, currentUserID,
						currentMovieID);
				try {
					weightedMeanWriter.write(String.valueOf(meanRating));
					weightedMeanWriter.newLine();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
		if (experimentNum == 3) {
			System.out.println("This is experiment 3");
			String experimentThreeOuputFile = "Experiment3.txt";
			try {
				weightedMeanWriter = new BufferedWriter(new FileWriter(
						new File(experimentThreeOuputFile)));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			SimilarityVector similarityVector = new ImprovedCosineSimilarityVector();
			int lastMovieID = Integer.parseInt(queryPairs.get(0).split(":")[0]);
			try {
				weightedMeanWriter.write(String.valueOf(lastMovieID) + ":");
				weightedMeanWriter.newLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			for (String queryPair : queryPairs) {
				String[] queryPairArr = queryPair.split(":");
				int currentMovieID = Integer.parseInt(queryPairArr[0]);
				if (lastMovieID != currentMovieID) {
					try {
						weightedMeanWriter.write(String.valueOf(currentMovieID)
								+ ":");
						weightedMeanWriter.newLine();
						lastMovieID = currentMovieID;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				int currentUserID = Integer.parseInt(queryPairArr[1]);
				List<Similarity> similairityVector = similarityVector
						.computeSimilarity(userMatrix, currentUserID);
				Neighbor knearestUserNeighbor = new KNearestUserNeighbor(400,
						similairityVector, userMatrix);
				double meanRating = userbasedRecommender.recommendCustomRating(
						knearestUserNeighbor, userMatrix, currentUserID,
						currentMovieID);
				try {
					weightedMeanWriter.write(String.valueOf(meanRating));
					weightedMeanWriter.newLine();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
		if (experimentNum == 4) {
			System.out.println("This is experiment 4");
			String experimentFourOuputFile = "Experiment4.txt";
			try {
				weightedMeanWriter = new BufferedWriter(new FileWriter(
						new File(experimentFourOuputFile)));
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			SimilarityVector similarityVector = new DotProductSimilarityVector();
			int lastMovieID = Integer.parseInt(queryPairs.get(0).split(":")[0]);
			try {
				weightedMeanWriter.write(String.valueOf(lastMovieID) + ":");
				weightedMeanWriter.newLine();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			for (String queryPair : queryPairs) {
				String[] queryPairArr = queryPair.split(":");
				int currentMovieID = Integer.parseInt(queryPairArr[0]);
				if (lastMovieID != currentMovieID) {
					try {
						weightedMeanWriter.write(String.valueOf(currentMovieID)
								+ ":");
						weightedMeanWriter.newLine();
						lastMovieID = currentMovieID;
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				}
				int currentUserID = Integer.parseInt(queryPairArr[1]);
				List<Similarity> similairityVector = similarityVector
						.computeSimilarity(userMatrix, currentUserID);
				Neighbor knearestUserNeighbor = new KNearestUserNeighbor(400,
						similairityVector, userMatrix);
				double meanRating = userbasedRecommender.recommendMeanRating(
						knearestUserNeighbor, userMatrix, currentUserID,
						currentMovieID);
				try {
					weightedMeanWriter.write(String.valueOf(meanRating));
					weightedMeanWriter.newLine();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}
		try {
			weightedMeanWriter.close();
			weightedMeanWriter.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Experiment experiment = new Experiment();
		experiment.loadQueryData("queries-small.txt");

		//using pcc
		long experiment_start1 = System.currentTimeMillis();
		experiment.executeExperiment(1);
		long experiment_end1 = System.currentTimeMillis();
		long running_time = experiment_end1 - experiment_start1;
		System.out.println("Experiment 1 " + running_time);
		
		//using cosine
		long experiment_start2 = System.currentTimeMillis();
	    //experiment.executeExperiment(2);
		long experiment_end2 = System.currentTimeMillis();
		long running_time2 = experiment_end2 - experiment_start2;
		System.out.println("Experiment 2 " + running_time2);

		//using improved cosine
		long experiment_start3 = System.currentTimeMillis();
		 experiment.executeExperiment(3);
		long experiment_end3 = System.currentTimeMillis();
		long running_time3 = experiment_end3 - experiment_start3;
		System.out.println("Experiment 3 " + running_time3);
		
		//using dot product 
		long experiment_start4 = System.currentTimeMillis();
		experiment.executeExperiment(4);
		long experiment_end4 = System.currentTimeMillis();
		long running_time4 = experiment_end4 - experiment_start4;
		System.out.println("Experiment 4 " + running_time4);
	}
}