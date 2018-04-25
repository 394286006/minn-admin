package p.minn.boot;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

import p.minn.filter.MyAuthFilter;

/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */
@SpringBootApplication
@EnableAutoConfiguration
//@EnableDiscoveryClient
//@EnableEurekaClient
//@EnableHystrix
//@EnableHystrixDashboard
@ImportResource({"classpath*:/spring/spring-mvc.xml"
		,"classpath*:/spring/applicationContext-privilege.xml"
		//,"classpath*:/spring/applicationContext-hadoopspark.xml"
		//,"classpath*:/spring/applicationContext-spring-security.xml"
		//,"classpath*:/spring/ignite/applicationContext-ignite.xml"
		})

public class Application extends SpringBootServletInitializer{
	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	  }
	

   // @Bean
    public FilterRegistrationBean myFilterRegistration () {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new MyAuthFilter());
        frb.setUrlPatterns(Arrays.asList("/*"));
        return frb;
    }
}
