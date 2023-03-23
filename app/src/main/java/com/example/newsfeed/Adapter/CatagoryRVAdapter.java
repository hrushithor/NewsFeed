package com.example.newsfeed.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Model.CatagoryRVModel;
import com.example.newsfeed.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatagoryRVAdapter extends RecyclerView.Adapter<CatagoryRVAdapter.ViewHolder> {

    private ArrayList<CatagoryRVModel> catagoryRVModels;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CatagoryRVAdapter(ArrayList<CatagoryRVModel> catagoryRVModels, Context context, CategoryClickInterface categoryClickInterface) {
        this.catagoryRVModels = catagoryRVModels;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CatagoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_items,parent,false);
        return  new CatagoryRVAdapter.ViewHolder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull CatagoryRVAdapter.ViewHolder holder, int position) {
        CatagoryRVModel catagoryRVModel= catagoryRVModels.get(holder.getAdapterPosition());
        holder.categortTV.setText(catagoryRVModel.getCategory());
//        Picasso.get()
//                .load(catagoryRVModel.getCategoryImageurl()).into(holder.categoryIV);

        if (catagoryRVModel.getCategoryImageurl()==null || catagoryRVModel.getCategoryImageurl().isEmpty()) {
            holder.categoryIV.setImageResource(R.drawable.ic_launcher_background);
        } else{
            Picasso.get().load(catagoryRVModel.getCategoryImageurl()).into(holder.categoryIV);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return catagoryRVModels.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categortTV;
        private ImageView categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categortTV=itemView.findViewById(R.id.tv_category);
            categoryIV= itemView.findViewById(R.id.iv_category);
        }
    }
}
