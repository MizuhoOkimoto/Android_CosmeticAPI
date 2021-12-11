package example.myapplication.assignment4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CosmeticAdapter.cosmeticClickListener,
        NetworkingService.NetworkingListener{

    ArrayList<Cosmetics> cosmetics = new ArrayList<Cosmetics>();
    RecyclerView recyclerView;
    CosmeticAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        recyclerView = findViewById(R.id.productsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        networkingManager.connect();

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public void cosmeticClicked(Cosmetics selectedCosmetic) {

    }

    // result from networkingManager.connect();
    // networkingManager goes to the background
    @Override
    public void dataListener(String jsonString) {
        cosmetics = new ArrayList<>(jsonService.getCosmeticsFromJSON(jsonString));
        adapter = new CosmeticAdapter(this,cosmetics);
        recyclerView.setAdapter(adapter);
        //setTitle("Search for new cities..");
    }

    @Override
    public void imageListener(Bitmap image) {

    }
}//end of the MainActivity class