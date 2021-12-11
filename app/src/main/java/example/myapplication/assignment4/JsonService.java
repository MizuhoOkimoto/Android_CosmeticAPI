package example.myapplication.assignment4;

import static android.text.TextUtils.indexOf;
import static android.text.TextUtils.substring;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {

    public ArrayList<Cosmetics> getCosmeticsFromJSON(String json)  {
        ArrayList<Cosmetics> arrayList = new ArrayList<>(0);
        try {
            //
            JSONArray json_cosmetics = new JSONArray(json);
            for (int i = 0 ; i< json_cosmetics.length(); i++){
                JSONObject cosmeticDataObj = json_cosmetics.getJSONObject(0);

                // "Torbert, LA, United States"
                String brand = cosmeticDataObj.getString("brand");
                String name = cosmeticDataObj.getString("name");
                Double price = cosmeticDataObj.getDouble("price");
                String image_link = cosmeticDataObj.getString("image_link");
                String description = cosmeticDataObj.getString("description");

                Cosmetics c = new Cosmetics(brand, name, price,image_link,description);
                arrayList.add(c);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}