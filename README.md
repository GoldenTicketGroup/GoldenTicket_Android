# GoldenTicket_Android

당일 공연 추첨식 예매 서비스 **골든 티켓**입니다.

## 소개 동영상
[![Video Label](http://img.youtube.com/vi/uLR1RNqJ1Mw/0.jpg)](https://youtu.be/uLR1RNqJ1Mw?t=0s)

## 서비스 work flow
---------------
![플로우](https://sopt24server.s3.ap-northeast-2.amazonaws.com/flow.png)

## 앱 아이콘
------------
![GoldenTicketIcon](https://user-images.githubusercontent.com/49272528/61025745-8f1a8500-a3ec-11e9-8e5a-ae246f2a9bbc.png)

어플 기능
------------
> * 로그인
> * 회원가입
> * 오늘 공연 보여주기
> * 공연 검색하기
> * 공연 응모하기
> * 응모한 공연 타이머 보기
> * 응모한 공연 당첨확인
> * 응모한 공연 당첨확인 알람
> * 매일 오전 10시 마다 오늘의 공연 알람
> * 관심있는 공연 알람


## 라이브러리
```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.core:core-ktx:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.preference:preference:1.1.0-beta01'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'org.jetbrains.anko:anko:0.10.8'

    implementation 'com.android.support:support-v4:29.0.0'
    implementation 'com.android.support:design:29.0.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'

    //서버와 통신을 위한 라이브러리
    implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.google.code.gson:gson:2.8.5'

    //material design을 위한 라이브러리
    implementation 'androidx.drawerlayout:drawerlayout:1.1.0-alpha01'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.github.aakira:expandable-layout:1.6.0@aar'
    implementation 'com.google.android.material:material:1.1.0-alpha07'
    implementation "org.jetbrains.anko:anko-commons:$anko_version"

    //recyclerView 자동 정렬 라이브러리
    implementation 'com.github.rubensousa:gravitysnaphelper:2.0'

    //이미지 넣기 라이브러리
    implementation 'com.github.bumptech.glide:glide:4.9.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.9.0'

    //버튼효과를 위한 라이브러리
    implementation 'com.github.traex.rippleeffect:library:1.3'
    implementation 'com.vipulasri:ticketview:1.0.7'

    //FCM을 위한 라이브러리
    implementation 'com.google.firebase:firebase-core:17.0.0'
    implementation 'com.google.firebase:firebase-messaging:19.0.1'

    //RxJava를 위한 라이브러리
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.2'
    implementation 'io.reactivex:rxjava:1.1.6'
}

```

## 개발자

- **김강희** - [강희 깃주소](https://github.com/ganghee) 
- **김민경** - [민경 깃주소](https://github.com/minkyoe) 
- **한선민** - [선민 깃주소](https://github.com/HanSeonmin)
- **전성은** - [성은 ](https://github.com/cse0616)

## Google Play Store


