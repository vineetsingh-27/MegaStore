package com.example.megastore.model;

public class ProductSpecificationModel {

    public static final int SPECIFICATION_TITLE = 0;
    public static final int SPECIFICATION_BODY = 1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    ////////specification title
    private String title;
    public ProductSpecificationModel(int type,String title) {
        this.type=type;
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    ////////specification title
    ////////specification title


    ////////specification body
    private String featuresName;
    private String featuresValue;

    public ProductSpecificationModel(int type, String featuresName, String featuresValue) {
        this.type = type;
        this.featuresName = featuresName;
        this.featuresValue = featuresValue;
    }

    public String getFeaturesName() {
        return featuresName;
    }

    public void setFeaturesName(String featuresName) {
        this.featuresName = featuresName;
    }

    public String getFeaturesValue() {
        return featuresValue;
    }

    public void setFeaturesValue(String featuresValue) {
        this.featuresValue = featuresValue;
    }
    ////////specification body
}

