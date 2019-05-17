package com.example.shopaholic;


public interface MpesaListener {

    public void onMpesaError(Pair<Integer, String> result);
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage);
}

