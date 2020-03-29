package com.qianjujun.data;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.hello7890.adapter.data.Group2Data;

import java.util.List;
import java.util.Random;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/28 18:13
 * @describe
 */
public class FriendData implements Group2Data<String,FriendData.Comment> {
    private static final String[] images = new String[]{
            "http://img1.imgtn.bdimg.com/it/u=1747563452,2047173239&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=216209579,1411994854&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2164724814,1401845036&fm=26&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1798173886,3671914447&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2427441392,1027688913&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2545380209,2451354813&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3015946809,1032771475&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2444031136,1156137205&fm=26&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1747563452,2047173239&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=2915512436,1541993188&fm=26&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3723747836,3447432399&fm=26&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=1731597746,428499852&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=3639413414,822479011&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=1956101362,3172476555&fm=26&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2904743760,1881633435&fm=26&gp=0.jpg"
    };

    private static Random random = new Random();

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

        public User(String avatar, String nickName) {
            this.avatar = avatar;
            this.nickName = nickName;
        }

        public User() {
            this.avatar = images[random.nextInt(images.length)];
            this.nickName = "昵称"+random.nextInt(1000);
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }

    public static class Comment{
        private User user;
        private String content;

        private CharSequence contentStr;


        public Comment(){
            this.user = new User();
            this.content = "评论内容"+random.nextInt(1000);
        }

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

        public CharSequence getContentStr() {
            if(TextUtils.isEmpty(content)){
                return content;
            }
            if(contentStr==null){
                SpannableStringBuilder sb = new SpannableStringBuilder();
                sb.append(user.getNickName());
                ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#336699"));
                sb.setSpan(foregroundColorSpan,0,sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.append(content);
                contentStr = sb;
            }
            return contentStr;
        }
    }

    public static class Link{

        public Link(String image, String content) {
            this.image = image;
            this.content = content;
        }


        public Link(){
            this.image = images[random.nextInt(images.length)];
            this.content = "链接内容"+random.nextInt(1000);
        }

        private String image;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }





    public int width,height;


}
