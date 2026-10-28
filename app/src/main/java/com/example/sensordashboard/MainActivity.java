
package com.example.sensordashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView dashboardWebView;
    private ProgressBar progressBar;
    private Button refreshButton;

    private static final String DASHBOARD_URL =
            "https://datastudio.google.com/reporting/6ab1984a-6183-4295-b73e-ad4fde5a848c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect XML components
        dashboardWebView =
                findViewById(R.id.dashboardWebView);

        progressBar =
                findViewById(R.id.progressBar);

        refreshButton =
                findViewById(R.id.refreshButton);

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

        // Handle page loading
        dashboardWebView.setWebViewClient(
                new WebViewClient() {

                    @Override
                    public void onPageStarted(
                            WebView view,
                            String url,
                            android.graphics.Bitmap favicon) {

                        progressBar.setVisibility(View.VISIBLE);

                        super.onPageStarted(
                                view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(
                            WebView view, String url) {

                        progressBar.setVisibility(View.GONE);

                        super.onPageFinished(view, url);
                    }

                    @Override
                    public void onReceivedError(
                            WebView view,
                            android.webkit.WebResourceRequest request,
                            android.webkit.WebResourceError error) {

                        super.onReceivedError(
                                view, request, error);

                        if (request.isForMainFrame()) {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(
                                    MainActivity.this,
                                    "Page gagal dimuatkan. Semak internet.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                });

        dashboardWebView.setWebChromeClient(
                new WebChromeClient());

        // Refresh button function
        refreshButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            dashboardWebView.reload();
        });

        // Load dashboard when app starts
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

    @Override
    protected void onDestroy() {
        if (dashboardWebView != null) {
            dashboardWebView.stopLoading();
            dashboardWebView.destroy();
            dashboardWebView = null;
        }

        super.onDestroy();
    }
}
