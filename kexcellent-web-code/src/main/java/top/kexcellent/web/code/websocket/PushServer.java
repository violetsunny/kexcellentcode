package top.kexcellent.web.code.websocket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 启动服务
 *
 * @author kll
 * @date 2020/4/13 14:16
 */
@Component
public class PushServer implements InitializingBean {

    @Resource
    private EventListenner eventListenner;

    @Value("${push.server.port}")
    private int serverPort;

    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration config = new Configuration();
        config.setPort(serverPort);

        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        config.setSocketConfig(socketConfig);
        config.setHostname("localhost");

        SocketIOServer server = new SocketIOServer(config);
        server.addListeners(eventListenner);
        server.start();
        System.out.println("启动正常");
    }
}
