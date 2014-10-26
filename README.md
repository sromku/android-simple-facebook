android-simple-facebook
=======================

Simple Facebook SDK for Android which wraps original [**Facebook SDK**](https://github.com/facebook/facebook-android-sdk). <br>
For support of older versions of facebook SDK 3.8 and below, go to [v1.0](https://github.com/sromku/android-simple-facebook/tree/v1.0) branch.

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK was too complicated for simple actions like login, I decided to create simpler API for the same actions. I use this API in my applications and maintain the code.

We have new <img src="http://stackoverflow.com/content/stackoverflow/img/apple-touch-icon.png" height="24" width="24"/> Stack overflow **tag**: [`android-simple-facebook`](http://stackoverflow.com/tags/android-simple-facebook/info)

## Releases
Library jar | Branch | Supported Facebook SDK
------------|--------|------------------------
[simple-facebook-2.1.jar](https://github.com/sromku/android-simple-facebook/releases/download/2.1/simple.facebook-2.1.jar) | [master](https://github.com/sromku/android-simple-facebook) | 3.14 > 
[simple-facebook-1.2.jar](https://github.com/sromku/android-simple-facebook/releases/download/1.2/simple.facebook.jar) | [v1.0](https://github.com/sromku/android-simple-facebook/tree/v1.0) | 3.0 - 3.8

## Sample App
The sample app includes examples for all actions. Check out this very short [wiki page](https://github.com/sromku/android-simple-facebook/wiki/Sample-App) of how to run and setup the sample app.

<img src="https://github.com/sromku/android-simple-facebook/wiki/images/sample-app.png" width="600"/>

## Setup/Configuration

- [Setup project](wiki/Setup-project)
- [Configuration](wiki/Configuration)

## Features
* **Login/Logout**
	- [Login](wiki/Login)
	- [Logout](wiki/Logout)

* **Publish**
	- [Publish feed](wiki/Publish-feed)
	- [Publish story](wiki/Publish-story)
	- [Publish album](wiki/Publish-album)
	- [Publish photo](wiki/Publish-photo)
	- [Publish video](wiki/Publish-video)
	- [Publish score](wiki/Publish-score)
    - [Publish comment](wiki/Publish-comment)
    - [Publish like](wiki/Publish-like)

* **Requests/Invite**
	- [Send request/invite](wiki/Send-request)
	- [Delete request/invite](wiki/Delete-request)

* **Get**
	- [Get accounts](wiki/Get-accounts)
	- [Get album/s](wiki/Get-albums)
	- [Get requests](wiki/Get-requests)
	- [Get books](wiki/Get-books)
	- [Get comment/s](wiki/Get-comments)
	- [Get events](wiki/Get-events)
	- [Get family](wiki/Get-family)
	- [Get friends](wiki/Get-friends)
	- [Get games](wiki/Get-games)
	- [Get groups](wiki/Get-groups)
	- [Get likes](wiki/Get-likes)
	- [Get movies](wiki/Get-movies)
	- [Get music](wiki/Get-music)
	- [Get notifications](wiki/Get-notifications)
	- [Get objects](wiki/Get-objects)
	- [Get page](wiki/Get-page)
	- [Get photos](wiki/Get-photos)
	- [Get posts](wiki/Get-posts)
	- [Get profile](wiki/Get-profile)
	- [Get scores](wiki/Get-scores)
	- [Get television](wiki/Get-television)
	- [Get videos](wiki/Get-videos)

* **Additional options**
	- [Pagination](wiki/Pagination)
	- [General 'GET'](wiki/General-get)
	- [Privacy settings](wiki/Privacy-settings)
	- [Configuration options](wiki/Configuration-options)
	- [Request new permissions](wiki/Request-new-permissions)
	- [Get granted persmissions](wiki/Get-granted-persmissions)
	- [Misc](wiki/Misc)
	- [Debug](wiki/Debug)

* **Samples**
  - [Setup sample app](wiki/Sample-App)

*And,*
* Based on latest Facebook SDK and Graph API v2.1
* Permission strings are predefined
* No need to use `LoginButton` view for being able to login/logout. You can use any `View`.
* No need to care for correct login with `READ` and `PUBLISH` permissions. Just mention the permissions you need and this library will care for the rest.
* Open graph support
* And much more

## Documentation
Everything is explained in **wiki**

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

