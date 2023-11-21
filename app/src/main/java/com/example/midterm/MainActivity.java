package com.example.midterm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText productNameEditText;
    Button captureImageBtn, saveBtn;
    ImageView cameraImageView;
    ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    ArrayList<Product> productsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameEditText = findViewById(R.id.productNameEditText);
        captureImageBtn = findViewById(R.id.takePhotoBtn);
        saveBtn = findViewById(R.id.saveBtn);
        cameraImageView = findViewById(R.id.cameraImageView);

        if(savedInstanceState != null){
            //save the product name if entered already
            productNameEditText.setText(savedInstanceState.getString("prodName"));
            //if the image was clicked, save it for when the activity recreates
            if(savedInstanceState.getParcelable("imgData") != null)
                cameraImageView.setImageBitmap(savedInstanceState.getParcelable("imgData"));
        }

        productsList = ((AppLevelVariables)getApplicationContext()).productsList;

        captureImageBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);

        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        //if activity returns something, set image in imageView
                        if(o.getResultCode()==RESULT_OK) {
                            Bitmap resultBitmap = o.getData().getParcelableExtra("data");
                            cameraImageView.setImageBitmap(resultBitmap);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.takePhotoBtn){
            //capture photo
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //This line is commented only for emulator
                //if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    cameraActivityResultLauncher.launch(cameraIntent);
//                }
            }
            else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},100);
            }
        }
        else {
            //save product
            String productName = productNameEditText.getText().toString();
            Bitmap image;
            if(productName.isEmpty()){
                Toast.makeText(this, "Product name is required", Toast.LENGTH_SHORT).show();
            }
            else if(cameraImageView.getDrawable() == null){
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
            else {
                image = ((BitmapDrawable) cameraImageView.getDrawable()).getBitmap();
                Product newProduct = new Product(productName, image);
                productsList.add(newProduct);
                Toast.makeText(this, newProduct.getProductName() + " added", Toast.LENGTH_SHORT).show();
                Intent productsListIntent = new Intent(this,ProductsListActivity.class);
                startActivity(productsListIntent);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String prodName = productNameEditText.getText().toString();
        if(!prodName.isEmpty())
            outState.putString("prodName",productNameEditText.getText().toString());
        if(cameraImageView.getDrawable() != null) {
            Bitmap imgData = ((BitmapDrawable) cameraImageView.getDrawable()).getBitmap();
            outState.putParcelable("imgData", imgData);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.appMenu)
            return true;
        else if(item.getItemId() == R.id.productsList_menu){
            Intent productsListMenuIntent = new Intent(this, ProductsListActivity.class);
            startActivity(productsListMenuIntent);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}