package com.dongdong999.cwm_layout

import android.os.Build
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient


class BlankFragment3 : Fragment() {
    private lateinit var MainwebView: WebView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_blank3, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView = view?.findViewById<WebView>(R.id.webview_fragment3)
        webView?.webViewClient = WebViewClient()
        webView?.webChromeClient = WebChromeClient()

        //웹뷰 설정
        webView?.apply {
            webViewClient = WebViewClientClass()
            webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {

                    return true
                }
            }

            //웹뷰 추가설정
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true                                      /*자바스크립트 허용 */
            settings.setSupportMultipleWindows(false)                            /* 새창 띄우기 허용 */
            settings.javaScriptCanOpenWindowsAutomatically = true             /*자바스크립트 새창 허용 */
            settings.loadWithOverviewMode = true
            settings.allowContentAccess = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }
        }
        
        // 원하는 주소를 WebView에 연결
        webView?.loadUrl("https://www.purmeecard.com/public.do?request=cardSelectForm")
    }
}

class WebViewClientClass : WebViewClient() {

}

