package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Post;
import com.sromku.simple.fb.entities.Post.PostType;
import com.sromku.simple.fb.utils.Utils;

public class GetPostsAction extends GetAction<List<Post>> {

	private PostType mPostType = PostType.ALL; // default

	public GetPostsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setPostType(PostType postType) {
		mPostType = postType;
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + mPostType.getGraphPath();
	}

	@Override
	protected List<Post> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Post> posts = new ArrayList<Post>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Post post = Post.create(graphObject);
			posts.add(post);
		}
		return posts;
	}

}
