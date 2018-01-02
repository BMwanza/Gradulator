package com.learn.buhle.gradulator;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class Course extends ListItem {

    private String mCourseName;
    private double mCurrGrade;
    private double mTargetGrade;
    //SylabusItem List

    public Course(String name, double targetGrade)
    {
        mCourseName = name;
        mTargetGrade = targetGrade;
    }


    //********************* SETTERS ********************************//
    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public void setCurrGrade(double currGrade) {
        mCurrGrade = currGrade;
    }

    public void setTargetGrade(double targetGrade) {
        mTargetGrade = targetGrade;
    }


    //******************* GETTERS ********************************//
    public String getCourseName() {
        return mCourseName;
    }

    public double getCurrGrade() {
        return mCurrGrade;
    }

    public double getTargetGrade() {
        return mTargetGrade;
    }

    @Override
    public String toString() {
        return mCourseName;
    }

}
