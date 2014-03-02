android-simple-facebook
=======================

Simple Facebook SDK for Android which wraps original [**Facebook SDK 3.7**](https://github.com/facebook/facebook-android-sdk)

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK was too complicated for simple actions like login, I decided to create simpler API for the same actions. I use this API in my applications and maintain the code.

Sample app:<br> 
<a href="https://play.google.com/store/apps/details?id=com.sromku.simple.fb.example">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>

## Features
* [Login](#login-1)
* [Logout](#logout-1)
* [Publish](#publish-feed)
	* [Feed](#publish-feed)
	* [Story (open graph)](#publish-story-open-graph)
	* [Photo](#publish-photo)
	* [Video](#publish-video)
* [Invite friend/s](#invite)
	* [Invite all friends](#all)
	* [Invite suggested friends](#suggested-friends)
	* [Invite one friend](#one-friend-only)
* [Get](#get-my-profile-1)
	* [Profile](#get-my-profile-1)
	* [Friends](#get-friends)
	* [Albums](#get-albums)
	* [Checkins](#get-checkins)
	* [Comments](#get-comments)
	* [Events](#get-events)
	* [Groups](#get-groups)
	* [Likes](#get-likes)
	* [Photos](#get-photos)
	* [Posts](#get-posts)
	* [Scores](#get-scores)
	* [Videos](#get-videos)

*And,*
* Based on latest Facebook SDK
* Permission strings are predefined
* No need to use `LoginButton` view for being able to login/logout. You can use any `View`.
* No need to care for correct login with `READ` and `PUBLISH` permissions. Just mention the permissions you need and this library will care for the rest.

## Few Examples
Just to give you the feeling, how simple it is. For all options and examples, follow the [**usage**](#usage) paragraph.

### Login

You can login by clicking on any `View` and you don't need to use `LoginButton`.

``` java
mSimpleFacebook.login(onLoginListener);
```

### Logout

As login, just call it anywhere you need.

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
1. Clone [Facebook SDK 3.7](https://github.com/facebook/facebook-android-sdk) or [download](https://developers.facebook.com/android/) it. Then, import the project to your workspace.

2. Clone and import this (Simple Facebook) project to your workspace.
 
3. Add reference from `Simple Facebook` project to `FacebookSDK` project.

    ![Screenshot](https://raw.github.com/sromku/android-simple-facebook/master/Refs/reference_to_sdk.png)
    
4. Now, you can add reference from **your app** to `Simple Facebook` project.
5. Add to your `string.xml` your app_id:

	``` xml
	<string name="app_id">625994234086470</string>
	```

6. Update the `manifest.xml` of your application and add next lines:

	``` xml
	<uses-permission android:name="android.permission.INTERNET" />

	<activity
		android:name="com.facebook.LoginActivity"
		android:label="@string/app_name"
		android:theme="@android:style/Theme.Translucent.NoTitleBar" />

	<meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="@string/app_id" />
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

* [Login](#login-1)
* [Logout](#logout-1)
* [Publish](#publish-feed)
	* [Feed](#publish-feed)
	* [Story (open graph)](#publish-story-open-graph)
	* [Photo](#publish-photo)
	* [Video](#publish-video)
* [Invite friend/s](#invite)
	* [Invite all friends](#all)
	* [Invite suggested friends](#suggested-friends)
	* [Invite one friend](#one-friend-only)
* [Get](#get-my-profile-1)
	* [Profile](#get-my-profile-1)
	* [Friends](#get-friends)
	* [Albums](#get-albums)
	* [Checkins](#get-checkins)
	* [Comments](#get-comments)
	* [Events](#get-events)
	* [Groups](#get-groups)
	* [Likes](#get-likes)
	* [Photos](#get-photos)
	* [Posts](#get-posts)
	* [Scores](#get-scores)
	* [Videos](#get-videos)

#### 3.	Override `onActivityResult` method and add this line:
``` java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
	mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data); 
	super.onActivityResult(requestCode, resultCode, data);
} 
```

## Actions

### Login

Initialize callback listener:
``` java
OnLoginListener onLoginListener = new SimpleFacebook.OnLoginListener()
{
	@Override
	public void onLogin()
	{
		// change the state of the button or do whatever you want
		Log.i(TAG, "Logged in");
	}
	
	@Override
	public void onNotAcceptingPermissions(Permission.Type type)
	{
		// user didn't accept READ or WRITE permission
		Log.w(TAG, String.format("You didn't accept %s permissions", type.name()));
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */	
};
```

Login:
``` java
mSimpleFacebook.login(onLoginListener);
```

### Logout

Initialize callback listener:
``` java
// logout listener
OnLogoutListener onLogoutListener = new SimpleFacebook.OnLogoutListener()
{
	@Override
	public void onLogout()
	{
		Log.i(TAG, "You are logged out");
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */	
};
```

Logout:
``` java
mSimpleFacebook.logout(onLogoutListener);
```

### Publish feed

Set `OnPublishListener` and call for:
- `publish(Feed, OnPublishListener)` without dialog. 
- `publish(Feed, true, OnPublishListener)` with dialog.

#### Basic properties

* `message` - The message of the user 
* `name` - The name of the link attachment
* `caption` - The caption of the link (appears beneath the link name)
* `description` - The description of the link (appears beneath the link caption)
* `picture` - The URL of a picture attached to this post. The picture must be at least 200px by 200px
* `link` - The link attached to this post

Initialize callback listener:
``` java
OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
{
	@Override
	public void onComplete(String postId)
	{
		Log.i(TAG, "Published successfully. The new post id = " + postId);
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */
};
```

Build feed:
``` java
Feed feed = new Feed.Builder()
	.setMessage("Clone it out...")
	.setName("Simple Facebook for Android")
	.setCaption("Code less, do the same.")
	.setDescription("The Simple Facebook library project makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more.")
	.setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
	.setLink("https://github.com/sromku/android-simple-facebook")
	.build();
```

Publish feed **without** dialog:
``` java
mSimpleFacebook.publish(feed, onPublishListener);
```

Publish feed **with** dialog:
``` java
mSimpleFacebook.publish(feed, true, onPublishListener);
```

And, the **result** is:

<p align="center">
  <img src="https://raw.github.com/sromku/android-simple-facebook/master/Refs/publish_feed.png" alt="Published feed"/>
</p>

#### Less basic properties

* `properties` - The key/value pairs which will appear in the stream attachment beneath the description
* `actions` - One action link which will appear next to the 'Comment' and 'Like' link under posts

Build feed:
``` java
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

Initialize callback listener:
``` java
OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
{
	@Override
	public void onComplete(String id)
	{
		Log.i(TAG, "Published successfully. id = " + id);
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */
};
```

Build photo for publishing:
``` java
// This is the image you want to upload
Bitmap bitmap = ...

// create Photo instance and add some properties
Photo photo = new Photo(bitmap);
photo.addDescription("Screenshot from #android_simple_facebook sample application");
photo.addPlace("110619208966868");
```

Publish photo to **application** (default) album:
``` java
mSimpleFacebook.publish(photo, onPublishListener);
```

Publish photo to **specific** album:
``` java
String albumId = ...;

// publish photo to album
mSimpleFacebook.publish(photo, albumId, onPublishListener);
```

### Publish video

You can publish (upload) a video only to the default "Videos" album. <br>
`Video` can be created from:<br>
- `File`
- `byte[]`

Initialize callback listener:
``` java
OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
{
	@Override
	public void onComplete(String id)
	{
		Log.i(TAG, "Published successfully. id = " + id);
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */
};
```

Build video for publishing:
``` java
File videoFile = ...

// create a Video instance and add some properties
Video video = new Video(videoFile);
video.addTitle("A video");
video.addDescription("Video from #android_simple_facebook sample application");
```

Publish video to "Videos" album
``` java
mSimpleFacebook.publish(video, onPublishListener);
```

### Invite

Facebook supports three kind of dialogs for invite friends. One dialog shows all your friends and user can select who he/she
wants to invite. In other dialog you set suggested friends only and the last one, you set concrete one friend.

For all options, set `OnInviteListener`:

``` java
OnInviteListener onInviteListener = new SimpleFacebook.OnInviteListener()
{
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

	/* 
	 * You can override other methods here: 
	 * onFail(String reason), onException(Throwable throwable)
	 */
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

Initialize callback listener:
``` java
OnProfileListener onProfileListener = new SimpleFacebook.OnProfileListener()
{			
	@Override
	public void onComplete(Profile profile)
	{
		Log.i(TAG, "My profile id = " + profile.getId());
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get the profile:
``` java
mSimpleFacebook.getProfile(onProfileListener);
```

#### Be specific and get what you need
By using this option, you define the properties you need, and you will get only them. Here, any property is possible to get.

Prepare the properties that you need:
``` java
Properties properties = new Properties.Builder()
	.add(Properties.ID)
	.add(Properties.FIRST_NAME)
	.add(Properties.COVER)
	.add(Properties.WORK)
	.add(Properties.EDUCATION)
	.add(Properties.PICTURE)
	.build();
``` 

Get the profile:
``` java			
mSimpleFacebook.getProfile(properties, onProfileListener);
```

#### Be even more specific - Picture Attributes
You can describe the picture you really need like: *`small`, `normal`, `large`, `square`* and set width and height.

Prepare specific picture that you need:
``` java
PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
pictureAttributes.setHeight(500);
pictureAttributes.setWidth(500);
pictureAttributes.setType(PictureType.SQUARE);
```

Prepare the properties that you need:
``` java
Properties properties = new Properties.Builder()
	.add(Properties.ID)
	.add(Properties.FIRST_NAME)
	.add(Properties.PICTURE, pictureAttributes)
	.build();			
```

Get the profile:
``` java
mSimpleFacebook.getProfile(properties, onProfileListener);
```


### Get friends

Initialize callback listener:
``` java
OnFriendsListener onFriendsListener = new SimpleFacebook.OnFriendsListener()
{			
	@Override
	public void onComplete(List<Profile> friends)
	{
		Log.i(TAG, "Number of friends = " + friends.size());
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */			
};
```

Get the friends:
``` java
mSimpleFacebook.getFriends(onFriendsListener);
```

### Get albums

Initialize callback listener:
``` java
OnAlbumsListener onAlbumsListener = new SimpleFacebook.OnAlbumsListener()
{			
	@Override
	public void onComplete(List<Album> albums)
	{
		Log.i(TAG, "Number of albums = " + albums.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my albums:
``` java
mSimpleFacebook.getAlbums(onAlbumsListener);
```

Get album of next entities by setting id:
- `Profile`
- `Page`
``` java
String entityId = ...;
mSimpleFacebook.getAlbums(entityId, onAlbumsListener);
```

### Get checkins
Set `OnCheckinsListener` and call for:
- `getCheckins(OnCheckinsListener)` - get your checkins
- `getCheckins(String, OnCheckinsListener)` - get checkins of any:
	- `Profile`
	- `Page`

``` java
OnCheckinsListener onCheckinsListener = new SimpleFacebook.OnCheckinsListener()
{			
	@Override
	public void onComplete(List<Checkin> checkins)
	{
		Log.i(TAG, "Number of checkins = " + checkins.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};

// get my checkins
mSimpleFacebook.getCheckins(onCheckinsListener);
```

### Get comments
Set `OnCommentsListener` and call for:
- `getComments(String, OnCommentsListener)` - get comments of any:
	- `Album`
	- `Checkin`
	- `Comment`
	- `Photo`
	- `Post`
	- `Video`

``` java
OnCommentsListener onCommentsListener = new SimpleFacebook.OnCommentsListener()
{			
	@Override
	public void onComplete(List<Comment> comments)
	{
		Log.i(TAG, "Number of comments = " + comments.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};

// get comments of entity
mSimpleFacebook.getComments(onCommentsListener);
```

### Get events
Set `EventDecision` and `OnEventsListener` and call for:
- `getEvents(EventDecision, OnEventsListener)` - get my events
- `getEvents(String, EventDecision, OnEventsListener)` - get events of any:
	- `Profile`
	- `Page`
	- `Group`

``` java
OnEventsListener onEventsListener = new SimpleFacebook.OnEventsListener()
{			
	@Override
	public void onComplete(List<Event> events)
	{
		Log.i(TAG, "Number of events = " + events.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};

// get my events that I replied as 'attending'
mSimpleFacebook.getEvents(EventDecision.ATTENDING, onEventsListener);
```

### Get groups
Set `OnGroupsListener` and call for:
- `getGroups(OnGroupsListener)` - get my groups
- `getGroups(String, OnGroupsListener)` - get groups of any:
	- `Profile`

``` java
OnGroupsListener onGroupsListener = new SimpleFacebook.OnGroupsListener()
{			
	@Override
	public void onComplete(List<Group> groups)
	{
		Log.i(TAG, "Number of groups = " + groups.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};

// get my groups 
mSimpleFacebook.getGroups(onGroupsListener);
```

### Get likes
Set `OnLikesListener` and call for:
- `getLikes(String, OnLikesListener)` - get likes of any:
	- `Album`
	- `Checkin`
	- `Comment`
	- `Photo`
	- `Post`
	- `Video`

Initialize callback listener: 
``` java
OnLikesListener onLikesListener = new SimpleFacebook.OnLikesListener()
{			
	@Override
	public void onComplete(List<Like> likes)
	{
		Log.i(TAG, "Number of likes = " + likes.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get likes of entity
``` java
String entityId = ...;
mSimpleFacebook.getLikes(entityId, onLikesListener);
```

### Get photos

### Get posts

### Get scores

### Get videos

### Adittional options

#### Set privacy settings of a single post
You can set the privacy settings of a single post (set who can / can't see the post). <br/>
Currently supported in:<br/>
- `Feed`
- `Photo`
- `Video`

``` java
// This is the object you want to set privacy of and the publish listener
Photo photo = ...
OnPublishListener onPublishListener = ...
```

**Predefined privacy settings**<br>
You can use one of the following predefined privacy settings: <br/>
- EVERYONE, 
- ALL_FRIENDS, 
- FRIENDS_OF_FRIENDS, 
- SELF

``` java
Privacy privacy = new Privacy(Privacy.PrivacySettings.EVERYONE);

photo.addPrivacy(privacy);

// Publish photo
mSimpleFacebook.publish(photo, onPublishListener);
```

**Custom privacy settings**<br>
``` java
Privacy privacy = new Privacy(Privacy.PrivacySettings.CUSTOM);

// All available options: 
privacy.addAllowedUserOrListID({user ID or friend list ID that "can" see the post});
privacy.addAllowedUserOrListIDs({mixed user IDs and friend list IDs that "can" see the post});
privacy.addDeniedUserOrListID({user ID or friend list ID that "cannot" see the post});
privacy.addDeniedUserOrListIDs({mixed user IDs and friend list IDs that "cannot" see the post});

// List ID can also be "ALL_FRIENDS" or "FRIENDS_OF_FRIENDS" (only) to include all members of those sets:
privacy.addAllowedUserOrListID(Privacy.PrivacySettings.ALL_FRIENDS);

photo.addPrivacy(privacy);

// Publish photo
mSimpleFacebook.publish(photo, onPublishListener);
```

#### Request publish

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

### More options

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
| [Gelatto](https://play.google.com/store/apps/details?id=com.doit.gelatto) <br>
| [Pony Racing](https://play.google.com/store/apps/details?id=com.tiarsoft.ponyrace) <br>
| [Violet Glasses](https://play.google.com/store/apps/developer?id=Violet+Glasses) <br>


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
