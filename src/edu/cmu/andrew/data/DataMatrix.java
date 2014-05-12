package edu.cmu.andrew.data;
import java.io.File;
import java.util.List;
/**
 * This interface defines the behaviors for a Data Matrix, it could either by a User User matrix or a Movie Movie 
 * Matrix
 * @author Tao Jiang
 *
 */
public interface DataMatrix {
	public void loadData(File dataFile);
	//this method get the subject vector, it could be user of rate or movie of user
	public SubjectVector getSubjectVector(int subjectID); 
	public List<SubjectVector> getSubjectRatingVectors();
}
