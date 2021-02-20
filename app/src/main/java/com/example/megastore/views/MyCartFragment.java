package com.example.megastore.views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;
    private TextView totalAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        /////loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////loading dialog

        cartItemRecyclerView = view.findViewById(R.id.cart_item_recycler_view);
        continueBtn = view.findViewById(R.id.cart_continue_btn);
        totalAmount = view.findViewById(R.id.total_cart_amount);


        cartAdapter = new CartAdapter(DBQueries.cartItemModelList, totalAmount, true);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryActivity.cartItemModelList = new ArrayList<>();

                DeliveryActivity.fromCart =true;
                for (int x = 0; x < DBQueries.cartItemModelList.size(); x++) {
                    CartItemModel cartItemModel = DBQueries.cartItemModelList.get(x);
                    if (cartItemModel.isInStock()) {
                        DeliveryActivity.cartItemModelList.add(cartItemModel);
                    }
                }
                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                loadingDialog.show();
                if (DBQueries.addressesModelList.size() == 0) {
                    DBQueries.loadAddresses(getContext(), loadingDialog);
                }else{
                    loadingDialog.dismiss();
                    Intent deliveryIntent = new Intent(getContext(),DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (DBQueries.cartItemModelList.size() == 0) {
            DBQueries.cartList.clear();
            DBQueries.loadCartList(getContext(), loadingDialog, true, new TextView(getContext()), totalAmount);
        } else {
            if (DBQueries.cartItemModelList.get(DBQueries.cartItemModelList.size() - 1).getType() == CartItemModel.TOTAL_AMOUNT) {
                LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }
            loadingDialog.dismiss();
        }
    }
}