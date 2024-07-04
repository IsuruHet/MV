package com.isuru.hettiarachchi.mv

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity


class DownloadActivity : ComponentActivity() {

    private var downloadId: Long = 0
    private lateinit var downloadManager: DownloadManager

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.download_movie)

        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val webView: WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webView.loadUrl("https://www.seedr.cc/?r=5416500")

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimetype)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file...")
            request.setTitle(getFileName(contentDisposition))
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFileName(contentDisposition))

            downloadId = downloadManager.enqueue(request)
        }
    }

    private fun getFileName(contentDisposition: String?): String {
        var fileName = "unknown"
        if (contentDisposition != null) {
            val split = contentDisposition.split("filename=")
            if (split.size == 2) {
                fileName = split[1].replace("\"", "")
            }
        }
        return fileName
    }

    // Method to pause the download
    private fun pauseDownload() {
        downloadManager.remove(downloadId)
    }

    // Method to resume the download
    private fun resumeDownload(url: String, fileName: String, downloadedBytes: Long) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setDescription("Resuming download...")
        request.setTitle(fileName)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.addRequestHeader("Range", "bytes=$downloadedBytes-")
        downloadId = downloadManager.enqueue(request)
    }

    // Method to get the current downloaded bytes
    private fun getDownloadedBytes(downloadId: Long): Long {
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)
        val cursor: Cursor = downloadManager.query(query)
        var downloadedBytes: Long = 0
        if (cursor.moveToFirst()) {
            val bytesDownloadedIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            downloadedBytes = cursor.getLong(bytesDownloadedIndex)
        }
        cursor.close()
        return downloadedBytes
    }
}
