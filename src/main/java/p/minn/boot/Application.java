package p.minn.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * 
 * @author minn 
 * @QQ:3942986006
 * 
 */

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@ImportResource({"classpath*:/spring/spring-mvc.xml"
		,"classpath*:/spring/applicationContext-privilege.xml"
		,"classpath*:/spring/applicationContext-workflow.xml"
		,"classpath*:/spring/applicationContext-hadoopspark.xml"
		,"classpath*:/spring/applicationContext-spring-security.xml"
		,"classpath*:/spring/ignite/applicationContext-ignite.xml"})

public class Application extends SpringBootServletInitializer{
	public static void main(String[] args) {
	    SpringApplication.run(Application.class, args);
	  }
}
