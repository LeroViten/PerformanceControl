package com.brewcrewfoo.performance.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brewcrewfoo.performance.R;

import java.util.ArrayList;

/**
 * Created by H0RN3T on 05.09.2014.
 */
public class TabAdapter extends ArrayAdapter<Tab> {
    private ArrayList<Tab> TabList;
    private Context context;

    public TabAdapter(Context context, int textViewResourceId,ArrayList<Tab> TabList) {
        super(context, textViewResourceId, TabList);
        this.context=context;
        this.TabList = new ArrayList<Tab>();
        this.TabList.addAll(TabList);
    }


    public class ViewHolder {
        public TextView name;
        public ImageView status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.tab_item, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.label);
            holder.status=(ImageView) convertView.findViewById(R.id.status);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Tab t = TabList.get(position);
        holder.name.setText(t.getName());
        if(t.isSelected()){
            holder.status.setImageDrawable(context.getResources().getDrawable(android.R.drawable.checkbox_on_background));
        }
        else{
            holder.status.setImageDrawable(context.getResources().getDrawable(android.R.drawable.checkbox_off_background));
        }

        return convertView;

    }

}