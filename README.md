android-simple-facebook
=======================

Simple Facebook API for Android which wraps original [**Facebook SDK 3.0**](https://github.com/facebook/facebook-android-sdk)

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK 3.0 was too complicated for simple actions like login, publish feeds and more, I decided to create simpler API for the same actions. I use this API in my applications and maintain the code.

## Features
* [Login](https://github.com/sromku/android-simple-facebook#login-1)
* [Logout](https://github.com/sromku/android-simple-facebook#logout-1)
* [Publish feed](https://github.com/sromku/android-simple-facebook#publish-feed)
* [Publish story (open graph)](https://github.com/sromku/android-simple-facebook#publish-story-open-graph)
* [Invite friend/s](https://github.com/sromku/android-simple-facebook#invite)
	* [Invite all friends](https://github.com/sromku/android-simple-facebook#all)
	* [Invite suggested friends](https://github.com/sromku/android-simple-facebook#suggested-friends)
	* [Invite one friend](https://github.com/sromku/android-simple-facebook#one-friend-only)
* [Get profile](https://github.com/sromku/android-simple-facebook#get-my-profile)

*And,*
* Based on latest Facebook SDK
* Permission strings are predefined
* No need to use `LoginButton` view for being able to login/logout. You can use any `View`.
* No need to care for correct login with `READ` and `PUBLISH` permissions. Just mention the permissions you need and this library will care for the rest.

## Few Examples
Just to give you the feeling, how simple it is. For all options and examples, follow the [**usage**](https://github.com/sromku/android-simple-facebook#usage) paragraph.

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
5. Update the `manifest.xml` of your application and add next lines:

	``` java
	<uses-permission android:name="android.permission.INTERNET" />

	<activity
		android:name="com.facebook.LoginActivity"
		android:label="@string/app_name"
		android:theme="@android:style/Theme.Translucent.NoTitleBar" />
	```

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
	.setAppId("625994234086470")
	.setNamespace("sromkuapp")
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
* [Login](https://github.com/sromku/android-simple-facebook#login-1)
* [Logout](https://github.com/sromku/android-simple-facebook#logout-1)
* [Publish feed](https://github.com/sromku/android-simple-facebook#publish-feed)
* [Publish story (open graph)](https://github.com/sromku/android-simple-facebook#publish-story-open-graph)
* [Invite friend/s](https://github.com/sromku/android-simple-facebook#invite)
	* [Invite all friends](https://github.com/sromku/android-simple-facebook#all)
	* [Invite suggested friends](https://github.com/sromku/android-simple-facebook#suggested-friends)
	* [Invite one friend](https://github.com/sromku/android-simple-facebook#one-friend-only)
* [Get profile](https://github.com/sromku/android-simple-facebook#get-my-profile)

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

### Login

Set `OnLoginOutListener` and call for `login(Activity)`

``` java
// login / logout listener
OnLoginOutListener onLoginOutListener = new SimpleFacebook.OnLoginOutListener()
{

	@Override
	public void onFail()
	{
		Log.w(TAG, "Failed to login");
	}

	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}

	@Override
	public void onThinking()
	{
		// show progress bar or something to the user while login is happening
		Log.i(TAG, "In progress");
	}

	@Override
	public void onLogout()
	{
		// change the state of the button or do whatever you want
		Log.i(TAG, "Logged out");
	}

	@Override
	public void onLogin()
	{
		// change the state of the button or do whatever you want
		Log.i(TAG, "Logged in");
	}
};

// login
mSimpleFacebook.setLogInOutListener(onLoginOutListener);
mSimpleFacebook.login(MainActivity.this);
```

### Logout

Same `OnLoginOutListener` that you defined for login action will trigger the `onLogout()` callback method while doing the logout action. 
Call `logout()` to disconnect from facebook

``` java
mSimpleFacebook.logout();
```

### Publish feed

Set `OnPublishListener` and call for `publish(Feed, OnPublishListener)`.
You can also publish without setting the listener by calling for `publish(Feed)` method.

``` java
// create publish listener
OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
{

	@Override
	public void onFail()
	{
		// insure that you are logged in before publishing
		Log.w(TAG, "Failed to publish");
	}

	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}

	@Override
	public void onThinking()
	{
		// show progress bar or something to the user while publishing
		Log.i(TAG, "In progress");
	}

	@Override
	public void onComplete(String postId)
	{
		Log.i(TAG, "Published successfully. The new post id = " + postId);
	}
};

// build feed
Feed feed = new Feed.Builder()
	.setMessage("Clone it out...")
	.setName("Simple Facebook for Android")
	.setCaption("Code less, do the same.")
	.setDescription("The Simple Facebook library project makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more.")
	.setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
	.setLink("https://github.com/sromku/android-simple-facebook")
	.build();

// publish the feed
mSimpleFacebook.publish(feed, onPublishListener);
```

And, the **result** is:

<p align="center">
  <img src="https://raw.github.com/sromku/android-simple-facebook/master/Refs/publish_feed.png" alt="Published feed"/>
</p>

### Publish story (open graph)
*TBE (to be explained)*

### Invite

Facebook supports three kind of dialogs for invite friends. One dialog shows all your friends and user can select who he/she
wants to invite. In other dialog you set suggested friends only and the last one, you set concrete one friend.

For all options, set `OnInviteListener`:

``` java
OnInviteListener onInviteListener = new SimpleFacebook.OnInviteListener()
{

	@Override
	public void onFail()
	{
		// insure that you are logged in before inviting
		Log.w(TAG, "Failed to invite");
	}

	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}

	@Override
	public void onComplete()
	{
		Log.i(TAG, "Invitation was sent");
	}
};
```

#### All
Show dialog with a list of all your friends. Call for `invite(Activity, String, OnInviteListener)`
The `String` is the message to be shown in the invintation. 
``` java
mSimpleFacebook.invite(MainActivity.this, "I invite you to use this app", onInviteListener);
```

#### Suggested friends
Show dialog with a list of suggested friends. Set array of user ids.

``` java
String[] friends = new String[]
{
	"630243197",
	"584419361",
	"1456233371",
	"100000490891462"
};
mSimpleFacebook.invite(MainActivity.this, friends, "I invite you to use this app", onInviteListener);
```

#### One friend only
Show dialog with only one friend to invite.

``` java
String friend = "630243197";
mSimpleFacebook.invite(MainActivity.this, friend, "I invite you to use this app", onInviteListener);
```

### Get my profile

Set `OnProfileRequestListener` and call for `getMyProfile(OnProfileRequestListener)`

``` java
OnProfileRequestListener onProfileRequestListener = new SimpleFacebook.OnProfileRequestListener()
{
	
	@Override
	public void onFail()
	{
		// insure that you are logged in before getting the profile
		Log.w(TAG, "Failed to get profile");
	}
			
	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}
			
	@Override
	public void onThinking()
	{
		// show progress bar or something to the user while fetching profile
		Log.i(TAG, "Thinking...");
	}
			
	@Override
	public void onComplete(String userId)
	{
		Log.i(TAG, "My profile id = " + userId);
	}
			
};

mSimpleFacebook.getMyProfile(onProfileRequestListener);
```

## More options

* `isLogin()` – check if you are logged in

## Sample Application
*TBE (to be explained)*

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
