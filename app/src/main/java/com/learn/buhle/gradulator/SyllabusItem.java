package com.learn.buhle.gradulator;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class SyllabusItem extends ListItem {

    private String mItemName;
    private double mWeight;
    private double mGradeAchieved;
    private double mNeededGrade;

    public SyllabusItem(String name, double weight)
    {
        mItemName = name;
        mWeight = weight;
    }


    @Override
    public String toString() {
        return mItemName;
    }
}
