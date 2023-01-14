
# What is it?

![ezgif com-gif-maker](https://user-images.githubusercontent.com/90193598/188314880-30bc546c-5b77-426c-88f1-8f4e105c93d3.gif)

This is Pager using ScrollView. (Like ViewPager)
[![](https://jitpack.io/v/djawnstj/ScrollPager.svg)](https://jitpack.io/#djawnstj/ScrollPager)

# How to use?
1. You should implement this 'lb' in your project.
  - in yout settings.gradle
``` groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
  - in yout byild.gradle (app level)
``` groovy
dependencies {
    implementation 'com.github.djawnstj:ScrollPager:Tag'
}
```
2. Call a scroll function when you want to.
in my case...
``` kotlin
binding.scrollPager.scrollToView(binding.textView2)
```
