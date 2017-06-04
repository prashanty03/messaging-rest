package challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ChallengeApplication{

	private static final Logger log = LoggerFactory.getLogger(ChallengeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}
}
