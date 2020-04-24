package com.example.trackcovid.ui.country;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackcovid.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class CovidCountryAdapter extends RecyclerView.Adapter<CovidCountryAdapter.ViewHolder> implements Filterable {

    private List<CovidCountry> covidCountries;
    private List<CovidCountry> covidCountriesFull;

    private Context context;

    public CovidCountryAdapter(List<CovidCountry> covidCountries, Context context) {
        this.covidCountries = covidCountries;
        this.context = context;
        covidCountriesFull = new ArrayList<>(covidCountries);
    }

    @NonNull
    @Override
    public CovidCountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_covid_country, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidCountryAdapter.ViewHolder holder, int position) {
        CovidCountry covidCountry = covidCountries.get(position);
        holder.tvTotalCases.setText(Integer.toString(covidCountry.getmCases()));
        holder.tvCountryName.setText(covidCountry.getmCovidCountry());

        // Glide
        Glide.with(context)
                .load(covidCountry.getmFlags())
                .apply(new RequestOptions().override(240, 160))
                .into(holder.imgCountryFlag);
    }

    @Override
    public int getItemCount() {
        return covidCountries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTotalCases, tvCountryName;
        ImageView imgCountryFlag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotalCases = itemView.findViewById(R.id.tvTotalCases);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            imgCountryFlag = itemView.findViewById(R.id.imgCountryFlag);
        }
    }

    @Override
    public Filter getFilter() {
        return covidCountriesFilter;
    }

    private Filter covidCountriesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CovidCountry> filteredCovidCountry = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredCovidCountry.addAll(covidCountriesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CovidCountry itemCovidCountry : covidCountriesFull) {
                    if (itemCovidCountry.getmCovidCountry().toLowerCase().contains(filterPattern)) {
                        filteredCovidCountry.add(itemCovidCountry);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCovidCountry;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            covidCountries.clear();
            covidCountries.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
