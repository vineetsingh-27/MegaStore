package com.example.megastore.views;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.megastore.R;
import com.example.megastore.adapters.MyOrderAdapter;
import com.example.megastore.model.MyOrderItemModel;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {

    private RecyclerView myOrderRecyclerView;


    public MyOrdersFragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrderRecyclerView = view.findViewById(R.id.my_orders_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.redimi,2,"Pixel 2XL (BLACK)","Delivered on Mon,15th JAn 2013"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.redimi,3,"Pixel 3XL (BLACK)","Delivered on Mon,15th JAn 2013"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.redimi,1,"Pixel 4XL (BLACK)","Delivered on Mon,15th JAn 2013"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.redimi,5,"Pixel 5XL (BLACK)","Cancelled"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }
}