package com.qianjujun.frame.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.qianjujun.frame.R;
import com.qianjujun.frame.utils.ClickHandler;
import com.qianjujun.frame.utils.Itn;
import com.qianjujun.frame.views.stateview.IPage;
import com.qianjujun.frame.views.title.CstTopBar;
import com.qianjujun.frame.views.title.ITopbarClickListner;
import com.qianjujun.frame.views.title.TitleConfig;

import java.util.List;


/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/17 9:04
 * @describe
 */
abstract class BetterBaseFragment extends Fragment implements View.OnClickListener, ITopbarClickListner, IPage {
    protected View mRootView;
    protected AppCompatActivity mActivity;

    protected static final String TAG = BetterBaseFragment.class.getSimpleName();




    //必须重写的









    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            initArgs(bundle);
        }
    }

    /**
     * 是否缓存View布局
     * @return
     */
    protected boolean needCacheRootView() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || !needCacheRootView()) {//如果view没有被初始化或者不需要缓存的情况下，重新初始化控件
            mRootView = initRootView(inflater,container);
        } else {
            ViewGroup v = (ViewGroup) mRootView.getParent();
            if (v != null) {
                v.removeView(mRootView);
            }
        }
        return mRootView;
    }

    private View initRootView(LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.better_fragment_base, container, false);
        ViewStub viewStub = rootView.findViewById(R.id.view_stup);
        loadContentView(viewStub);
        FrameLayout vsTitle = rootView.findViewById(R.id.titleContainer);
        loadTitleView(inflater,vsTitle);
        return rootView;
    }



    private void loadTitleView(LayoutInflater inflater,FrameLayout vsTitle){
        TitleConfig config = getTitleConfig();
        if(config!=null){
            config.click(this);
            CstTopBar topBar = new CstTopBar(getContext());
            vsTitle.addView(topBar,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Itn.getDimensionPixelOffset(R.dimen.better_title_height)));
            topBar.setTitlConfig(config);
        }else {
            View view = getTitleView(inflater,vsTitle);
            if(view==null){
               return;
            }
            if(view.getParent()==null){
                vsTitle.addView(view,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }else if(vsTitle.getChildCount()==0){
                ViewGroup v = (ViewGroup) view.getParent();
                v.removeView(view);
                vsTitle.addView(view,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
    }


    protected abstract void loadContentView(ViewStub viewStub);


    /**
     * 头部view
     * @param inflater
     * @param container
     * @return
     */
    protected View getTitleView(LayoutInflater inflater,FrameLayout container){
        return null;
    }

    /**
     * 头部配置 优先级大于上面
     * @return
     */
    protected TitleConfig getTitleConfig(){
        return null;
    }


    public <T extends View> T findViewById(int viewId){
        if(mRootView==null){
            return null;
        }
        return mRootView.findViewById(viewId);
    }


    /**
     *
     * @return 返回true表示自己处理返回事件
     */
    protected boolean onBackPressed() {
        return false;
    }


    /**
     * 直接关闭
     */
    public void finish(){
        mActivity.finish();
    }

    /**
     * 最终会被fragment的onBackPressed拦截
     */
    public void backPress(){
        mActivity.onBackPressed();
    }

    protected void initArgs(@NonNull Bundle args){

    }


    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent,requestCode);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        mActivity.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        mActivity.startActivityForResult(intent, requestCode, options);
    }


    @Override
    public final void onClick(View v) {
        if(!ClickHandler.isValidClick()){
            return;
        }
        onViewClick(v);
    }



    public void onViewClick(View view){

    }

    @Override
    public void leftClick() {

    }

    @Override
    public void centerClick() {

    }

    @Override
    public void rightClick() {

    }

    @Override
    public void right2Click() {

    }

    @Override
    public void topbarClick() {

    }

    @Override
    public void topbarDoubleClick() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showErrorView(int errorCode, String msg) {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showSuccessView() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
        if(fragmentList==null||fragmentList.size()==0){
            return;
        }
        for(Fragment fragment:fragmentList){
            if(fragment.isVisible()&&fragment.isResumed()){
                fragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }



    protected boolean checkActivityResult(int resultCode,Intent data){
        return resultCode== Activity.RESULT_OK&&data!=null;
    }
}
