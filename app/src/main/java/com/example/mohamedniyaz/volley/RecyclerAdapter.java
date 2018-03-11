package com.example.mohamedniyaz.volley;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ProductViewHolder>{



    private Context mCtx;

    //we are storing all the products in a list
    private List<Data> productList;

    public RecyclerAdapter(Context mCtx, List<Data> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_card, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Data product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewName.setText(product.getName());
        holder.textViewSkill.setText(product.getSkill());
        if(!product.getImage().isEmpty()) {
            System.out.println(product.getImage());
            Picasso.with(mCtx).load(product.getImage()).fit().centerCrop()

                    .placeholder(R.drawable.error_icon_13)
                    .error(R.drawable.error_icon_13)
                    .into(holder.imageView);
        }else {
            Picasso.with(mCtx)
                    .load(R.drawable.error_icon_13)
                    .placeholder(android.R.drawable.ic_input_add)
                    .error(R.drawable.error_icon_13)
                    .into(holder.imageView);
        }

        //holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewSkill;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewSkill = itemView.findViewById(R.id.skills);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
