package example.myapplication.assignment4;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DatabaseManager {
    static CosmeticsDatabase dbClient;
    static Context dbContext;

    interface DataBaseListener {
        //Implement this interface in any activity that will use this query
        public void ListOfCosmeticsListener(List<Cosmetics> iFList); //get all
        public void CosmeticsListener(Cosmetics cosmetics); //get one
    }

    static DataBaseListener listener;

    public static final ExecutorService dbExectuor = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper()); //Get a pointer to main Thread
    //A different option from Handler would be creating a Thread object

    DatabaseManager(Context context){
        dbContext = context;
        dbClient= Room.databaseBuilder(context, CosmeticsDatabase.class, "database-Cosmetics").build();
    }

    //Make sure we create only one instance of the Database
    //Singleton Design Pattern, because db is an expansive resource
    public static CosmeticsDatabase getDbClient(){
        if(dbClient == null){
            dbClient = new DatabaseManager(dbContext).dbClient;
        }
        return dbClient;
    }

    //This happens on bg thread.
    public static void insertNewCosmetic(Cosmetics newCosmetic){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getCosmeticDAO().insertNewCosmetic(newCosmetic);
            }
        });
    }

    //This happens on bg thread.
    public static void deleteImaginaryFriend(int id){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getCosmeticDAO().deleteCosmetic(id);
            }
        });
    }

    //This happens on bg thread.
    public static void deleteAllImaginaryFriend(){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getCosmeticDAO().deleteAll();
            }
        });
    }

    //GET ALL
    //To send back data, I need Runnable inside Runnable
    public static void getAllCosmetics(){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                //Handler and looper will give the data back to main Thread
                List<Cosmetics> listOfIF = dbClient.getCosmeticDAO().getAll();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //On the second Runnable, I need an Interface to send data to main Thread
                        listener.ListOfCosmeticsListener(listOfIF);
                    }
                });
            }
        });
    }

    public static void getCosmeticByName(String name){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                //Handler and looper will give the data back to main Thread
                Cosmetics cosmetic = dbClient.getCosmeticDAO().getOne(name); //GET ONE

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //On the second Runnable, I need an Interface to send data to main Thread
                        listener.CosmeticsListener(cosmetic);
                    }
                });
            }
        });
    }

    //DELETE LATER!

/*    public static void getCosmeticsById(int Id){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                //Handler and looper will give the data back to main Thread
                Cosmetics cosmetic = dbClient.getCosmeticDAO().getOne(Id);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //On the second Runnable, I need an Interface to send data to main Thread
                        listener.ImaginaryFriendsListener(iF);
                    }
                });
            }
        });
    }*/

}
