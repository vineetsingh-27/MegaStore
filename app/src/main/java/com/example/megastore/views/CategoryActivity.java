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
import com.example.megastore.model.WishListModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.megastore.views.DBQueries.lists;
import static com.example.megastore.views.DBQueries.loadFragmentData;
import static com.example.megastore.views.DBQueries.loadedCategoriesName;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ////home page fake list
        HomePageAdapter adapter;
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));

        homePageModelFakeList.add(new HomePageModel(0, sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1, "", "#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2, "", "#ffffff", horizontalProductScrollModelFakeList, new ArrayList<WishListModel>()));
        homePageModelFakeList.add(new HomePageModel(3, "", "#ffffff", horizontalProductScrollModelFakeList));
        ////home page fake list

        categoryRecyclerView = findViewById(R.id.category_recycler_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        adapter = new HomePageAdapter(homePageModelFakeList);

        int listPosition = 0;
        for (int x = 0; x < loadedCategoriesName.size(); x++) {
            if (loadedCategoriesName.get(x).equals(title.toUpperCase())) {
                listPosition = x;
            }
        }
        if (listPosition == 0) {
            loadedCategoriesName.add(title.toUpperCase());
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(categoryRecyclerView, this, loadedCategoriesName.size() - 1, title);
        } else {
            adapter = new HomePageAdapter(lists.get(listPosition));
        }
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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