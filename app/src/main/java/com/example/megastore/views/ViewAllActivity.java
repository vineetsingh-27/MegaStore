package com.example.megastore.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.megastore.R;
import com.example.megastore.adapters.GridProductLayoutAdapter;
import com.example.megastore.adapters.WishListAdapter;
import com.example.megastore.model.HorizontalProductScrollModel;
import com.example.megastore.model.WishListModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Deals of the Day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (layout_code == 0) {
            recyclerView.setVisibility(View.VISIBLE);
            List<WishListModel> wishListModelList = new ArrayList<>();
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 1, "3", 25, "Rs.49999/-", "Rs.54999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 2, "4", 30, "Rs.49999/-", "Rs.54999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 0, "5", 45, "Rs.49999/-", "Rs.54999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 3, "2", 55, "Rs.49999/-", "Rs.54999/-", "Online Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 1, "3", 25, "Rs.49999/-", "Rs.54999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 2, "4", 30, "Rs.49999/-", "Rs.54999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 0, "5", 45, "Rs.49999/-", "Rs.54999/-", "Cash On Delivery"));
            wishListModelList.add(new WishListModel(R.drawable.redimi, "Redmi 5A", 3, "2", 55, "Rs.49999/-", "Rs.54999/-", "Online Delivery"));


            WishListAdapter adapter = new WishListAdapter(wishListModelList, false);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else if (layout_code == 1) {
            gridView.setVisibility(View.VISIBLE);

            List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();

            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));


            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}