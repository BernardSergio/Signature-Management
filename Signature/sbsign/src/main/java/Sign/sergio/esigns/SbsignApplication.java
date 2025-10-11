package Sign.sergio.esigns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {
		"Sign.sergio.esigns.entity" // <- entity package from Signdata
})
@EnableJpaRepositories(basePackages = {
		"Sign.sergio.esigns.repository" // <- repository package from Signdata
})
public class SbsignApplication {
	public static void main(String[] args) {
		SpringApplication.run(SbsignApplication.class, args);
	}
}
