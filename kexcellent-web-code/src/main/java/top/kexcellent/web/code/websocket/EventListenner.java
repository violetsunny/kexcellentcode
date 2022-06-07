package top.kexcellent.web.code.websocket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 监听客户端的连接和断开，从而进行处理
 *
 * @author kll
 * @date 2020/4/13 14:18
 */
@Component
public class EventListenner {

    @Resource
    private ClientCache clientCache;

    /**
     * 客户端连接
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        UUID sessionId = client.getSessionId();
        clientCache.saveClient(userId,sessionId,client);
        System.out.println("建立连接");
    }

    /**
     * 客户端断开
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        clientCache.deleteSessionClient(userId,client.getSessionId());
        System.out.println("关闭连接");
    }

    @OnEvent("chatevent")
    public void onEvent(SocketIOClient client, Message message, AckRequest request) {
        Map<String, List<String>> urlParams = client.getHandshakeData().getUrlParams();
        List<String> userIds = urlParams.get("userId");
        String userId = null;
        if ( !CollectionUtils.isEmpty(userIds)) {
            userId = userIds.get(0);
        }
        System.out.println("通道" + userId + "消息：" + message.getContent());
    }
}
