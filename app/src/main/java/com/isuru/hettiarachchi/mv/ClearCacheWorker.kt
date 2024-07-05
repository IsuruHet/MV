package com.isuru.hettiarachchi.mv

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.webkit.CookieManager
import android.webkit.WebView

class ClearCacheWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        clearCacheAndCookies()
        return Result.success()
    }

    private fun clearCacheAndCookies() {
        val webView = WebView(applicationContext)
        webView.clearCache(true)
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
    }
}
