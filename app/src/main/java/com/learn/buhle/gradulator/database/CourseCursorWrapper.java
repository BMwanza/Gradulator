package com.learn.buhle.gradulator.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learn.buhle.gradulator.Course;
import com.learn.buhle.gradulator.SyllabusItem;
import com.learn.buhle.gradulator.database.CourseDatabaseSchema.CourseTable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by BMwanza on 1/6/2018.
 */

public class CourseCursorWrapper extends CursorWrapper {

    public CourseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Extract a course from a entry in the Database total
     * @return The course extracted from the data
     */
    public Course extractCourseData()
    {

        UUID courseID;
        String courseName;
        double currGrade;
        double targetGrade;
        double totalWeight;
        boolean gradesRecived;
        String jSonSyllabus;

        courseID = UUID.fromString(getString(getColumnIndex(CourseTable.Columns.CID)));
        courseName = getString(getColumnIndex(CourseTable.Columns.COURSE_NAME));
        currGrade = getDouble(getColumnIndex(CourseTable.Columns.CURRENT_GRADE));
        targetGrade = getDouble(getColumnIndex(CourseTable.Columns.TARGET_GRADE));
        totalWeight = getDouble(getColumnIndex(CourseTable.Columns.TOTAL_WEIGHT));
        gradesRecived = getInt(getColumnIndex(CourseTable.Columns.GRADEDSTATE)) > 0;
        jSonSyllabus = getString(getColumnIndex(CourseTable.Columns.SYLLABUS));

        return new Course(courseID, courseName, currGrade, targetGrade, totalWeight, gradesRecived,
                deserializeSyllabus(jSonSyllabus));
    }

    /**
     * Deserialize the Json String into an arraylist
     * @param jsonString
     * @return Derserialized ArrayList from the Json String
     */
    private static ArrayList<SyllabusItem> deserializeSyllabus(String jsonString)
    {
        Type syllabusListType = new TypeToken<ArrayList<SyllabusItem>>(){}.getType();

        ArrayList<SyllabusItem> syllabus = new Gson().fromJson(jsonString, syllabusListType);

        return syllabus;
    }
}
