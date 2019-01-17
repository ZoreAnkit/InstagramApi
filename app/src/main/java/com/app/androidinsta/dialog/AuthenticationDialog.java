package com.app.androidinsta.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.androidinsta.Constants;
import com.app.androidinsta.R;
import com.app.androidinsta.interfaces.AuthenticationListener;

public class AuthenticationDialog extends Dialog {
    private final AuthenticationListener listener;
    private Context context;
    private WebView webView;

    private final String url = Constants.BASE_URL
            +"https://instagram.com/oauth/authorize/?client_id="
            +Constants.INSTAGRAN_CLIENT_ID
            +"&redirect_url"
            +Constants.REDIRECT_URL
            +"&response_type=token"
            +"&display=touch&scope=public_content";

    public AuthenticationDialog(Context context, AuthenticationListener listener) {
        super(context);

        this.context=context;
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.auth_dialog);
        initializeWebView();
    }

    private void initializeWebView() {
        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){

            String access_token;
            boolean authComplete = false;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(url.contains("#access_token=") && !authComplete){
                    Uri uri = Uri.parse(url);
                    access_token = uri.getEncodedFragment();
                    access_token = access_token.substring(access_token.lastIndexOf("=")+1);
                    Log.e("access_token",access_token );
                    authComplete=true;
                    listener.onCodeReceived(access_token);
                    dismiss();


                }
                else if(url.contains("?error")){
                    Log.e("access_token","Getting Error fetching access token" );
                    Log.e("response",url);
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
