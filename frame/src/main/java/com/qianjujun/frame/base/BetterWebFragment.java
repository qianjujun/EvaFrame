package com.qianjujun.frame.base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.qianjujun.frame.R;
import com.qianjujun.frame.databinding.BetterFragmentWebBinding;
import com.qianjujun.frame.views.title.TitleConfig;

public class BetterWebFragment extends BetterDbFragment<BetterFragmentWebBinding>{
    private boolean loadError;
    private String url;
    protected TitleConfig mTitleConfig;

    @Override
    protected void initArgs(@NonNull Bundle args) {
        super.initArgs(args);
        url = args.getString("url");
    }



    @Override
    protected void initView(BetterFragmentWebBinding dataBinding) {

        WebSettings mWebSettings = dataBinding.webview.getSettings();
//        mWebSettings.setUserAgentString(Env.userAgent);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setDatabaseEnabled(true);
        // mWebSettings.setDatabasePath(context.getDir("database", Context.MODE_PRIVATE).getPath());
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setAllowFileAccess(true);
        //mWebSettings.setAppCachePath(context.getDir("webCache", Context.MODE_PRIVATE).getPath());
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        dataBinding.webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                dataBinding.topProgress.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                onUpdateTitle(title);
            }
        });
        dataBinding.webview.addJavascriptInterface(this, "android");
        dataBinding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadError = false;
                mDataBinding.topProgress.setVisibility(View.VISIBLE);
                //showLoadingView();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(loadError){
                    showErrorView(1,"加载错误");
                }else {
                    showSuccessView();
                }
                mDataBinding.topProgress.setVisibility(View.INVISIBLE);
            }






            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                showErrorView(errorCode, description);
                loadError = true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                //super.onReceivedSslError(webView, sslErrorHandler, sslError);
                final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("认证错误");
                builder.setPositiveButton("continue", (dialog, which) -> sslErrorHandler.proceed());
                builder.setNegativeButton("cancel", (dialog, which) -> sslErrorHandler.cancel());
                final AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

        if(!TextUtils.isEmpty(url)){
            mDataBinding.webview.loadUrl(url);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.better_fragment_web;
    }


    @JavascriptInterface
    public void toNativePage(String router,String json){

    }


    protected void onUpdateTitle(String title){

    }
}
