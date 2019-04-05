package com.example.happi.ratemycourse;

/**
 * Rating Database Model
 *
 * @author Brodie Heywood
 * @version 0.1
 */
public class RatingDataModel {

    private static int ratingID;

    // Rating metrics
    private int _homework;
    private int _reading;
    private int _usefulness;
    private int _stress;

    /**
     * RatingDataModel Constructor
     *
     * @param homework user-inputted amount of homework on 10 point scale
     * @param reading amount of reading required from course on 10 point scale
     * @param usefulness usefulness of course on 10 point scale
     * @param stress stressfulness of course on 10 point scale
     */
    public RatingDataModel(int homework,
                           int reading,
                           int usefulness,
                           int stress) {
        _homework = homework;
        _reading = reading;
        _usefulness = usefulness;
        _stress = stress;
        ratingID += 1;
    }

    public int getRatingID() {
        return ratingID;
    }

    public int getHomework() {
        return _homework;
    }

    public void setHomework(int homeworkAmount) {
        _homework = homeworkAmount;
    }

    public int getReading() {
        return _reading;
    }

    public void setReading(int readingAmount) {
        _reading = readingAmount;
    }

    public int getUsefulness() {
        return _usefulness;
    }

    public void setUsefulness(int usefulnessRating) {
        _usefulness = usefulnessRating;
    }

    public int getStress() {
        return _stress;
    }

    public void setStress(int stressRating) {
        _stress = stressRating;
    }

    @Override
    public String toString() {
        return String.format("Homework: %s, " + "Reading: %s, " +
                        "Usefulness: %s, " + "Stress: %s\n",
                _homework, _reading, _usefulness, _stress);
    }
}
