package com.example.playeropener

import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity

class ZoomedWebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        setContentView(webView)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            // setAppCacheEnabled(true) // Deprecated, kaldırıldı
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            // Chrome User-Agent taklidi
            userAgentString = "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Mobile Safari/537.36"
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let { view?.loadUrl(it) }
                return true
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: android.net.http.SslError?
            ) {
                // Sadece test için: SSL hatalarını geç
                handler?.proceed()
            }
        }

        webView.setInitialScale(125) // %125 zoom
        webView.loadUrl("https://webnav.poilabs.com/?placeId=673c7846-ea6d-4dfb-b8a2-d32f49493167")
    }

    override fun onDestroy() {
        super.onDestroy()
        // WebView belleğini temizle
        (findViewById<WebView>(android.R.id.content))?.destroy()
    }
}
