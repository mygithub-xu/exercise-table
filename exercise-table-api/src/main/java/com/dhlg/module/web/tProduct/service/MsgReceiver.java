package com.dhlg.module.web.tProduct.service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MsgReceiver {
//    @Autowired
//    TProductServiceImpl doservice;
//
//    @RabbitListener(queues = "test")
//    public void receive(@Payload Integer userId, @Headers Channel channel, Message message)throws IOException {
//        log.info("用户{}开始抢单", userId);
//        try {
//            //处理消息
//            doservice.robbingProduct(userId);
////             确认消息已经消费成功
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
//            log.error("消费处理异常:{} - {}", userId, e);
////             拒绝当前消息，并把消息返回原队列
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//        }
//    }
//    @RabbitListener(queues = "test")
//    public void receive2(String o){
//
//        System.out.println("收到消息："+o);
//    }
}
