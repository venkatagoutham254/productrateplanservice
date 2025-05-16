package aforo.productrateplanservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "aforo.productrateplanservice")
@EntityScan(basePackages = "aforo.productrateplanservice")
public class ProductrateplanservieApplication {
	public static void main(final String[] args) {
		SpringApplication.run(ProductrateplanservieApplication.class, args);
	}
}
