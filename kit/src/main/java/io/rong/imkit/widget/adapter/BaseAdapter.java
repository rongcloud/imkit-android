package io.rong.imkit.widget.adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class BaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private final String TAG = BaseAdapter.class.getSimpleName();
    protected IViewProviderListener<T> mListener;
    protected List<T> mDataList = new ArrayList<>();
    protected OnItemClickListener mOnItemClickListener;
    protected ProviderManager<T> mProviderManager = new ProviderManager<>();

    public BaseAdapter() {

    }

    public BaseAdapter(IViewProviderListener<T> listener, ProviderManager<T> providerManager) {
        mListener = listener;
        mProviderManager = providerManager;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.size() == 0) {
            return mProviderManager.getEmptyItemViewType();
        }
        if (mProviderManager != null) {
            return mProviderManager.getItemViewType(mDataList.get(position), position);
        } else {
            throw new IllegalArgumentException(
                    "adapter did not set providerManager");
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mDataList.size() == 0 && mProviderManager.getEmptyViewProvider() != null) {
            return mProviderManager.getEmptyViewProvider().onCreateViewHolder(parent, viewType);
        } else {
            IViewProvider<T> provider = mProviderManager.getProvider(viewType);
            return provider.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (mDataList.size() == 0) {
            return;
        }
        IViewProvider<T> provider = mProviderManager.getProvider(mDataList.get(position));

        provider.bindViewHolder(holder, mDataList.get(position), position, mDataList, mListener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = holder.getBindingAdapterPosition();
                    mOnItemClickListener.onItemClick(v, holder, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = holder.getBindingAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, holder, position);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        //如果设置空布局
        if (mProviderManager != null && mProviderManager.getEmptyViewProvider() != null) {
            return mDataList.size() == 0 ? 1 : mDataList.size();
        } else {
            return mDataList.size();
        }
    }

    public void setDataCollection(List<T> data) {
        if (data != null) {
            this.mDataList.clear();
            this.mDataList.addAll(data);
        }
    }

    public void add(T t) {
        mDataList.add(t);
    }

    public void remove(T t) {
        mDataList.remove(t);
    }

    public List<T> getData() {
        return mDataList;
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, ViewHolder holder, int position);

        boolean onItemLongClick(View view, ViewHolder holder, int position);
    }

}
