package com.learn.buhle.gradulator;

import android.support.v4.app.Fragment;

public class NewCourseActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new NewCourseFragment();
    }


}
