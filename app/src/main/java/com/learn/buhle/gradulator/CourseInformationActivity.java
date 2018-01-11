package com.learn.buhle.gradulator;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CourseInformationActivity extends SingleFragmentActivity {

    private static final String COURSE_ID_EXTRA = "course id";


    @Override
    protected Fragment createFragment()
    {
        UUID courseId;
        courseId = (UUID) getIntent().getSerializableExtra(COURSE_ID_EXTRA);

        return CourseInfoFragment.newInstance(courseId);
    }

    public static Intent newIntent(Context context, UUID courseId)
    {
        Intent intent = new Intent(context, CourseInformationActivity.class);
        intent.putExtra(COURSE_ID_EXTRA, courseId);
        return intent;

    }
}

