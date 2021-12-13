package example.myapplication.assignment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

//implement interfaces/ send us back data
public class MainActivity extends AppCompatActivity implements CosmeticAdapter.cosmeticClickListener,
        NetworkingService.NetworkingListener, DatabaseManager.DataBaseListener {

    ArrayList<Cosmetics> cosmetics = new ArrayList<Cosmetics>();
    RecyclerView recyclerView;
    CosmeticAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;
    MenuItem search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager.listener = this;


        networkingManager = ((myApp) getApplication()).getNetworkingService();
        jsonService = ((myApp) getApplication()).getJsonService();
        //listen for this networking notification
        networkingManager.listener = this;
        recyclerView = findViewById(R.id.productsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        networkingManager.connect();

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

    } //end of onCreate

    private void doMySearch(String query) {

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

    // result from networkingManager.connect();
    // networkingManager goes to the background
    @Override
    public void dataListener(String jsonString) {
        cosmetics = new ArrayList<>(jsonService.getCosmeticsFromJSON(jsonString));
        adapter = new CosmeticAdapter(this, cosmetics);
        recyclerView.setAdapter(adapter);

        //setTitle("Search for new cities..");
    }

    @Override
    public void imageListener(Bitmap image) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


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
                //onBackPressed();
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void ListOfCosmeticsListener(List<Cosmetics> cosmeticsList) {

    }

    @Override
    public void CosmeticsListener(Cosmetics cosmetics) {

    }
}//end of the MainActivity class