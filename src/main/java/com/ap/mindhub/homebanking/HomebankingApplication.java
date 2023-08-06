package com.ap.mindhub.homebanking;

import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
@Bean
public CommandLineRunner initData(ClientRepository clientRepository){
		return (args -> {

			Client client1 = new Client("Melba","Morel","Melba@mindhub.com");
			Client client2 = new Client("Guillermo","Kopacek","kopacek5@gmail.com");

			clientRepository.save(client1);
			clientRepository.save(client2);
		});
	}

}
