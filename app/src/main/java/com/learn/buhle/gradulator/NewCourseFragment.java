package com.learn.buhle.gradulator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by BMwanza on 1/8/2018.
 */

public class NewCourseFragment extends Fragment
{
    private static final String COURSE_TITLE_RESULT = "course title";
    private static final String COURSE_TARGET_RESULT = "target grade";


    private static final String COURSE_TITLE = "course name";
    private static final String COURSE_TARGET_GRADE = "course target";

    private EditText mCourseNameEdit;
    private EditText mCourseTargetEdit;
    private Button mCreateCourseButton;
    private Button mCancelCourseButton;

    private String mCourseTitle;
    private double mCourseTarget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.new_course_fragment, container, false);



        mCourseNameEdit = (EditText) view.findViewById(R.id.new_course_title);
        mCourseTargetEdit = (EditText) view.findViewById(R.id.new_course_target);
        mCreateCourseButton = (Button) view.findViewById(R.id.create_course_button);
        mCancelCourseButton = (Button) view.findViewById(R.id.cancel_course_button);


        if(savedInstanceState != null)
        {
            mCourseNameEdit.setText(savedInstanceState.getString(COURSE_TITLE));
            mCourseTargetEdit.setText(savedInstanceState.getString(COURSE_TARGET_GRADE));
        }

        mCourseNameEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    mCourseTitle = textView.getText().toString();

                }

                return false;
            }
        });

        mCourseTargetEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_DONE)
                {
                    mCourseTarget = Double.parseDouble(ErrorManager.validateString(textView.getText().toString()));
                }

                return false;
            }
        });

        mCreateCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCourseNameEdit.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(), R.string.enter_course_name_prompt, Toast.LENGTH_SHORT).show();
                }
                if(mCourseTargetEdit.getText().toString().matches(""))
                {
                    Toast.makeText(getActivity(), R.string.set_target_grade, Toast.LENGTH_LONG).show();
                }
                else if(!ErrorManager.validTargetGrade(Double.parseDouble(mCourseTargetEdit.getText().toString())))
                {
                    Toast.makeText(getActivity(), R.string.valid_target_grade, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mCourseTitle = mCourseNameEdit.getText().toString();
                    mCourseTarget = Double.parseDouble(ErrorManager.validateString(mCourseTargetEdit.getText().toString()));

                    Intent intent = new Intent();
                    intent.putExtra(COURSE_TITLE_RESULT, mCourseTitle);
                    intent.putExtra(COURSE_TARGET_RESULT, mCourseTarget);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }

            }
        });


        mCancelCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();

            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(COURSE_TITLE, mCourseNameEdit.getText().toString());
        savedInstanceState.putString(COURSE_TARGET_GRADE, mCourseTargetEdit.getText().toString());
    }

}
