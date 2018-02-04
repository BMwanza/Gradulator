package com.learn.buhle.gradulator;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by bmwanza on 04/02/18.
 */

public class RemoveCourseWarningFragment extends DialogFragment {

    private static final String COURSE_NAME_ARG = "course title arg";
    private static final String COURSE_ID_ARG = "course id arg";
    private static final String COURSE_ID_RETURN = "course id return";

    private TextView mConfirmWarning;
    private String mCourseTitle;
    private UUID mCourseID;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.remove_course_warning_dialog, null);

        mCourseTitle = getArguments().getString(COURSE_NAME_ARG);
        mCourseID = (UUID) getArguments().getSerializable(COURSE_ID_ARG);
        mConfirmWarning = (TextView) view.findViewById(R.id.are_you_sure_text);
        mConfirmWarning.setText("Are you sure you want to remove " + mCourseTitle + " from your course" +
                "list?");

        builder.setView(view);
        builder.setTitle(R.string.remove);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendResult(Activity.RESULT_OK, mCourseID);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }

    public static RemoveCourseWarningFragment newInstance(String courseTitle, UUID courseID) {
        Bundle args = new Bundle();
        args.putString(COURSE_NAME_ARG, courseTitle);
        args.putSerializable(COURSE_ID_ARG, courseID);
        RemoveCourseWarningFragment frag = new RemoveCourseWarningFragment();
        frag.setArguments(args);

        return frag;
    }

    private void sendResult(int resultCode, UUID courseID) {
        if(getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(COURSE_ID_RETURN, courseID);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
}
