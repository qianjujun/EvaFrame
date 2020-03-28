package com.qianjujun.data;

import com.hello7890.adapter.data.TwoGroupData;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 18:13
 * @describe
 */
public class FriendData implements TwoGroupData<String,FriendData.Comment> {

    private User user;
    private String content;
    private Link link;
    private List<String> imageList;
    private List<Comment> commentList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public List<String> getChild1List() {
        return getImageList();
    }

    @Override
    public List<Comment> getChild2List() {
        return getCommentList();
    }

    @Override
    public String getChild1(int childPosition) {
        return imageList==null?null:imageList.get(childPosition);

    }

    @Override
    public Comment getChild2(int childPosition) {
        return commentList==null?null:commentList.get(childPosition);
    }

    @Override
    public int getChild1Size() {
        return imageList==null?0:imageList.size();
    }

    @Override
    public int getChild2Size() {
        return commentList==null?0:commentList.size();
    }

    public static class User{
        private String avatar;
        private String nickName;
    }

    public static class Comment{
        private User user;
        private String content;
    }

    public static class Link{
        private String image;
        private String content;
    }



}
