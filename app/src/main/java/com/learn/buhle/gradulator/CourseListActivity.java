package com.learn.buhle.gradulator;

import android.support.v4.app.Fragment;

public class CourseListActivity extends SingleFragmentActivity {




    @Override
    protected Fragment createFragment() {
        return new CourseInfoFragment();
    }
}

