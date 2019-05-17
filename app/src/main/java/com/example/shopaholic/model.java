package com.example.shopaholic;

public class model {
    String title;
    int icon;

    public model(String title,  int icon) {
        this.title = title;
        this.icon = icon;
    }

    //getters


    public String getTitle() {
        return this.title;
    }



    public int getIcon() {
        return this.icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
