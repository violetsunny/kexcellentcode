package top.kexcellent.back.code.http.remote;

import org.springframework.http.HttpEntity;

import java.util.List;
import java.util.Map;

public interface BaseHTTPClient {

    HttpEntity<String> get(String url);

    HttpEntity<String> get(String url, Map<String, Object> params);

    HttpEntity<String> post(String url, Map<String, Object> body);

    HttpEntity<String> post(String url, List body);
}
