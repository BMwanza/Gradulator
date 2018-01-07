package com.learn.buhle.gradulator;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class Course extends ListItem {

    private UUID mCourseID;
    private String mCourseName;
    private double mCurrGrade;
    private double mTargetGrade;
    private double mTotalWeight;
    private ArrayList<SyllabusItem> mSyllabus;

    public Course(String name)
    {
        mCourseID = UUID.randomUUID();

        mCourseName = name;
//        mTargetGrade = targetGrade;
        mSyllabus = new ArrayList<SyllabusItem>();
        mCurrGrade = 0;

        for(int i = 0; i < 20; i++)
        {
            mSyllabus.add(new SyllabusItem("Item #" + i, 5));
        }
    }

    public Course(UUID courseID, String courseName, double currGrade, double targetGrade, double totalWeight, ArrayList<SyllabusItem> syllabus) {
        mCourseID = courseID;
        mCourseName = courseName;
        mCurrGrade = currGrade;
        mTargetGrade = targetGrade;
        mTotalWeight = totalWeight;
        mSyllabus = syllabus;
    }

    public boolean addSyllabusItem(SyllabusItem newItem)
    {
        boolean added = false;
        if(getTotalWeight() + newItem.getWeight() <= 100)
        {
            added = true;
            mSyllabus.add(newItem);
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

    public double getTotalWeight()
    {
        mTotalWeight = 0;

        for(int i = 0; i < mSyllabus.size(); i++)
        {
            mTotalWeight += mSyllabus.get(i).getWeight();
        }

        return mTotalWeight;
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

}
