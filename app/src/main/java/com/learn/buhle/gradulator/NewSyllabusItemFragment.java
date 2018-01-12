package com.learn.buhle.gradulator;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by bmwanza on 11/01/18.
 */

public class NewSyllabusItemFragment extends DialogFragment
{
    private static final String TOTAL_COURSE_WEIGHT = "course_total_weight";
    private static final String SYLLABUS_ITEM_NAME = "syllabus_item_weight";
    private static final String SYLLABUS_ITEM_WEIGHT = "syllabus_item_weight";

    private EditText mSyllabusItemName;
    private EditText mSyllabusItemWeight;

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.syllabus_item_dialog, null);
        mSyllabusItemName = view.findViewById(R.id.syllabus_item_name);
        mSyllabusItemWeight = view.findViewById(R.id.syllabus_item_weight);
        mSyllabusItemWeight.setText(Double.toString(getArguments().getDouble(TOTAL_COURSE_WEIGHT)));

        builder.setView(view);
        builder.setTitle(R.string.new_syllabus_item);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String itemName;
                double itemWeight;

                //Use validate String
                itemName = mSyllabusItemName.getText().toString();
                itemWeight = Double.parseDouble(mSyllabusItemWeight.getText().toString());
                sendResult(Activity.RESULT_OK, itemName, itemWeight);
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

    public static NewSyllabusItemFragment newInstance(Double totalCourseWeight)
    {
        Bundle args = new Bundle();
        args.putDouble(TOTAL_COURSE_WEIGHT, totalCourseWeight);

        NewSyllabusItemFragment frag = new NewSyllabusItemFragment();
        frag.setArguments(args);

        return frag;
    }

    private void sendResult(int resultCode, String itemName, Double itemWeight)
    {
        if(getTargetFragment() != null)
        {
            Intent intent = new Intent();
            intent.putExtra(SYLLABUS_ITEM_NAME, itemName);
            intent.putExtra(SYLLABUS_ITEM_WEIGHT, itemWeight);

            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }



}
