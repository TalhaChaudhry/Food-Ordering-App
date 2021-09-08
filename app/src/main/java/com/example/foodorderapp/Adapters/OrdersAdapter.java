package com.example.foodorderapp.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.DataBase.DbHandler;
import com.example.foodorderapp.DetailActivity;
import com.example.foodorderapp.Models.OrdersModel;
import com.example.foodorderapp.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.Viewholder> {
    Context context;
    ArrayList<OrdersModel> list;

    public OrdersAdapter(Context context, ArrayList<OrdersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final OrdersModel model = list.get(position);
        holder.soldItemImage.setImageResource(model.getOrderImage());
        holder.orderNumber.setText(model.getOrderNumber());
        holder.soldItemName.setText(model.getSoldItemName());
        holder.price.setText(model.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id", Integer.parseInt(model.getOrderNumber()));
                intent.putExtra("type", 2);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context).
                        setTitle("Delete").
                        setIcon(R.drawable.delete_icon).
                        setMessage("Do you want to delete this order").
                        setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbHandler dbHandler = new DbHandler(context);
                                if (dbHandler.DeleteNow(model.getOrderNumber()) > 0) {
                                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                    // reload Activity
                                    ((Activity)context).finish();
                                    context.startActivity(((Activity) context).getIntent());
                                } else {
                                    Toast.makeText(context, "Can't Delete", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).
                        setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView soldItemImage;
        TextView soldItemName, price, orderNumber;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            soldItemImage = itemView.findViewById(R.id.orderImage);
            soldItemName = itemView.findViewById(R.id.orderItemName);
            price = itemView.findViewById(R.id.orderPrice);
            orderNumber = itemView.findViewById(R.id.orderNumber);

        }
    }
}
