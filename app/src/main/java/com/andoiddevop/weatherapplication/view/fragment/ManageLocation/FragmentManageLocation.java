package com.andoiddevop.weatherapplication.view.fragment.ManageLocation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.weatherapplication.Database.SavedPlaces;
import com.andoiddevop.weatherapplication.Database.SavedPlacesRepository;
import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.utils.Constants;
import com.andoiddevop.weatherapplication.view.fragment.BaseFragment;
import com.andoiddevop.weatherapplication.view.fragment.ManageLocation.adapter.SavedPlacesAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

import static android.app.Activity.RESULT_CANCELED;

public class FragmentManageLocation extends BaseFragment implements SavedPlacesRepository.FetchSavedLocations, SavedPlacesAdapter.PlaceInterface {

    TextView textViewCityCount;
    RecyclerView recyclerViewLocations;
    FloatingActionButton fabButtonAddLocation;

    private SavedPlaces savedPlaces;
    private SavedPlacesRepository savedPlacesRepository;
    private SavedPlacesRepository.FetchSavedLocations fetchSavedLocations;
    private List<SavedPlaces> savedPlacesList = new ArrayList<>();
    private SavedPlacesAdapter savedPlacesAdapter;

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_location, container, false);
        textViewCityCount = view.findViewById(R.id.textViewCityCount);
        recyclerViewLocations = view.findViewById(R.id.recyclerViewLocations);
        fabButtonAddLocation = view.findViewById(R.id.fabButtonAddLocation);


        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        Places.initialize(getActivity(), getResources().getString(R.string.google_places_key));
        PlacesClient placesClient = Places.createClient(getActivity());
        savedPlacesRepository = new SavedPlacesRepository(getActivity());
        fetchSavedLocations = this;

        fabButtonAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList).build(getActivity());
                startActivityForResult(intent, Constants.AUTOCOMPLETE_REQUEST_CODE);
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                insertIntoDB(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("AutoComplete", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void insertIntoDB(Place place) {
        savedPlaces = new SavedPlaces();
        savedPlaces.setLatitude(String.valueOf(place.getLatLng().latitude));
        savedPlaces.setLongitude(String.valueOf(place.getLatLng().longitude));
        savedPlaces.setTitle(place.getName());
        savedPlaces.setSubTitle(place.getAddress());

        savedPlacesRepository.insertTask(savedPlaces);

    }

    @Override
    public void onResume() {
        super.onResume();
        savedPlacesRepository.getTasks(fetchSavedLocations);
    }

    @Override
    public void savedPlaces(List<SavedPlaces> savedPlaces) {
        savedPlacesList = savedPlaces;
        if (savedPlaces != null) {
            setAdapter();
            textViewCityCount.setText(savedPlacesList.size() + "/10");
        }

    }

    private void setAdapter() {
        savedPlacesAdapter = new SavedPlacesAdapter(savedPlacesList, getActivity(), FragmentManageLocation.this);
        recyclerViewLocations.setAdapter(savedPlacesAdapter);
    }

    @Override
    public void deletePopup(int position, View view) {
        showdeletePopup(view,position);

    }

    private void showdeletePopup(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.item_delete:
                        savedPlacesRepository.deleteTask(String.valueOf(savedPlacesList.get(position).getId()));
                        savedPlacesRepository.getTasks(fetchSavedLocations);
                        break;
                    case R.id.item_cancel:
                        popupMenu.dismiss();
                        break;
                }
                return  true;
            }
        });

        popupMenu.inflate(R.menu.menu_delete_popup);
        popupMenu.show();
    }
}
