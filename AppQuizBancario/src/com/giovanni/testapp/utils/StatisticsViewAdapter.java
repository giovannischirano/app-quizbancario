package com.giovanni.testapp.utils;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        TextView categoriaUtente;
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
        	convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
	
		holder.punteggio.setText(statisticItem.getPunteggio());
		holder.data.setText(statisticItem.getDataPunteggio());
		
		return convertView;
	}
}
