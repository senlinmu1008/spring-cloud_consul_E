package net.zhaoxiaobin.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
public class ZuulNoRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulNoRegisterApplication.class, args);
    }

}
