package com.andoiddevop.weatherapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    Long insertTask(SavedPlaces savedPlaces);

    @Query("SELECT * FROM SavedPlaces ORDER BY created_at desc")
    List<SavedPlaces> retrieveAllTask();

    @Query("DELETE FROM SavedPlaces WHERE id = :id")
    public void deleteById(String id);
}
