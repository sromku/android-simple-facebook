package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Post;
import com.sromku.simple.fb.entities.Post.PostType;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

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
    protected List<Post> processResponse(GraphResponse response) {
        Utils.DataResult<Post> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Post>>() {
        }.getType());
        return dataResult.data;
    }

}
