package com.coahr.fanoftruck.mvp.view.BusinessOpportunity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.coahr.fanoftruck.R;
import com.coahr.fanoftruck.Utils.LoadCity.JsonBean;
import com.coahr.fanoftruck.Utils.LoadCity.JsonFileReader;
import com.coahr.fanoftruck.Utils.ToastUtils;
import com.coahr.fanoftruck.Utils.ValidateUtils;
import com.coahr.fanoftruck.commom.Constants;
import com.coahr.fanoftruck.mvp.Base.BaseApplication;
import com.coahr.fanoftruck.mvp.Base.BaseFragment;
import com.coahr.fanoftruck.mvp.constract.Fragment_recommendCar_C;
import com.coahr.fanoftruck.mvp.model.Bean.Business_car;
import com.coahr.fanoftruck.mvp.model.Bean.getBuyCarCode;
import com.coahr.fanoftruck.mvp.presenter.Fragment_recommendCar_P;
import com.coahr.fanoftruck.widgets.BlockTextView;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Leehor
 * on 2018/11/30
 * on 17:09
 * 推荐购车
 */
public class Fragment_RecommendCar extends BaseFragment<Fragment_recommendCar_C.Presenter> implements Fragment_recommendCar_C.View, View.OnClickListener {

    @Inject
    Fragment_recommendCar_P p;
    @BindView(R.id.tv_select_city)
    TextView tv_select_city;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.getCode)
    BlockTextView getCode;
    @BindView(R.id.business_phone)
    EditText business_phone;
    @BindView(R.id.tv_slected_car)
    TextView tv_slected_car;
    @BindView(R.id.tv_select_car)
    TextView tv_select_car;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (!isLoaded) {
                        if (thread == null) {//如果已创建就不再重新创建子线程了
                            thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    getJson();
                                }
                            });
                            thread.start();
                        }
                    } else {
                        showCityPickerView();
                    }


                    break;
                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    showCityPickerView();
                    break;
                case MSG_LOAD_FAILED:
                    isLoaded = false;
                    break;
            }
        }
    };
    public static  Fragment_RecommendCar newInstance() {
        return new Fragment_RecommendCar();
    }

    @Override
    public Fragment_recommendCar_C.Presenter getPresenter() {
        return p;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_recommentcar;
    }

    @Override
    public void initView() {
        tv_select_city.setOnClickListener(this);
        getCode.setOnClickListener(this);
        tv_select_car.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onLocationSuccess(AMapLocation location) {

    }

    @Override
    public void onLocationFailure(int failure) {

    }

    @Override
    public void getBusiness_CarSuccess(Business_car business_car) {
        if (business_car != null) {
            showCarPickerView( business_car.getJdata().getCars());
        }
    }

    @Override
    public void getBusiness_CarFailure(String failure) {

    }

    @Override
    public void getCarCodeSuccess(getBuyCarCode carCode) {

    }

    @Override
    public void getCarCodeFailure(String failure) {

    }

    /**
     * 解析json
     */
    public void getJson() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = JsonFileReader.getJson(BaseApplication.mContext, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 城市
     */
    private void showCityPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                KLog.d("城市", tx);
                tv_address.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
    private void showCarPickerView(List<Business_car.JdataBean.CarsBean> list) {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1).getC_name();
                KLog.d("车辆", tx);
                tv_slected_car.setText(tx);
            }
        })

                .setTitleText("车辆选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(list);//三级选择器
        pvOptions.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_city:
                mHandler.sendEmptyMessage(MSG_LOAD_DATA);
                break;
            case R.id.getCode:
                if (ValidateUtils.isMobile(business_phone.getText().toString())){
                    getCode.startGetCount();
                    getCarCode();
                } else {
                    ToastUtils.showLong("请填写正确的手机号");
                }
                break;
            case R.id.tv_select_car:
                getCarList();
                break;

        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    //短信
    private void getCarCode() {
        Map map = new HashMap();
        map.put("phone", null);
        p.getCarCode(map);
    }

    //车辆
    private void getCarList() {
        Map map = new HashMap();
        map.put("token",Constants.token);
        p.getBusiness_Car(map);
    }
}
