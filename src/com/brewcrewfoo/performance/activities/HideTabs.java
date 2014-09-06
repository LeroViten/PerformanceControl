package com.brewcrewfoo.performance.activities;

/**
 * Created by h0rn3t on 22.10.2013.
 */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;

import com.brewcrewfoo.performance.R;
import com.brewcrewfoo.performance.util.ActivityThemeChangeInterface;
import com.brewcrewfoo.performance.util.Constants;
import com.brewcrewfoo.performance.util.DragSortListView;
import com.brewcrewfoo.performance.util.Helpers;
import com.brewcrewfoo.performance.util.Tab;
import com.brewcrewfoo.performance.util.TabAdapter;

import java.util.ArrayList;


public class HideTabs extends Activity implements Constants, ActivityThemeChangeInterface {
    private boolean mIsLightTheme;
    SharedPreferences mPreferences;
    private final Context context=this;
    private TabAdapter dataAdapter;


    private DragSortListView.DropListener onDrop =
            new DragSortListView.DropListener() {
                @Override
                public void drop(int from, int to) {
                    if (from != to) {
                        Tab item = dataAdapter.getItem(from);
                        dataAdapter.remove(item);
                        dataAdapter.insert(item, to);
                        dataAdapter.notifyDataSetChanged();
                        String s="";
                        for(int i=0;i<dataAdapter.getCount();i++){
                            s+=dataAdapter.getItem(i).getId()+":";
                        }
                        mPreferences.edit().putString("tab_ids",s).apply();
                    }
                }
            };
    private DragSortListView.RemoveListener onRemove =
            new DragSortListView.RemoveListener() {
                @Override
                public void remove(int which) {
                    dataAdapter.remove(dataAdapter.getItem(which));
                }
            };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setTheme();
        setContentView(R.layout.hide_tabs);
        DragSortListView listView = (DragSortListView) findViewById(R.id.applist);
        listView.setDropListener(onDrop);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tab t = (Tab) parent.getItemAtPosition(position);
                t.setSelected(!t.isSelected());
                dataAdapter.notifyDataSetChanged();
            }
        });
        ArrayList<Tab> TabList = new ArrayList<Tab>();

        String def_ids="";
        for(int i=0;i<getResources().getStringArray(R.array.tabs).length;i++) def_ids+=i+":";
        final String Tids=mPreferences.getString("tab_ids",def_ids);
        for(int i=0;i<getResources().getStringArray(R.array.tabs).length;i++) {
            String tid=Tids.split(":")[i];
            if((tid!=null)&&(!tid.equals(""))) {
                int id=Integer.valueOf(tid);
                final String sTab=getResources().getStringArray(R.array.tabs)[id];
                if (Helpers.is_Tab_available(id)) TabList.add(new Tab(sTab, mPreferences.getBoolean(sTab, true), id));
            }
        }

        /*for(int i=0;i<getResources().getStringArray(R.array.tabs).length;i++) {
            Tab t = new Tab(getResources().getStringArray(R.array.tabs)[i],mPreferences.getBoolean(getResources().getStringArray(R.array.tabs)[i],true),i);
            if(Helpers.is_Tab_available(i)) TabList.add(t);
        }*/
        dataAdapter = new TabAdapter(this,R.layout.tab_item, TabList);
        listView.setAdapter(dataAdapter);

    }
    @Override
    public boolean isThemeChanged() {
        final boolean is_light_theme = mPreferences.getBoolean(PREF_USE_LIGHT_THEME, false);
        return is_light_theme != mIsLightTheme;
    }

    @Override
    public void setTheme() {
        final boolean is_light_theme = mPreferences.getBoolean(PREF_USE_LIGHT_THEME, false);
        mIsLightTheme = is_light_theme;
        setTheme(is_light_theme ? R.style.Theme_Light : R.style.Theme_Dark);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        boolean flag=false;
        boolean changed=false;
        for(int i=0;i<dataAdapter.getCount();i++){
            Tab t = dataAdapter.getItem(i);
            if(mPreferences.getBoolean(t.getName(),true)!=t.isSelected()) changed=true;
            if(t.isSelected()) flag=true;
            mPreferences.edit().putBoolean(t.getName(), t.isSelected()).apply();
        }
        if(!flag){
            // at least one tab (tab0) must be visible
            Tab t = dataAdapter.getItem(0);
            mPreferences.edit().putBoolean(t.getName(),true).apply();
        }
        if(changed){
            MainActivity.thide=true;
        }
    }


}
