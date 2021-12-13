package example.myapplication.assignment4;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity implements CosmeticAdapter.cosmeticClickListener,
        DatabaseManager.DataBaseListener {

    ArrayList<Cosmetics> cosmetics = new ArrayList<Cosmetics>();
    RecyclerView recyclerView;
    CosmeticAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;
    DatabaseManager dbClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        DatabaseManager.listener = this;
        recyclerView = findViewById(R.id.favList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbClient = new DatabaseManager(this);
        dbClient.getAllCosmetics();

        //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void cosmeticClicked(Cosmetics selectedCosmetic) {
        Intent intent = new Intent(this, CosmeticDetail.class);

        intent.putExtra("brand", selectedCosmetic.getBrand());
        intent.putExtra("name", selectedCosmetic.getName());
        intent.putExtra("price", selectedCosmetic.getPrice());
        intent.putExtra("image_link", selectedCosmetic.getImage_link());
        intent.putExtra("description", selectedCosmetic.getDescription());
        startActivity(intent);
    }

    @Override
    public void ListOfCosmeticsListener(List<Cosmetics> CosmeticsList) {
        cosmetics = new ArrayList<>(CosmeticsList);
        adapter = new CosmeticAdapter(this, cosmetics);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void CosmeticsListener(Cosmetics cosmetics) {

    }

    //for back button
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

}