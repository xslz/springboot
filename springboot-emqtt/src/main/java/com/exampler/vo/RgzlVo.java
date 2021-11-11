package com.exampler.vo;

import cn.hutool.json.JSONObject;

/**
 * @version 1.0.0
 * @description:
 * @author: guorf
 * @time: 2021/11/3 15:18
 */
public class RgzlVo {

    private String sn;

    private String productKey;

    private String deviceName;

    private JSONObject params;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }
}
