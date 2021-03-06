package com.coahr.fanoftruck.mvp.model;

import com.coahr.fanoftruck.mvp.Base.BaseModel;
import com.coahr.fanoftruck.mvp.constract.Fragment_register_C;
import com.coahr.fanoftruck.mvp.model.Bean.LoginBean;
import com.coahr.fanoftruck.mvp.model.Bean.RegisterBean;
import com.coahr.fanoftruck.mvp.model.Bean.VerifyCode;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Leehor
 * on 2018/11/30
 * on 9:27
 */
public class Fragment_register_M extends BaseModel<Fragment_register_C.Presenter> implements Fragment_register_C.Model {

    @Inject
    public Fragment_register_M() {
        super();
    }

    @Override
    public void Register(Map<String, String> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<RegisterBean>(getApiService().register(map.get("phone"), map.get("userName")
                , map.get("pwd"), map.get("email"), map.get("device_token"),map.get("verify_code"))))
                .subscribeWith(new SimpleDisposableSubscriber<RegisterBean>() {
                    @Override
                    public void _onNext(RegisterBean registerBean) {
                        if (getPresenter() != null) {
                            if (registerBean.getCode() == 0) {
                                getPresenter().RegisterSuccess(registerBean);
                            } else {
                                getPresenter().RegisterFailure(registerBean.getMsg());
                            }
                        }
                    }
                }));
    }

    @Override
    public void getVerifyCode(Map<String, String> map) {
        mRxManager.add(createFlowable(new SimpleFlowableOnSubscribe<VerifyCode>(getApiService().getRegisterCode(map.get("phone"))))
                .subscribeWith(new SimpleDisposableSubscriber<VerifyCode>() {
                    @Override
                    public void _onNext(VerifyCode verifyCode) {
                        if (getPresenter() != null) {
                            if (verifyCode.getCode() == 0) {
                                getPresenter().getVerifyCodeSuccess(verifyCode);
                            } else {
                                getPresenter().getVerifyCodeFailure(verifyCode.getMsg());
                            }
                        }
                    }
                }));
    }
}
