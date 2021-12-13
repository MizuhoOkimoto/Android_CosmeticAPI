package example.myapplication.assignment4;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

// insert / update / delete/ quere

@Dao
public interface CosmeticsDAO {
    @Insert
    void insertNewCosmetic(Cosmetics cosmetic);

    /*@Delete
    void deleteCosmetic(Cosmetics deleteCosmetic);*/

    @Query("DELETE FROM Cosmetics WHERE id = :Id")
        void deleteCosmetic(int Id);


    @Query("SELECT * FROM Cosmetics")
    List<Cosmetics> getAll();

    @Query("DELETE FROM Cosmetics")
    void deleteAll();

    @Query("SELECT * FROM Cosmetics WHERE name = :Name")
    Cosmetics getOne(String Name);


    @Query("SELECT * FROM Cosmetics WHERE price >= :amount ")
    List<Cosmetics> getAllCosmeticsBiggerThan(Double amount);


}
