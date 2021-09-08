package com.example.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.foodorderapp.DataBase.DbHandler;
import com.example.foodorderapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DbHandler dbHandler = new DbHandler(getApplicationContext());
        if(getIntent().getIntExtra("type",0)==1) {
            int f_image = getIntent().getIntExtra("image", 0);
            int f_price = Integer.parseInt(getIntent().getStringExtra("price"));
            String f_name = getIntent().getStringExtra("name");
            String f_description = getIntent().getStringExtra("description");
            binding.detailImage.setImageResource(f_image);
            binding.detailDescription.setText(f_description);
            binding.detailPrice.setText(String.format("%d", f_price));
            binding.foodLable.setText(f_name);
            binding.orderNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = dbHandler.insertOrder(binding.detailName.getText().toString(),
                            binding.detailMobile.getText().toString(),
                            f_price,
                            f_image,
                            Integer.parseInt(binding.quantity.getText().toString()),
                            f_description,
                            f_name
                    );
                    if (isInserted) {
                        Toast.makeText(DetailActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            int id=getIntent().getIntExtra("id",0);
            Cursor cursor=dbHandler.getOrderById(id);
            binding.detailImage.setImageResource(cursor.getInt(4));
            binding.detailDescription.setText(cursor.getString(5));
            binding.detailPrice.setText(String.format("%d", cursor.getInt(3)));
            binding.foodLable.setText(cursor.getString(7));
            binding.detailName.setText(cursor.getString(1));
            binding.detailMobile.setText(cursor.getString(2));
            binding.orderNow.setText("Update Now");
            binding.orderNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isUpdated= dbHandler.UpdateNow(binding.detailName.getText().toString(),
                            binding.detailMobile.getText().toString(),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            Integer.parseInt(binding.quantity.getText().toString()),
                            cursor.getString(5),
                            cursor.getString(7),
                            id
                    );
                    if(isUpdated){
                        Toast.makeText(DetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();
        }
    }
}