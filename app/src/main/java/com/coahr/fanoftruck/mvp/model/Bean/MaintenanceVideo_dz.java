package com.coahr.fanoftruck.mvp.model.Bean;

/**
 * Created by Leehor
 * on 2018/11/27
 * on 23:53
 * 点赞接口
 */
public class MaintenanceVideo_dz {

    /**
     * code : 10103
     * msg : token不存在或已过期，请重新登录
     * jdata : {}
     */

    private int code;
    private String msg;
    private JdataBean jdata;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JdataBean getJdata() {
        return jdata;
    }

    public void setJdata(JdataBean jdata) {
        this.jdata = jdata;
    }

    public static class JdataBean {

    }
}
