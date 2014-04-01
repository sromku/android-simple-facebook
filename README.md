android-simple-facebook
=======================

Simple Facebook SDK for Android which wraps original [**Facebook SDK 3.8**](https://github.com/facebook/facebook-android-sdk)

This is a library project which makes the life much easier by coding less code for being able to login, publish feeds and open graph stories, invite friends and more. 

Since my feeling was that the usage of Facebook SDK was too complicated for simple actions like login, I decided to create simpler API for the same actions. I use this API in my applications and maintain the code.

We have new <img src="http://stackoverflow.com/content/stackoverflow/img/apple-touch-icon.png" height="24" width="24"/> Stack overflow **tag**: [`android-simple-facebook`](http://stackoverflow.com/tags/android-simple-facebook/info)

Sample app:<br> 
<a href="https://play.google.com/store/apps/details?id=com.sromku.simple.fb.example">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>

## Features
* [Login](#login)
* [Logout](#logout)
* [Publish](#publish-feed)
	* [Feed](#publish-feed)
	* [Photo](#publish-photo)
	* [Video](#publish-video)
	* [Score](#publish-score)
* [Invite friend/s](#invite)
	* [Invite all friends](#all)
	* [Invite suggested friends](#suggested-friends)
	* [Invite one friend](#one-friend-only)
	* [Delete invite/request](#delete-requestinvite)
* [Get](#get-my-profile)
	* [Profile](#get-my-profile)
	* [Friends](#get-friends)
	* [Albums](#get-albums)
	* [Checkins](#get-checkins)
	* [Comments](#get-comments)
	* [Events](#get-events)
	* [Groups](#get-groups)
	* [Likes](#get-likes)
	* [Page](#get-page)
	* [Photos](#get-photos)
	* [Posts](#get-posts)
	* [Scores](#get-scores)
	* [Videos](#get-videos)
* [Additional options](#additional-options)
	* [Pagination](#pagination)
	* [General 'GET'](#general-get)
	* [Set privacy settings of a single post](#set-privacy-settings-of-a-single-post)
	* [Configuration options](#configuration-options)
	* [Permissions](#permissions)
		* [Request new permissions](#request-new-permissions)
		* [Granted persmissions](#granted-persmissions)
	* [Open Graph](#open-graph)
	* [Misc](#misc)
	* [Debug](#debug)

*And,*
* Based on latest Facebook SDK
* Permission strings are predefined
* No need to use `LoginButton` view for being able to login/logout. You can use any `View`.
* No need to care for correct login with `READ` and `PUBLISH` permissions. Just mention the permissions you need and this library will care for the rest.


## Setup Project
1. Clone [Facebook SDK 3.8](https://github.com/facebook/facebook-android-sdk) or [download](https://developers.facebook.com/android/) it. Then, import the project to your workspace.

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
	Permission[] permissions = new Permission[] {
		Permission.USER_PHOTOS,
		Permission.EMAIL,
		Permission.PUBLISH_ACTION
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
public void onResume() {
	super.onResume();
	mSimpleFacebook = SimpleFacebook.getInstance(this);
}
```

#### 2. Run the [`action`](#actions) (login, publish, invite, get something …)

#### 3.	Override `onActivityResult` method and add this line:
``` java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data); 
	super.onActivityResult(requestCode, resultCode, data);
} 
```

## Actions

### Login

Initialize callback listener:
``` java
OnLoginListener onLoginListener = new OnLoginListener() {
	@Override
	public void onLogin() {
		// change the state of the button or do whatever you want
		Log.i(TAG, "Logged in");
	}
	
	@Override
	public void onNotAcceptingPermissions(Permission.Type type) {
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
OnLogoutListener onLogoutListener = new OnLogoutListener() {
	@Override
	public void onLogout() {
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
OnPublishListener onPublishListener = new OnPublishListener() {
	@Override
	public void onComplete(String postId) {
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

### Publish photo

You can publish (upload) photo to default album or to any other album you have. <br>
`Photo` can be created from:<br>
- `Bitmap`
- `File`
- `byte[]`

Initialize callback listener:
``` java
OnPublishListener onPublishListener = new OnPublishListener() {
	@Override
	public void onComplete(String id) {
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
Photo photo = new Photo.Builder()
	.setImage(bitmap)
	.setName("Screenshot from #android_simple_facebook sample application")
	.setPlace("110619208966868")
	.build();
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
OnPublishListener onPublishListener = new OnPublishListener() {
	@Override
	public void onComplete(String id) {
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
Video video = new Video.Builder()
	.setVideo(videoFile)
	.setDescription("Video from #android_simple_facebook sample application")
	.setName("Cool video")
	.build();
```

Publish video to "Videos" album
``` java
mSimpleFacebook.publish(video, onPublishListener);
```

### Publish score

Initialize callback listener:
``` java
OnPublishListener onPublishListener = new OnPublishListener() {
	@Override
	public void onComplete(String postId) {
		Log.i(TAG, "Published successfully");
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */
};
```

Build score:
``` java
Score score = new Score.Builder()
	.setScore(25)
	.build();
```

Publish score:
``` java
mSimpleFacebook.publish(score, onPublishListener);
```

### Invite

Facebook supports three kind of dialogs for invite friends. One dialog shows all your friends and user can select who he/she
wants to invite. In other dialog you set suggested friends only and the last one, you set concrete one friend.

For all options, set `OnInviteListener`:

``` java
OnInviteListener onInviteListener = new OnInviteListener() {
	@Override
	public void onComplete(List<String> invitedFriends, String requestId) {
		Log.i(TAG, "Invitation was sent to " + invitedFriends.size() + " users with request id " + requestId); 
	}
	
	@Override
	public void onCancel() {
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
String[] friends = new String[] {
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

#### Delete request/invite:

Initialize callback listener:
``` java
OnDeleteListener onDeleteListener = new OnDeleteListener() {
	@Override
	public void onComplete(Void response) {
		Log.i(TAG, "Deleted successfully");
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */
};
```

Delete request:
``` java
String requestId = ...;
mSimpleFacebook.deleteRequest(requestId, onDeleteListener);
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
OnProfileListener onProfileListener = new OnProfileListener() {			
	@Override
	public void onComplete(Profile profile) {
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
Profile.Properties properties = new Profile.Properties.Builder()
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
Profile.Properties properties = new Profile.Properties.Builder()
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
OnFriendsListener onFriendsListener = new OnFriendsListener() {			
	@Override
	public void onComplete(List<Profile> friends) {
		Log.i(TAG, "Number of friends = " + friends.size());
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */			
};
```

Get my friends:
``` java
mSimpleFacebook.getFriends(onFriendsListener);
```

### Get albums

Initialize callback listener:
``` java
OnAlbumsListener onAlbumsListener = new OnAlbumsListener() {			
	@Override
	public void onComplete(List<Album> albums) {
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

Get album of next entities by passing entity id:
- `Profile`
- `Page`
``` java
String entityId = ...;
mSimpleFacebook.getAlbums(entityId, onAlbumsListener);
```

### Get checkins

Initialize callback listener:
``` java
OnCheckinsListener onCheckinsListener = new OnCheckinsListener() {			
	@Override
	public void onComplete(List<Checkin> checkins) {
		Log.i(TAG, "Number of checkins = " + checkins.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my checkins:
``` java
mSimpleFacebook.getCheckins(onCheckinsListener);
```

Get checkins of next entities by passing entity id:
- `Profile`
- `Page`
``` java
String entityId = ...;
mSimpleFacebook.getCheckins(entityId, onCheckinsListener);
```

### Get comments

Initialize callback listener:
``` java
OnCommentsListener onCommentsListener = new OnCommentsListener() {			
	@Override
	public void onComplete(List<Comment> comments) {
		Log.i(TAG, "Number of comments = " + comments.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get comments of next entities by passing entity id:
- `Album`
- `Checkin`
- `Comment`
- `Photo`
- `Post`
- `Video`
``` java
String entityId = ...;
mSimpleFacebook.getComments(entityId, onCommentsListener);
```

### Get events

Initialize callback listener:
``` java
OnEventsListener onEventsListener = new OnEventsListener() {			
	@Override
	public void onComplete(List<Event> events) {
		Log.i(TAG, "Number of events = " + events.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my events that I replied as 'attending':
``` java
mSimpleFacebook.getEvents(EventDecision.ATTENDING, onEventsListener);
```

Get attending events of next entities by passing entity id:
- `Profile`
- `Page`
- `Group`
``` java
String entityId = ...;
mSimpleFacebook.getEvents(entityId, EventDecision.ATTENDING, onEventsListener);
```

### Get groups

Initialize callback listener:
``` java
OnGroupsListener onGroupsListener = new OnGroupsListener() {			
	@Override
	public void onComplete(List<Group> groups) {
		Log.i(TAG, "Number of groups = " + groups.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my groups:
``` java
mSimpleFacebook.getGroups(onGroupsListener);
```

Get groups of next entities by passing entity id:
- `Profile`
``` java
String entityId = ...;
mSimpleFacebook.getGroups(entityId, onGroupsListener);
```

### Get likes

Initialize callback listener:
``` java
OnLikesListener onLikesListener = new OnLikesListener() {			
	@Override
	public void onComplete(List<Like> likes) {
		Log.i(TAG, "Number of likes = " + likes.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get likes of next entities by passing entity id:
- `Album`
- `Checkin`
- `Comment`
- `Photo`
- `Post`
- `Video`
``` java
String entityId = ...;
mSimpleFacebook.getLikes(entityId, onLikesListener);
```

### Get page

**Two** options are possible to get page data.

#### Default

Initialize callback listener:
``` java
OnPageListener onPageListener = new OnPageListener() {			
	@Override
	public void onComplete(Page page) {
		Log.i(TAG, "Page id = " + page.getId());
	}

	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get the page:
``` java
String pageId = ... ;
mSimpleFacebook.getPage(pageId, onPageListener);
```

#### Be specific and get what you need
By using this option, you define the properties you need, and you will get only them. Here, any property is possible to get.

Prepare the properties that you need:
``` java
Page.Properties properties = new Page.Properties.Builder()
	.add(Properties.ID)
	.add(Properties.NAME)
	.add(Properties.LINK)
	.build();
``` 

Get the page:
``` java		
String pageId = ...	;
mSimpleFacebook.getPage(pageId, properties, onPageListener);
```

### Get photos

Initialize callback listener:
``` java
OnPhotosListener onPhotosListener = new OnPhotosListener() {			
	@Override
	public void onComplete(List<Photo> photos) {
		Log.i(TAG, "Number of photos = " + photos.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my photos:
``` java
mSimpleFacebook.getPhotos(onPhotosListener);
```

Get photos of next entities by passing entity id:
- `Profile`
- `Album`
- `Event`
- `Page`
``` java
String entityId = ...;
mSimpleFacebook.getPhotos(entityId, onPhotosListener);
```

### Get posts

Initialize callback listener:
``` java
OnPostsListener onPostsListener = new OnPostsListener() {			
	@Override
	public void onComplete(List<Post> posts) {
		Log.i(TAG, "Number of posts = " + posts.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get all posts and feeds that are shown on my wall:
``` java
mSimpleFacebook.getPosts(onPostsListener);
```

Get all posts and feeds that are shown specific entity wall:
- `Profile`
- `Event`
- `Group`
- `Page`
``` java
String entityId = ...;
mSimpleFacebook.getPosts(entityId, onPostsListener);
```

You can filter and get only `links`, `statuses`, `posts` or `tagged` by using:
``` java
String entityId = ...;
mSimpleFacebook.getPosts(entityId, PostType.STATUSES, onPostsListener);
```

### Get scores

Initialize callback listener:
``` java
OnScoresListener onScoresListener = new OnScoresListener() {			
	@Override
	public void onComplete(List<Score> scores) {
		Log.i(TAG, "Number of scores = " + scores.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my scores:
``` java
mSimpleFacebook.getScores(onScoresListener); 
```

### Get videos

Initialize callback listener:
``` java
OnVideosListener onVideosListener = new OnVideosListener() {			
	@Override
	public void onComplete(List<Video> videos) {
		Log.i(TAG, "Number of videos = " + videos.size());
	}
	
	/* 
	 * You can override other methods here: 
	 * onThinking(), onFail(String reason), onException(Throwable throwable)
	 */		
};
```

Get my videos:
``` java
mSimpleFacebook.getVideos(onVideosListener);
```

Get videos of next entities by passing entity id:
- `Profile`
- `Event`
- `Page`
``` java
String entityId = ...;
mSimpleFacebook.getVideos(entityId, onVideosListener);
```

## Additional options
* [Pagination](#pagination)
* [General 'GET'](#general-get)
* [Set privacy settings of a single post](#set-privacy-settings-of-a-single-post)
* [Configuration options](#configuration-options)
* [Permissions](#permissions)
	* [Request new permissions](#request-new-permissions)
	* [Granted persmissions](#granted-persmissions)
* [Open Graph](#open-graph)
* [Misc](#misc)
* [Debug](#debug)

### Pagination
When you ask for photos, albums and other list of entities, you will get the first page of results. Usualy it is **25** first results.
For getting next or previous pages you can use methods below or get `Cursor` and start iterating.<br><br>
Each `OnActionListener` has next methods:
- `hasNext()` - return `true` if one more page exists
- `hasPrev()` - return `true` if previous page exists
- `getNext()` - execute request for getting next page and the results return to the same listener
- `getPrev()` - execute request for getting previous page and the results return to the same listener
- `getCursor()` - return `Cursor` which has the same methods above

#### Example:
Let's say we are asking for comments by: `getCommets(entityId, onCommentsListener)`.<br>
We want to check if there is more pages with comments. If so, we will ask for more. 

And this is our listener:
``` java
OnCommentsListener onCommentsListener = new OnCommentsListener() {			
	@Override
	public void onComplete(List<Comment> comments) {
		Log.i(TAG, "Number of comments = " + comments.size());
		if (hasNext()) {
			getNext();
		}
	}
};
```

> Of course, it is just an example. In your app, you will maybe add a button that will say 'get more...' and then you will actually call for `getNext()` but it is up to you how to handle it. You can `getCursor()` and iterate to the next pages from other place in you app. Again, it's up to you :)

### General 'GET'
You can get anything from facebook by using this generic function. By using this method, you should be familiar with Graph API. 

``` java
String entityId = ... ;
String edge = ... ;
OnActionListener<T> onActionListener = ... ;
mSimpleFacebook.get(entityId, edge, onActionListener);
```

#### Example:
If you want to get **music** that you like, then the graph path should be `me/music` and the expeted result is of type `Page` as explained in [this document](https://developers.facebook.com/docs/graph-api/reference/user/music).

Get my music: 
``` java
mSimpleFacebook.get("me", "music", null, new OnActionListener<List<Page>>() {
	
	@Override
	public void onComplete(List<Page> response) {
		Log.i(TAG, "Number of music pages I like = " + response.size());
	}

});
```

> **Note:** This method is expensive since it uses reflection. It also supports only **List** and entities that have `create(GraphObject)` method.

### Set privacy settings of a single post
You can set the privacy settings of a single post (set who can / can't see the post). <br/>
Currently supported in:<br/>
- `Feed`
- `Photo`
- `Video`

You have <b>2 options</b> to define privacy. 
- Use **basic** settings
- Use **custom** setting

#### Basic settings

Use one of the following predefined privacy settings: 
- `EVERYONE`
- `ALL_FRIENDS`
- `FRIENDS_OF_FRIENDS`
- `SELF`

Build `Privacy` instance:
``` java
Privacy privacy = new Privacy.Builder()
	.setPrivacySettings(PrivacySettings.FRIENDS_OF_FRIENDS)
	.build();
```

Set to `Photo`, `Video` or `Feed` and publish:
``` java
Photo photo = new Photo.Builder()
	.setPrivacy(privacy)
	...
	.build();

// Publish photo
mSimpleFacebook.publish(photo, onPublishListener);
```

#### Custom settings
Use `CUSTOM` and select the users, friendlists to be in allowed or/and denied lists. 

Build `Privacy` instance:
``` java
Privacy privacy = new Privacy.Builder()
	.setPrivacySettings(PrivacySettings.CUSTOM)
	.addAllowed("630243197") 
	.addAllowed(Collection<String>) 
	.addAllowed(PrivacySettings.ALL_FRIENDS)
	.addDenied("1456233371") 
	.addDenied(Collection<String>)
	.build();
```
> You can combine and define users, friendlist to be in allowed and denied lists without limits. Just don't forget to set `CUSTOM`.
See javadocs for additional details.

### Configuration options

The configuration, you need to define once in your app. The best place for that will be in your extension to `Application` class.<br> 
Configuration properties:

| Property         | Description                                | Must/Optional | Default Value
 ------------------|--------------------------------------------|---------------|----------------------
| app id 		   | Application id                             |  Must	        |
| namespace        | Application namespace                      |  Must         |
| permissions      | Set of permissions you want to use on login|  Must         |
| default audience | Decide who will see your published posts   |  Optional     | FRIENDS
| login behaviour  | Descide what to use: SSO or not            |  Optional     | SSO_WITH_FALLBACK
| all at once      | Ask from user all permissions at once      |  Optional     | false

### Permissions
One of the main ideas of this library is to make life easier by simplifing usage of permissions. You can find that all permissions are predefined and you don't need to care for the order and code flow of READ and PUBLISH permissions.

#### Request new permissions
You can request new permissions in runtime. The popup with new set of permissions will be shown to user.

Initialize callback listener:
``` java
OnNewPermissionsListener onNewPermissionsListener = new OnNewPermissionsListener() {

	@Override
	publicvoid onSuccess(String accessToken) {
		// updated access token 
	}

	@Override
	public void onNotAcceptingPermissions(Permission.Type type) {
		// user didn't accept READ or WRITE permission
		Log.w(TAG, String.format("You didn't accept %s permissions", type.name()));
	}
	
}
```

Set array of new permissions you want:
``` java
Permission[] permissions = new Permission[] { 
	Permission.USER_VIDEOS,
	Permission.FRIENDS_EVENTS,
	Permission.PUBLISH_STREAM
};
```

Ask for new permissions:
``` java
boolean showPublish = true;
mSimpleFacebook.requestNewPermissions(permissions, showPublish, onNewPermissionsListener);
```

> `showPublish` flag - This flag is relevant only in cases when new permissions include PUBLISH permission. Then you can decide if you want the dialog of requesting publish permission to appear <b>right away</b> or <b>later</b>, at first time of real publish action.

#### Granted persmissions
Get all permissions that user already granted:
``` java 
List<String> permissions = mSimpleFacebook.getGrantedPermissions();
```

Check if all permissions from configuration are granted:
``` java
boolean isAllGranted = mSimpleFacebook.isAllPermissionsGranted();
```

### Open Graph
Currently this feature exists in the library but still in experimental mode. <br>
The used method is: `publish(Story)` and it is possible to build `Story` entity. It should work, because it works for me, but the final API of this action and `Story` entity aren't finalized yet. 

### Misc

* `isLogin()` – Check if you are logged in
* `getSession()` - Get active session
* `clean()` - Clean all references like `Activity` to prevent memory leaks
* `eventAppLaunched()` - Install report to facebook. Useful for statistics

### Debug

Print logs to logcat:
Set `Logger.DEBUG` to `true` 

Print logs with full stacktrace to logcat:
Set `Logger.DEBUG_WITH_STACKTRACE` to `true` 


## Sample application
<a href="https://play.google.com/store/apps/details?id=com.sromku.simple.fb.example">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

## Applications using the library

| [Pregnancy Tickers - Widget](https://play.google.com/store/apps/details?id=com.romkuapps.tickers) | [Pregnancy Calculator](https://play.google.com/store/apps/details?id=com.romkuapps.enfree.duedate) | [Ring Drop : Fun Ring Toss Game](https://play.google.com/store/apps/details?id=com.aitrich.ringdrop) | [Fun Call](https://play.google.com/store/apps/details?id=com.rami_bar.fun_call) | [8tracks Radio](https://play.google.com/store/apps/details?id=com.e8tracks) | [Violet Glasses](https://play.google.com/store/apps/developer?id=Violet+Glasses) | [Dough Pro - Artisan Baking](https://play.google.com/store/apps/details?id=com.ollygrov.doughpro) | [Mental Arithmetic](https://play.google.com/store/apps/details?id=nintenda.calculmental) | [Pemex Checker](https://play.google.com/store/apps/details?id=com.xoco.pemex.checker) | [Ayuda Alguien](https://play.google.com/store/apps/details?id=com.xoco.ayuda) 
| and more...<br>

> If you use this library in your project and you found it helpful, it will be really great to share it here :) 

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


