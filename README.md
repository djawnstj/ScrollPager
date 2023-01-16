
# What is it?

![ezgif com-gif-maker](https://user-images.githubusercontent.com/90193598/188314880-30bc546c-5b77-426c-88f1-8f4e105c93d3.gif)

This is Pager using ScrollView.

It works like ViewPager, but it works vertically.
[![](https://jitpack.io/v/djawnstj/ScrollPager.svg)](https://jitpack.io/#djawnstj/ScrollPager)

# How to use?
1. You should implement in your project.
   - in your settings.gradle
       ``` groovy
       allprojects {
           repositories {
               ...
               maven { url 'https://jitpack.io' }
           }
       }
       ```
   - in your build.gradle (app level)
       ``` groovy
       dependencies {
           implementation 'com.github.djawnstj:ScrollPager:v1.1.0'
       }
       ```
2. add layout xml like...
   ``` xml
      <com.github.djawnstj.VerticalScrollPager
         android:id="@+id/scrollPager"
         android:layout_width="match_parent"
         android:layout_height="match_parent">
           
      </com.github.djawnstj.VerticalScrollPager>
   ```
3. add child view
   - in xml
      ``` xml
      <com.github.djawnstj.VerticalScrollPager
         android:id="@+id/scrollPager"
         android:layout_width="match_parent"
         android:layout_height="match_parent">
      
      <ImageView
         android:id="@+id/imageView"
         android:layout_width="match_parent"
         android:layout_height="match_parent"/>
         
         ...
           
      </com.github.djawnstj.VerticalScrollPager>
      ```
   - in kotlin
      ``` kotlin
      val scrollPager = findViewById<VerticalScrollPager>(R.id.scrollPager)
      val imageView = ImageView(this)
      
      scrollPager.addView(imageView)
      ```
4. Call a scroll function when you want to.
   ``` kotlin
   binding.scrollPager.scrollToView(binding.imageView)
   ```
   
# Change Log
- ` 1.1.0 ` :
  1. You no longer need to add top-level layouts.
  2. Force to change the size of child view (match_parent)
- ` 1.0.2 ` : Troubleshooting library implementation
