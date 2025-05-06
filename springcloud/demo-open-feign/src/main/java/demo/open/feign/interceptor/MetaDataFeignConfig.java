package demo.open.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * feign拦截器配置，调用前先鉴权.
 * @author : chenbo
 * @date : 2025-01-21
 */
@Component
public class MetaDataFeignConfig implements RequestInterceptor {
    public MetaDataFeignConfig() {
    }
    /**
     * 给feign请求加上accessToken请求头.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        //feign加请求头 自定义fangjia.auth.token"
        template.header("access_token", "123");
    }
}
