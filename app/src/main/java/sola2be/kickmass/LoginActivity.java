package sola2be.kickmass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramLoginCallbackListener;
import com.instagram.instagramapi.objects.IGSession;
import com.instagram.instagramapi.utils.InstagramKitLoginScope;
import com.instagram.instagramapi.widgets.InstagramLoginButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private InstagramLoginCallbackListener loginListener = new InstagramLoginCallbackListener() {
        @Override
        public void onSuccess(IGSession result) {
            Log.d("onSuccess"," "+result.getAccessToken());
            startListFollowersActivity(result.getAccessToken());
        }

        @Override
        public void onCancel() {
            Log.d("onCancel", "");
        }

        @Override
        public void onError(InstagramException error) {
            Log.d("onError", error.getErrorReason());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //InstagramEngine.getInstance(LoginActivity.this).logout(LoginActivity.this,0);
        String[] scopes = {InstagramKitLoginScope.BASIC, InstagramKitLoginScope.FOLLOWER_LIST, InstagramKitLoginScope.RELATIONSHIP};
        InstagramLoginButton instagramLoginButton = (InstagramLoginButton) findViewById(R.id.instagramLoginButton);
        instagramLoginButton.setInstagramLoginCallback(loginListener);
        instagramLoginButton.setScopes(scopes);
    }

    public void startListFollowersActivity(String token) {
        Intent intent = new Intent(LoginActivity.this, ListFollowersActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

}
