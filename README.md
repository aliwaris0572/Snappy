
# Snappy
 
[![](https://jitpack.io/v/aliwaris0572/Snappy.svg)](https://jitpack.io/#aliwaris0572/Snappy)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Snappy-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7038)


## A breathing toolbar library in Kotlin
Want to use the breathing toolbar effect similar to *Snapchat* in your app?  You have reached the right place.

|![sample1](https://github.com/aliwaris0572/Snappy/blob/dev/app/art/BreathingToolbar.gif)|  ![sample2](https://github.com/aliwaris0572/Snappy/blob/dev/app/art/BreathingToolbar2.gif)|


## How to use?

 1. Initialize - `val  snappy  = Snappy(this, colorArray)`
 2. Start the magic - `snappy.startBreathing(toolbar)`
 3. When you're done - `snappy.stopBreathing(toolbar)`
 
Easy enough!!! :)
For more customization, refer sample application code.

## Gradle
Add it in your root build.gradle at the end of repositories

    allprojects {
		    repositories {
			    ...
			    maven { url 'https://jitpack.io' }
		    }
	    }
  
---------------------------------------------------------------

Then, add this in you app level build.gradle

    dependencies {
	            implementation 'com.github.aliwaris0572:Snappy:{latest_version}'
	    }
