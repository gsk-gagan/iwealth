package com.github.florent37.materialviewpager.sample.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.chart.Tools;
import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.sample.HistoryHelper;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.Reward;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.lang.Math.max;


public class GoalsScrollFragment extends Fragment {

    private SharedPreferences sharedpreferences;

    @BindView(R.id.scrollViewGoals)
    NestedScrollView mScrollView;

    private TextView goalNameTV, goalValueTV, goalValueRemainingTV, goalTimeRemainingTV;

    public static GoalsScrollFragment newInstance() {
        return new GoalsScrollFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goals_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView);

        sharedpreferences = this.getActivity().getSharedPreferences(MainActivity.MY_PREFERENCE_KEY, Context.MODE_PRIVATE);

        goalNameTV = (TextView) view.findViewById(R.id.goals_goal_name_tv);
        goalValueTV = (TextView) view.findViewById(R.id.goals_goal_value_tv);
        goalValueRemainingTV = (TextView) view.findViewById(R.id.goals_goal_remaining_tv);
        goalTimeRemainingTV = (TextView) view.findViewById(R.id.goals_time_tv);

        updateViews();
    }

    @Override
    public void onResume(){
        super.onResume();

        updateViews();
    }


    private void updateViews() {
        int totalRewardValue = sharedpreferences.getInt(MainActivity.REWARD_TOTAL_KEY, 663);
        int goalValue = sharedpreferences.getInt(MainActivity.GOAL_VALUE_KEY, 2400);
        int goalValueRemaining = max(goalValue - totalRewardValue, 0);

        goalNameTV.setText("Trip to Paris");
        goalValueTV.setText("$" + goalValue);
        goalValueRemainingTV.setText("$" + goalValueRemaining);
        goalTimeRemainingTV.setText(" in 34 weeks");
    }


}
