package com.example.foodorderapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DetailActivity;
import com.example.foodorderapp.Models.MainModel;
import com.example.foodorderapp.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Viewholder> {
    ArrayList<MainModel> list;
    Context context;

    public MainAdapter(ArrayList<MainModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_mainfood, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final MainModel mainModel = list.get(position);
        holder.foodImage.setImageResource(mainModel.getImage());
        holder.name.setText(mainModel.getName());
        holder.price.setText(mainModel.getPrice());
        holder.description.setText(mainModel.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("image",mainModel.getImage());
                intent.putExtra("price",mainModel.getPrice());
                intent.putExtra("name",mainModel.getName());
                intent.putExtra("description",mainModel.getDescription());
                intent.putExtra("type",1);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView price, name, description;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.imageView);
            price = itemView.findViewById(R.id.orderPrice);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);

        }
    }

}
