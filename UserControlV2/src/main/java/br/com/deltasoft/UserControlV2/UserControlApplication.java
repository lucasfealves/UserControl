package br.com.deltasoft.UserControlV2;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.jboss.jandex.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javassist.CtField.Initializer;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages={""})
@EnableAutoConfiguration
public class UserControlApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UserControlApplication.class, args);
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(
                new Class[] { Main.class, Initializer.class});
    }

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
      sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
      sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
    }
    

}
