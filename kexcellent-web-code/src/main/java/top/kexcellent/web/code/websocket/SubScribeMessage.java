/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.web.code.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kanglele01
 * @version $Id: SubScribeMessage, v 0.1 2020/4/26 13:47 kanglele01 Exp $
 */
@Component
public class SubScribeMessage {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static AtomicInteger count = new AtomicInteger(0);

    //定时任务发布消息（每十秒执行一次）
    @Scheduled(cron = "*/30 * * * * ?")//运行周期时间可配
    public void publicMarketValueMessage() {
        //这里的nowDate 暂且作为订阅的内容--可更换为具体的业务数据
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String response;
        int number = count.getAndIncrement();
        if(number%4==0){
            response = getJson("over");
        } else {
            response = getJson("");
        }

        //这里的destination 是 订阅的地址
        String destination = "/topic/rcmdResult";
        try {
            System.out.println("发送出去="+response);
            this.messagingTemplate.convertAndSend(destination, response);
        } catch (Exception e) {
        }
    }

    String getJson(String callStatus){

        return "{\n" +
                "\"agentId\":1001,\n" +
                "\"guid\":\"test1001\",\n" +
                "\"flowName\":\"投诉处理\",\n" +
                "\"callStatus\":\""+callStatus+"\",\n" +
                "\"content\":\"我要投诉你们\",\n" +
                "\"highlightKeywords\":[\"投诉\"],\n" +
                "\"scene\":\"投诉\",\n" +
                "\"vars\":[],\n" +
                "\"speeches\":[{\n" +
                "\"topic\":\"安抚\",\n" +
                "\"speech\":\"建议您user_name赶紧把电话self_phone_num删除，你maxdefaultdays total_amount需要还owingamount利息owinginterest\"\n" +
                "},{\n" +
                "\"topic\":\"情绪\",\n" +
                "\"speech\":\"你是user_namebirth_yearageid_address么\"\n" +
                "}\n" +
                "]\n" +
                "}";
    }


    //定时任务发布消息（每十秒执行一次）
    //@Scheduled(cron = "*/30 * * * * ?")//运行周期时间可配
    public void publicVerbalMonitorMessage() {
        //这里的nowDate 暂且作为订阅的内容--可更换为具体的业务数据
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String response;
        int number = count.getAndIncrement();
        if(number%2==0){
            if(number%6==0){
                response=getMonitorJson("USER",getJson("over"),"over",true);
            }else {
                response=getMonitorJson("USER",getJson(""),"",true);
            }
        } else {
            response=getMonitorJson("AGENT",null,"",true);
        }


        //这里的destination 是 订阅的地址
        String destination = "/topic/rcmdResult/monitor";
        try {
            System.out.println("发送出去="+response);
            this.messagingTemplate.convertAndSend(destination, response);
        } catch (Exception e) {
        }
    }

    String getMonitorJson(String role,String recommendModelResult,String callStatus,Boolean isHit){

        return "{\"agentId\":260017,\n" +
                "\"conversation\":{\n" +
                "\"content\":\"我要投诉你们,不,你不需要\",\n" +
                "\"role\":\""+role+"\"" +
                "},\n" +
                "\"recommendModelResult\":"+recommendModelResult+",\n" +
                "\"callStatus\":\""+callStatus+"\",\n" +
                "\"isHit\":"+isHit+"\n" +
                "}";
    }
}
