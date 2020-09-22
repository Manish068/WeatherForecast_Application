package com.andoiddevop.weatherapplication.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class SavedPlacesRepository {
    private String DB_NAME = "locations_db";
    private List<SavedPlaces> savedPlaces = new ArrayList<>();
    private SavedPlacesDataBase savedPlacesDataBase;
    private FetchSavedLocations fetchSavedLocations;

    public SavedPlacesRepository(Context context){
        savedPlacesDataBase = Room.databaseBuilder(context,SavedPlacesDataBase.class,DB_NAME).build();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertTask(final SavedPlaces savedPlaces){
        new AsyncTask<Void,Void,Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                return savedPlacesDataBase.daoAccess().insertTask(savedPlaces);
            }
        }.execute();
    }

    public void getTasks(FetchSavedLocations fetchSavedLocations){
        this.fetchSavedLocations = fetchSavedLocations;

        new AsyncTask<Void,Void,List<SavedPlaces>>(){
            @Override
            protected List<SavedPlaces> doInBackground(Void... voids) {
                savedPlaces =savedPlacesDataBase.daoAccess().retrieveAllTask();
                return savedPlaces;
            }

            @Override
            protected void onPostExecute(List<SavedPlaces> savedPlaces) {
                super.onPostExecute(savedPlaces);
                fetchSavedLocations.savedPlaces(savedPlaces);
            }
        }.execute();
    }

    public void deleteTask(String key){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                savedPlacesDataBase.daoAccess().deleteById(key);
                return null;
            }
        }.execute();
    }

    public interface FetchSavedLocations{
        void savedPlaces(List<SavedPlaces> savedPlaces);
    }
}
