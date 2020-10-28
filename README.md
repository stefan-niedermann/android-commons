# android-commons

[![Latest Release](https://img.shields.io/github/v/tag/stefan-niedermann/android-commons?label=latest+release&sort=semver)](https://github.com/stefan-niedermann/android-commons/releases)
[![GitHub issues](https://img.shields.io/github/issues/stefan-niedermann/android-commons.svg)](https://github.com/stefan-niedermann/android-commons/issues)
[![GitHub stars](https://img.shields.io/github/stars/stefan-niedermann/android-commons.svg)](https://github.com/stefan-niedermann/android-commons/stargazers)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

- [What is this](#what-is-this)
- [How to use](#how-to-use)
- [Modules](#modules)
  - [shared-preferences](#shared-preferences)
  - [util](#util)
- [License](#notebook-license)

## What is this

Many Android clients for Nextcloud apps need similar mechanisms. To reduce maintenance efforts and provide a similar look & feel, this library aims to provide tooling and support which can be useful for various Android clients.

## How to use

Add this dependency to your `build.gradle`-file to include *all* modules at once:

```groovy
implementation 'com.github.stefan-niedermann:android-commons:0.0.9'
```

## Modules

### shared-preferences

```groovy
implementation 'com.github.stefan-niedermann.android-commons:shared-preferences:0.0.9'
```

Provides `LiveData` wrapper around `SharedPreferences`.

#### Usage

```java
SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
SharedPreferenceIntLiveData liveData = SharedPreferenceIntLiveData(sharedPreferences, PREF_KEY_MY_COLOR, Color.WHITE)
```

### util

```groovy
implementation 'com.github.stefan-niedermann.android-commons:util:0.0.9'
```

#### Usage

```java
ClipboardUtil.INSTANCE.copyToClipboard(context, "MyText");
ClipboardUtil.INSTANCE.copyToClipboard(context, "MyTitle", "MyText");
ClipboardUtil.INSTANCE.getClipboardURLorNull(context);
```

```java
DimensionUtil.INSTANCE.dpToPx(context, R.dimen.my_dimension);
```

```java
ColorUtil.INSTANCE.formatColorToParsableHexString("123");       // → #112233
ColorUtil.INSTANCE.formatColorToParsableHexString("#12345605"); // → #123456
```

## :notebook: License

This project is licensed under the [GNU GENERAL PUBLIC LICENSE](/LICENSE).
