package com.learn.buhle.gradulator;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class SyllabusItem extends ListItem {

    private static final double NOT_MARKED = Double.MIN_VALUE;

    private String mItemName;
    private double mWeight;
    private double mGradeAchieved;
    private double mNeededGrade;

    public SyllabusItem(String name, double weight)
    {
        mItemName = name;
        mWeight = weight;
        mGradeAchieved = NOT_MARKED;
    }

    public void setItemName(String itemName)
    {
        mItemName = itemName;
    }

    public void setWeight(double weight)
    {
        mWeight = weight;
    }

    public void setGradeAchieved(double gradeAchieved)
    {

        mGradeAchieved = gradeAchieved;
    }

    public void setNeededGrade(double neededGrade)
    {
        mNeededGrade = neededGrade;
    }



    public String getItemName() {
        return mItemName;
    }

    public double getWeight()
    {
        return mWeight;
    }

    public double getGradeAchieved()
    {
        return mGradeAchieved;
    }

    public double getNeededGrade()
    {
        return mNeededGrade;
    }

    /*
    If the achived Grade is set to our special value then it is not yet marked
     */
    public boolean isMarked()
    {
        return 0 != Double.compare(NOT_MARKED, mGradeAchieved);
    }

    @Override
    public String toString()

    {
        return mItemName;
    }

}
