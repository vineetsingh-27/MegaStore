package com.example.megastore.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.megastore.R;
import com.example.megastore.adapters.MyRewardsAdapter;
import com.example.megastore.model.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class MyRewardsFragment extends Fragment {

    private RecyclerView rewardsRecyclerView;

    public MyRewardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);

        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Discount", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Discount", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList);

        rewardsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();


        return view;
    }
}