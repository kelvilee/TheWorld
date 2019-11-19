package com.example.theworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapsFragment extends Fragment {
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        mWebView = (WebView) v.findViewById(R.id.webview);
        mWebView.loadUrl("https://www.surrey.ca/city-services/14487.aspx");

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient());

        return v;
        //return inflater.inflate(R.layout.fragment_maps, container, false);
    }
}
