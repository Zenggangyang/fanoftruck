package com.coahr.fanoftruck.mvp.model;

import com.coahr.fanoftruck.mvp.Base.BaseModel;
import com.coahr.fanoftruck.mvp.constract.Fragment_business_child_C;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/20
 * on 11:56
 */
public class Fragment_business_child_M extends BaseModel<Fragment_business_child_C.Presenter> implements Fragment_business_child_C.Model {

   @Inject
    public Fragment_business_child_M() {
        super();
    }

    @Override
    public void startLocation() {

    }
}