package p.minn.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;
/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
//@EnableHystrix
//@EnableHystrixDashboard
@ImportResource({"classpath*:/spring/spring-mvc.xml"
		,"classpath*:/spring/applicationContext-privilege.xml"
		,"classpath*:/spring/applicationContext-hadoopspark.xml"
		,"classpath*:/spring/applicationContext-spring-security.xml"
		,"classpath*:/spring/ignite/applicationContext-ignite.xml"
		})

public class Application extends SpringBootServletInitializer{
	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	  }
	@Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
