package com.coahr.fanoftruck.mvp.constract;

import com.amap.api.location.AMapLocation;
import com.coahr.fanoftruck.mvp.Base.BaseContract;
import com.coahr.fanoftruck.mvp.model.Bean.SearchBean;
import com.coahr.fanoftruck.mvp.model.Bean.CityInfoBean;
import com.coahr.fanoftruck.mvp.model.Bean.StoreBean;

import java.util.Map;

/**
 * Created by Leehor
 * on 2018/11/6
 * on 17:49
 */
public interface Fragment_store_C {

    interface View extends BaseContract.View {

        void onLocationSuccess(AMapLocation location);

        void onLocationFailure(int failure);

        void getStoreListSuccess(StoreBean storeBean);

        void getStoreListFailure(String failure);

        void getStoreMoreSuccess(StoreBean storeBean);

        void getStoreMoreFailure(String failure);

        void getSearchSuccess(SearchBean searchBean);

        void getSearchFailure(String failure);


        void getCityListSuccess(CityInfoBean cityInfoBean);

        void getCityListFailure(String failure);

    }

    interface Presenter extends BaseContract.Presenter {
        void startLocation();

        void onLocationSuccess(AMapLocation location);

        void onLocationFailure(int failure);


        void getStoreList(Map<String,String> map);

        void getStoreListSuccess(StoreBean storeBean);

        void getStoreListFailure(String failure);


        void  getStoreMore(Map<String,String> map);

        void getStoreMoreSuccess(StoreBean storeBean);

        void getStoreMoreFailure(String failure);

        void getSearchMap(Map<String,String> map);

        void getSearchSuccess(SearchBean searchBean);

        void getSearchFailure(String failure);


        void getCityList(Map<String,String> map);

        void getCityListSuccess(CityInfoBean cityInfoBean);

        void getCityListFailure(String failure);
    }

    interface Model extends BaseContract.Model {
        void startLocation();
        void getStoreList(Map<String,String> map);
        void  getStoreMore(Map<String,String> map);

        void getSearchMap(Map<String,String> map);

        void getCityList(Map<String,String> map);
    }
}
