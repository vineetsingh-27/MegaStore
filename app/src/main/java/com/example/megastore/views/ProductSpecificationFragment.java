package com.example.megastore.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.megastore.R;
import com.example.megastore.adapters.ProductSpecificationAdapter;
import com.example.megastore.model.ProductSpecificationModel;

import java.util.List;

public class ProductSpecificationFragment extends Fragment {

    private RecyclerView productSpecificationRecyclerView;
    public List<ProductSpecificationModel> productSpecificationModelsList;


    public ProductSpecificationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecyclerView = view.findViewById(R.id.product_specification_recyclerview);

        //        productSpecificationModelList.add(new ProductSpecificationModel(0,"General"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0,"Display"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM","4GB"));

        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelsList);
        productSpecificationRecyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }
}