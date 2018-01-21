package com.learn.buhle.gradulator;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class Course extends ListItem {

    private static final double STARTING_GRADE = 0.0;

    private UUID mCourseID;
    private String mCourseName;
    private double mCurrGrade;
    private double mTargetGrade;
    private double mTotalWeight;
    private boolean mgradesRecieved;
    private ArrayList<SyllabusItem> mSyllabus;

    public Course(String name)
    {
        mCourseID = UUID.randomUUID();
        mCourseName = name;
        mgradesRecieved = false;
        mCurrGrade = STARTING_GRADE;
        mSyllabus = new ArrayList<SyllabusItem>();
        assertCourse();
    }

    public Course(UUID courseID, String courseName, double currGrade, double targetGrade, double totalWeight,
                  boolean recieved, ArrayList<SyllabusItem> syllabus) {
        mCourseID = courseID;
        mCourseName = courseName;
        mCurrGrade = currGrade;
        mTargetGrade = targetGrade;
        mTotalWeight = totalWeight;
        mgradesRecieved = recieved;
        mSyllabus = syllabus;
        assertCourse();
    }

    public boolean addSyllabusItem(SyllabusItem newItem)
    {
        boolean added = false;
        if(getTotalWeight() + newItem.getWeight() <= 100)
        {
            added = true;
            mSyllabus.add(newItem);
            mTotalWeight += newItem.getWeight();
        }

        return added;
    }


    public void updateMarks()
    {
        double neededMark = calculateNeededGrade();
        for(int i = 0; i < mSyllabus.size(); i++)
        {
            if(!mSyllabus.get(i).isMarked())
            {
                mSyllabus.get(i).setNeededGrade(neededMark);
            }
        }

        mCurrGrade = calculateCurrMark()/getGradedMarks() ;
    }

    /*
    Calculate the Mark needed for every unmarked syllabus item
    based on a Targetted grade
    Using the formula:
    markNeeded = TargetGrade - currMark
    Returns the grade needed as a percentage
     */
    public double calculateNeededGrade()
    {
        double markNeeded;

        markNeeded = mTargetGrade - calculateCurrMark();

        markNeeded /= getRemainingMarks();

        return markNeeded;
    }

    /*
    Calculates the current Mark in a course
     */
    public double calculateCurrMark()
    {
        double currMark = 0;
        double decimalGrade;

        for (int i = 0; i < mSyllabus.size(); i++)
        {
            if(mSyllabus.get(i).isMarked())
            {
                decimalGrade = mSyllabus.get(i).getGradeAchieved() / 100;
                currMark += decimalGrade * mSyllabus.get(i).getWeight();
            }
        }

        return currMark;
    }



    //********************* SETTERS ********************************//


    public void setSyllabus(ArrayList<SyllabusItem> syllabus) {
        mSyllabus = syllabus;
    }

    public void setCourseName(String courseName)
    {
        mCourseName = courseName;
    }

    public void setCurrGrade(double currGrade)
    {
        mCurrGrade = currGrade;
    }

    /*
    Set a target Grade in the course and calculate the
    needed grade for each syllabus item
     */
    public void setTargetGrade(double targetGrade)
    {
        mTargetGrade = targetGrade;
        double neededGrade = calculateNeededGrade();

        for(int i = 0; i < mSyllabus.size(); i++)
        {
            if(!mSyllabus.get(i).isMarked())
            {
                mSyllabus.get(i).setNeededGrade(neededGrade);
            }
        }
    }


    //******************* GETTERS ********************************//


    public UUID getCourseID() {
        return mCourseID;
    }

    private double getRemainingMarks()
    {
        double remainingMarks = 0;

        for(int i = 0; i < mSyllabus.size(); i++)
        {
            if(!mSyllabus.get(i).isMarked())
            {
                remainingMarks += mSyllabus.get(i).getWeight();
            }
        }

        return remainingMarks;
    }

    private double getGradedMarks()
    {
        double gradedMarks = 0;

        for(int i = 0; i < mSyllabus.size(); i++)
        {
            if(mSyllabus.get(i).isMarked())
            {
                gradedMarks += mSyllabus.get(i).getWeight();
            }
        }

        return gradedMarks;
    }

    public String getCourseName()
    {
        return mCourseName;
    }

    public double getCurrGrade()
    {
        return mCurrGrade;
    }

    public double getTargetGrade()
    {
        return mTargetGrade;
    }

    public double getTotalWeight() { return mTotalWeight; }

    public boolean hasGradesRecieved() {
        return mgradesRecieved;
    }

    public ArrayList<SyllabusItem> getSyllabus()
    {
        return mSyllabus;
    }

    @Override
    public String toString()
    {
        return mCourseName;
    }

    //********************* INVARIANT METHODS *****************************//

    public void assertCourse()
    {
        assertName();
        assertCurrentGrade();
        assertTargetGrade();
        assertTotalWeight();
        mgradesRecieved = assertGradeReceivedState();
    }

    private boolean assertName()
    {
        return !mCourseName.equals("");
    }

    private boolean assertCurrentGrade()
    {
        return mCurrGrade >= 0 && mCurrGrade <= 100;
    }

    private boolean assertTargetGrade()
    {
        return mTargetGrade >= 0 && mTargetGrade <= 100;
    }

    private boolean assertTotalWeight()
    {
        return mTotalWeight >= 0 && mTotalWeight <= 100;
    }

    private boolean assertGradeReceivedState()
    {
        boolean hasGrades = false;

        for(int i = 0; i < mSyllabus.size() && !hasGrades; i++)
        {
            if(mSyllabus.get(i).isMarked())
            {
                hasGrades = true;
            }
        }

        return hasGrades;
    }
}
