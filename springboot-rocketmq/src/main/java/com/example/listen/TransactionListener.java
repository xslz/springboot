package com.example.listen;

import com.alibaba.fastjson.JSONObject;
import com.example.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;

@Slf4j
@RocketMQTransactionListener
public class TransactionListener implements RocketMQLocalTransactionListener {
    /**
     * 执行本地事务
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            String rocketmqKeys = msg.getHeaders().get("rocketmq_KEYS").toString();
            log.info("事务消息key为:{}", rocketmqKeys);
            String payload = new String((byte[]) msg.getPayload());
            log.info("事务消息为:{}", payload);
            User user = JSONObject.parseObject(payload, User.class);
//            userInfoService.saveUserInfoAndLogInfo(userInfo, rocketmqKeys);
        } catch (Exception e) {
            log.error("发送事务消息异常!异常信息为:{}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 校验本地事务
     * @param msg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        String rocketmqKeys = msg.getHeaders().get("rocketmq_KEYS").toString();
        log.info("事务消息key为:{}", rocketmqKeys);
//        User logInfo = logInfoService.selectLogInfoByKey(rocketmqKeys);
//        log.info("查询日志信息为:{}", JSON.toJSONString(logInfo));
//        if (null == logInfo) {
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
        return RocketMQLocalTransactionState.COMMIT;
    }
}
