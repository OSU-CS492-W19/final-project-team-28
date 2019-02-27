package com.example.swapisearcher;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.example.android.lifecycleweather.data.WeatherPreferences;
import com.example.swapisearcher.utils.SWAPIUtils;
//import com.example.swapisearcher.utils.SWAPIUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.lang.String;

public class SWAPIAdapter extends RecyclerView.Adapter<SWAPIAdapter.SWAPIItemViewHolder>{
    private ArrayList<SWAPIUtils.SWAPIItem> mSWAPIItems;
    private OnSWAPIItemClickListener mSWAPIItemClickListener;




    public interface OnSWAPIItemClickListener{
        void onSWAPIItemClick(SWAPIUtils.SWAPIItem swapiItem);
    }


    public SWAPIAdapter(OnSWAPIItemClickListener clickListener){
        mSWAPIItemClickListener = clickListener;
    }

    public void updateSWAPIITems(ArrayList<SWAPIUtils.SWAPIItem> swapiItems){
        mSWAPIItems = swapiItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (mSWAPIItems != null){
            return mSWAPIItems.size();
        } else {
            return 0;
        }
    }


    @Override
    public SWAPIItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        //find xml that contains this
        View itemView = inflater.inflate(R.layout.forecast_list_item, parent, false);

        return new SWAPIItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SWAPIAdapter.SWAPIItemViewHolder swapiItemViewHolder, int i) {
        swapiItemViewHolder.bind(mSWAPIItems.get(i));
    }

    class SWAPIItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mSWAPINameTV;


        public SWAPIItemViewHolder(View itemView){
            super(itemView);

            //find xml call for this
            mSWAPINameTV = (TextView)itemView.findViewById(R.id.tv_forecast_temp_description);
            itemView.setOnClickListener(this);
        }

        public void bind(SWAPIUtils.SWAPIItem swapiItem){

            String detailString = swapiItem.name;
            mSWAPINameTV.setText(detailString);
        }

        @Override
        public void onClick(View v){
            SWAPIUtils.SWAPIItem swapiItem = mSWAPIItems.get(getAdapterPosition());
            mSWAPIItemClickListener.onSWAPIItemClick(swapiItem);
        }
    }


}
