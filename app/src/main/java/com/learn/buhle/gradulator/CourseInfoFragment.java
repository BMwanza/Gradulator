package com.learn.buhle.gradulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class CourseInfoFragment extends Fragment {

    private Course mCourse;

    private TextView mTargetGradeTextView;
    private TextView mCurrentGradeScore;
    private EditText mEditTargetView;

    private RecyclerView mRecyclerView;
    private SyllabusItemAdapter mSyllabusItemAdapter;



    /*
    The onCreate View simply inflates the course Fragment layout and links up our Recycler View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        mCourse = new Course("COMP 2160");
        View view = inflater.inflate(R.layout.course_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.syllabus_item_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTargetGradeTextView = (TextView) view.findViewById(R.id.target_grade_id);
        mCurrentGradeScore = (TextView) view.findViewById(R.id.current_grade_score);
        mEditTargetView = (EditText) view.findViewById(R.id.edit_target_grade_id);

        mEditTargetView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    mEditTargetView.setText(textView.getText());
                    mCourse.setTargetGrade(Double.parseDouble(textView.getText().toString()));
                    updateInterface(0);
                }

                return true;
            }
        });

        updateInterface(0);

        return view;
    }

    private void updateInterface(int position)
    {

        if (mSyllabusItemAdapter == null)
        {
            mSyllabusItemAdapter = new SyllabusItemAdapter(mCourse.getSyllabus());
            mRecyclerView.setAdapter(mSyllabusItemAdapter);
        }
        else
        {
            mSyllabusItemAdapter.setSyllabusItems(mCourse.getSyllabus());
            mSyllabusItemAdapter.notifyDataSetChanged();
        }
    }


    //******************************** PRIVATE ADAPTER CLASS***************************************//

    /*
    The Adapter that will be a mediator bewtween the View holder and the Recycler
     */
    private class SyllabusItemAdapter extends RecyclerView.Adapter<SyllabusItemHolder> {

        private ArrayList<SyllabusItem> mSyllabusItems;


        public SyllabusItemAdapter(ArrayList<SyllabusItem> syllabus)
        {
            mSyllabusItems = syllabus;
        }

        public void setSyllabusItems(ArrayList<SyllabusItem> syllabus)
        {
            mSyllabusItems = syllabus;
        }
        @Override
        /*
         *Called by the Recycler View when time to show a new item in the list
         *Creates a view and wraps it in a view holder
         */
        public SyllabusItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.syllabus_item_layout, parent, false);

            return new SyllabusItemHolder(view);
        }

        @Override
        /*
        Will bind the view holder to a piece of data in some position in our adapter list
         */
        public void onBindViewHolder(SyllabusItemHolder holder, int position) {

            SyllabusItem item = mSyllabusItems.get(position);
            holder.bindToData(item);

        }


        @Override
        public int getItemCount() {
            return mSyllabusItems.size();
        }
    }


}
