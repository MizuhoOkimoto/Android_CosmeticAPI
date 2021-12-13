package example.myapplication.assignment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CosmeticDetail extends AppCompatActivity implements DatabaseManager.DataBaseListener, NetworkingService.NetworkingListener {

    TextView brand, name, price, description;
    ImageView image;
    NetworkingService networkingManager;
    DatabaseManager dbClient;
    Button fav;

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

        networkingManager = ((myApp) getApplication()).getNetworkingService();

        brand = findViewById(R.id.brand);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        image = findViewById(R.id.image);
        description = findViewById(R.id.desc);
        fav = findViewById(R.id.fav);

        networkingManager.listener = this;
        dbClient = new DatabaseManager(this);
        DatabaseManager.listener = this;

        if (getIntent().hasExtra("brand")) {
            cosmetic_brand = getIntent().getStringExtra("brand");
            cosmetic_name = getIntent().getStringExtra("name");
            cosmetic_price = getIntent().getDoubleExtra("price", 0.0);
            cosmetic_image_link = getIntent().getStringExtra("image_link");
            cosmetic_description = getIntent().getStringExtra("description");

            networkingManager.getImageData(cosmetic_image_link);

            brand.setText("Brand: " + cosmetic_brand);
            name.setText("Name: " + cosmetic_name);
            //if price is 0.0
            if (cosmetic_price.equals(0.0)) {
                price.setText("Please check store price");
            } else {
                price.setText(String.valueOf("Price: $" + cosmetic_price));
            }
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
        DatabaseManager.insertNewCosmetic(new Cosmetics(cosmetic_brand, cosmetic_name, cosmetic_price, cosmetic_image_link, cosmetic_description));
        Toast.makeText(getApplicationContext(), "Added to favorite list", Toast.LENGTH_LONG).show();
        if (fav.isPressed()) {
            fav.setBackgroundColor(Color.GRAY);
        } else {
            fav.setBackgroundColor(Color.BLUE);
        }
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

    @Override
    public void dataListener(String jsonString) {

    }

    @Override
    public void imageListener(Bitmap imageCosmetic) {
        image.setImageBitmap(imageCosmetic);

    }
}