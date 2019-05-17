package com.example.shopaholic;

public interface AuthListener {
    public void onAuthError(Pair<Integer, String> result);
    public void onAuthSuccess();
}
