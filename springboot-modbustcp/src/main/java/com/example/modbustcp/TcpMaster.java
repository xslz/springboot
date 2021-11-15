package com.example.modbustcp;

import cn.hutool.core.util.StrUtil;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;

import java.util.Map;

/**
 * @author guoruifeng
 * @description
 * @date 2019.09.06 10:58
 */
public class TcpMaster {

    private static ModbusFactory modbusFactory;

    static {
        if (modbusFactory == null) {
            modbusFactory = new ModbusFactory();
        }
    }

    /**
     * 获取master
     *
     * @return master
     */
    public static ModbusMaster getMaster(Map<String, String> param) {
        String ip = param.get("ip");
        if(StrUtil.isBlank(ip)){
            System.err.println("IP地址为空");
            return null;
        }
        String port = param.get("port");
        if(StrUtil.isBlank(port)){
            System.err.println("端口地址为空");
            return null;
        }
        IpParameters params = new IpParameters();
        params.setHost(ip);
        params.setPort(Integer.parseInt(port));
        //设置为true，会导致TimeoutException: request=com.serotonin.modbus4j.ip.encap.EncapMessageRequest@774dfba5",
        params.setEncapsulated(false);
        ModbusMaster master = modbusFactory.createTcpMaster(params, true);// TCP 协议
        try {
            //设置超时时间
            master.setTimeout(2000);
            //设置重连次数
            master.setRetries(3);
            //初始化
            master.init();
            // 设置是否连接
            master.setConnected(true);
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
        return master;
    }

    // 关闭连接
    public void closeConnect(ModbusMaster master){
        master.destroy();
    }
}
