package com.brewcrewfoo.performance.util;

/**
 * Created by H0RN3T on 01.09.2014.
 */
public class Tab {
    String name = null;
    boolean selected = false;
    long id=-1;

    public Tab(String name, boolean selected,int id) {
        super();
        this.name = name;
        this.selected = selected;
        this.id=id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) { this.selected = selected; }
    public long getId(){return id;}
}
