package edu.cmu.andrew.data;
import java.util.List;
/**
 * interface of the subject vertoc 
 * @author tao
 *
 */
public interface SubjectVector {
	public int getSubjectID();
	public List<Rating> getRatingList();
	public Rating getSubjectRating(int subjectID);
	public double getAvgRating();
}
