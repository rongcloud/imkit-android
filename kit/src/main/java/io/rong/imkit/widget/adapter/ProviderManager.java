package io.rong.imkit.widget.adapter;

import androidx.collection.SparseArrayCompat;

import java.util.List;


public class ProviderManager<T> {
    private final int EMPTY_ITEM_VIEW_TYPE = -100;
    private final int DEFAULT_ITEM_VIEW_TYPE = -200;
    private SparseArrayCompat<IViewProvider<T>> mProviders = new SparseArrayCompat<>();
    private IViewProvider<T> mDefaultProvider;
    private IViewProvider<T> mEmptyViewProvider;

    public ProviderManager() {
        mDefaultProvider = new DefaultProvider();
    }

    public ProviderManager(List<IViewProvider<T>> providerList) {
        this();
        for (IViewProvider<T> provider : providerList) {
            addProvider(provider);
        }
    }

    public int getProviderCount() {
        return mProviders.size();
    }

    public void addProvider(IViewProvider<T> provider) {
        int viewType = mProviders.size();
        if (provider != null) {
            mProviders.put(viewType, provider);
        }
    }

    public void addProvider(int viewType, IViewProvider<T> provider) {
        if (mProviders.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewProvider is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewProvider is "
                            + mProviders.get(viewType));
        }
        mProviders.put(viewType, provider);
    }

    /**
     * 设置默认模板。当找不到和 viewType 对应的模板时，使用此默认模板进行 ui 处理。
     */
    public void setDefaultProvider(IViewProvider<T> defaultProvider) {
        mDefaultProvider = defaultProvider;
    }

    /**
     * 设置 list 为空时的展示模板。
     *
     * @param emptyViewProvider 空模板展示类
     */
    public void setEmptyViewProvider(IViewProvider<T> emptyViewProvider) {
        mEmptyViewProvider = emptyViewProvider;
    }

    public IViewProvider<T> getEmptyViewProvider() {
        return mEmptyViewProvider;
    }

    public void removeProvider(IViewProvider<T> provider) {
        if (provider == null) {
            throw new NullPointerException("ItemViewProvider is null");
        }
        int indexToRemove = mProviders.indexOfValue(provider);

        if (indexToRemove >= 0) {
            mProviders.removeAt(indexToRemove);
        }
    }

    public void replaceProvider(Class oldProviderClass, IViewProvider<T> provider) {
        int key = -1;
        for (int i = 0; i < mProviders.size(); i++) {
            int index = mProviders.keyAt(i);
            IViewProvider<T> item = mProviders.get(index);
            if (item != null && item.getClass().equals(oldProviderClass)) {
                key = index;
                break;
            }
        }
        if (key != -1) {
            mProviders.put(key, provider);
        }
    }

    public void removeProvider(int itemType) {
        int indexToRemove = mProviders.indexOfKey(itemType);
        if (indexToRemove >= 0) {
            mProviders.removeAt(indexToRemove);
        }
    }


    public IViewProvider<T> getProvider(int viewType) {
        IViewProvider<T> provider = mProviders.get(viewType);
        if (provider == null) {
            provider = mDefaultProvider;
        }
        return provider;
    }

    public int getItemViewType(IViewProvider<T> provider) {
        return mProviders.indexOfValue(provider);
    }


    public int getItemViewType(T item, int position) {
        int count = mProviders.size();
        for (int i = count - 1; i >= 0; i--) {
            IViewProvider<T> provider = mProviders.valueAt(i);
            if (provider.isItemViewType(item)) {
                return mProviders.keyAt(i);
            }
        }
        return DEFAULT_ITEM_VIEW_TYPE;
    }

    public int getEmptyItemViewType() {
        return EMPTY_ITEM_VIEW_TYPE;
    }


    public IViewProvider<T> getProvider(T item) {
        for (int i = 0; i < mProviders.size(); i++) {
            IViewProvider<T> provider = mProviders.valueAt(i);
            if (provider.isItemViewType(item)) {
                return provider;
            }
        }
        return mDefaultProvider;
    }
}
