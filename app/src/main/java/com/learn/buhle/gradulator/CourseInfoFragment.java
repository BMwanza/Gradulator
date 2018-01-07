package com.learn.buhle.gradulator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class CourseInfoFragment extends Fragment implements TextView.OnEditorActionListener, View.OnFocusChangeListener{

    // On Save Instance State keys if needed
//    private static final String CURRENT_DEFAULT_VALUE = "Current Grade";
//    private static final String TARGET_DEFAULT_VALUE = "Set Target Grade";
//    private static final String EMPTY_STRING_VALUE = "";

    private static final String COURSE_TARGET_GRADE = "target_grade";
    private static final String COURSE_CURRENT_GRADE = "course_current_grade";
    private static final String COURSE_SYLLABUS = "course_information";

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


        mEditTargetView.setOnEditorActionListener(this);
        mEditTargetView.setOnFocusChangeListener(this);

        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState)
//    {
//        String currGrade, tarGrade;
//
//        super.onSaveInstanceState(savedInstanceState);
//
//        if(!mCurrentGradeScore.getText().toString().equals(CURRENT_DEFAULT_VALUE) &&
//                !mCurrentGradeScore.getText().toString().equals(EMPTY_STRING_VALUE))
//        {
//            currGrade = validateString(mCurrentGradeScore.getText().toString());
//            savedInstanceState.putDouble(COURSE_CURRENT_GRADE, Double.parseDouble(currGrade));
//        }
//
//        if(!mEditTargetView.getText().toString().equals(TARGET_DEFAULT_VALUE) &&
//                !mEditTargetView.getText().toString().equals(EMPTY_STRING_VALUE))
//        {
//            tarGrade = validateString(mEditTargetView.getText().toString());
//            savedInstanceState.putDouble(COURSE_TARGET_GRADE, Double.parseDouble(tarGrade));
//        }
//
//        //Save the current Grade, Target Grade, and the Arraylist of Syllabus items
//        savedInstanceState.putSerializable(COURSE_SYLLABUS, mCourse.getSyllabus());
//    }


    /*
    On Editor Action for Target Grade Editor
     */
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        String validString;

        if(i == EditorInfo.IME_ACTION_DONE)
        {
            mEditTargetView.clearFocus();
            if(!textView.getText().toString().matches("")) {

                validString = validateString(textView.getText().toString());
                mCourse.setTargetGrade(Double.parseDouble(validString));
                mEditTargetView.setText(validString + "%");
            }
            else
            {
                mEditTargetView.setText("90%");
                mCourse.setTargetGrade(90.0);


            }

        }

        //Close the Keyboard
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //Updatethe interface
        updateInterface(0);

        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        if(b)
        {
            mEditTargetView.getText().clear();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateInterface(0);
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

    /*
    Validates a String that was recieved from input
    Checks for multiple decimals in the input
    Returns a string that only considers the first occuring decimal point
    eg:
    8.8.34 = 8.834
    */
    private String validateString(String inputString)
    {
        String validString = "";
        boolean vStringHasDecimal = false;


        for(int i = 0; i < inputString.length(); i++)
        {
            if(inputString.charAt(i) != '.' && inputString.charAt(i) != '%')
            {
                validString += inputString.charAt(i);
            }
            else if(inputString.charAt(i) == '.' && !vStringHasDecimal)
            {
                vStringHasDecimal = true;
                validString += inputString.charAt(i);
            }
        }

        return validString;
    }


    //******************************** PRIVATE ViewHolder CLASS***************************************//
    /***************************************************************************************************/

    private class SyllabusItemHolder extends RecyclerView.ViewHolder implements TextView.OnEditorActionListener, View.OnFocusChangeListener {

        private SyllabusItem mSyllabusItem;
        private TextView mSyllabusItemTitle;
        private EditText mSyllabusItemScore;
        private TextView mScoreState;


        public SyllabusItemHolder(View itemView) {
            super(itemView);

            //Link the views of the layout
            mSyllabusItemTitle = itemView.findViewById(R.id.syllabus_item_title);
            mSyllabusItemScore = itemView.findViewById(R.id.syllabus_item_score);
            mScoreState = itemView.findViewById(R.id.syllabus_item_score_state);

            mSyllabusItemScore.setOnEditorActionListener(this);
            mSyllabusItemScore.setOnFocusChangeListener(this);

        }


        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

            String validString;


            if (i == EditorInfo.IME_ACTION_DONE)
            {
                if(!textView.getText().toString().matches(""))
                {
                    validString = validateString(textView.getText().toString());

                    mSyllabusItemScore.setText(validString);
                    mSyllabusItem.setGradeAchieved(Double.parseDouble(validString));
                    mScoreState.setText(R.string.achieved_grade);
                    mCourse.updateMarks();
                    mCurrentGradeScore.setText(String.format("%.1f", mCourse.getCurrGrade() * 100) + "%");

                }
                else
                {
                    //If the user does not enter any data


                    if(mSyllabusItem.isMarked())
                    {
                    /*
                    If the SyllabusItem is marked simply set the text to the achieved grade
                    */
                        mSyllabusItemScore.setText(Double.toString(mSyllabusItem.getGradeAchieved()));
                        mScoreState.setText(R.string.achieved_grade);

                    }
                    else
                    {
                    /*
                     If the Syllabus Item is not marked set the hint for the needed grade
                     */
                        mSyllabusItemScore.setHint(Double.toString(mSyllabusItem.getNeededGrade()));
                        mScoreState.setText(R.string.needed_grade);
                    }

                }

                //Update the interface
                updateInterface(0);


            //Close the Keyboard
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }

            return false;
        }

        @Override
        public void onFocusChange(View view, boolean b) {
            if(b)
            {
                mSyllabusItemScore.getText().clear();
            }
        }

        /*
        Bind the Views to the data of a course
         */
        public void bindToData(SyllabusItem item)
        {
            mSyllabusItem = item;

            mSyllabusItemTitle.setText(item.getItemName());

        /*
        Depending on the state of the Syllabus item set the Views accordingly when
        binding the data to a view.
         */
            if(!mSyllabusItem.isMarked())
            {
                mSyllabusItemScore.setHint(String.format("%.1f", item.getNeededGrade() * 100) + "%");
                mScoreState.setText(R.string.needed_grade);
            }
            else
            {
                mSyllabusItemScore.setText(String.format("%.1f", item.getGradeAchieved()) + "%");
                mScoreState.setText(R.string.achieved_grade);
            }

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
        public void onBindViewHolder(SyllabusItemHolder holder, int position)
        {
            holder.setIsRecyclable(false);
            SyllabusItem item = mSyllabusItems.get(position);
            holder.bindToData(item);

        }


        @Override
        public int getItemCount() {
            return mSyllabusItems.size();
        }
    }


}
