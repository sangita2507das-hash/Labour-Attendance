package com.labourattendance.app

import android.app.Activity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.content.ContentValues
import android.util.Base64
import android.widget.Toast
import java.io.OutputStream

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private val fileRequestCode = 501

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        webView = WebView(this)
        setContentView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(view: WebView?, callback: ValueCallback<Array<Uri>>?, params: FileChooserParams?): Boolean {
                fileCallback?.onReceiveValue(null)
                fileCallback = callback
                val intent = params?.createIntent() ?: Intent(Intent.ACTION_GET_CONTENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "*/*"
                }
                return try { startActivityForResult(intent, fileRequestCode); true }
                catch (_: Exception) { fileCallback = null; false }
            }
        }
        webView.addJavascriptInterface(DownloadBridge(), "AndroidBridge")
        webView.loadUrl("file:///android_asset/index.html")
    }

    inner class DownloadBridge {
        @JavascriptInterface
        fun saveBase64(fileName: String, dataUrl: String) {
            runOnUiThread {
                try {
                    val encoded = dataUrl.substringAfter(",")
                    val bytes = Base64.decode(encoded, Base64.DEFAULT)
                    val mime = if (fileName.endsWith(".csv")) "text/csv" else "application/json"
                    val values = ContentValues().apply {
                        put(MediaStore.Downloads.DISPLAY_NAME, fileName.replace("/", "_"))
                        put(MediaStore.Downloads.MIME_TYPE, mime)
                    }
                    val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
                    if (uri != null) {
                        contentResolver.openOutputStream(uri).use { it?.write(bytes) }
                        Toast.makeText(this@MainActivity, "Saved to Downloads", Toast.LENGTH_LONG).show()
                    } else Toast.makeText(this@MainActivity, "Could not save file", Toast.LENGTH_LONG).show()
                } catch (_: Exception) {
                    Toast.makeText(this@MainActivity, "Could not save file", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @Deprecated("Deprecated by Android, retained for WebView file chooser compatibility")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileRequestCode) {
            val result = if (resultCode == RESULT_OK) {
                val uri = data?.data
                if (uri != null) arrayOf(uri) else null
            } else null
            fileCallback?.onReceiveValue(result)
            fileCallback = null
        }
    }

    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
