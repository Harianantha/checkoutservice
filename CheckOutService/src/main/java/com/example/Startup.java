package com.example;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
//import com.netflix.hystrix.dashbaord.stream.MockStreamServlet;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableHystrix
@ComponentScan("com.example")
@EnableHystrixDashboard
public class Startup extends SpringBootServletInitializer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SpringApplication.run(Startup.class, args);
		new SpringApplicationBuilder(Startup.class).web(true).run(args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Startup.class).web(true);
    }
	
	@Bean
    public Docket newsApi() {
		Docket dc=new Docket(DocumentationType.SWAGGER_2);
		dc.groupName("greetings");
		dc.apiInfo(apiInfo());
		//dc.pathMapping("/order/checkout");
		dc.select().build();
		//dc.pathMapping("/order/checkout");
		return dc;

		
        /*return new Docket(DocumentationType.SWAGGER_2)
                .groupName("greetings")
                .apiInfo(apiInfo())
                .select()
                .paths("/greeting.*"))
                .build();*/
    }
     
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Sample with Swagger")
                .description("Spring REST Sample with Swagger")
                .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact("Niklas Heidloff")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
    @Bean
    public ServletRegistrationBean mockStreamServlet() {
        return new ServletRegistrationBean(new MockStreamServlet(), "/mock.stream");
    }


}
