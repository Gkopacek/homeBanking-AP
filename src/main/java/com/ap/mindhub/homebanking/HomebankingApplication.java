package com.ap.mindhub.homebanking;

import com.ap.mindhub.homebanking.models.Account;
import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.repositories.AccountRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
@Bean
public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args -> {

			Client client1 = new Client("Melba","Morel","melba@mindhub.com");

			LocalDate actualdate1 = LocalDate.now();
			LocalDate tomorrow = actualdate1.plusDays(1);
			Account accountMelba1 = new Account("VIN001",actualdate1,5000);
			Account accountMelba2 = new Account("VIN002",tomorrow,7500);

			Client client2 = new Client("Guillermo","Kopacek","kopacek5@gmail.com");
			Account accountGuillermo1 = new Account("VIN003",actualdate1,1000000);

			clientRepository.save(client1);
			client1.addAccount(accountMelba1);
			client1.addAccount(accountMelba2);
			accountRepository.save(accountMelba1);
			accountRepository.save(accountMelba2);

			clientRepository.save(client2);
			client2.addAccount(accountGuillermo1);
			accountRepository.save(accountGuillermo1);
		});
	}

}
