package bootcamp.com.cardms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableEurekaClient
public class CardMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardMsApplication.class, args);
	}

}
