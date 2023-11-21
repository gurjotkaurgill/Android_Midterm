package com.example.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ProductViewHolder> {
    ArrayList<Product> productsList;
    Context context;

    public ProductRecyclerAdapter(ArrayList<Product> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate((R.layout.product_row),parent,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ImageView imageViewRow = holder.itemView.findViewById(R.id.product_img_row);
        TextView textViewRow = holder.itemView.findViewById(R.id.product_name_row);
        imageViewRow.setImageBitmap(productsList.get(position).getProductImage());
        textViewRow.setText(productsList.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
