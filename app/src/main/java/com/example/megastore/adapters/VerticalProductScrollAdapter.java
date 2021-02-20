package com.example.megastore.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.megastore.R;
import com.example.megastore.model.VerticalProductScrollModel;

import java.util.List;

public class VerticalProductScrollAdapter extends RecyclerView.Adapter<VerticalProductScrollAdapter.ViewHolder> {
    private List<VerticalProductScrollModel> verticalProductScrollModelList;

    public VerticalProductScrollAdapter(List<VerticalProductScrollModel> verticalProductModelList) {
        this.verticalProductScrollModelList = verticalProductModelList;
    }

    @NonNull
    @Override
    public VerticalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalProductScrollAdapter.ViewHolder holder, int position) {
        int resource = verticalProductScrollModelList.get(position).getProductImage();
        String title = verticalProductScrollModelList.get(position).getProductTitle();
        String price = verticalProductScrollModelList.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {
        return verticalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
//                    itemView.getContext().startActivity(productDetailsIntent);
//                }
//            });
        }

        private void setProductImage(int resource){
            productImage.setImageResource(resource);
        }

        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private void setProductPrice(String price){
            productPrice.setText("Rs." + price + "/-");
        }
    }
}

