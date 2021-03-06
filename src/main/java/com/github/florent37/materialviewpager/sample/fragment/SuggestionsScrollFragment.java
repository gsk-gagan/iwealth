package com.github.florent37.materialviewpager.sample.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.RetirementSuggestion;
import com.github.florent37.materialviewpager.sample.Reward;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SuggestionsScrollFragment extends Fragment {

    private SharedPreferences sharedpreferences;

    @BindView(R.id.scrollViewSuggestions)
    NestedScrollView mScrollView;

    public static SuggestionsScrollFragment newInstance() {
        return new SuggestionsScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggestions_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);

        //Floating Action Bar
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.chat_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RetirementSuggestion.class);
                startActivity(intent);
            }
        });

//        updateViews();
    }

    @Override
    public void onResume(){
        super.onResume();

//        updateViews();
    }


    private void updateViews() {

    }

}
