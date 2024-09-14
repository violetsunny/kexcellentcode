/**
 * LY.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package top.kexcellent.web.code.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author kanglele01
 * @version $Id: WebSocketConfig, v 0.1 2020/4/26 13:43 kanglele01 Exp $
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /*
         * 用户可以订阅来自以"/topic"为前缀的消息，
         * 客户端只可以订阅这个前缀的主题
         */
        config.enableSimpleBroker("/topic");
        /*
         * 客户端发送过来的消息，需要以"/app"为前缀，再经过Broker转发给响应的Controller,
         * 我这里基本没用到，声明有说
         */
        config.setApplicationDestinationPrefixes("/app");

        System.out.println("注册订阅");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * 路径"/webSocketEndPoint"被注册为STOMP端点，对外暴露，客户端通过该路径接入WebSocket服务
         */
        //registry.addEndpoint("websocket/socketServer").withSockJS();
        //添加一个/websocket端点，客户端就可以通过这个端点来进行连接；withSockJS作用是添加SockJS支持
        //registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
        registry.addEndpoint("/websocket").setAllowedOrigins("*");


        System.out.println("注册连接");
    }
}
