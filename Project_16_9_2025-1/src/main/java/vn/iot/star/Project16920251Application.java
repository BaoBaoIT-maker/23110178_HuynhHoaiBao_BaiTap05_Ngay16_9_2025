package vn.iot.star;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"vn.iot.star.controller"})
@ComponentScan
public class Project16920251Application {

	public static void main(String[] args) {
		SpringApplication.run(Project16920251Application.class, args);
	}

}
