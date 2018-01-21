package com.learn.buhle.gradulator.database;

/**
 * Created by BMwanza on 1/6/2018.
 */

/**
 * A class that Defines our Schema
 * Split up into seperate classes for better referencing.
 */
public class CourseDatabaseSchema
{
    public static final class CourseTable
    {
        public static final String TABLE_NAME = "Courses";

        public static final class Columns
        {
            public static final String CID = "courseID";
            public static final String COURSE_NAME = "course_name";
            public static final String CURRENT_GRADE = "course_current_grade";
            public static final String TARGET_GRADE = "course_target_grade";
            public static final String TOTAL_WEIGHT = "course_weight";
            public static final String GRADEDSTATE = "grades_recieved_states";
            public static final String SYLLABUS = "course_syllabus";

        }
    }
}
