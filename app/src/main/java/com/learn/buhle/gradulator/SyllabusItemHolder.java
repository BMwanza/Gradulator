package com.learn.buhle.gradulator;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class SyllabusItemHolder extends RecyclerView.ViewHolder {

    private TextView mSyllabusItemTitle;
    private EditText mSyllabusItemScore;
    private TextView mScoreState;


    public SyllabusItemHolder(View itemView) {
        super(itemView);

        //Link the views of the layout
        mSyllabusItemTitle = itemView.findViewById(R.id.syllabus_item_title);
        mSyllabusItemScore = itemView.findViewById(R.id.current_grade_score);
        mScoreState = itemView.findViewById(R.id.syllabus_item_score_state);


    }
}
