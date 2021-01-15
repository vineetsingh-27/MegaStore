package com.example.megastore.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.megastore.R;
import com.example.megastore.adapters.HomePageAdapter;
import com.example.megastore.model.HomePageModel;
import com.example.megastore.model.HorizontalProductScrollModel;
import com.example.megastore.model.SliderModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recycler_view);

        ////////////Banner Slider ViewPager
        /**
         * Trick used for infinite scrolling
         */
        List<SliderModel> sliderModelList = new ArrayList<>();

        sliderModelList.add(new SliderModel(R.drawable.ic_wishlist_24, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.user, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_green, "#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.ic_shopping_cart_24, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_home_black_24, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_order_24, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_wishlist_24, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_wishlist_24, "#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.user, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_email_green, "#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_shopping_cart_24, "#077AE4"));

        ////////////Banner Slider ViewPager


        /////////Horizontal Product Layout
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redmi note 8 pro", "Mt Helios G90T", "Rs.17999/-"));
        /////////Horizontal Product Layout


        ///////////////////////////
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#000000"));
        homePageModelList.add(new HomePageModel(2, "Deals of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3, "Deals of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#FFFFFF"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#E4E4E4"));

        HomePageAdapter homePageAdapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}