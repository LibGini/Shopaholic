package com.example.shopaholic;

import com.google.firebase.firestore.Exclude;

import io.reactivex.annotations.NonNull;

public class postID  {
    @Exclude
    public String postID;
    public < T extends postID>T withid(@NonNull final String id){
        this.postID=id;
        return (T)this;
    }
}
