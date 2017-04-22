[Deprecated] android-simple-facebook
=======================

#### Currently this lib was tested and works with graph api `v2.3` and below. For now, I don't have much time to maintain this library anymore. Beside this, Facebook made a lot of improvements on their side. Please, use their pure Android SDK. 

#### :mega: Thanks for everyone who was involved in making this project better over 3 years. A lot of apps used this project, which I am very proud of. Love you all :heart:

-----

Simple Facebook SDK for Android which wraps original [**Facebook SDK**](https://github.com/facebook/facebook-android-sdk). <br>

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK was too complicated for simple actions like login, I decided to create simpler API for the same actions. I use this library in my applications and maintain the code.

## Community & Issues

<img src="https://fbstatic-a.akamaihd.net/rsrc.php/yl/r/H3nktOa7ZMg.ico" height="16" width="16"/> Facebook page: [`Library: simple-fb`](https://www.facebook.com/pages/Library-simple-fb/1501124800143936)<br>
<img src="http://stackoverflow.com/content/stackoverflow/img/apple-touch-icon.png" height="16" width="16"/> Stack overflow: [`android-simple-facebook`](http://stackoverflow.com/tags/android-simple-facebook/info)<br>
<img src="https://assets-cdn.github.com/favicon.ico" height="16" width="16"/> Issues: [`here`](https://github.com/sromku/android-simple-facebook/issues)

## Latest Release

[ ![Download](https://api.bintray.com/packages/sromku/maven/simple-fb/images/download.svg) ](https://bintray.com/sromku/maven/simple-fb/_latestVersion)

``` gradle
dependencies {
    compile 'com.sromku:simple-fb:4.1.1'
}
```

## Old Versions
Library jar | Branch | Supported Facebook SDK
------------|--------|------------------------
[simple-facebook-2.2.jar](https://github.com/sromku/android-simple-facebook/releases/download/2.2/simple.facebook-2.2.jar) | [v2.0](https://github.com/sromku/android-simple-facebook) | 3.14 - 3.23 
[simple-facebook-1.2.jar](https://github.com/sromku/android-simple-facebook/releases/download/1.2/simple.facebook.jar) | [v1.0](https://github.com/sromku/android-simple-facebook/tree/v1.0) | 3.0 - 3.8

## Sample App
The sample app includes examples for all actions. Check out this very short [wiki page](https://github.com/sromku/android-simple-facebook/wiki/Sample-App) of how to run and setup the sample app.

<img src="https://github.com/sromku/android-simple-facebook/wiki/images/sample-app.png" width="600"/>

## Setup/Configuration

- [Setup project](https://github.com/sromku/android-simple-facebook/wiki/Setup-project)
- [Configuration](https://github.com/sromku/android-simple-facebook/wiki/Configuration)

## Features
* **Login/Logout**
	- [Login](https://github.com/sromku/android-simple-facebook/wiki/Login)
	- [Logout](https://github.com/sromku/android-simple-facebook/wiki/Logout)

* **Publish**
	- [Publish feed](https://github.com/sromku/android-simple-facebook/wiki/Publish-feed)
	- [Publish story](https://github.com/sromku/android-simple-facebook/wiki/Publish-story)
	- [Publish album](https://github.com/sromku/android-simple-facebook/wiki/Publish-album)
	- [Publish photo](https://github.com/sromku/android-simple-facebook/wiki/Publish-photo)
	- [Publish video](https://github.com/sromku/android-simple-facebook/wiki/Publish-video)
	- [Publish score](https://github.com/sromku/android-simple-facebook/wiki/Publish-score)
    - [Publish comment](https://github.com/sromku/android-simple-facebook/wiki/Publish-comment)
    - [Publish like](https://github.com/sromku/android-simple-facebook/wiki/Publish-like)

* **Requests/Invite**
	- [Send request/invite](https://github.com/sromku/android-simple-facebook/wiki/Send-request)
	- [Delete request/invite](https://github.com/sromku/android-simple-facebook/wiki/Delete-request)

* **Get**
	- [Get accounts](https://github.com/sromku/android-simple-facebook/wiki/Get-accounts)
	- [Get album/s](https://github.com/sromku/android-simple-facebook/wiki/Get-albums)
	- [Get requests](https://github.com/sromku/android-simple-facebook/wiki/Get-requests)
	- [Get books](https://github.com/sromku/android-simple-facebook/wiki/Get-books)
	- [Get comment/s](https://github.com/sromku/android-simple-facebook/wiki/Get-comments)
	- [Get events](https://github.com/sromku/android-simple-facebook/wiki/Get-events)
	- [Get family](https://github.com/sromku/android-simple-facebook/wiki/Get-family)
	- [Get friends](https://github.com/sromku/android-simple-facebook/wiki/Get-friends)
	- [Get games](https://github.com/sromku/android-simple-facebook/wiki/Get-games)
	- [Get groups](https://github.com/sromku/android-simple-facebook/wiki/Get-groups)
	- [Get likes](https://github.com/sromku/android-simple-facebook/wiki/Get-likes)
	- [Get movies](https://github.com/sromku/android-simple-facebook/wiki/Get-movies)
	- [Get music](https://github.com/sromku/android-simple-facebook/wiki/Get-music)
	- [Get notifications](https://github.com/sromku/android-simple-facebook/wiki/Get-notifications)
	- [Get objects](https://github.com/sromku/android-simple-facebook/wiki/Get-objects)
	- [Get page](https://github.com/sromku/android-simple-facebook/wiki/Get-page)
	- [Get photos](https://github.com/sromku/android-simple-facebook/wiki/Get-photos)
	- [Get posts](https://github.com/sromku/android-simple-facebook/wiki/Get-posts)
	- [Get profile](https://github.com/sromku/android-simple-facebook/wiki/Get-profile)
	- [Get scores](https://github.com/sromku/android-simple-facebook/wiki/Get-scores)
	- [Get tagged places](https://github.com/sromku/android-simple-facebook/wiki/Get-tagged-places)
	- [Get television](https://github.com/sromku/android-simple-facebook/wiki/Get-television)
	- [Get videos](https://github.com/sromku/android-simple-facebook/wiki/Get-videos)

* **Additional options**
	- [Pagination](https://github.com/sromku/android-simple-facebook/wiki/Pagination)
	- [General 'GET'](https://github.com/sromku/android-simple-facebook/wiki/General-get)
	- [Privacy settings](https://github.com/sromku/android-simple-facebook/wiki/Privacy-settings)
	- [Configuration options](https://github.com/sromku/android-simple-facebook/wiki/Configuration-options)
	- [Request new permissions](https://github.com/sromku/android-simple-facebook/wiki/Request-new-permissions)
	- [Get granted persmissions](https://github.com/sromku/android-simple-facebook/wiki/Get-granted-persmissions)
	- [Misc](https://github.com/sromku/android-simple-facebook/wiki/Misc)
	- [Debug](https://github.com/sromku/android-simple-facebook/wiki/Debug)
	- [Connect Smart Device](https://github.com/sromku/android-simple-facebook/wiki/Connect-smart-device)

* **Samples**
    - [Setup sample app](https://github.com/sromku/android-simple-facebook/wiki/Sample-App)
	- [LikeView](https://github.com/sromku/android-simple-facebook/wiki/LikeView)

*And,*
* Based on latest Facebook SDK and Graph API.
* Supports oldest Graph API versions.
* Permission strings are predefined.
* No need to use `LoginButton` view for being able to login/logout. You can use any `View`.
* No need to care for correct login with `READ` and `PUBLISH` permissions. Just mention the permissions you need and this library will care for the rest.
* Open graph support
* And much more

## Documentation
Everything is explained in [**wiki**](https://github.com/sromku/android-simple-facebook/wiki)

## Contributors


- [Rony Lutsky](https://github.com/ronlut)
- [Martín Marconcini](https://github.com/Gryzor)
- [Koray Balcı](https://github.com/koraybalci)
- [Andrew Chen](https://github.com/yongjhih)
- [Denny Huang](https://github.com/denny0223)
- [Mateusz Mlodawski](https://github.com/MateuszMlodawski)
- [booker0108](https://github.com/booker0108)
- [Ansgar Mertens](https://github.com/ansgarm)
- [bperin](https://github.com/bperin)
- [Norbert Schuler](https://github.com/norbertschuler)
- [Chang Yu-heng](https://github.com/changyuheng)
- [Samed Ozdemir](https://github.com/xsorifc28)
- [Filipe de Lima Brito](https://github.com/filipedelimabrito)

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-android--simple--facebook-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/949)

## License

    Copyright 2013-present Roman Kushnarenko

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

