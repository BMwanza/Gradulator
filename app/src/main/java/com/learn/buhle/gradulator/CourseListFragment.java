package com.learn.buhle.gradulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BMwanza on 1/6/2018.
 */

public class CourseListFragment extends Fragment
{
    private static final int NEW_CRIME_REQUEST_CODE = 0;
    private static final String COURSE_TITLE_RESULT = "course title";

    private RecyclerView mRecyclerView;
    private CourseAdapter mCourseAdapter;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateInterface(0);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.course_list_fragment, containter, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.course_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateInterface(0);

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.course_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        CourseManager manager = CourseManager.getInstance(getActivity());
        if(item.getItemId() == R.id.menu_item_add_course)
        {
            Intent intent = new Intent(getActivity(), NewCourseActivity.class);
            startActivityForResult(intent, NEW_CRIME_REQUEST_CODE);

        }


        return true;
    }

    public void updateInterface(int position)
    {
        ArrayList<Course> courses = CourseManager.getInstance(getActivity()).getAllCourses();

        if(mCourseAdapter == null)
        {
            mCourseAdapter = new CourseAdapter(courses);
            mRecyclerView.setAdapter(mCourseAdapter);
        }
        else
        {
            mCourseAdapter.setCourses(courses);
            mCourseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        String courseTitle;
        if(requestCode == NEW_CRIME_REQUEST_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                courseTitle = data.getStringExtra(COURSE_TITLE_RESULT);
                CourseManager.getInstance(getActivity()).addCourse(new Course(courseTitle));
                updateInterface(0);
            }
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

        public void setCourses(ArrayList<Course> courses) {
            mCourses = courses;
        }
    }


}
