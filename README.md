# android-commons

[![Latest Release](https://img.shields.io/github/v/tag/stefan-niedermann/android-commons?label=latest+release&sort=semver)](https://github.com/stefan-niedermann/android-commons/releases)
[![GitHub issues](https://img.shields.io/github/issues/stefan-niedermann/android-commons.svg)](https://github.com/stefan-niedermann/android-commons/issues)
[![GitHub stars](https://img.shields.io/github/stars/stefan-niedermann/android-commons.svg)](https://github.com/stefan-niedermann/android-commons/stargazers)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

- [What is this](#what-is-this)
- [How to use](#how-to-use)
- [Modules](#modules)
  - [shared-preferences](#shared-preferences)
  - [reactive-livedata](#reactive-livedata)
  - [util](#util)
- [License](#notebook-license)

## What is this

Many Android clients for Nextcloud apps need similar mechanisms. To reduce maintenance efforts and provide a similar look & feel, this library aims to provide tooling and support which can be useful for various Android clients.

## How to use

Add this dependency to your `build.gradle`-file to include *all* modules at once:

```groovy
implementation 'com.github.stefan-niedermann:android-commons:1.0.4'
```

## Modules

### shared-preferences

```groovy
implementation 'com.github.stefan-niedermann.android-commons:shared-preferences:1.0.4'
```

Provides `LiveData` wrapper around `SharedPreferences`.

#### Usage

```java
final var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
final var liveData = SharedPreferenceIntLiveData(sharedPreferences, PREF_KEY_MY_COLOR, Color.WHITE)
```

### reactive-livedata

```groovy
implementation 'com.github.stefan-niedermann.android-commons:reactive-livedata:1.0.4'
```

Provides [`ReactiveX`](https://reactivex.io/) features for `LiveData`.

#### Usage

```java
final var liveData = new MutableLiveData<Boolean>(false);
final var rxLiveData = new ReactiveLiveData(liveData);
rxLiveData
  .debounce(500)
  .map(val -> !val)
  .filter(val -> !val)
  .take(3)
  .distinctUntilChanged()
  .observe(this, number -> {
      // …
  });
```

### util

```groovy
implementation 'com.github.stefan-niedermann.android-commons:util:1.0.4'
```

Provides various independent low level util functions.

#### Usage

```java
ClipboardUtil.copyToClipboard(context, "MyText");
ClipboardUtil.copyToClipboard(context, "MyTitle", "MyText");
ClipboardUtil.getClipboardURLorNull(context);
```

```java
ColorUtil.formatColorToParsableHexString("123");       // → #112233
ColorUtil.formatColorToParsableHexString("#12345605"); // → #123456
ColorUtil.intColorToHexString(Color.WHITE);            // →  ffffff
```

## :notebook: License

This project is licensed under the [GNU GENERAL PUBLIC LICENSE](/LICENSE).
