package com.coahr.fanoftruck.mvp.model;

import com.amap.api.location.AMapLocation;
import com.coahr.fanoftruck.Utils.GpsLocation.GaodeMapLocation;
import com.coahr.fanoftruck.mvp.Base.BaseModel;
import com.coahr.fanoftruck.mvp.model.Bean.SearchBean;
import com.coahr.fanoftruck.mvp.constract.Fragment_shopping_C;
import com.coahr.fanoftruck.mvp.model.Bean.ShoppingMallBean;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:53
 */
public class Fragment_shopping_M extends BaseModel<Fragment_shopping_C.Presenter> implements Fragment_shopping_C.Model {
    @Inject
    public Fragment_shopping_M() {
        super();
    }
    @Inject
    GaodeMapLocation gaodeMapLocation;

    private GaodeMapLocation.OnLocationCallBack locationCallBack=new GaodeMapLocation.OnLocationCallBack() {
        @Override
        public void onLocationSuccess(AMapLocation location) {
            if (getPresenter() != null) {
                getPresenter().onLocationSuccess(location);
                gaodeMapLocation.stopLocation();

            }
        }

        @Override
        public void onLocationFailure(int locType) {
            if (getPresenter() != null) {
                getPresenter().onLocationFailure(locType);
            }
            gaodeMapLocation.stopLocation();
        }
    };

    @Override
    public void detachPresenter() {
        super.detachPresenter();
        gaodeMapLocation.unRegisterLocationCallback(locationCallBack);
    }



    private void initlocation() {
        gaodeMapLocation.registerLocationCallback(locationCallBack);
    }

    @Override
    public void startLocation() {
    initlocation();
        gaodeMapLocation.startLocation();
    }

    @Override
    public void getShoppingList(Map<String, String> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<ShoppingMallBean>(
                getApiService().getShoppingList(map.get("brand")
                ,map.get("order"),map.get("price"),map.get("sort"),map.get("page"),map.get("length"))))
                .subscribeWith(new SimpleDisposableSubscriber<ShoppingMallBean>() {
                    @Override
                    public void _onNext(ShoppingMallBean shoppingMallBean) {
                        if (getPresenter() != null) {
                            if (shoppingMallBean.getCode() == 0) {
                                getPresenter().getShoppingListSuccess(shoppingMallBean);
                            } else {
                                getPresenter().getShoppingListFailure(shoppingMallBean.getMsg());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getShoppingMore(Map<String, String> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<ShoppingMallBean>(getApiService().getShoppingList(map.get("brand")
                ,map.get("order"),map.get("sort"),map.get("price"),map.get("page"),map.get("length"))))
                .subscribeWith(new SimpleDisposableSubscriber<ShoppingMallBean>() {
                    @Override
                    public void _onNext(ShoppingMallBean bean) {
                        if (getPresenter() != null){
                            if (bean.getCode()==0) {
                                getPresenter().getShoppingMoreSuccess(bean);
                            }else {
                                getPresenter().getShoppingMoreFailure(bean.getMsg());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getSearchMap(Map<String, String> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<SearchBean>(getApiService().searchAll(map.get("search_key"))))
                .subscribeWith(new SimpleDisposableSubscriber<SearchBean>() {
                    @Override
                    public void _onNext(SearchBean searchBean) {
                        if (getPresenter() != null) {
                            if (searchBean.getCode() == 0) {
                                getPresenter().getSearchSuccess(searchBean);
                            } else {
                                getPresenter().getSearchFailure(searchBean.getMsg());
                            }
                        }

                    }
                }));
    }
}
