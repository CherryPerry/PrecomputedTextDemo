## PrecomputedTextCompat demo

[![Build Status](https://travis-ci.com/CherryPerry/PrecomputedTextDemo.svg?branch=master)](https://travis-ci.com/CherryPerry/PrecomputedTextDemo)
[![Version](https://img.shields.io/github/release/CherryPerry/PrecomputedTextDemo.svg)](https://github.com/CherryPerry/PrecomputedTextDemo/releases)

This project is demonstration of
[PrecomputedTextCompat](https://developer.android.com/reference/androidx/core/text/PrecomputedTextCompat)
usage. There is two modes: default and with new API. It can be switched in toolbar menu.

### Results

With current configuration new API is slower and worse than simple with 
[TextView.setText()](https://developer.android.com/reference/android/widget/TextView.html#setText(java.lang.CharSequence))
call.

#### Original API

Fast enough calculations of layout on top device.

![screenshot_20181118-220544](https://user-images.githubusercontent.com/9081555/48676898-59cfd700-eb7e-11e8-9aac-4bd29cc48555.png)

#### PrecomputedTextCompat API

Very big spikes with probably same level of measure/draw difficulty between them.

![screenshot_20181118-220522](https://user-images.githubusercontent.com/9081555/48676899-5a686d80-eb7e-11e8-86de-afcd6ef65b76.png)