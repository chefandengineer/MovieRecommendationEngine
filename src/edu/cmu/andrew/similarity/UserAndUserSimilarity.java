package edu.cmu.andrew.similarity;
/**
 * This similarity class can actually used by both movie and user
 * @author tao
 *
 */
public class UserAndUserSimilarity implements Similarity {

	private int userID;
	private double userSimilarity;

	public UserAndUserSimilarity(int userID, double similarity) {
		this.userID = userID;
		this.userSimilarity = similarity;
	}

	public int getSubjectID() {
		return userID;
	}

	public void setSubjectID(int userID) {
		this.userID = userID;
	}

	public double getSimilarity() {
		return userSimilarity;
	}

	public void setUserSimilarity(double userSimilarity) {
		this.userSimilarity = userSimilarity;
	}

	@Override
	public int compareTo(Similarity comparingSimilarity) {
		double similarity1 = this.userSimilarity;
		double similarity2 = comparingSimilarity.getSimilarity();
		return (similarity1 > similarity2 ? -1
				: (similarity1 == similarity2 ? 0 : 1));
	}
}
