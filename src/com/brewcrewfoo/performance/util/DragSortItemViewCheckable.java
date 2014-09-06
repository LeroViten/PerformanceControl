package com.brewcrewfoo.performance.util;

/*
A subclass of the Android ListView component that enables drag
and drop re-ordering of list items.

Copyright 2012 Carl Bauer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
import android.content.Context;
import android.view.View;
import android.widget.Checkable;

public class DragSortItemViewCheckable extends DragSortItemView implements Checkable {

    public DragSortItemViewCheckable(Context context) {
        super(context);
    }

    @Override
    public boolean isChecked() {
        View child = getChildAt(0);
        if (child instanceof Checkable)
            return ((Checkable) child).isChecked();
        else
            return false;
    }

    @Override
    public void setChecked(boolean checked) {
        View child = getChildAt(0);
        if (child instanceof Checkable)
            ((Checkable) child).setChecked(checked);
    }

    @Override
    public void toggle() {
        View child = getChildAt(0);
        if (child instanceof Checkable)
            ((Checkable) child).toggle();
    }
}
