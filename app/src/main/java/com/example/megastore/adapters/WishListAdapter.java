package com.example.megastore.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.megastore.R;
import com.example.megastore.model.WishListModel;
import com.example.megastore.views.DBQueries;
import com.example.megastore.views.ProductDetailsActivity;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private List<WishListModel> wishListModelList;
    private Boolean wishList;
    private int lastPosition = -1;

    public WishListAdapter(List<WishListModel> wishListModelList, Boolean wishList) {
        this.wishListModelList = wishListModelList;
        this.wishList = wishList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        String productId = wishListModelList.get(position).getProductId();
        String resource = wishListModelList.get(position).getProductImage();
        String title = wishListModelList.get(position).getProductTitle();
        long freeCoupons = wishListModelList.get(position).getFreeCoupons();
        String rating = wishListModelList.get(position).getRating();
        long totalRatings = wishListModelList.get(position).getTotalRatings();
        String productPrice = wishListModelList.get(position).getProductPrice();
        String cuttedPrice = wishListModelList.get(position).getCuttedPrice();
        boolean paymentMethod = wishListModelList.get(position).isPaymentMethod();
        boolean inStock = wishListModelList.get(position).isInStock();
        holder.setData(productId, resource, title, freeCoupons, rating, totalRatings, productPrice, cuttedPrice, paymentMethod, position, inStock);

        if (lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private ImageView couponIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private ImageButton deleteBtn;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupons = itemView.findViewById(R.id.free_coupon);
            couponIcon = itemView.findViewById(R.id.coupon_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_method);
        }

        private void setData(final String productId, String resource, String title, long freeCouponsNo, String averageRate, long totalRatingNo, String price, String cuttedPriceValue, boolean payMethod, final int index, boolean inStock) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.pic)).into(productImage);
            productTitle.setText(title);
            if (freeCouponsNo != 0 && inStock) {
                couponIcon.setVisibility(View.VISIBLE);
                if (freeCouponsNo == 1) {
                    freeCoupons.setText("Free " + freeCouponsNo + "coupon");
                } else {
                    freeCoupons.setText("Free " + freeCouponsNo + "coupons");
                }
            } else {
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }

            LinearLayout linearLayout = (LinearLayout) rating.getParent();
            if (inStock) {
                linearLayout.setVisibility(View.VISIBLE);
                rating.setVisibility(View.VISIBLE);
                totalRatings.setVisibility(View.VISIBLE);
                productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setVisibility(View.VISIBLE);

                rating.setText(averageRate);
                totalRatings.setText("(" + totalRatingNo + ")ratings");
                productPrice.setText("Rs." + price + "/-");
                cuttedPrice.setText("Rs." + cuttedPriceValue + "/-");

                if (payMethod) {
                    paymentMethod.setVisibility(View.VISIBLE);
                } else {
                    paymentMethod.setVisibility(View.INVISIBLE);
                }
            } else {
                linearLayout.setVisibility(View.INVISIBLE);
                rating.setVisibility(View.INVISIBLE);
                totalRatings.setVisibility(View.INVISIBLE);
                productPrice.setText("Out Of Stock");
                productPrice.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
                cuttedPrice.setVisibility(View.INVISIBLE);
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wishList) {
                deleteBtn.setVisibility(View.VISIBLE);
            } else {
                deleteBtn.setVisibility(View.GONE);
            }

            /**
             * Delete button
             **/
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!ProductDetailsActivity.running_wishlist_query) {
                        ProductDetailsActivity.running_wishlist_query = true;
                        DBQueries.removeFromWishList(index, itemView.getContext());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID", productId);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });
        }
    }
}
