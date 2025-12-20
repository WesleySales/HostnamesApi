package com.wesley.ValidadorHostnames;

//import com.wesley.ValidadorHostnames.services.Postgre.WhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching

// Define onde estão os repositórios JPA (SQL)
@EnableJpaRepositories(basePackages = "com.wesley.ValidadorHostnames.repositories.Postgre")
// Define onde estão os repositórios MongoDB (NoSQL)
@EnableMongoRepositories(basePackages = "com.wesley.ValidadorHostnames.repositories.MongoDB")
public class ValidadorHostnamesApplication implements CommandLineRunner {

	@Autowired
//	private WhitelistService whitelistService;

	public static void main(String[] args) {
		SpringApplication.run(ValidadorHostnamesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		whitelistService.isAllowed("api.example.com");
//		whitelistService.isAllowed("api.example.com");

//		System.out.println(whitelistService.listaDeHosts());
//		System.out.println(whitelistService.listaDeHosts());


	}

}
