package com.mvptutorial.ui.list;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Uche on 2016-04-03.
 */
public class ListItemViewModel extends BaseObservable {
    private String text;
    private long id;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return getText();
    }
}
