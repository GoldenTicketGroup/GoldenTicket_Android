package com.example.goldenticket.etc

import android.content.Context

fun statusBarHeight(context: Context): Int {
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

    return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
    else 0
}

//화면을 full로 만든다음 상태바를 투명하게 한다.
//아래 코드를 onCreate() 안에 붙여넣고
//ll_signuplayout는 xml의 최상위 태그의 id이다
/*window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
ll_signuplayout.setPadding(0, statusBarHeight(this), 0, 0)*/


//아래 코드를 styleTheme에 넣는다
/*
 *   <item name="android:statusBarColor">@android:color/transparent</item>
*/
