package com.qianjujun.frame.loadmore;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.vh.BaseDbViewHolder;
import com.hello7890.adapter.vm.ViewModule;
import com.qianjujun.frame.R;
import com.qianjujun.frame.databinding.RvItemFooterErrorBinding;
import com.qianjujun.frame.databinding.RvItemFooterLoadingBinding;
import com.qianjujun.frame.databinding.RvItemFooterNomoredataBinding;
import com.qianjujun.frame.databinding.RvItemFooterNormalBinding;

public class LoadMoreViewModule extends ViewModule<Footer> {
    public static final String TAG = "LoadMoreViewModule";
    private View postView;//延迟执行
    private Footer footer = new Footer();
    private final OnLoadMoreListener onLoadMoreListener;
    private boolean enable = true;

    public LoadMoreViewModule(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        setData(footer);
    }

    public void setState(int state) {
        footer.setState(state);
        notifyItemChanged(0);
        if (state == Footer.STATE_SUCCESS) {
            footer.setState(Footer.STATE_NORMAL);
        }
    }

    public void setEnable(boolean enable){
        this.enable = enable;
    }




    @Override
    public BaseViewHolder<Footer> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (postView == null) {
            postView = parent;
        }
        BaseViewHolder<Footer> holder = null;
        switch (viewType) {
            case Footer.STATE_LODING:
                holder = onCreateLoadingHolder1(parent);
                break;
            case Footer.STATE_FAIL:
                holder = onCreateFailHolder1(parent);
                break;
            case Footer.STATE_NO_MORE:
                holder = onCreateNoMoreHodler1(parent);
                break;
            case Footer.STATE_NORMAL:
                holder = onCreateNormalHodler1(parent);
                break;
            case Footer.STATE_SUCCESS:
            default:
                holder = onCreateSuccessHodler1(parent);
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int dataPosition) {
        return footer.getState();
    }

    private void loadData() {
        if(!enable){
            return;
        }
        if (onLoadMoreListener == null) {
            postSetState(Footer.STATE_NO_MORE);
            return;
        }
        postSetState(Footer.STATE_LODING);
        onLoadMoreListener.onLoadMore();
    }


    private void postSetState(final int state) {
        if (postView == null) {
            return;
        }
        postView.post(() -> setState(state));
    }


    protected BaseViewHolder<Footer> onCreateLoadingHolder1(ViewGroup parent) {
        return new LoadingHolder(R.layout.rv_item_footer_loading, parent);
    }

    protected BaseViewHolder<Footer> onCreateFailHolder1(ViewGroup parent) {
        return new FailHolder(R.layout.rv_item_footer_error, parent);
    }

    protected BaseViewHolder<Footer> onCreateNormalHodler1(ViewGroup parent) {
        return new BaseDbViewHolder<Footer, RvItemFooterNormalBinding>(R.layout.rv_item_footer_normal, parent) {
            @Override
            public void onBindData(Footer footer, int dataPosition, int adapterPosition) {
                Log.d(TAG, "onBindData() called with: footer = [" + footer + "], dataPosition = [" + dataPosition + "], layoutPosition = [" + adapterPosition + "]");
                loadData();
            }
        };
    }

    protected BaseViewHolder<Footer> onCreateSuccessHodler1(ViewGroup parent) {
        return new BaseDbViewHolder<Footer, RvItemFooterNormalBinding>(R.layout.rv_item_footer_normal, parent) {
            @Override
            public void onBindData(Footer footer, int dataPosition, int adapterPosition) {
            }
        };
    }

    protected BaseViewHolder<Footer> onCreateNoMoreHodler1(ViewGroup parent) {
        return new BaseDbViewHolder<Footer, RvItemFooterNomoredataBinding>(R.layout.rv_item_footer_nomoredata, parent) {
            @Override
            public void onBindData(Footer footer, int dataPosition, int adapterPosition) {
                mDataBinding.text.setText("没有更多数据");
            }
        };
    }


    class LoadingHolder extends BaseDbViewHolder<Footer, RvItemFooterLoadingBinding> {

        public LoadingHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(Footer footer, int dataPosition, int adapterPosition) {
            mDataBinding.loadingTv.setText("正在加载中...");
        }
    }

    class FailHolder extends BaseDbViewHolder<Footer, RvItemFooterErrorBinding> {

        public FailHolder(int layoutId, ViewGroup container) {
            super(layoutId, container);
        }

        @Override
        public void onBindData(Footer footer, int dataPosition, int adapterPosition) {
            mDataBinding.loadingText.setText("点击重新加载");
            mDataBinding.root.setOnClickListener(view -> loadData());
        }
    }
}
