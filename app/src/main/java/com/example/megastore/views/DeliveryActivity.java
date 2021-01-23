package com.example.megastore.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.megastore.R;
import com.example.megastore.adapters.CartAdapter;
import com.example.megastore.model.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeORaddNewAddressBtn;

    public static final int SELECT_ADDRESS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        changeORaddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.redimi,"Redimi 5A",0,"Rs.49999/-","Rs.54999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.redimi,"Redimi 5A",0,"Rs.49999/-","Rs.54999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.redimi,"Redimi 5A",0,"Rs.49999/-","Rs.54999/-",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"Price(2 Items)","Rs.49999/-","Free","Rs.49999/-","Rs.4000/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

       changeORaddNewAddressBtn.setVisibility(View.VISIBLE);
       changeORaddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressActivity.class);
               myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
               startActivity(myAddressesIntent);
           }
       });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}