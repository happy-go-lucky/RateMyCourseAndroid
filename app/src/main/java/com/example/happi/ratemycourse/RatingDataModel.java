package com.example.happi.ratemycourse;

/**
 * Rating Database Model
 *
 * @author Brodie Heywood
 * @version 0.1
 */
public class RatingDataModel {

    private static int numberOfRatings = 0;

    // Rating metrics
    private int _homework;
    private int _reading;
    private int _usefulness;
    private int _stress;

    private int _ratingID;

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

        numberOfRatings++;
        _ratingID = numberOfRatings;
    }

    public int getRatingID() {
        return _ratingID;
    }

    public void setRatingID(int ID) {
        _ratingID = ID;
    }

    public int getHomework() {
        return _homework;
    }

    public void setHomework(int rating) {
        _homework = rating;
    }

    public int getReading() {
        return _reading;
    }

    public void setReading(int rating) {
        _reading = rating;
    }

    public int getUsefulness() {
        return _usefulness;
    }

    public void setUsefulness(int rating) {
        _usefulness = rating;
    }

    public int getStress() {
        return _stress;
    }

    public void setStress(int rating) {
        _stress = rating;
    }

    @Override
    public String toString() {
        // Rating ID not included.
        return String.format("Homework: %s, " + "Reading: %s, " +
                        "Usefulness: %s, " + "Stress: %s\n",
                _homework, _reading, _usefulness, _stress);
    }
}
