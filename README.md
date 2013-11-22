android-simple-facebook
=======================

Simple Facebook SDK for Android which wraps original [**Facebook SDK 3.5**](https://github.com/facebook/facebook-android-sdk)

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK was too complicated for simple actions like login, publish feeds and more, I decided to create simpler API for the same actions. I use this API in my applications and maintain the code.

Sample app:
<a href="https://play.google.com/store/apps/details?id=com.sromku.simple.fb.example">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>

## Features
* [Login](https://github.com/sromku/android-simple-facebook#login-1)
* [Logout](https://github.com/sromku/android-simple-facebook#logout-1)
* [Publish feed](https://github.com/sromku/android-simple-facebook#publish-feed)
* [Publish story (open graph)](https://github.com/sromku/android-simple-facebook#publish-story-open-graph)
* [Publish photo](https://github.com/sromku/android-simple-facebook#publish-photo)
* [Invite friend/s](https://github.com/sromku/android-simple-facebook#invite)
	* [Invite all friends](https://github.com/sromku/android-simple-facebook#all)
	* [Invite suggested friends](https://github.com/sromku/android-simple-facebook#suggested-friends)
	* [Invite one friend](https://github.com/sromku/android-simple-facebook#one-friend-only)
* [Get profile](https://github.com/sromku/android-simple-facebook#get-my-profile-1)
* [Get friends](https://github.com/sromku/android-simple-facebook#get-friends)
* [Get albums](https://github.com/sromku/android-simple-facebook#get-albums)
* [Get publish permissions](https://github.com/Gryzor/android-simple-facebook#request-publish)

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
mSimpleFacebook.login(onLoginListener);
```

### Logout

As login, just call it anywhere you need
``` java
mSimpleFacebook.logout(onLogoutListener);
```

### Invite friends

``` java
String[] friends = new String[]
{
	"630243197",
	"787878788",
	"751875181"
};

mSimpleFacebook.invite(friends, "Some free text", null);
```

### Get my profile

``` java
mSimpleFacebook.getProfile(new OnProfileRequestAdapter()
{
	@Override
	public void onComplete(Profile profile)
	{
		String id = profile.getId();
		String firstName = profile.getFirstName();
		String birthday = profile.getBirthday();
		String email = profile.getEmail();
		String bio = profile.getBio();
		// ... and many more properties of profile ...
	}
});

```

### More examples
More API actions is in the same simplicity. Just follow the explanation and examples below.

--------------------
## Setup Project
1. Clone [Facebook SDK 3.5](https://github.com/facebook/facebook-android-sdk) or [download](https://developers.facebook.com/android/) it. Then, import the project to your workspace.

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

## Configuration
Add next lines in your `Application` or `Activity` class. 

- Define and select permissions you need:

	``` java
	Permissions[] permissions = new Permissions[]
	{
		Permissions.USER_PHOTOS,
		Permissions.EMAIL,
		Permissions.PUBLISH_ACTION
	};
	``` 

- Build and define the configuration by putting `app_id`, `namespace` and `permissions`:

	``` java
	SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
		.setAppId("625994234086470")
		.setNamespace("sromkuapp")
		.setPermissions(permissions)
		.build();
	``` 	

- And, set this configuration: 

	``` java
	SimpleFacebook.setConfiguration(configuration);
	``` 
	There is no need to set the configuration in any activity, it should be done just once.

## Usage

#### 1. `onResume()`
In each `Activity` where you want to use the library, just override the `onResume()` and set `SimpleFacebook` instance:

``` java
@Override
public void onResume()
{
	super.onResume();
	mSimpleFacebook = SimpleFacebook.getInstance(this);
}
```

#### 2. Run the action (login, publish, invite,…)

* [Login](https://github.com/sromku/android-simple-facebook#login-1)
* [Logout](https://github.com/sromku/android-simple-facebook#logout-1)
* [Publish feed](https://github.com/sromku/android-simple-facebook#publish-feed)
* [Publish story (open graph)](https://github.com/sromku/android-simple-facebook#publish-story-open-graph)
* [Publish photo](https://github.com/sromku/android-simple-facebook#publish-photo)
* [Invite friend/s](https://github.com/sromku/android-simple-facebook#invite)
	* [Invite all friends](https://github.com/sromku/android-simple-facebook#all)
	* [Invite suggested friends](https://github.com/sromku/android-simple-facebook#suggested-friends)
	* [Invite one friend](https://github.com/sromku/android-simple-facebook#one-friend-only)
* [Get profile](https://github.com/sromku/android-simple-facebook#get-my-profile-1)
* [Get friends](https://github.com/sromku/android-simple-facebook#get-friends)
* [Get albums](https://github.com/sromku/android-simple-facebook#get-albums)
* [Get Publish Permissions](https://github.com/Gryzor/android-simple-facebook#request-publish)

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

Set `OnLoginOutListener` and call for `login(OnLoginOutListener)`

``` java
// login listener
OnLoginListener onLoginListener = new SimpleFacebook.OnLoginListener()
{

	@Override
	public void onFail(String reason)
	{
		Log.w(TAG, reason);
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
	public void onLogin()
	{
		// change the state of the button or do whatever you want
		Log.i(TAG, "Logged in");
	}
	
	@Override
	public void onNotAcceptingPermissions()
	{
		Log.w(TAG, "User didn't accept read permissions");
	}
	
};

// login
mSimpleFacebook.login(onLoginListener);
```

### Logout

Set `OnLogoutListener` and call for `logout(OnLogoutListener)` to disconnect from facebook.

``` java
// logout listener
OnLogoutListener onLogoutListener = new SimpleFacebook.OnLogoutListener()
{

	@Override
	public void onFail(String reason)
	{
		Log.w(TAG, reason);
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
		Log.i(TAG, "You are logged out");
	}
	
};

// logout
mSimpleFacebook.logout(onLogoutListener);
```

### Publish feed

Set `OnPublishListener` and call for `publish(Feed, OnPublishListener)`.

#### Basic properties

* `message` - The message of the user 
* `name` - The name of the link attachment
* `caption` - The caption of the link (appears beneath the link name)
* `description` - The description of the link (appears beneath the link caption)
* `picture` - The URL of a picture attached to this post. The picture must be at least 200px by 200px
* `link` - The link attached to this post

``` java
// create publish listener
OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
{

	@Override
	public void onFail(String reason)
	{
		// insure that you are logged in before publishing
		Log.w(TAG, reason);
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

#### Less basic properties

* `properties` - The key/value pairs which will appear in the stream attachment beneath the description
* `actions` - One action link which will appear next to the 'Comment' and 'Like' link under posts

``` java
// build feed
Feed feed = new Feed.Builder()
	.setMessage("Clone it out...")
	.setName("Simple Facebook SDK for Android")
	.setCaption("Code less, do the same.")
	.setDescription("Login, publish feeds and stories, invite friends and more...")
	.setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
	.setLink("https://github.com/sromku/android-simple-facebook")
	.addAction("Clone", "https://github.com/sromku/android-simple-facebook")
	.addProperty("Full documentation", "http://sromku.github.io/android-simple-facebook", "http://sromku.github.io/android-simple-facebook")
	.addProperty("Stars", "14")
	.build();
```

And, the **result** is:

<p align="center">
  <img src="https://raw.github.com/sromku/android-simple-facebook/master/Refs/publish_feed_advanced.png" alt="Published feed"/>
</p>

### Publish story (open graph)
*TBE (to be explained)*

### Publish photo

You can publish (upload) photo to default album or to any other album you have. <br>
`Photo` can be created from:<br>
- `Bitmap`
- `File`
- `byte[]`

#### App (default) album
Set `OnPublishListener` and call for `publish(Photo, OnPublishListener)`.

``` java
// create publish listener
OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
{

	@Override
	public void onFail(String reason)
	{
		// insure that you are logged in before publishing
		Log.w(TAG, reason);
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
	public void onComplete(String id)
	{
		Log.i(TAG, "Published successfully. id = " + id);
	}
};

// This is the image you want to upload
Bitmap bitmap = ...

// create Photo instace and add some properties
Photo photo = new Photo(bitmap);
photo.addDescription("Screenshot from #android_simple_facebook sample application");
photo.addPlace("110619208966868");

// publish photo to app album
mSimpleFacebook.publish(photo, onPublishListener);
```

#### Specific album

Set `OnPublishListener` and call for `publish(Photo, String, OnPublishListener)`. While the `String` is the album id.

``` java
String albumId = ...;

// publish photo to album
mSimpleFacebook.publish(photo, albumId, onPublishListener);
```

### Invite

Facebook supports three kind of dialogs for invite friends. One dialog shows all your friends and user can select who he/she
wants to invite. In other dialog you set suggested friends only and the last one, you set concrete one friend.

For all options, set `OnInviteListener`:

``` java
OnInviteListener onInviteListener = new SimpleFacebook.OnInviteListener()
{

	@Override
	public void onFail(String reason)
	{
		// insure that you are logged in before inviting
		Log.w(TAG, reason);
	}

	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}

	@Override
	public void onComplete(List<String> invitedFriends, String requestId)
	{
		Log.i(TAG, "Invitation was sent to " + invitedFriends.size() + " users with request id " + requestId); 
	}
	
	@Override
	public void onCancel()
	{
		Log.i(TAG, "Canceled the dialog");
	}
	
};
```

#### All
Show dialog with a list of all your friends. Call for `invite(Activity, String, OnInviteListener)`
The `String` is the message to be shown in the invitation. 
``` java
mSimpleFacebook.invite("I invite you to use this app", onInviteListener);
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
mSimpleFacebook.invite(friends, "I invite you to use this app", onInviteListener);
```

#### One friend only
Show dialog with only one friend to invite.

``` java
String friend = "630243197";
mSimpleFacebook.invite(friend, "I invite you to use this app", onInviteListener);
```

### Get my profile

Facebook doesn't reveal all user fields by default. For example, if you need picture, then you need to specify it in your graph api request. 
I can understand this, since getting all possible user fields will be time consuming task and this is not what we want.<br>
Thus, **two** options are possible to get profile data.

#### Default
By using this way, you can get many properties like: *id*, *name*, *education* and more. Just ensure to have needed permissions. Read the javadoc to know what is needed.
But, here you won't be able to get several properties like: *cover*, *picture* and other. 

Set `OnProfileRequestListener` and call for `getMyProfile(OnProfileRequestListener)`

``` java
OnProfileRequestListener onProfileRequestListener = new SimpleFacebook.OnProfileRequestListener()
{
	
	@Override
	public void onFail(String reason)
	{
		// insure that you are logged in before getting the profile
		Log.w(TAG, reason);
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
	public void onComplete(Profile profile)
	{
		Log.i(TAG, "My profile id = " + profile.getId());
	}
			
};

// do the get profile action
mSimpleFacebook.getProfile(onProfileRequestListener);
```

#### Be specific and get what you need
By using this option, you define the properties you need, and you will get only them. Here, any property is possible to get.
Set `OnProfileRequestListener` and call for `getMyProfile(Properties, OnProfileRequestListener)`

``` java
// prepare the properties that you need
Properties properties = new Properties.Builder()
	.add(Properties.ID)
	.add(Properties.FIRST_NAME)
	.add(Properties.COVER)
	.add(Properties.WORK)
	.add(Properties.EDUCATION)
	.add(Properties.PICTURE)
	.build();
				
// do the get profile action
mSimpleFacebook.getProfile(properties, onProfileRequestListener);
```

#### Be even more specific - Picture Attributes
You can describe the picture you really need like: *`small`, `normal`, `large`* and set width and height.

``` java
// prepare specific picture that we need
PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
pictureAttributes.setHeight(500);
pictureAttributes.setWidth(500);
pictureAttributes.setType(PictureType.SQUARE);

// prepare the properties that you need
Properties properties = new Properties.Builder()
	.add(Properties.ID)
	.add(Properties.FIRST_NAME)
	.add(Properties.PICTURE, pictureAttributes)
	.build();
				
// do the get profile action
mSimpleFacebook.getProfile(properties, onProfileRequestListener);
```


### Get friends

Set `OnFriendsRequestListener` and call for `getFriends(OnFriendsRequestListener)`

``` java
OnFriendsRequestListener onFriendsRequestListener = new SimpleFacebook.OnFriendsRequestListener()
{
	
	@Override
	public void onFail(String reason)
	{
		// insure that you are logged in before getting the friends
		Log.w(TAG, reason);
	}
			
	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}
			
	@Override
	public void onThinking()
	{
		// show progress bar or something to the user while fetching friends
		Log.i(TAG, "Thinking...");
	}
			
	@Override
	public void onComplete(List<Profile> friends)
	{
		Log.i(TAG, "Number of friends = " + friends.size());
	}
			
};

mSimpleFacebook.getFriends(onFriendsRequestListener);
```

### Get albums

Set `OnAlbumsRequestListener` and call for `getAlbums(OnAlbumsRequestListener)`

``` java
OnAlbumsRequestListener onAlbumsRequestListener = new SimpleFacebook.OnAlbumsRequestListener()
{
	
	@Override
	public void onFail(String reason)
	{
		// insure that you are logged in before getting the albums
		Log.w(TAG, reason);
	}
			
	@Override
	public void onException(Throwable throwable)
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}
			
	@Override
	public void onThinking()
	{
		// show progress bar or something to the user while fetching albums
		Log.i(TAG, "Thinking...");
	}
			
	@Override
	public void onComplete(List<Album> albums)
	{
		Log.i(TAG, "Number of albums = " + albums.size());
	}
			
};

mSimpleFacebook.getAlbums(onAlbumsRequestListener);
```

### Request publish

Use this method to request PUBLISH permissions, without having to perform any action yet. 
Useful if you need the new access token to pass to your Backend.

Set `OnPermissionListener`and call for `requestPublish(OnPermissionListener)`

```java
private OnPermissionListener mOnPermissionListener = new OnPermissionListener() {
	
	@Override
	public void onSuccess(final String accessToken) 
	{
		// the updated access token
		Log.i(TAG, accessToken);
	}
		
	@Override
	public void onNotAcceptingPermissions() 
	{
		Log.w(TAG, "User didn't accept publish permissions");
	}
	
	@Override
	public void onThinking() 
	{
		// show progress bar or something 
		Log.i(TAG, "Thinking...");
	}
	
	@Override
	public void onException(final Throwable throwable) 
	{
		Log.e(TAG, "Bad thing happened", throwable);
	}
	
	@Override
	public void onFail(final String reason) 
	{
		// insure that you are logged in before getting the albums
		Log.w(TAG, reason);
	}
};

mSimpleFacebook.requestPermission(mOnPermissionListener);
```


## More options

* `isLogin()` – Check if you are logged in
* `getAccessToken()` - Get current access token
* `clean()` - Clean all references like `Activity` to prevent memory leaks
* `Logger.DEBUG` or `DEBUG_WITH_STACKTRACE` - Print info and errors to logcat

## Sample application
<a href="https://play.google.com/store/apps/details?id=com.sromku.simple.fb.example">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

## Applications using the library

| [Besties](https://play.google.com/store/apps/details?id=com.besties) <br>
| [Pregnancy Tickers - Widget](https://play.google.com/store/apps/details?id=com.romkuapps.tickers) <br>
| [Pregnancy Calculator](https://play.google.com/store/apps/details?id=com.romkuapps.enfree.duedate) <br>
| [Ring Drop : Fun Ring Toss Game](https://play.google.com/store/apps/details?id=com.aitrich.ringdrop) <br>
| [שיחה מצחיקה - שינוי קול בקלות](https://play.google.com/store/apps/details?id=com.rami_bar.fun_call) <br>
| [8tracks Radio](https://play.google.com/store/apps/details?id=com.e8tracks) <br>

If you `use` this library in `your` project and you found it helpful, it will be really great to `share it here` :) 

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
    
[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/sromku/android-simple-facebook/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
