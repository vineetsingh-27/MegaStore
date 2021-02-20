package com.example.megastore.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.megastore.R;
import com.example.megastore.adapters.MyRewardsAdapter;
import com.example.megastore.adapters.ProductDetailsAdapter;
import com.example.megastore.adapters.ProductImagesAdapter;
import com.example.megastore.model.CartItemModel;
import com.example.megastore.model.ProductSpecificationModel;
import com.example.megastore.model.RewardModel;
import com.example.megastore.model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.megastore.views.DBQueries.cartList;
import static com.example.megastore.views.MainActivity.showCart;
import static com.example.megastore.views.RegisterActivity.setSignUpFragment;

public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;
    public static boolean running_cart_query = false;
    public static Activity productDetailsActivity;

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;

    private TextView productTitle;
    private TextView averageRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;
    private ImageView cod_indicator;
    private TextView tv_cod_indicator;

    private TextView rewardTitle;
    private TextView rewardBody;

    ///////rating layout
    public static LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;
    public static int initialRating;
    ///////rating layout

    private Button buyNowBtn;
    private LinearLayout couponRedemptionLayout;
    private Button couponRedeemBtn;
    private LinearLayout addToCartBtn;
    public static MenuItem cartItem;
    private TextView badgeCount;

    public static FloatingActionButton addToWishListButton;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CART = false;

    ////////CouponDialog
    public static TextView couponTitle;
    public static TextView couponExpiryDate;
    public static TextView couponBody;
    private static RecyclerView couponsRecyclerView;
    private static LinearLayout selectedCoupon;
    ////////CouponDialog

    private Dialog signInDialog;
    private FirebaseUser currentUser;
    private Dialog loadingDialog;

    ////////Product Description
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private TextView productOnlyDescriptionBody;

    private String productDescription;
    private String productOtherDetails;
    public static String productID;
    private DocumentSnapshot documentSnapshot;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    ////////Product Description

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishListButton = findViewById(R.id.add_to_wishList_button);
        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.but_now_button);
        couponRedeemBtn = findViewById(R.id.coupon_redemption_button);
        productTitle = findViewById(R.id.product_title);
        averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        cod_indicator = findViewById(R.id.cod_indicator_imageView);
        tv_cod_indicator = findViewById(R.id.tv_cod_indicator);
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);
        productDetailsTabContainer = findViewById(R.id.product_details_tabs_container);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressBar_container);
        averageRating = findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_button);
        couponRedemptionLayout = findViewById(R.id.coupon_redemption_layout);
        firebaseFirestore = FirebaseFirestore.getInstance();

        /////loading dialog
        initialRating = -1;
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        /////loading dialog

        final List<String> productImages = new ArrayList<>();
        productID = getIntent().getStringExtra("PRODUCT_ID");
        firebaseFirestore.collection("PRODUCTS").document(productID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    totalRatingMiniView.setText("(" + documentSnapshot.get("total_ratings") + ")ratings");
                    productPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                    cuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price").toString() + "/-");
                    if ((boolean) documentSnapshot.get("COD")) {
                        cod_indicator.setVisibility(View.VISIBLE);
                        tv_cod_indicator.setVisibility(View.VISIBLE);
                    } else {
                        cod_indicator.setVisibility(View.INVISIBLE);
                        tv_cod_indicator.setVisibility(View.INVISIBLE);
                    }
                    rewardTitle.setText((long) documentSnapshot.get("free_coupons") + documentSnapshot.get("free_coupon_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupon_body").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();
                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));
                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(),
                                        documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()));
                            }
                        }
                    } else {
                        productDetailsTabContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }

                    totalRatings.setText((long) documentSnapshot.get("total_ratings") + " ratings");

                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long) documentSnapshot.get(5 - x + "_star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_ratings")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "_star"))));
                    }
                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));
                    averageRating.setText(documentSnapshot.get("average_rating").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));

                    if (currentUser != null) {
                        if (DBQueries.myRating.size() == 0) {
                            DBQueries.loadRatingList(ProductDetailsActivity.this);
                        }

                        if (DBQueries.cartList.size() == 0) {
                            DBQueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
                        }
                        if (DBQueries.wishList.size() == 0) {
                            DBQueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
                        } else {
                            loadingDialog.dismiss();
                        }

                    } else {
                        loadingDialog.dismiss();
                    }

                    if (DBQueries.myRatedIds.contains(productID)) {
                        int index = DBQueries.myRatedIds.indexOf(productID);
                        initialRating = Integer.parseInt(String.valueOf(DBQueries.myRating.get(index))) - 1;
                        setRating(initialRating);
                    }
                    if (DBQueries.cartList.contains(productID)) {
                        ALREADY_ADDED_TO_CART = true;
                    } else {
                        ALREADY_ADDED_TO_CART = false;
                    }

                    if (DBQueries.wishList.contains(productID)) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    } else {
                        addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }
                    if ((boolean) documentSnapshot.get("in_stock")) {
                        addToCartBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (currentUser == null) {
                                    signInDialog.show();
                                } else {
                                    if (!running_cart_query) {
                                        running_cart_query = true;
                                        if (ALREADY_ADDED_TO_CART) {
                                            running_cart_query = false;
                                            Toast.makeText(ProductDetailsActivity.this, "Already added to cart!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Map<String, Object> addProduct = new HashMap<>();
                                            addProduct.put("product_ID_" + String.valueOf(DBQueries.cartList.size()), productID);
                                            addProduct.put("list_size", (long) (DBQueries.cartList.size() + 1));

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        if (DBQueries.cartItemModelList.size() != 0) {
                                                            DBQueries.cartItemModelList.add(0, new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString()
                                                                    , documentSnapshot.get("product_title").toString()
                                                                    , (long) documentSnapshot.get("free_coupons")
                                                                    , documentSnapshot.get("product_price").toString()
                                                                    , documentSnapshot.get("cutted_price").toString()
                                                                    , (long) 1
                                                                    , (long) 0
                                                                    , (long) 0
                                                                    , (boolean) documentSnapshot.get("in_stock")));
                                                        }
                                                        ALREADY_ADDED_TO_CART = true;
                                                        DBQueries.cartList.add(productID);
                                                        Toast.makeText(ProductDetailsActivity.this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                                                        invalidateOptionsMenu();
                                                        running_cart_query = false;
                                                    } else {
                                                        running_cart_query = false;
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        buyNowBtn.setVisibility(View.GONE);
                        TextView outOfStock = (TextView) addToCartBtn.getChildAt(0);
                        outOfStock.setText("Out of Stock");
                        outOfStock.setTextColor(getResources().getColor(R.color.colorPrimary));
                        outOfStock.setCompoundDrawables(null, null, null, null);

                    }
                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (!running_wishlist_query) {
                        running_wishlist_query = true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DBQueries.wishList.indexOf(productID);
                            DBQueries.removeFromWishList(index, ProductDetailsActivity.this);
                            addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBQueries.wishList.size()), productID);
                            addProduct.put("list_size", (long) (DBQueries.wishList.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        if (DBQueries.wishListModelList.size() != 0) {
                                            DBQueries.wishListModelList.add(new WishListModel(productID, documentSnapshot.get("product_image_1").toString(),
                                                    documentSnapshot.get("product_title").toString(),
                                                    (long) documentSnapshot.get("free_coupons"),
                                                    documentSnapshot.get("average_rating").toString(),
                                                    (long) documentSnapshot.get("total_ratings"),
                                                    documentSnapshot.get("product_price").toString(),
                                                    documentSnapshot.get("cutted_price").toString(),
                                                    (boolean) documentSnapshot.get("COD"),
                                                    (boolean) documentSnapshot.get("in_stock")));
                                        }
                                        ALREADY_ADDED_TO_WISHLIST = true;
                                        addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                        DBQueries.wishList.add(productID);
                                        Toast.makeText(ProductDetailsActivity.this, "Added to wishlist successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    running_wishlist_query = false;
                                }
                            });
                        }
                    }
                }
            }
        });

        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ///////rating layout
        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null) {
                        signInDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!running_rating_query) {
                                running_rating_query = true;

                                setRating(starPosition);
                                Map<String, Object> updateRating = new HashMap<>();

                                if (DBQueries.myRatedIds.contains(productID)) {
                                    TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldRating.getText().toString()) - 1);
                                    updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalRating.getText().toString()) + 1);
                                    updateRating.put("average_rating", calculateAverageRating((long) starPosition - initialRating, true));

                                } else {
                                    updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                    updateRating.put("average_rating", calculateAverageRating((long) starPosition + 1, false));
                                    updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);
                                }

                                firebaseFirestore.collection("PRODUCTS").document(productID).update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Map<String, Object> myRating = new HashMap<>();

                                            if (DBQueries.myRatedIds.contains(productID)) {
                                                myRating.put("rating_" + DBQueries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                            } else {
                                                myRating.put("list_size", (long) DBQueries.myRatedIds.size() + 1);
                                                myRating.put("product_ID_" + DBQueries.myRatedIds.size(), productID);
                                                myRating.put("rating_" + DBQueries.myRatedIds.size(), (long) starPosition + 1);
                                            }

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATINGS")
                                                    .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        if (DBQueries.myRatedIds.contains(productID)) {

                                                            DBQueries.myRating.set(DBQueries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                                            oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                            finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));
                                                        } else {
                                                            DBQueries.myRatedIds.add(productID);
                                                            DBQueries.myRating.add((long) starPosition + 1);

                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                            totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + ")ratings");
                                                            totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " ratings");
                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));

                                                            Toast.makeText(ProductDetailsActivity.this, "Thank you for rating!", Toast.LENGTH_SHORT).show();
                                                        }

                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingFigures = (TextView) ratingsNoContainer.getChildAt(x);
                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                            progressBar.setMax(maxProgress);
                                                            progressBar.setProgress(Integer.parseInt(ratingFigures.getText().toString()));
                                                        }
                                                        initialRating = starPosition;
                                                        averageRating.setText(calculateAverageRating(0, true));
                                                        averageRatingMiniView.setText(calculateAverageRating(0, true));

                                                        if (DBQueries.wishList.contains(productID) && DBQueries.wishListModelList.size() != 0) {
                                                            int index = DBQueries.wishList.indexOf(productID);
                                                            DBQueries.wishListModelList.get(index).setRating(averageRating.getText().toString());
                                                            DBQueries.wishListModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));
                                                        }

                                                    } else {
                                                        setRating(initialRating);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    running_rating_query = false;
                                                }
                                            });
                                        } else {
                                            running_rating_query = false;
                                            setRating(initialRating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
        ///////rating layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    DeliveryActivity.fromCart = false;
                    loadingDialog.show();
                    productDetailsActivity = ProductDetailsActivity.this;
                    DeliveryActivity.cartItemModelList = new ArrayList<>();
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productID, documentSnapshot.get("product_image_1").toString()
                            , documentSnapshot.get("product_title").toString()
                            , (long) documentSnapshot.get("free_coupons")
                            , documentSnapshot.get("product_price").toString()
                            , documentSnapshot.get("cutted_price").toString()
                            , (long) 1
                            , (long) 0
                            , (long) 0
                            , (boolean) documentSnapshot.get("in_stock")));
                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                    if (DBQueries.addressesModelList.size() == 0) {
                        DBQueries.loadAddresses(ProductDetailsActivity.this, loadingDialog);
                    } else {
                        loadingDialog.dismiss();
                        Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(deliveryIntent);
                    }
                }
            }
        });

        ////////coupon dialog

        final Dialog checkCouponPriceDialog = new Dialog(ProductDetailsActivity.this);
        checkCouponPriceDialog.setContentView(R.layout.coupon_redeem_dialog);
        checkCouponPriceDialog.setCancelable(true);
        checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recycler_view);
        couponsRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupon_recyclerView);
        selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupon);
        couponTitle = checkCouponPriceDialog.findViewById(R.id.coupon_title);
        couponExpiryDate = checkCouponPriceDialog.findViewById(R.id.coupon_valadity);
        couponBody = checkCouponPriceDialog.findViewById(R.id.coupon_body);


        TextView originalPrice = checkCouponPriceDialog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCouponPriceDialog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Discount", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Discount", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));
        rewardModelList.add(new RewardModel("CashBack", "Till 2nd June 2021", "GET 20% OFF on any product above Rs.1000/- and below Rs.500/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList, true);
        couponsRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerView();
            }
        });

        //////////////coupon dialog

        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkCouponPriceDialog.show();
            }
        });

        /////sign in dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);

        final Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        ////sign in dialog
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            couponRedemptionLayout.setVisibility(View.GONE);
        } else {
            couponRedemptionLayout.setVisibility(View.VISIBLE);
        }

        if (currentUser != null) {
            if (DBQueries.myRating.size() == 0) {
                DBQueries.loadRatingList(ProductDetailsActivity.this);
            }
            if (DBQueries.wishList.size() == 0) {
                DBQueries.loadWishList(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }

        if (DBQueries.myRatedIds.contains(productID)) {
            int index = DBQueries.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBQueries.myRating.get(index))) - 1;
            setRating(initialRating);
        }
        if (DBQueries.cartList.contains(productID)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }

        if (DBQueries.wishList.contains(productID)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            ALREADY_ADDED_TO_WISHLIST = false;
        }
        invalidateOptionsMenu();
    }

    public static void showDialogRecyclerView() {
        if (couponsRecyclerView.getVisibility() == View.GONE) {
            couponsRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponsRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    public static void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= starPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    private String calculateAverageRating(long currentUserRating, boolean update) {
        Double totalStar = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalStar = totalStar + (Long.parseLong(ratingNo.getText().toString()) * x);
        }
        totalStar = totalStar + currentUserRating;
        if (update) {
            return String.valueOf(totalStar / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);
        } else {
            return String.valueOf(totalStar / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);

        cartItem = menu.findItem(R.id.main_cart_icon);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeIcon.setImageResource(R.drawable.ic_shopping_cart_24);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

        if (currentUser != null) {
            if (DBQueries.cartList.size() == 0) {
                DBQueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
            } else {
                badgeCount.setVisibility(View.VISIBLE);
            }
            if (cartList.size() < 99) {
                badgeCount.setText(String.valueOf(cartList.size()));
            } else {
                badgeCount.setText("99");
            }
        }
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                    showCart = true;
                    startActivity(cartIntent);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            productDetailsActivity = null;
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            //todo: search
            return true;
        } else if (id == R.id.main_cart_icon) {
            //todo: cart
            // myCart();
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productDetailsActivity = null;
        super.onBackPressed();
    }
}