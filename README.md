# ToastCompat - A Beautiful Toast Library for Android Kotlin 🤩🔥

[![](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16#l16)
[![](https://jitpack.io/v/tarifchakder/ToastCompat.svg)](https://jitpack.io/#tarifchakder/ToastCompat)
[![APK](https://img.shields.io/badge/Download-Demo-brightgreen.svg)](https://github.com/tarifchakder/ToastCompat/blob/master/ToastCompat-apk-demo.apk)

<div align="center">
	<img src="https://github.com/tarifchakder/ToastCompat/blob/master/ic_launcher.png" width="128">
</div>

An Android library that takes the standard toast to the next level with many styling options.

Style your toasts either by code or with a style in `styles.xml`.

## Prerequisites

```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

## Dependency

Add this to your module's `build.gradle` file (make sure the version matches the JitPack badge above):

```gradle
dependencies 
        {
         ...
         implementation 'com.github.tarifchakder:ToastCompat:1.2.0'
   }
```

## Example using style attributes

Define a style in `styles.xml`. put all style attributes:

```xml

<style name="allStyle">
    <item name="toastBackgroundType">SOLID</item>
    <item name="toastColorBackground">@color/black</item>
    <item name="toastTextColor">@color/white</item>
    <item name="toastRadius">15dp</item>
    <item name="toastTextBold">true</item>
    <item name="toastFont">@font/marko_one</item>
    <item name="toastTextSize">12sp</item>
    <item name="toastIconSize">20</item>
    <item name="toastIconStart">@drawable/ic_baseline_circle_notifications_24</item>
    <item name="toastIconEnd">@drawable/ic_baseline_circle_notifications_24</item>
    <item name="toastGravity">BOTTOM</item>
    <item name="toastStrokeColor">@android:color/holo_green_light</item>
    <item name="toastStrokeWidth">2dp</item>
    <item name="toastLength">LONG</item>
</style>
```

## Donation

If this project help you reduce time to develop, you can give me a cup of coffee :)

[!["Buy Me A Coffee"](https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png)](https://www.buymeacoffee.com/tarifchakder)

## License

    Copyright 2022 Md Tarif Chakder

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

