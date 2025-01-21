package demo.open.feign;

import demo.open.feign.client.EchoClient;
import demo.open.feign.client.ServerClient;
import demo.open.feign.dto.EchoDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author : chenbo
 * @date : 2025-01-07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);

        EchoClient echoClient = context.getBean(EchoClient.class);
        EchoDTO echo = echoClient.echo("123");
        System.out.println(echo);

        ServerClient serverClient = context.getBean(ServerClient.class);
        String loginResult = serverClient.login();
        System.out.println(loginResult);
    }
}
