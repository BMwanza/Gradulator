package com.learn.buhle.gradulator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.learn.buhle.gradulator.database.CourseDatabaseSchema.CourseTable;

/**
 * Created by BMwanza on 1/6/2018.
 */

public class CourseBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "courseBase.db";

    public CourseBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + CourseTable.TABLE_NAME + "(" +
                "_id integer primary key autoincrement, " +
                CourseTable.Columns.CID + ", " +
                CourseTable.Columns.COURSE_NAME + ", " +
                CourseTable.Columns.CURRENT_GRADE + ", " +
                CourseTable.Columns.TARGET_GRADE + ", " +
                CourseTable.Columns.TOTAL_WEIGHT + ", " +
                CourseTable.Columns.SYLLABUS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
