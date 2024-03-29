package song.mall2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Mall2Application {

	public static void main(String[] args) {
		SpringApplication.run(Mall2Application.class, args);
	}
}
