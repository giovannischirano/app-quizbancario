package com.giovanni.testapp.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testapp.R;

public class StatisticsViewAdapter extends ArrayAdapter<StatisticItem>{

	Context context;
	
	public StatisticsViewAdapter(Context context, int resource, List<StatisticItem> objects) {
		super(context, resource, objects);
		this.context = context;
	}
	
	private class ViewHolder {
		TextView punteggio;
        TextView data;
        ImageView imagePunteggio;
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        StatisticItem statisticItem = getItem(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        
        if (convertView == null) {
        	convertView = mInflater.inflate(R.layout.list_statistiche_layout, null);
        	holder = new ViewHolder();
        	holder.punteggio = (TextView) convertView.findViewById(R.id.punteggioList);
        	holder.data = (TextView) convertView.findViewById(R.id.dataPunteggioList);
        	holder.imagePunteggio = (ImageView) convertView.findViewById(R.id.imagePunteggio);
        	convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        
        String punteggioString = statisticItem.getPunteggio();
        int punteggioInt = Integer.valueOf(punteggioString);
        
		holder.punteggio.setText(punteggioString + "%");
		holder.data.setText(statisticItem.getDataPunteggio());
		
		if(punteggioInt < 30){
			holder.imagePunteggio.setImageResource(R.drawable.like);
		}
		else if(punteggioInt>=30 && punteggioInt<75){
			holder.imagePunteggio.setImageResource(R.drawable.warning);
		}
		else{
			holder.imagePunteggio.setImageResource(R.drawable.not_like);
		}
		
		return convertView;
	}
}
