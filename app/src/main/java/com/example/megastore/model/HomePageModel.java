package com.example.megastore.model;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER = 0;
    public static final int STRIP_AD_BANNER = 1;
    public static final int HORIZONTAL_PRODUCT_VIEW = 2;
    public static final int GRID_PRODUCT_VIEW = 3;
    public static final int VERTICAL_PRODUCT_VIEW = 4;

    private int type;
    private String backgroundColor;

    /**
     * Banner slider *
     */
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }


    /**
     * Strip ad Layout *
     */
    private String resource;

    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    /**
     * Horizontal view layout & Grid Product Layout
     */
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    //////Horizontal Product layout

    private List<WishListModel> viewAllProductList;

    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList, List<WishListModel> viewALlProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductList = viewALlProductList;

    }

    public List<WishListModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishListModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    ////////////Horizontal Product layout

    /////////// Grid Product layout
    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor = backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    ///////// Grid Product layout
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    /////////Grid Product layout

    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }

    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    ////////Vertical Product Layout

    private List<VerticalProductScrollModel> verticalProductScrollModelList;

    public HomePageModel(int type, String title, List<VerticalProductScrollModel> verticalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.verticalProductScrollModelList = verticalProductScrollModelList;
    }

    public List<VerticalProductScrollModel> getVerticalProductScrollModelList() {
        return verticalProductScrollModelList;
    }

    public void setVerticalProductScrollModelList(List<VerticalProductScrollModel> verticalProductScrollModelList) {
        this.verticalProductScrollModelList = verticalProductScrollModelList;
    }
}
