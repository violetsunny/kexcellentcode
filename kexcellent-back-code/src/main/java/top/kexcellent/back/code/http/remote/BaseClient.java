package top.kexcellent.back.code.http.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import top.kdla.framework.exception.BizException;
import top.kexcellent.back.code.http.remote.impl.BaseHTTPClientImpl;

import java.util.Map;

@Service("baseClient")
@Slf4j
public class BaseClient extends BaseHTTPClientImpl {


    protected HttpEntity<String> getResponse(String url, Map<String,Object> map) throws Exception {
        log.info("OtaBaseClient getResponse param:{}", JSON.toJSONString(map));
        HttpEntity<String> entity = null;
        if(null == map){
            entity = get(url);
        }else {
            entity = post(url,map);
        }

        log.info("OtaBaseClient getResponse entity:{}", JSON.toJSONString(entity));
        JSONObject objRes = JSON.parseObject(JSONObject.toJSON(entity).toString());
        if(!objRes.getInteger("statusCodeValue").equals(200)){
            throw new BizException("","");
        }
        return entity;
    }


}
