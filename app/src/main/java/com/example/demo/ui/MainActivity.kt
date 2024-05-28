package com.example.demo.ui

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        val redirect_uri = "https://f1aa-2001-4456-c77-ce00-b052-557f-6ddd-9784.ngrok-free.app/api/express/redirect"
        val client_id = "ca_Q90fohkiqEhnP3oChSMlUuUMOAyrPhbx"
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(
            "https://connect.stripe.com/oauth/authorize?response_type=code&client_id=$client_id&scope=read_write&redirect_uri=$redirect_uri"
        )
    }
}