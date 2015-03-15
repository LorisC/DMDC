package com.csumb.dmdc.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.csumb.dmdc.Activity.CropActivity;
import com.csumb.dmdc.JavaClass.TouchyWebView;
import com.csumb.dmdc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View v;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_home, container, false);
        String myurl = "https://www.dmdc.osd.mil/milconnect/faces/index.jspx?_afrLoop=1466755800658654&_afrWindowMode=0&_adf.ctrl-state=ditxj6hru_4";
        TouchyWebView myWebView = (TouchyWebView) v.findViewById(R.id.view);
        myWebView.loadUrl("https://mobile.twitter.com/dmdc");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());

        return v;
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("https://www.dmdc.osd.mil/milconnect/faces/index.jspx?_afrLoop=1466755800658654&_afrWindowMode=0&_adf.ctrl-state=ditxj6hru_4")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return false;
        }
    }


}
