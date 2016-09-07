package sola2be.kickmass;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Sola2Be on 04.09.2016.
 */

public class LoginDialog  {

    private ProgressBar bar;
    private WebView web;
    public LoginDialog (Context context) {
        View view  = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_login,null);
        final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
        web = (WebView) view.findViewById(R.id.webView);

        web.loadUrl(ApplicationData.AUTORIZATION_URL);
        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                Log.d("onReceivedLoginRequest",account);
                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                Log.d("onReceivedHttpAuth",host);
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("onPageFinished",url);
                if (url.startsWith(ApplicationData.REDIRECT_URI)) {
                    dialog.dismiss();
                }
                else
                web.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("OverrideUrlLoading",url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        bar.setVisibility(View.VISIBLE);
        dialog.show();
    }

}
