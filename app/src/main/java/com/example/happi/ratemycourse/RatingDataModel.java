package com.example.happi.ratemycourse;

/**
 * Rating Database Model
 *
 * @author Brodie Heywood
 * @version 0.1
 */
public class RatingDataModel {

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
    }

}
