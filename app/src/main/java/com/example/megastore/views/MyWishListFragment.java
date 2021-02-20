package com.example.megastore.views;

import android.app.Dialog;
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
    private Dialog loadingDialog;
    public static WishListAdapter wishListAdapter;

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

        /////loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////loading dialog

        if (DBQueries.wishListModelList.size() ==0){
            DBQueries.wishList.clear();
            DBQueries.loadWishList(getContext(),loadingDialog,true);
        }else{
            loadingDialog.dismiss();
        }

        wishListAdapter = new WishListAdapter(DBQueries.wishListModelList,true);
        wishListRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

        return view;
    }
}