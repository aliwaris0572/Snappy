
# Snappy
 
[![](https://jitpack.io/v/aliwaris0572/Snappy.svg)](https://jitpack.io/#aliwaris0572/Snappy)

## A breathing toolbar library in Kotlin
Want to use the breathing toolbar effect similar to *Snapchat* in your app?  You have reached the right place.

![Breathing Toolbar](https://github.com/aliwaris0572/Snappy/blob/master/app/art/BreathingToolbar.gif)

## How to use?

 1. Initialize - `val  snappy  = Snappy(this, colorArray)`
 2. Start the magic - `snappy.startBreathing(toolbar)`
 3. When you're done - `snappy.stopBreathing(toolbar)`
 
Easy enough!!! :)

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
