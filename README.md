android-simple-facebook
=======================

Simple Facebook API for Android which wraps original [**Facebook SDK 3.0**](https://github.com/facebook/facebook-android-sdk)

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK 3.0 was too complicated for simple actions like login, publish feeds and more, I decided to create simpler API for the same actions. I use this API in my applications and maintain the code.

## Features
* [Login](https://github.com/sromku/android-simple-facebook/edit/master/README.md#login-1)
* [Logout](https://github.com/sromku/android-simple-facebook/edit/master/README.md#logout-1)
* [Publish feed](https://github.com/sromku/android-simple-facebook/edit/master/README.md#publish-feed)
* [Publish story](https://github.com/sromku/android-simple-facebook/edit/master/README.md#publish-story-open-graph)
* [Invite friend/s](https://github.com/sromku/android-simple-facebook/edit/master/README.md#invite)
* [Get profile](https://github.com/sromku/android-simple-facebook/edit/master/README.md#get-my-profile)

*And,*
* Based on latest Facebook SDK
* Permission strings are predefined
* No need to use `LoginButton` view for being able to login/logout. You can use any `View`.
* No need to care for correct login with `READ` and `PUBLISH` permissions. Just mention the permissions you need and this library will care for the rest.

## Few Examples
Just to give you the feeling, how simple it is. For all options and examples, follow the [**usage**](https://github.com/sromku/android-simple-facebook/edit/master/README.md#usage) paragraph.

### Login
You can call `login(Activity)` method on click of any `View` and you don't need to use `LoginButton`

``` java
mSimpleFacebook.login(MainActivity.this);
```

### Logout

As login, just call it anywhere you need
``` java
mSimpleFacebook.logout();
```

### Invite friends

``` java
String[] friends = new String[]
		{
			"630243197",
			"787878788",
			"751875181"
		};

mSimpleFacebook.invite(MainActivity.this, friends, "Some free text", null);
```

### More examples
More API actions is in the same simplicity. Just follow the explanation and examples below.


## Setup Project
1. Clone [Facebook SDK 3.0](https://github.com/facebook/facebook-android-sdk) or [download](https://developers.facebook.com/android/) it. Then, import the project to your workspace.
2. Clone and import this (Simple Facebook) project to your workspace.
3. Add reference from `Simple Facebook` project to `FacebookSDK` project.
    ![Screenshot](https://raw.github.com/sromku/android-simple-facebook/master/Refs/reference_to_sdk.png)
4. Now, you can add reference from **your app** to `Simple Facebook` project.


## Usage

Just add next lines in your `Application` class or any other place (like `Activity`) that has `Context` instance.

#### 1.	Define and select permissions you need:

``` java
Permissions[] permissions = new Permissions[]
		{
			Permissions.USER_PHOTOS,
			Permissions.FRIENDS_PHOTOS,
			Permissions.PUBLISH_ACTION
		};
``` 

#### 2.	Build and define the configuration by putting `app_id`, `namespace` and `permissions`:

``` java
SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
			.setAppId("234555990008855")
			.setNamespace("appnamespace")
			.setPermissions(permissions)
			.build();
``` 	

#### 3.	And, set this configuration: 

``` java
SimpleFacebook simpleFacebook = SimpleFacebook.getInstance(getApplicationContext());
simpleFacebook.setConfiguration(configuration);
``` 

### Run the action (login, publish, invite,…)

#### 1.	In your `Activity` create instance of `SimpleFacebook`

``` java
SimpleFacebook mSimpleFacebook = SimpleFacebook.getInstance(getApplicationContext());
```
#### 2.	Do an action like:
* [Login](https://github.com/sromku/android-simple-facebook/edit/master/README.md#login-1)
* [Logout](https://github.com/sromku/android-simple-facebook/edit/master/README.md#logout-1)
* [Publish feed](https://github.com/sromku/android-simple-facebook/edit/master/README.md#publish-feed)
* [Publish story](https://github.com/sromku/android-simple-facebook/edit/master/README.md#publish-story-open-graph)
* [Invite friend/s](https://github.com/sromku/android-simple-facebook/edit/master/README.md#invite)
* [Get profile](https://github.com/sromku/android-simple-facebook/edit/master/README.md#get-my-profile)

#### 3.	Override `onActivityResult` method and add this line:
``` java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
	mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data); 
	super.onActivityResult(requestCode, resultCode, data);
} 
```

## Actions (Examples)
..soon will be committed

### Login

### Logout

### Publish feed

### Publish story (open graph)

### Invite

#### All
#### Suggested friends
#### One friend only

### Get my profile

## More options

* `isLogin()` – check if you are logged in

## Sample Application
..soon will be committed

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
