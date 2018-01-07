package com.learn.buhle.gradulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BMwanza on 1/6/2018.
 */

public class CourseListFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private CourseAdapter mCourseAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.course_list_fragment, containter, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.course_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateInterface(0);

        return view;

    }

    public void updateInterface(int position)
    {
        ArrayList<Course> courses = new ArrayList<Course>();
        courses.add(new Course("COMP 2160"));
        courses.add(new Course("COMP 2140"));

        if(mCourseAdapter == null)
        {
            mCourseAdapter = new CourseAdapter(courses);
            mRecyclerView.setAdapter(mCourseAdapter);
        }
        else
        {
            mCourseAdapter.notifyDataSetChanged();
        }
    }


    //******************************** PRIVATE ViewHolder CLASS***************************************//
    /***************************************************************************************************/
    private class CourseHolder extends RecyclerView.ViewHolder
    {
        private Course mCourse;
        private TextView mCourseTitle;
        private TextView mCourseScore;

        public CourseHolder(View itemView)
        {
            super(itemView);

            mCourseTitle = (TextView) itemView.findViewById(R.id.course_title);
            mCourseScore = (TextView) itemView.findViewById(R.id.course_grade_score);
        }

        public void bindToCourseData(Course course)
        {
            mCourseTitle.setText(course.getCourseName());
            mCourseScore.setText(Double.toString(course.getCurrGrade()));
        }
    }

    private class CourseAdapter extends RecyclerView.Adapter<CourseHolder>
    {
        private ArrayList<Course> mCourses;

        public CourseAdapter(ArrayList<Course> courses)
        {
            mCourses = courses;
        }


        @Override
        public CourseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.course_item_layout, parent, false);

            return new CourseHolder(view);
        }

        @Override
        public void onBindViewHolder(CourseHolder holder, int position)
        {
            holder.setIsRecyclable(false);
            Course course = mCourses.get(position);
            holder.bindToCourseData(course);
        }


        @Override
        public int getItemCount() {
            return mCourses.size();
        }
    }


}
