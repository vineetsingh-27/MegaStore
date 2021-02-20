package com.example.megastore.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.megastore.R;
import com.example.megastore.adapters.CategoryAdapter;
import com.example.megastore.adapters.HomePageAdapter;
import com.example.megastore.adapters.VerticalProductScrollAdapter;
import com.example.megastore.model.CategoryModel;
import com.example.megastore.model.HomePageModel;
import com.example.megastore.model.HorizontalProductScrollModel;
import com.example.megastore.model.SliderModel;
import com.example.megastore.model.VerticalProductScrollModel;
import com.example.megastore.model.WishListModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.megastore.views.DBQueries.categoryModelList;
import static com.example.megastore.views.DBQueries.lists;
import static com.example.megastore.views.DBQueries.loadCategories;
import static com.example.megastore.views.DBQueries.loadFragmentData;
import static com.example.megastore.views.DBQueries.loadedCategoriesName;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private RecyclerView verticalRecyclerView;
    private HomePageAdapter homePageAdapter;
    private ImageView noInternetConnection;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retryBtn;

    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        retryBtn = view.findViewById(R.id.retry_btn);
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        homePageRecyclerView = view.findViewById(R.id.home_page_recycler_view);

        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary), getContext().getResources().getColor(R.color.colorPrimary));
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

        ////categories fake list
        categoryModelFakeList.add(new CategoryModel("null", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        categoryModelFakeList.add(new CategoryModel("", ""));
        ////categories fake list

        ////home page fake list
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));
        sliderModelFakeList.add(new SliderModel("null", "#dfdfdf"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("", "", "", "", ""));

        homePageModelFakeList.add(new HomePageModel(0, sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1, "", "#dfdfdf"));
        homePageModelFakeList.add(new HomePageModel(2, "", "#dfdfdf", horizontalProductScrollModelFakeList, new ArrayList<WishListModel>()));
        homePageModelFakeList.add(new HomePageModel(3, "", "#dfdfdf", horizontalProductScrollModelFakeList));
        ////home page fake list


        categoryAdapter = new CategoryAdapter(categoryModelFakeList);
        homePageAdapter = new HomePageAdapter(homePageModelFakeList);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if (categoryModelList.size() == 0) {
                loadCategories(categoryRecyclerView, getContext());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            if (lists.size() == 0) {
                loadedCategoriesName.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView, getContext(), 0, "HOME");
            } else {
                homePageAdapter = new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(homePageAdapter);
        } else {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.home).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }

        verticalRecyclerView = view.findViewById(R.id.vertical_recycler_view);

        List<VerticalProductScrollModel> verticalProductScrollModelList = new ArrayList<>();

        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));
        verticalProductScrollModelList.add(new VerticalProductScrollModel(R.drawable.redimi, "Pixel 2XL", "9999"));

        VerticalProductScrollAdapter verticalProductScrollAdapter = new VerticalProductScrollAdapter(verticalProductScrollModelList);
        verticalRecyclerView.setAdapter(verticalProductScrollAdapter);

        //// refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });
        //// refresh layout

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
            }
        });
        return view;
    }

    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
//        categoryModelList.clear();
//        lists.clear();
//        loadedCategoriesName.clear();
        DBQueries.clearData();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            homePageAdapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(homePageAdapter);

            loadCategories(categoryRecyclerView, getContext());

            loadedCategoriesName.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView, getContext(), 0, "HOME");
        } else {
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            Toast.makeText(getContext(), "No internet Connection found!", Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.home).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);

            swipeRefreshLayout.setRefreshing(false);
        }
    }
}