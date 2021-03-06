package com.coahr.fanoftruck.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Author： hengzwd on 2018/8/13.
 * Email：hengzwdhengzwd@qq.com
 */
public class MapUtils {


    /**
     * 获取打开百度地图应用
     * @param context 上下文对象
     * @param originLat 起点经度
     * @param originLon 起点纬度
     * @param desLat 终点经度
     * @param desLon 终点纬度
     * @return
     *
     * mode:导航模式，可选transit（公交）、driving（驾车）、walking（步行）和riding（骑行）.默认:driving
     *
     */
    public static void getBaiduMapUri(Context context, String originLat, String originLon, String desLat, String desLon){
        String uri="baidumap://map/direction?origin=name:我的位置|latlng:"+originLat+","+originLon+"&destination=name:目的地|latlng:"+desLat+","+desLon+"&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.baidu.BaiduMap");
        context.startActivity(intent);
    }


    /**
     * 获取打开百度地图应用，不需要自己定位，进入百度地图后，百度地图帮我们定位起点
     * @param context 上下文对象
     * @param desLat 终点经度
     * @param desLon 终点纬度
     * @return
     *
     * mode:导航模式，可选transit（公交）、driving（驾车）、walking（步行）和riding（骑行）.默认:driving
     *
     */
    public static void getBaiduMapUri(Context context,  String desLat, String desLon){
        String uri="baidumap://map/direction?destination=name:目的地|latlng:"+desLat+","+desLon+"&mode=driving";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.baidu.BaiduMap");
        context.startActivity(intent);
    }

    /**
     * 启动高德App进行导航
     *
     * @param slat 起点纬度。如果不填写此参数则自动将用户当前位置设为起点纬度。
     * @param slon 起点经度。如果不填写此参数则自动将用户当前位置设为起点经度。
     * @param dlat 终点纬度
     * @param dlon 终点经度
     *
     *  dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     *  t 必填 t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）(骑行仅在V788以上版本支持）
     *
     */
    public static  void getGaoDeMapUri(Context context, String slat , String slon ,String dlat , String dlon){
        String uri="amapuri://route/plan/?slat="+slat+"&slon="+slon+"&sname=我的位置&dlat="+dlat+"&dlon="+dlon+"&dname=目的地&dev=0&t=0";
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }
    /**
     * 启动高德App进行导航 不需要自己定位，进入高德后，高德地图帮我们定位起点
     *
     * @param dlat 终点纬度
     * @param dlon 终点经度
     *
     *  dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     *  t 必填 t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）(骑行仅在V788以上版本支持）
     *
     */
    public static  void getGaoDeMapUri(Context context,String dlat , String dlon){
        String uri="amapuri://route/plan/?dlat="+dlat+"&dlon="+dlon+"&dname=目的地&dev=0&t=0";
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }


    /**
     * 根据包名检测某个APP是否安装
     * @param packageName 包名
     * @return true 安装 false 没有安装
     */
    public static boolean isInstallByRead(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 百度地图定位经纬度转高德经纬度
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
        gd_lat_lon[0] = z * Math.cos(theta);
        gd_lat_lon[1] = z * Math.sin(theta);
        return gd_lat_lon;
    }

    /**
     * 高德地图定位经纬度转百度经纬度
     * @param gd_lon
     * @param gd_lat
     * @return
     */
    public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
        double[] bd_lat_lon = new double[2];
        double PI = 3.14159265358979324 * 3000.0 / 180.0;
        double x = gd_lon, y = gd_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
        bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
        bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
        return bd_lat_lon;
    }
}
