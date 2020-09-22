package com.andoiddevop.weatherapplication.view.fragment.ManageLocation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.weatherapplication.Database.SavedPlaces;
import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.model.DataItem;
import com.andoiddevop.weatherapplication.utils.FrequentFunction;
import com.bumptech.glide.Glide;

import java.util.List;

public class SavedPlacesAdapter extends RecyclerView.Adapter<SavedPlacesAdapter.SavePlacesViewHolder> {

    View view;
    Context mcontext;
    private List<SavedPlaces> savedPlacesList;
    private PlaceInterface placeInterface;

    public SavedPlacesAdapter(List<SavedPlaces> savedPlacesList, Context mcontext,PlaceInterface placeInterface) {
        this.savedPlacesList = savedPlacesList;
        this.mcontext =mcontext;
        this.placeInterface=placeInterface;
    }

    @NonNull
    @Override
    public SavePlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_saved_places, parent, false);
        return new SavePlacesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SavePlacesViewHolder holder, int position) {
        holder.textViewTitle.setText(String.valueOf(savedPlacesList.get(position).getTitle()));
        holder.textViewSubTitle.setText(savedPlacesList.get(position).getSubTitle());
        holder.imageViewPopup.setOnClickListener(v -> {
            placeInterface.deletePopup(position,view);
        });
    }

    @Override
    public int getItemCount() {
        return savedPlacesList.size();
    }

    public class SavePlacesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewSubTitle;
        public ImageView imageViewPopup;

        public SavePlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSubTitle = itemView.findViewById(R.id.textViewSubTitle);
            imageViewPopup = itemView.findViewById(R.id.imageViewPopup);

        }
    }

    public interface PlaceInterface{
        void deletePopup(int position, View view);
    }
}
