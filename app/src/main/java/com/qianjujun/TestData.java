package com.qianjujun;

import com.qianjujun.data.Data;
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
                            //throw new EmptyException();
                        case 1:
                            //throw new AppException("数据加载错误");
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
                            //throw new EmptyException();
                        case 1:
                            //throw new AppException("数据加载错误");
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


}
