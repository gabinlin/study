package top.gabin.demo.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

@Slf4j
public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        TransactionMQProducer producer = new TransactionMQProducer("group-gabin");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        // 二进制安全
        producer.send(new Message("topic-gabin", "同步消息".getBytes()));
        producer.send(new Message("topic-gabin", "异步消息".getBytes()), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(sendResult.getMsgId() + ":" + sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable e) {

            }
        });
        producer.send(new Message("topic-gabin", "异步消息".getBytes()), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info(sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable e) {

            }
        });
        producer.setTransactionListener(new TransactionListener() {

            // 2、老婆：可以，晚上准时下班
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                log.info("3、老公：那我出发了，开车中……");
                // 4.1、老公：我到了。可以下来了    提交状态，直接把消息提交了，算是事务提交结束了
//                return LocalTransactionState.COMMIT_MESSAGE;
                // 4.2、老公：临时有事，没法去接你了     回滚状态，把消息回滚了，事务回滚结束
//                return LocalTransactionState.ROLLBACK_MESSAGE;
                // 4.3、老公：临时有事，需要等一会，不确定时间    未知状态，则会触发checkLocalTransaction
                return LocalTransactionState.UNKNOW;
            }
            // 5.1、老婆:那我下来了
            // 5.2、老婆:那我自己打车了
            // 5.3、老婆:那我再等等吧，等会给你打电话

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                // 回查机制
                log.info("6.3、老婆：打电话给老公，开这么久，到了没啊");
                // 7.1、老公：还没呢，领导有事找，还不确定什么时候能到
                return LocalTransactionState.UNKNOW;
                // 7.2、已经到了，忘了给你打电话了
//                return LocalTransactionState.COMMIT_MESSAGE;
                // 7.3、去不了了，老婆，领导要求加班
//                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
            // 8.1、老婆多次打电话都没办法确认，算了，我自己打车回去吧
        });
        producer.sendMessageInTransaction(new Message("topic-gabin", "1、我：老婆，晚上接你吗".getBytes()) ,  null);
    }

}
