package com.example.megastore.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.megastore.R;
import com.example.megastore.adapters.WishListAdapter;
import com.example.megastore.model.WishListModel;

import java.util.ArrayList;
import java.util.List;

public class MyWishListFragment extends Fragment {

    private RecyclerView wishListRecyclerView;

    public MyWishListFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_my_wish_list, container, false);

        wishListRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);
        List<WishListModel> wishListModelList = new ArrayList<>();
        wishListModelList.add(new WishListModel(R.drawable.redimi,"Redmi 5A",1,"3",25,"Rs.49999/-","Rs.54999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.drawable.redimi,"Redmi 5A",2,"4",30,"Rs.49999/-","Rs.54999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.drawable.redimi,"Redmi 5A",0,"5",45,"Rs.49999/-","Rs.54999/-","Cash On Delivery"));
        wishListModelList.add(new WishListModel(R.drawable.redimi,"Redmi 5A",3,"2",55,"Rs.49999/-","Rs.54999/-","Online Delivery"));

        WishListAdapter wishListAdapter = new WishListAdapter(wishListModelList,true);
        wishListRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

        return view;
    }
}