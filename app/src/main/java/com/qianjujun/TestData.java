package com.qianjujun;

import com.qianjujun.data.Data;
import com.qianjujun.data.FriendData;
import com.qianjujun.frame.exception.AppException;
import com.qianjujun.frame.exception.EmptyException;
import com.qianjujun.rx.RxUtil;
import com.qianjujun.vm.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 14:00
 * @describe
 */
public class TestData {
    private static final Random random = new Random();
    private static int index = 0;

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



    public static List<String> createTestStringList() {
        int max = random.nextInt(25) + 1;
        return createTestStringList(max);
    }

    public static List<String> createTestStringList(int max) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            list.add("数据：" + (i));
        }
        return list;
    }


    public static Flowable<Data<List<String>>> createTestStringListRequest() {
        int state = requestNum % 3;
        return Flowable.just(state)
                .delay(400, TimeUnit.MILLISECONDS)
                .map(_state->requestNum++)
                .map(strings -> {
                    switch (state) {
                        case 0:
                            throw new EmptyException();
                        case 1:
                            throw new AppException("数据加载错误");
                        default:
                            return Data.success(TestData.createTestStringList());
                    }
                })

                .compose(RxUtil.rxBaseParamsHelper());
    }


    public static Flowable<Data<List<String>>> createTestStringListRequest(int index) {
        int state = requestNum % 3;
        return Flowable.just(state)
                .delay(600, TimeUnit.MILLISECONDS)
                .map(_state->requestNum++)
                .map(strings -> {
                    switch (state) {
                        case 0:
                            throw new EmptyException();
                        case 1:
                            throw new AppException("数据加载错误");
                        default:
                            return Data.success(TestData.createTestStringList(new Random().nextInt(10)+1,index));
                    }
                })

                .compose(RxUtil.rxBaseParamsHelper());
    }

    public static Flowable<Data<List<String>>> createTestStringListRequest(int index,int num) {
        int state = requestNum % 3;
        return Flowable.just(state)
                .delay(600, TimeUnit.MILLISECONDS)
                .map(_state->requestNum++)
                .map(strings -> {
                    switch (state) {
                        case 0:
                            //throw new EmptyException();
                        case 1:
                            //throw new AppException("数据加载错误");
                        default:
                            return Data.success(TestData.createTestStringList(num,index));
                    }
                })

                .compose(RxUtil.rxBaseParamsHelper());
    }



    public static List<String> createTestStringList(int max,int index) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            list.add("第"+index+"次数据：" + (i));
        }
        return list;
    }



    private static int requestNum = 0;



    public static Flowable<Data<List<Group>>> createGroupRequest(){
       return Flowable.just(Group.createTestData())
                .map(new Function<List<Group>, Data<List<Group>>>() {

                    @Override
                    public Data<List<Group>> apply(List<Group> groups) throws Exception {
                        return Data.success(groups);
                    }
                })
               .delay(600,TimeUnit.MILLISECONDS)
                .compose(RxUtil.rxBaseParamsHelper());
    }






    public static List<FriendData> createFriendData(){
        List<FriendData> list = new ArrayList<>();
        FriendData friendData;
        for(int i = 0;i<20;i++){
            friendData = new FriendData();
            friendData.setUser(new FriendData.User());
            friendData.setContent(random.nextBoolean()?"朋友圈内容"+random.nextInt(1000):null);
            friendData.setLink(random.nextBoolean()?new FriendData.Link():null);
            friendData.setImageList(createMax9Images());
            friendData.setCommentList(createRandomComment());
            list.add(friendData);
        }
        return list;
    }

    private static List<String> createMax9Images(){
        List<String> list = new ArrayList<>();
        int max = random.nextInt(9);
        if(max==0){
            max = 1;
        }
        for(int i = 0;i<max;i++){
            list.add(images[random.nextInt(images.length)]);
        }

        return list;
    }

    private static List<FriendData.Comment> createRandomComment(){
        List<FriendData.Comment> list = new ArrayList<>();
        int max = random.nextInt(10);
        for(int i = 0;i<max;i++){
            list.add(new FriendData.Comment());
        }
        return list;
    }

}
