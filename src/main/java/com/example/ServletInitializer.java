package com.example;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * An opinionated WebApplicationInitializer to run a SpringApplication from a
 * traditional WAR deployment. Binds Servlet, Filter and
 * ServletContextInitializer beans from the application context to the servlet
 * container.
 * 
 * To configure the application either override the
 * configure(SpringApplicationBuilder) method (calling
 * SpringApplicationBuilder.sources(Object)) or make the initializer itself
 * a @Configuration. If you are using SpringBootServletInitializer in
 * combination with other WebApplicationInitializers you might also want to add
 * an @Ordered annotation to configure a specific startup order.
 * 
 * @author Ramin Esfandiari </br>
 *         7. aug. 2017 </br>
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OrderSoftApplication.class);
	}
}
