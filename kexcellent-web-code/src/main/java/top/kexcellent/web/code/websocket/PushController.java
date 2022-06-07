package top.kexcellent.web.code.websocket;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author kll
 * @date 2020/4/13 15:13
 */
@RestController
@RequestMapping(value = "/push")
public class PushController {

    @Autowired
    private ClientCache clientCache;

    @GetMapping("/user/{userId}")
    public String pushTuUser(@PathVariable("userId") String userId) {
        HashMap<UUID, SocketIOClient> userClient = clientCache.getUserClient(userId);
        userClient.forEach((uuid, socketIOClient) -> {
            //向客户端推送消息
            Message message = new Message();
            message.setUserId(userId);
            message.setContent(userId + "你好，我是服务端");
            socketIOClient.sendEvent("chatevent",message);
        });
        return "success";
    }
}
