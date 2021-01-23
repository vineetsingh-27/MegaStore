package com.example.megastore.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.megastore.R;
import com.example.megastore.adapters.CartAdapter;
import com.example.megastore.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;
    private Button continueBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemRecyclerView = view.findViewById(R.id.cart_item_recycler_view);
        continueBtn = view.findViewById(R.id.cart_continue_btn);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.redimi,"Redimi 5A",0,"Rs.49999/-","Rs.54999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.redimi,"Redimi 5A",0,"Rs.49999/-","Rs.54999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.redimi,"Redimi 5A",0,"Rs.49999/-","Rs.54999/-",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"Price(2 Items)","Rs.49999/-","Free","Rs.49999/-","Rs.4000/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addressIntent = new Intent(getContext(),AddressActivity.class);
                getContext().startActivity(addressIntent);
            }
        });


        return view;
    }
}