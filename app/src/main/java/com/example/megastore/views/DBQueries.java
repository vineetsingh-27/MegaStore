package com.example.megastore.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.megastore.R;
import com.example.megastore.adapters.CategoryAdapter;
import com.example.megastore.adapters.HomePageAdapter;
import com.example.megastore.model.AddressesModel;
import com.example.megastore.model.CartItemModel;
import com.example.megastore.model.CategoryModel;
import com.example.megastore.model.HomePageModel;
import com.example.megastore.model.HorizontalProductScrollModel;
import com.example.megastore.model.SliderModel;
import com.example.megastore.model.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.megastore.views.ProductDetailsActivity.initialRating;
import static com.example.megastore.views.ProductDetailsActivity.productID;
import static com.example.megastore.views.ProductDetailsActivity.rateNowContainer;
import static com.example.megastore.views.ProductDetailsActivity.running_rating_query;
import static com.example.megastore.views.ProductDetailsActivity.setRating;

public class DBQueries {


    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesName = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishListModel> wishListModelList = new ArrayList<>();

    public static List<String> myRatedIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();

    public static int selectedAddress = -1;
    public static List<AddressesModel> addressesModelList = new ArrayList<>();

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(final RecyclerView homepageRecyclerView, final Context context, final int index, String categoryName) {

        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                    for (long x = 1; x <= no_of_banners; x++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + x).toString(),
                                                documentSnapshot.get("banner_" + x + "_background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0, sliderModelList));
                                } else if (((long) documentSnapshot.get("view_type") == 1)) {
                                    lists.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("background").toString()));
                                } else if (((long) documentSnapshot.get("view_type") == 2)) {

                                    List<WishListModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString(),
                                                documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("product_title_" + x).toString(),
                                                documentSnapshot.get("product_subtitle_" + x).toString(),
                                                documentSnapshot.get("product_price_" + x).toString()));

                                        viewAllProductList.add(new WishListModel(documentSnapshot.get("product_ID_" + x).toString(), documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("product_full_title_" + x).toString(),
                                                (long) documentSnapshot.get("free_coupon_" + x),
                                                documentSnapshot.get("average_rating_" + x).toString(),
                                                (long) documentSnapshot.get("total_ratings_" + x),
                                                documentSnapshot.get("product_price_" + x).toString(),
                                                documentSnapshot.get("cutted_price_" + x).toString(),
                                                (boolean) documentSnapshot.get("COD_" + x),
                                                (boolean) documentSnapshot.get("in_stock_" + x)));
                                    }
                                    lists.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList, viewAllProductList));
                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    List<HorizontalProductScrollModel> gridProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        gridProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString(),
                                                documentSnapshot.get("product_image_" + x).toString(),
                                                documentSnapshot.get("product_title_" + x).toString(),
                                                documentSnapshot.get("product_subtitle_" + x).toString(),
                                                documentSnapshot.get("product_price_" + x).toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), gridProductScrollModelList));
                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            homepageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishList(final Context context, final Dialog dialog, final boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA")
                .document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add(task.getResult().get("product_ID_" + x).toString());

                        if (DBQueries.wishList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                            if (ProductDetailsActivity.addToWishListButton != null) {
                                ProductDetailsActivity.addToWishListButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }
                        } else {
                            if (ProductDetailsActivity.addToWishListButton != null) {
                                ProductDetailsActivity.addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                        }

                        if (loadProductData) {
                            wishListModelList.clear();
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS")
                                    .document(productId).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                wishListModelList.add(new WishListModel(productId, task.getResult().get("product_image_1").toString(),
                                                        task.getResult().get("product_title").toString(),
                                                        (long) task.getResult().get("free_coupons"),
                                                        task.getResult().get("average_rating").toString(),
                                                        (long) task.getResult().get("total_ratings"),
                                                        task.getResult().get("product_price").toString(),
                                                        task.getResult().get("cutted_price").toString(),
                                                        (boolean) task.getResult().get("COD"),
                                                        (boolean) task.getResult().get("in_stock")));
                                                MyWishListFragment.wishListAdapter.notifyDataSetChanged();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishList(final int index, final Context context) {
        final String removedProductId = wishList.get(index);
        wishList.remove(index);
        Map<String, Object> updateWishList = new HashMap<>();

        for (int x = 0; x < wishList.size(); x++) {
            updateWishList.put("product_ID_" + x, wishList.get(x));
        }
        updateWishList.put("list_size", (long) wishList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).
                collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (wishListModelList.size() != 0) {
                                wishListModelList.remove(index);
                                MyWishListFragment.wishListAdapter.notifyDataSetChanged();
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                            Toast.makeText(context, "Removed successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (ProductDetailsActivity.addToWishListButton != null) {
                                ProductDetailsActivity.addToWishListButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }
                            wishList.add(index, removedProductId);
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.running_wishlist_query = false;
                    }
                });
    }

    public static void loadRatingList(final Context context) {
        if (!running_rating_query) {
            running_rating_query = true;
            myRatedIds.clear();
            myRating.clear();
            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                            myRatedIds.add(task.getResult().get("product_ID_" + x).toString());
                            myRating.add((long) task.getResult().get("rating_" + x));

                            if (task.getResult().get("product_ID_" + x).toString().equals(productID)) {
                                initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + x))) - 1;
                                if (rateNowContainer != null) {
                                    setRating(initialRating);
                                }
                            }

                        }
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    }
                    running_rating_query = false;
                }
            });
        }
    }

    public static void loadCartList(final Context context, final Dialog dialog, final boolean loadProductData, final TextView badgeCount, final TextView cartTotalAmount) {
        cartList.clear();
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA")
                .document("MY_CART")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        cartList.add(task.getResult().get("product_ID_" + x).toString());

                        if (DBQueries.cartList.contains(ProductDetailsActivity.productID)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                        } else {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                        }

                        if (loadProductData) {
                            cartItemModelList.clear();
                            final String productId = task.getResult().get("product_ID_" + x).toString();
                            firebaseFirestore.collection("PRODUCTS")
                                    .document(productId).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                int index = 0;
                                                if (cartList.size() >= 2) {
                                                    index = cartList.size() - 2;
                                                }
                                                cartItemModelList.add(index, new CartItemModel(CartItemModel.CART_ITEM, productId, task.getResult().get("product_image_1").toString()
                                                        , task.getResult().get("product_title").toString()
                                                        , (long) task.getResult().get("free_coupons")
                                                        , task.getResult().get("product_price").toString()
                                                        , task.getResult().get("cutted_price").toString()
                                                        , (long) 1
                                                        , (long) 0
                                                        , (long) 0
                                                        , (boolean) task.getResult().get("in_stock")));
                                                if (cartList.size() == 1) {
                                                    cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));
                                                    LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                                    parent.setVisibility(View.VISIBLE);
                                                }
                                                if (cartList.size() == 0) {
                                                    cartItemModelList.clear();
                                                }
                                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                                            } else {
                                                String error = task.getException().getMessage();
                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }

                    }
                    if (cartList.size() != 0) {
                        badgeCount.setVisibility(View.VISIBLE);
                    } else {
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                    if (DBQueries.cartList.size() < 99) {
                        badgeCount.setText(String.valueOf(DBQueries.cartList.size()));
                    } else {
                        badgeCount.setText("99");
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }

    public static void removeFromCart(final int index, final Context context, final TextView cartTotalAmount) {
        final String removedProductId = cartList.get(index);
        cartList.remove(index);
        Map<String, Object> updateCartList = new HashMap<>();

        for (int x = 0; x < cartList.size(); x++) {
            updateCartList.put("product_ID_" + x, cartList.get(x));
        }
        updateCartList.put("list_size", (long) cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).
                collection("USER_DATA").document("MY_CART")
                .set(updateCartList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (cartItemModelList.size() != 0) {
                                cartItemModelList.remove(index);
                                MyCartFragment.cartAdapter.notifyDataSetChanged();
                            }
                            if (cartList.size() == 0) {
                                LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                parent.setVisibility(View.GONE);
                                cartItemModelList.clear();
                            }
                            Toast.makeText(context, "Removed successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            cartList.add(index, removedProductId);
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        ProductDetailsActivity.running_cart_query = false;
                    }
                });
    }

    public static void loadAddresses(final Context context, final Dialog loadingDialog) {
        addressesModelList.clear();

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_ADDRESSES").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Intent deliveryIntent;
                            if ((long) task.getResult().get("list_size") == 0) {
                                deliveryIntent = new Intent(context, AddressActivity.class);
                                deliveryIntent.putExtra("INTENT", "deliveryIntent");
                            } else {
                                for (long x = 1; x < (long) task.getResult().get("list_size") + 1; x++) {
                                    addressesModelList.add(new AddressesModel(
                                            task.getResult().get("fullname_" + x).toString(),
                                            task.getResult().get("address_" + x).toString(),
                                            task.getResult().get("pincode_" + x).toString(),
                                            (boolean) task.getResult().get("selected_" + x),
                                            task.getResult().get("mobile_no_" + x).toString()));
                                    if ((boolean) task.getResult().get("selected_" + x)) {
                                        selectedAddress = Integer.parseInt(String.valueOf(x - 1));
                                    }
                                }
                                deliveryIntent = new Intent(context, DeliveryActivity.class);
                            }
                            context.startActivity(deliveryIntent);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
    }

    public static void clearData() {

        categoryModelList.clear();
        lists.clear();
        loadedCategoriesName.clear();
        wishList.clear();
        wishListModelList.clear();
        cartList.clear();
        cartItemModelList.clear();
        myRatedIds.clear();
        myRating.clear();
        addressesModelList.clear();
    }
}
