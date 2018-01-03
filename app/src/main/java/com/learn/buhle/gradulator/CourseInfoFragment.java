package com.learn.buhle.gradulator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by BMwanza on 1/2/2018.
 */

public class CourseInfoFragment extends Fragment {

    private RecyclerView mRecyclerView;



    /*
    The onCreate View simply inflates the course Fragment layout and links up our Recycler View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.course_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.syllabus_item_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


    //******************************** PRIVATE ADAPTER CLASS***************************************//

    /*
    The Adapter that will be a mediator bewtween the View holder and the Recycler
     */
    private class SyllabusItemAdapter extends RecyclerView.Adapter<SyllabusItemHolder> {
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

            //The holder will show the piece of data of the Syllabus Item at n position

        }


        @Override
        public int getItemCount() {
            return 0;
        }
    }


}
