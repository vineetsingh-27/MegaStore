package com.example.megastore.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.megastore.R;
import com.example.megastore.adapters.CategoryAdapter;
import com.example.megastore.adapters.HomePageAdapter;
import com.example.megastore.model.CategoryModel;
import com.example.megastore.model.HomePageModel;
import com.example.megastore.model.HorizontalProductScrollModel;
import com.example.megastore.model.SliderModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);

        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("Link", "Home"));
        categoryModelList.add(new CategoryModel("Link", "Toys"));
        categoryModelList.add(new CategoryModel("Link", "Shoes"));
        categoryModelList.add(new CategoryModel("Link", "Electronics"));
        categoryModelList.add(new CategoryModel("Link", "Appliance"));
        categoryModelList.add(new CategoryModel("Link", "Appliance1"));
        categoryModelList.add(new CategoryModel("Link", "Appliance2"));
        categoryModelList.add(new CategoryModel("Link", "Appliance3"));
        categoryModelList.add(new CategoryModel("Link", "Appliance4"));
        categoryModelList.add(new CategoryModel("Link", "Appliance5"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        ////////////////Banner Slider

        List<SliderModel> sliderModelList = new ArrayList<>();

        sliderModelList.add(new SliderModel(R.drawable.ic_email_green, "#077AE4"));
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

        ////////////////Banner Slider


        //////////Horizontal Product Layout
        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();

        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.redimi, "Redimi 5A", "SD 625 Processor", "Rs.5999/-"));

        //////////Horizontal Product Layout


        //////////////////////////////

        testing = view.findViewById(R.id.home_page_recycler_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#000000"));
        homePageModelList.add(new HomePageModel(2, "Deals of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3, "Deals of the day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#FFFFFF"));
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, R.drawable.banner, "#E4E4E4"));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //////////////////////////////

        return view;
    }
}