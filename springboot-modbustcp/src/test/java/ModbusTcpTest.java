
import com.example.modbustcp.TcpMaster;
import com.example.modbustcp.utils.ModbusTcpReadUtils;
import com.example.modbustcp.utils.ModbusTcpWriteUtils;
import com.serotonin.modbus4j.BatchRead;
import com.serotonin.modbus4j.BatchResults;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guoruifeng
 * @description
 * @date 2019.09.06 10:20
 */
public class ModbusTcpTest {


    public static void main(String[] args) {

        Map<String,String> param = new HashMap<>();
        param.put("ip","192.168.100.250");
        param.put("port","502");
        ModbusMaster master = TcpMaster.getMaster(param);
        try {
            // 站点ID写入测试
            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 2, 20, DataType.FOUR_BYTE_FLOAT);
            // 站点ID读取测试float类型
            byte[] b = ModbusTcpReadUtils.readInputRegistersByte(master, 1, 2, 2);
            //创建一个 ByteArrayInputStream，使用bytes作为其缓冲区数组
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            //再将bais 封装为DataInputStream类型
            DataInputStream dis=new DataInputStream(bais);
            try {
                float flt=dis.readFloat();
                System.out.println(flt);
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (ModbusInitException e) {
            e.printStackTrace();
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        } finally {
            master.destroy();
        }

//        try {
//            // 设置1#选中1#开度
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 9, 1, DataType.TWO_BYTE_INT_SIGNED);
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 10, 600, DataType.TWO_BYTE_INT_SIGNED);
//            // 设置2#选中2#开度
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 19, 1, DataType.TWO_BYTE_INT_SIGNED);
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 20, 600, DataType.TWO_BYTE_INT_SIGNED);
//            // 设置3#选中3#开度
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 29, 1, DataType.TWO_BYTE_INT_SIGNED);
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 30, 600, DataType.TWO_BYTE_INT_SIGNED);
//            // 启动
//            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 0, 1, DataType.TWO_BYTE_INT_SIGNED);
//            // 停止
////            ModbusTcpWriteUtils.writeHoldingRegister(master,1, 0, 0, DataType.TWO_BYTE_INT_SIGNED);
//        } catch (ModbusTransportException e) {
//            e.printStackTrace();
//        } catch (ModbusInitException e) {
//            e.printStackTrace();
//        } catch (ErrorResponseException e) {
//            e.printStackTrace();
//        }
//        getVal(master);
//        七星渠数据采集
//        getQxqScVal(master);
    }

    public static void getVal(ModbusMaster master) {
        System.out.println(master.isConnected());
        ModbusTcpReadUtils modbusTcpReadUtils = new ModbusTcpReadUtils();
        try {
//            short[] ss = modbusTcpReadUtils.readHoldingRegister(master,1, 3,1);
//            System.out.println(ss);
//            System.out.println(ss.length+"#######"+ss.toString());
//            String ll = "";
//            for (int i=0;i<ss.length;i++){
//                ll += ss[i];
//            }
//            System.out.println("@@@@@@@@@"+ll);
//            ModbusTcpWriteUtils.writeRegister(master,1, 4, (short)44);

            // 批量读取
            BatchRead<Integer> batch = new BatchRead<Integer>();
            batch.addLocator(0, BaseLocator.holdingRegister(1, 0, DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(1, BaseLocator.holdingRegister(1, 4,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(2, BaseLocator.holdingRegister(1, 14,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(3, BaseLocator.holdingRegister(1, 6, DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(4, BaseLocator.holdingRegister(1, 8,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(5, BaseLocator.holdingRegister(1, 16,DataType.FOUR_BYTE_FLOAT));


            batch.setContiguousRequests(true);
            BatchResults<Integer> results = master.send(batch);
            System.out.println("batchRead:" + results.getValue(0));
//            System.out.println("batchRead:" + results.getValue(1));
//            System.out.println("batchRead:" + results.getValue(2));
//            System.out.println("batchRead:" + results.getValue(3));
//            System.out.println("batchRead:" + results.getValue(4));
//            System.out.println("batchRead:" + results.getValue(5));
        } catch (ModbusTransportException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
//        } catch (ModbusInitException e) {
//            e.printStackTrace();
        } finally {
            master.destroy();
        }
    }

    /**
     * 七星渠时昶闸门数据批量采集测试
     * @param master
     */
    public static void getQxqScVal(ModbusMaster master) {
        System.out.println(master.isConnected());
        ModbusTcpReadUtils modbusTcpReadUtils = new ModbusTcpReadUtils();
        try {
            // 批量读取
            BatchRead<Integer> batch = new BatchRead<Integer>();
            batch.addLocator(0, BaseLocator.holdingRegister(1, 49, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(1, BaseLocator.holdingRegister(1, 50,DataType.FOUR_BYTE_FLOAT));
            batch.addLocator(2, BaseLocator.holdingRegister(1, 52,DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(3, BaseLocator.holdingRegister(1, 53, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(4, BaseLocator.holdingRegister(1, 89, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(5, BaseLocator.holdingRegister(1, 90, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(6, BaseLocator.holdingRegister(1, 59, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(7, BaseLocator.holdingRegister(1, 69, DataType.TWO_BYTE_INT_SIGNED));
//            batch.addLocator(4, BaseLocator.holdingRegister(1, 60,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(5, BaseLocator.holdingRegister(1, 70,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(3, BaseLocator.holdingRegister(1, 50, DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(4, BaseLocator.holdingRegister(1, 60,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(5, BaseLocator.holdingRegister(1, 70,DataType.FOUR_BYTE_FLOAT));

            batch.setContiguousRequests(true);
            BatchResults<Integer> results = master.send(batch);
            System.out.println("batchRead:" + results.getValue(0));
            System.out.println("batchRead:" + results.getValue(1));
            System.out.println("batchRead:" + results.getValue(2));
            System.out.println("batchRead:" + results.getValue(3));
            System.out.println("batchRead:" + results.getValue(4));
            System.out.println("batchRead:" + results.getValue(5));
            System.out.println("batchRead:" + results.getValue(6));
            System.out.println("batchRead:" + results.getValue(7));
        }catch (ModbusTransportException | ErrorResponseException e) {
            e.printStackTrace();
        } finally {
            master.destroy();
        }
    }

    /**
     * 七星渠黄骅闸门数据批量采集测试
     * @param master
     */
    public static void getQxqHhVal(ModbusMaster master) {
        System.out.println(master.isConnected());
        ModbusTcpReadUtils modbusTcpReadUtils = new ModbusTcpReadUtils();
        try {
            // 批量读取
            BatchRead<Integer> batch = new BatchRead<Integer>();
            batch.addLocator(0, BaseLocator.holdingRegister(1, 49, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(1, BaseLocator.holdingRegister(1, 50,DataType.FOUR_BYTE_FLOAT));
            batch.addLocator(2, BaseLocator.holdingRegister(1, 52,DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(3, BaseLocator.holdingRegister(1, 53, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(4, BaseLocator.holdingRegister(1, 89, DataType.TWO_BYTE_INT_SIGNED));
            batch.addLocator(5, BaseLocator.holdingRegister(1, 90, DataType.TWO_BYTE_INT_SIGNED));
//            batch.addLocator(4, BaseLocator.holdingRegister(1, 60,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(5, BaseLocator.holdingRegister(1, 70,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(3, BaseLocator.holdingRegister(1, 50, DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(4, BaseLocator.holdingRegister(1, 60,DataType.FOUR_BYTE_FLOAT));
//            batch.addLocator(5, BaseLocator.holdingRegister(1, 70,DataType.FOUR_BYTE_FLOAT));

            batch.setContiguousRequests(true);
            BatchResults<Integer> results = master.send(batch);
            System.out.println("batchRead:" + results.getValue(0));
            System.out.println("batchRead:" + results.getValue(1));
            System.out.println("batchRead:" + results.getValue(2));
            System.out.println("batchRead:" + results.getValue(3));
            System.out.println("batchRead:" + results.getValue(4));
            System.out.println("batchRead:" + results.getValue(5));
        }catch (ModbusTransportException | ErrorResponseException e) {
            e.printStackTrace();
        } finally {
            master.destroy();
        }
    }
}
