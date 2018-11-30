package com.coahr.fanoftruck.mvp.presenter;

import com.baidu.location.BDLocation;
import com.coahr.fanoftruck.mvp.Base.BasePresenter;
import com.coahr.fanoftruck.mvp.Base.SearchBean;
import com.coahr.fanoftruck.mvp.constract.Fragment_shoppingDetail_C;
import com.coahr.fanoftruck.mvp.constract.Fragment_shopping_C;
import com.coahr.fanoftruck.mvp.model.Bean.ShoppingMallBean;
import com.coahr.fanoftruck.mvp.model.Bean.ShoppingMallDetailBean;
import com.coahr.fanoftruck.mvp.model.Fragment_ShoppingDetail_M;
import com.coahr.fanoftruck.mvp.model.Fragment_shopping_M;
import com.coahr.fanoftruck.mvp.view.Shopping.Fragment_Shopping;
import com.coahr.fanoftruck.mvp.view.Shopping.Fragment_ShoppingDetail;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/20
 * on 11:31
 */
public class Fragment_shoppingDetail_P extends BasePresenter<Fragment_shoppingDetail_C.View,Fragment_shoppingDetail_C.Model> implements Fragment_shoppingDetail_C.Presenter {
    @Inject
    public Fragment_shoppingDetail_P(Fragment_ShoppingDetail mview, Fragment_ShoppingDetail_M mModel) {
        super(mview, mModel);
    }
    @Override
    public void startLocation() {
        if (mModle != null) {
            mModle.startLocation();
        }
    }

    @Override
    public void onLocationSuccess(BDLocation location) {
        if (getView() != null) {
            getView().onLocationSuccess(location);
        }
    }

    @Override
    public void onLocationFailure(int failure) {
        if (getView() != null) {
            getView().onLocationFailure(failure);
        }
    }

    @Override
    public void getShoppingDetail(Map<String, String> map) {
        if (mModle != null) {
            mModle.getShoppingDetail(map);
        }
    }

    @Override
    public void getShoppingDetailSuccess(ShoppingMallDetailBean shoppingMallDetailBean) {
        if (getView() != null) {
            getView().getShoppingDetailSuccess(shoppingMallDetailBean);
        }
    }

    @Override
    public void getShoppingDetailFailure(String failure) {
        if (getView() != null) {
            getView().getShoppingDetailFailure(failure);
        }
    }

}
