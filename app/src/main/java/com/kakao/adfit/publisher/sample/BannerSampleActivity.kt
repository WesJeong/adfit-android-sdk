package com.kakao.adfit.publisher.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kakao.adfit.ads.AdListener
import kotlinx.android.synthetic.main.activity_banner_sample.*

class BannerSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_banner_sample)

        val adView = adView!!  // 배너 광고 뷰
        adView.setClientId("DAN-1h82js7czjqsj")  // 할당 받은 광고 단위(clientId) 설정
        adView.setAdListener(object : AdListener {  // 광고 수신 리스너 설정

            override fun onAdLoaded() {
                toast("Banner is loaded")
            }

            override fun onAdFailed(errorCode: Int) {
                toast("Failed to load banner :: errorCode = $errorCode")
            }

            override fun onAdClicked() {
                toast("Banner is clicked")
            }

        })

        // lifecycle 사용 가능한 경우
        // 참조 :: https://developer.android.com/topic/libraries/architecture/lifecycle
        // 사용 불가능한 경우는 BannerJava320x50Activity 참조
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                adView.resume()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                adView.pause()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                adView.destroy()
            }

        })

        adView.loadAd()  // 광고 요청
    }

    private fun toast(message: String) {
        Toast.makeText(adView?.context ?: return, message, Toast.LENGTH_SHORT).show()
    }

}

