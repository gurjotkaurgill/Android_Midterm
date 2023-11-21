package com.example.midterm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class ProductsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> productsList;
    ProductRecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list);

        recyclerView = findViewById(R.id.recyclerView);

        productsList = ((AppLevelVariables)getApplicationContext()).productsList;
        recyclerAdapter = new ProductRecyclerAdapter(productsList,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.appMenu) {
            Intent homeScreenMenuIntent = new Intent(this, MainActivity.class);
            startActivity(homeScreenMenuIntent);
            return true;
        }
        else if(item.getItemId() == R.id.productsList_menu)
            return true;
        else
            return super.onOptionsItemSelected(item);
    }
}