package example.myapplication.assignment4;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



/*"id":1048,
        "brand":"colourpop",
        "name":"Lippie Pencil",
        "price":"5.0",
        "price_sign":"$",
        "currency":"CAD",
        "image_link":"https://cdn.shopify.com/s/files/1/1338/0845/collections/lippie-pencil_grande.jpg?v=1512588769",
        "product_link":"https://colourpop.com/collections/lippie-pencil",
        "website_link":"https://colourpop.com",
        "description":"Lippie Pencil A long-wearing and high-intensity lip pencil that glides on easily and prevents feathering. Many of our Lippie Stix have a coordinating Lippie Pencil designed to compliment it perfectly, but feel free to mix and match!",
        "rating":null,
        "category":"pencil",
        "product_type":"lip_liner",*/


@Entity
public class Cosmetics { // App Model

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String brand;
    public String name;
    public Double price;
    public String image_link;
    public String description;

    @Ignore
    Bitmap image;

    public Cosmetics(String brand, String name, Double price, String image_link, String description) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.image_link = image_link;
        this.description = description;

    }

    public Cosmetics(){
        this.brand = "";
        this.name = "";
        this.price = 0.0;
        this.image_link = "";
        this.description = "";

    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
