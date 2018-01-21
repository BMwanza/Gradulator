package com.learn.buhle.gradulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.learn.buhle.gradulator.database.CourseBaseHelper;
import com.learn.buhle.gradulator.database.CourseCursorWrapper;
import com.learn.buhle.gradulator.database.CourseDatabaseSchema.CourseTable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by BMwanza on 1/6/2018.
 */

/**
 * REMARKS
 * This class will communicate between the Database and the System.
 * A singleton class such that is will be the only class and only instance
 * of the class that interacts with the Database
 */
public class CourseManager {

    private static CourseManager sCourseManager;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    static CourseManager getInstance(Context context)
    {
        if (sCourseManager == null)
        {
            sCourseManager = new CourseManager(context);
        }

        return sCourseManager;
    }

    private CourseManager(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new CourseBaseHelper(mContext).getWritableDatabase();
    }

    //***************************** WRITING TO THE DATABASE ****************************//

    /**
     * Adds a course to the database
     * @param course
     */
    public void addCourse(Course course)
    {
        if(course != null)
        {
            ContentValues courseValues = getCourseValues(course);
            mDatabase.insert(CourseTable.TABLE_NAME, null, courseValues);
        }
    }

    public void removeCourse(Course course)
    {
        if(course != null)
        {
            String uuid = course.getCourseID().toString();
            mDatabase.delete(CourseTable.TABLE_NAME, CourseTable.Columns.CID + " = ?", new String[] {uuid});
        }
    }

    /**
     * Updates an existing Course in our database with new data
     * @param course
     */
    public void updateCourse(Course course)
    {
        String uuid = course.getCourseID().toString();
        ContentValues values = getCourseValues(course);

        mDatabase.update(CourseTable.TABLE_NAME, values, CourseTable.Columns.CID + " = ?", new String[] {uuid});
        //The question mark is oddd but it is for safety
    }

    /**
     * Store the Attributes from a course into content values to be stored in the Database table
     * @param course
     * @return
     */
    private static ContentValues getCourseValues(Course course)
    {

        ContentValues values = new ContentValues();

        values.put(CourseTable.Columns.CID, course.getCourseID().toString());
        values.put(CourseTable.Columns.COURSE_NAME, course.getCourseName());
        values.put(CourseTable.Columns.CURRENT_GRADE, course.getCurrGrade());
        values.put(CourseTable.Columns.TARGET_GRADE, course.getTargetGrade());
        values.put(CourseTable.Columns.TOTAL_WEIGHT, course.getTotalWeight());
        values.put(CourseTable.Columns.GRADEDSTATE, course.hasGradesRecieved() ? 1:0);
        values.put(CourseTable.Columns.SYLLABUS, seriliazeSyllabus(course.getSyllabus()));

        return values;

    }

    /**
     * Serializes the ArrayList of Syllabus items into a JSON String
     * @param syllabus
     * @return
     */
    private static String seriliazeSyllabus(ArrayList<SyllabusItem> syllabus)
    {
        return new Gson().toJson(syllabus);
    }

    //***************************** READING FROM THE DATABASE ****************************//


    /**
     * Find all the Courses in our database
     * @return
     */
    public ArrayList<Course> getAllCourses()
    {
        Course course;
        ArrayList<Course> courses = new ArrayList<Course>();
        CourseCursorWrapper cursorWrapper = queryCourse(null, null); //Find all the courses

        try
        {
            cursorWrapper.moveToFirst();
            while(!cursorWrapper.isAfterLast())
            {
                course = cursorWrapper.extractCourseData();
                course.assertCourse();
                courses.add(course);
                cursorWrapper.moveToNext();
            }
        }
        finally
        {

            cursorWrapper.close();
        }

        return courses;
    }


    /**
     * Find a particular course using the CID primary key
     * @param cID
     * @return
     */
    public Course getCourse(UUID cID)
    {
        Course course = null;
        CourseCursorWrapper cursorWrapper = queryCourse(CourseTable.Columns.CID + " = ?", new String[] {cID.toString()});

        try
        {
            if(cursorWrapper.getCount() > 0)
            {
                cursorWrapper.moveToFirst();
                course = cursorWrapper.extractCourseData();
            }
        }
        finally
        {
            cursorWrapper.close();
        }

        return course;

    }

    /**
     * Queries the Database for Courses
     * For simplicity we are only worried about the Specific Columns and the conditions
     * to be met to match that column
     * @param whereClause The Specified Column
     * @param whereArgs The conditions that the column must meet
     * @return
     */
    private CourseCursorWrapper queryCourse(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(CourseTable.TABLE_NAME, //FROM
                null, //SELECT ROWS
                whereClause, //WHERE
                whereArgs, //Conditions
                null, //Group By
                null, //Having
                null, //Order By
                null); //Limit

        return new CourseCursorWrapper(cursor);
    }

}
