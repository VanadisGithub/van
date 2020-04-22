package taobao;

import com.google.common.base.Strings;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author yaoyuan
 */
public class HttpUtils {

    private static int timeout = 5000;
    private static RequestConfig baseRequestConfig = RequestConfig.custom()
            .setMaxRedirects(20)
            .setCircularRedirectsAllowed(true)
            .setConnectTimeout(timeout)
            .setSocketTimeout(timeout)
            .build();

    public static String get(String url) {
        return http(new HttpGet(url), null, null, true);
    }

    public static String get(String url, HttpHost proxy) {
        return http(new HttpGet(url), null, proxy, true);
    }

    public static String get(String url, Map<String, Object> headerMap, HttpHost proxy) {
        return http(new HttpGet(url), headerMap, proxy, true);
    }

    public static String post(String url, String dataStr, Map<String, Object> headerMap, HttpHost proxy) {
        HttpPost post = new HttpPost(url);
        if (Strings.isNullOrEmpty(dataStr)) {
            StringEntity entity = new StringEntity(dataStr, Charset.forName("utf-8"));
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            post.setEntity(entity);
        }
        return http(post, headerMap, proxy, true);
    }

    public static String visit(String url, HttpHost proxy) {
        return http(new HttpGet(url), null, proxy, false);
    }

    public static <T extends HttpRequestBase> String http(T http, Map<String, Object> headerMap, HttpHost proxy, boolean isLog) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        if (proxy != null) {
            RequestConfig requestConfig = RequestConfig.copy(baseRequestConfig).setProxy(proxy).build();
            http.setConfig(requestConfig);
        }

        //默认添加请求头
        http.addHeader("user-agent", HttpUserAgentPools.getUserAgent());


        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                http.setHeader(key, String.valueOf(headerMap.get(key)));
            }
        }

        try {
            HttpResponse response = httpClient.execute(http);
            int statusCode = response.getStatusLine().getStatusCode();
//            log.info("[Vap.HttpUtils.{}] {} {}", http.getMethod(), http.getURI(), statusCode);

            if (statusCode == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (IOException e) {
            if (isLog) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public enum Method {
        GET, POST
    }
}
