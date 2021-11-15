package com.example.modbustcp;

import cn.hutool.core.util.StrUtil;
import com.serotonin.modbus4j.ModbusMaster;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guoruifeng
 * @description
 * @date 2019.09.06 16:46
 */
public class TcpMasterCache {
    private static TcpMasterCache ins = new TcpMasterCache();
    public static TcpMasterCache getInstance() {
        return ins;
    }

    /**
     * 闸门PlC在线列表
     */
    public Map<String, ModbusMaster> onlineMap = new HashMap<>();

    /**
     * 判断闸门是否在线
     * @param ip
     * @return
     */
    public Boolean isOnline(String ip){
        ModbusMaster modbusMaster = this.onlineMap.get(ip);
        if(StrUtil.isEmptyIfStr(modbusMaster)){
            return false;
        } else {
            return true;
        }
    }
}
