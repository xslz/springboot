package com.example.modbustcp.utils;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.*;

/**
 * @author guoruifeng
 * @description
 * @date 2019.09.06 11:01
 */

public class ModbusTcpWriteUtils {

    /**
     * 写单个（线圈）开关量数据
     *
     * @param slaveId     slave的ID
     * @param writeOffset 位置
     * @param writeValue  值
     * @return 是否写入成功
     */
    public static boolean writeCoil(ModbusMaster tcpMaster, int slaveId, int writeOffset, boolean writeValue)
            throws ModbusTransportException, ModbusInitException {
        tcpMaster.setTimeout(2000);
        // 创建请求
        WriteCoilRequest request = new WriteCoilRequest(slaveId, writeOffset, writeValue);
        // 发送请求并获取响应对象
        WriteCoilResponse response = (WriteCoilResponse) tcpMaster.send(request);
        return !response.isException();
    }

    /**
     * 写多个开关量数据（线圈）
     *
     * @param slaveId     slaveId
     * @param startOffset 开始位置
     * @param bdata       写入的数据
     * @return 是否写入成功
     */
    public static boolean writeCoils(ModbusMaster tcpMaster, int slaveId, int startOffset, boolean[] bdata)
            throws ModbusTransportException, ModbusInitException {
        tcpMaster.setTimeout(2000);
        // 创建请求
        WriteCoilsRequest request = new WriteCoilsRequest(slaveId, startOffset, bdata);
        // 发送请求并获取响应对象
        WriteCoilsResponse response = (WriteCoilsResponse) tcpMaster.send(request);
        return !response.isException();

    }

    /***
     *  保持寄存器写单个
     *
     * @param slaveId slaveId
     * @param writeOffset 开始位置
     * @param writeValue 写入的数据
     */
    public static boolean writeRegister(ModbusMaster tcpMaster, int slaveId, int writeOffset, short writeValue)
            throws ModbusTransportException, ModbusInitException {
        tcpMaster.setTimeout(2000);
        // 创建请求对象
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, writeOffset, writeValue);
        // 发送请求并获取响应对象
        WriteRegisterResponse response = (WriteRegisterResponse) tcpMaster.send(request);
        return !response.isException();

    }

    /**
     * 保持寄存器写入多个模拟量数据
     *
     * @param slaveId     modbus的slaveID
     * @param startOffset 起始位置偏移量值
     * @param sdata       写入的数据
     * @return 返回是否写入成功
     */
    public static boolean writeRegisters(ModbusMaster tcpMaster, int slaveId, int startOffset, short[] sdata)
            throws ModbusTransportException, ModbusInitException {
        tcpMaster.setTimeout(2000);
        // 创建请求对象
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, startOffset, sdata);
        // 发送请求并获取响应对象
        WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);
        return !response.isException();
    }

    /**
     * 根据类型写数据（如:写入Float类型的模拟量、Double类型模拟量、整数类型Short、Integer、Long）
     *
     * @param value    写入值
     * @param dataType com.serotonin.modbus4j.code.DataType
     */
    public static void writeHoldingRegister(ModbusMaster tcpMaster, int slaveId, int offset, Number value, int dataType)
            throws ModbusTransportException, ErrorResponseException, ModbusInitException {
        // 类型
        BaseLocator<Number> locator = BaseLocator.holdingRegister(slaveId, offset, dataType);
        tcpMaster.setTimeout(2000);
        tcpMaster.setValue(locator, value);
    }
}
