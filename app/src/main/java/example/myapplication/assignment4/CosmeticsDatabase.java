package example.myapplication.assignment4;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1,entities = {Cosmetics.class})
public abstract class CosmeticsDatabase extends RoomDatabase{

    abstract public CosmeticsDAO getCosmeticDAO();

}
