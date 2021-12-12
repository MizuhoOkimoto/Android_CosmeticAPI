package example.myapplication.assignment4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {

    private String API = "http://makeup-api.herokuapp.com/api/v1/products.json?product_tags=Canadian&product_tags=vegan&product_tags=Organic";


    //provide multi thread service
    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    //access to the main thread
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    //notify our activity
    interface NetworkingListener{
        void dataListener(String jsonString);
        void imageListener(Bitmap image);
    }

    public NetworkingListener listener;

/*    public void searchForCity(String cosmeticChars){
        String urlString =  cosmeticChars;
        connect(urlString);
    }*/

 /*   private void connect(String urlString) {
    }*/

    //GET IMAGE
    public void getImageData(String img){

        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL urlObj = new URL(img);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) urlObj.getContent());
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // any code here will run in main thread
                            listener.imageListener(bitmap);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //goes to background thread
    public void connect(){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {

                    String jsonData = "";
                    URL urlObj = new URL(API);
                    Bitmap bitmap = BitmapFactory.decodeStream((InputStream) urlObj.getContent());

                    //open connection
                    httpURLConnection = (HttpURLConnection) urlObj.openConnection();
                    httpURLConnection.setRequestMethod("GET");// post, delete, put
                    //add configuration/header
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    //open stream itself
                    InputStream in = httpURLConnection.getInputStream();
                    //create a reader
                    InputStreamReader reader = new InputStreamReader(in);
                    //read integer by integer
                    int inputSteamData = 0;
                    //-1 is the end of the stream
                    while ( (inputSteamData = reader.read()) != -1){// there is data in this stream
                        char current = (char)inputSteamData;
                        //read json data as a string
                        jsonData += current;
                    }
                    //json should be final
                    final String finalData = jsonData;

                    // the data is ready
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // any code here will run in main thread
                            //
                            listener.dataListener(finalData);
                            listener.imageListener(bitmap);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }

            }
        });
    }

}
