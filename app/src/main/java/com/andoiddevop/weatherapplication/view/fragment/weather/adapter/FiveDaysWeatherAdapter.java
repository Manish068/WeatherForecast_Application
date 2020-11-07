package com.andoiddevop.weatherapplication.view.fragment.weather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.model.DataItem;
import com.andoiddevop.weatherapplication.utils.FrequentFunction;
import com.bumptech.glide.Glide;

import java.util.List;

public class FiveDaysWeatherAdapter extends RecyclerView.Adapter<FiveDaysWeatherAdapter.FiveDaysViewHolder> {

    private static final String TAG = "FiveDaysWeatherAdapter";
    View view;
    Context mcontext;
    private List<DataItem> dataItems;

    public FiveDaysWeatherAdapter(List<DataItem> data, Context mcontext) {
        this.dataItems = data;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public FiveDaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_weather, parent, false);
        return new FiveDaysViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FiveDaysViewHolder holder, int position) {
        holder.textViewMaxMin.setText(String.valueOf(dataItems.get(position).getMax_temp()) + "/" + String.valueOf(dataItems.get(position).getMin_temp()) + " \u00B0C");
        holder.textViewWeather.setText(dataItems.get(position + 1).getWeather().getDescription());


        String check = dataItems.get(position + 1).getWeather().getIcon();
        Log.d(TAG, "onBindViewHolder: " + dataItems.get(position + 1).getWeather().getIcon());

        switch (check) {
            case "c01d":
                Glide.with(mcontext).load(R.drawable.ic_sun).into(holder.imageViewWeather);
                break;
            case "c03d":
                Glide.with(mcontext).load(R.drawable.ic_brokencloud).into(holder.imageViewWeather);
                break;
            case "c04d":
                Glide.with(mcontext).load(R.drawable.ic_overcast).into(holder.imageViewWeather);
                break;
            case "c02d":
                Glide.with(mcontext).load(R.drawable.ic_fewclouds).into(holder.imageViewWeather);
                break;
            case "r01d":
                Glide.with(mcontext).load(R.drawable.ic_light_rain).into(holder.imageViewWeather);
                break;
            case "r02d":
                Glide.with(mcontext).load(R.drawable.ic_moderate_rain).into(holder.imageViewWeather);
                break;
            case "r03d":
                Glide.with(mcontext).load(R.drawable.ic_heavy_rain).into(holder.imageViewWeather);
                break;
            case "r04d":
            case "r06d":
                Glide.with(mcontext).load(R.drawable.ic_shower_rain).into(holder.imageViewWeather);
                break;
            case "r05d":
                Glide.with(mcontext).load(R.drawable.ic_showerraining).into(holder.imageViewWeather);
                break;

            case "d01d":
            case "d02d":
            case "d03d":
                Glide.with(mcontext).load(R.drawable.ic_drizzle).into(holder.imageViewWeather);
                break;
            case "t01d":
            case "t02d":
            case "t03d":
                Glide.with(mcontext).load(R.drawable.ic_t01d).into(holder.imageViewWeather);
                break;
            case "t04d":
            case "t05d":
                Glide.with(mcontext).load(R.drawable.ic_t05d).into(holder.imageViewWeather);
                break;
            case "a01d":
                Glide.with(mcontext).load(R.drawable.ic_mist).into(holder.imageViewWeather);
                break;
            case "a03d":
                Glide.with(mcontext).load(R.drawable.ic_haze).into(holder.imageViewWeather);
                break;
            case "a04d":
                Glide.with(mcontext).load(R.drawable.ic_weather_dust).into(holder.imageViewWeather);
                break;
            case "a05d":
            case "a06d":
                Glide.with(mcontext).load(R.drawable.ic_fog).into(holder.imageViewWeather);
                break;

            case "s05d":
                Glide.with(mcontext).load(R.drawable.ic_sleet).into(holder.imageViewWeather);
                break;

            case "s01d":
                Glide.with(mcontext).load(R.drawable.ic_snow_shower).into(holder.imageViewWeather);
                break;

            case "s02d":
                Glide.with(mcontext).load(R.drawable.ic_snow_heavy).into(holder.imageViewWeather);
                break;

            case "s04d":
                Glide.with(mcontext).load(R.drawable.ic_moderate_snow).into(holder.imageViewWeather);
                break;
        }


        String imageUrl = "https://www.weatherbit.io/static/img/icons/" + dataItems.get(position + 1).getWeather().getIcon() + ".png";
/*
        Glide.with(mcontext).load(imageUrl).into(holder.imageViewWeather);
*/
        holder.textViewDays.setText(FrequentFunction.getWeekDays(dataItems.get(position + 1).getTs()));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FiveDaysViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewWeather;
        private TextView textViewDays, textViewWeather, textViewMaxMin;

        public FiveDaysViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDays = itemView.findViewById(R.id.textViewDays);
            textViewWeather = itemView.findViewById(R.id.textViewWeather);
            textViewMaxMin = itemView.findViewById(R.id.textViewMaxMin);
            imageViewWeather = itemView.findViewById(R.id.imageViewWeather);

        }
    }
}
