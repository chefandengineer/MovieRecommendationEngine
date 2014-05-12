package edu.cmu.andrew.similarity;
/**
 * Similarity is extends comparable in order to sort efficienty
 * @author tao
 *
 */
public interface Similarity extends Comparable<Similarity>{
	public int compareTo(Similarity comparingSimilarity);
	public double getSimilarity();
	public int getSubjectID();
}
