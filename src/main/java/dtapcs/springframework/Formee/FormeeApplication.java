package dtapcs.springframework.Formee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FormeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormeeApplication.class, args);
	}

}
