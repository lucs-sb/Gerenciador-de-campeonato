package api.championship.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ChampionshipManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChampionshipManagerApplication.class, args);

		System.out.println(new BCryptPasswordEncoder().encode("soares"));
	}

}
