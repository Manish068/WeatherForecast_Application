package com.andoiddevop.weatherapplication.view.fragment.weather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.weatherapplication.R;
import com.andoiddevop.weatherapplication.model.DataItem;
import com.andoiddevop.weatherapplication.model.WeatherBean;
import com.andoiddevop.weatherapplication.utils.FrequentFunction;
import com.bumptech.glide.Glide;

import java.util.List;

public class FiveDaysWeatherAdapter extends RecyclerView.Adapter<FiveDaysWeatherAdapter.FiveDaysViewHolder> {

    View view;
    Context mcontext;
    private List<DataItem> dataItems;

    public FiveDaysWeatherAdapter(List<DataItem> data,Context mcontext) {
        this.dataItems = data;
        this.mcontext =mcontext;
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
        holder.textViewMaxMin.setText(String.valueOf(dataItems.get(position).getMax_temp())+"/"+String.valueOf(dataItems.get(position).getMin_temp())+" \u00B0C");
        holder.textViewWeather.setText(dataItems.get(position+1).getWeather().getDescription());

        String imageUrl = "https://www.weatherbit.io/static/img/icons/" + dataItems.get(position+1).getWeather().getIcon() + ".png";
        Glide.with(mcontext).load(imageUrl).into(holder.imageViewWeather);
        holder.textViewDays.setText(FrequentFunction.getWeekDays(dataItems.get(position+1).getTs()));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FiveDaysViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDays, textViewWeather, textViewMaxMin;
        public ImageView imageViewWeather;

        public FiveDaysViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDays = itemView.findViewById(R.id.textViewDays);
            textViewWeather = itemView.findViewById(R.id.textViewWeather);
            textViewMaxMin = itemView.findViewById(R.id.textViewMaxMin);
            imageViewWeather = itemView.findViewById(R.id.imageViewWeather);

        }
    }
}
