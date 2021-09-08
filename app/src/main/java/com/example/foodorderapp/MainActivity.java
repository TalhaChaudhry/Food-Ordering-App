package com.example.foodorderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.foodorderapp.Adapters.MainAdapter;
import com.example.foodorderapp.Models.MainModel;
import com.example.foodorderapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

// For recycler View Implementation we need to do following steps
// Design the layout
// Make Model of Recycler View
// then make adapter of Recycler View
// At last pass all in main activity

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    // get rid of findViewById use concept of Binding
    // 1-firstly add buildFeatures in build.gradle and put viewBinding as true in it
    // 2-declare the variable in respective java class
    // 3-initialize your binding variable in onCreate under the super
    // 4-in setContentView pass binding variable . getRoot()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<MainModel> list = new ArrayList<>();
        list.add(new MainModel(R.drawable.burger, "Burger", "10", "Extra Cheese Chicken Burger..."));
        list.add(new MainModel(R.drawable.pizza, "Pizza", "50", "Fresh Malai Boti Pizza"));
        MainAdapter adapter=new MainAdapter(list,this);
        binding.recylerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recylerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.orders:
                startActivity(new Intent(getApplicationContext(),ordersActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}