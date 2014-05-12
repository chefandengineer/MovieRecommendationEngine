package edu.cmu.andrew.data;
/**
 * This class stores the rating 
 * @author tao
 *
 */
public class Rating {
	private int movieID;
	private double rate;
	private double normalizedRate;
	
	
	public Rating(int movieID,double rate){
		this.movieID = movieID;
		this.rate = rate;
	}
	public int getMovieID() {
		return movieID;
	}
	public void setMovieID(int movieID) {
		this.movieID = movieID;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getNormalizedRate() {
		return normalizedRate;
	}
	public void setNormalizedRate(double normalizedRate) {
		this.normalizedRate = normalizedRate;
	}
	
}
