package pl.franasze.wszib.SpringBookstoreApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringBookstoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBookstoreAppApplication.class, args);
	}

}
