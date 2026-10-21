package com.example.sensordashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView dashboardWebView;
    private ProgressBar progressBar;

    private static final String DASHBOARD_URL =
            "https://datastudio.google.com/embed/reporting/bb0908bd-6f48-4dd2-b3c0-aab1ee0aa529/page/page_12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dashboardWebView =
                findViewById(R.id.dashboardWebView);

        progressBar =
                findViewById(R.id.progressBar);

        // WebView settings
        WebSettings webSettings =
                dashboardWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        dashboardWebView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public void onPageStarted(
                            WebView view,
                            String url,
                            android.graphics.Bitmap favicon) {

                        progressBar.setVisibility(View.VISIBLE);
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(
                            WebView view, String url) {

                        progressBar.setVisibility(View.GONE);
                        super.onPageFinished(view, url);
                    }
                });

        dashboardWebView.setWebChromeClient(
                new WebChromeClient());

        dashboardWebView.loadUrl(DASHBOARD_URL);
    }

    @Override
    public void onBackPressed() {
        if (dashboardWebView != null
                && dashboardWebView.canGoBack()) {
            dashboardWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
