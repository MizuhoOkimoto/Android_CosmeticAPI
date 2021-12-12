package example.myapplication.assignment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CosmeticDetail extends AppCompatActivity implements DatabaseManager.DataBaseListener{

    TextView brand, name, price, description;
    ImageView image;

    DatabaseManager dbClient;

    String cosmetic_brand = "";
    String cosmetic_name = "";
    Double cosmetic_price = 0.0;
    String cosmetic_image_link = "";
    String cosmetic_description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_detail);


        //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        brand = findViewById(R.id.brand);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        description = findViewById(R.id.desc);

        dbClient = new DatabaseManager(this);
        DatabaseManager.listener = this;

        if (getIntent().hasExtra("brand")){
           cosmetic_brand = getIntent().getStringExtra("brand");
            cosmetic_name = getIntent().getStringExtra("name");
            cosmetic_price = getIntent().getDoubleExtra("price",0.0);
            cosmetic_image_link = getIntent().getStringExtra("image_link");
            cosmetic_description = getIntent().getStringExtra("description");

            brand.setText("Brand: " + cosmetic_brand);
            name.setText("Name: " + cosmetic_name);
            price.setText(String.valueOf("Price: $" + cosmetic_price));
            //image.setText(cosmetic_image_link);
            description.setText("Description: " + cosmetic_description);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.favlist: {

                Intent intent = new Intent(this, Favorite.class);
                startActivity(intent);
                break;
            }
            case R.id.reset: {
                DatabaseManager.deleteAllCosmetics();

                break;
            }
            case android.R.id.home:
                this.finish();
                //return true;
                break;
        }
        return true;
    }

    public void addFav(View view) {
        DatabaseManager.insertNewCosmetic( new Cosmetics(cosmetic_brand, cosmetic_name, cosmetic_price, cosmetic_image_link, cosmetic_description));
    }

    /*@Override
    public void imageListener(Bitmap image) {
        image.setImageBitmap(image);
    }*/

    @Override
    public void ListOfCosmeticsListener(List<Cosmetics> cosmeticsList) {

    }

    @Override
    public void CosmeticsListener(Cosmetics cosmetics) {

    }
}