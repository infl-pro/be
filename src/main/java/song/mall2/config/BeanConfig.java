package song.mall2.config;

import com.siot.IamportRestClient.IamportClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BeanConfig {
    @Value("${portone.imp-key}")
    private String impKey;
    @Value("${portone.imp-secret}")
    private String impSecret;
    @Value("${portone.api-secret}")
    private String apiSecret;

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(impKey, impSecret, apiSecret, 1L);
    }
}
