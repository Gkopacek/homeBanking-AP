package com.ap.mindhub.homebanking;

import com.ap.mindhub.homebanking.models.Account;
import com.ap.mindhub.homebanking.models.Client;
import com.ap.mindhub.homebanking.models.Transaction;
import com.ap.mindhub.homebanking.models.TransactionType;
import com.ap.mindhub.homebanking.repositories.AccountRepository;
import com.ap.mindhub.homebanking.repositories.ClientRepository;
import com.ap.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
@Bean
public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args -> {

			Client client1 = new Client("Melba","Morel","melba@mindhub.com");

			LocalDate actualdate1 = LocalDate.now();
			LocalDate tomorrow = actualdate1.plusDays(1);
			Account accountMelba1 = new Account("VIN001",actualdate1,5000);
			Account accountMelba2 = new Account("VIN002",tomorrow,7500);

			Client client2 = new Client("Guillermo","Kopacek","kopacek5@gmail.com");
			Account accountGuillermo1 = new Account("VIN003",actualdate1,1000000);


			LocalDateTime actualDatetime1 = LocalDateTime.now();
			LocalDateTime tomorrowDateTime = actualDatetime1.plusDays(32);
			Transaction transactionMelba1 = new Transaction(-300,"Compra en el chino",actualDatetime1, TransactionType.DEBIT);

			Transaction transactionMelba2 = new Transaction(4000, "deposito suledo", actualDatetime1, TransactionType.CREDIT);
			Transaction transactionMelba3 = new Transaction(-4000, "gastos varios", actualDatetime1, TransactionType.DEBIT);

			Transaction transactionGuillermo1 = new Transaction(100000, "deposito sueldo", tomorrowDateTime, TransactionType.CREDIT);

			clientRepository.save(client1);
			client1.addAccount(accountMelba1);
			client1.addAccount(accountMelba2);

			accountRepository.save(accountMelba1);
			accountMelba1.addTransaction(transactionMelba1);
			transactionRepository.save(transactionMelba1);


			accountRepository.save(accountMelba2);
			accountMelba2.addTransaction(transactionMelba2);
			transactionRepository.save(transactionMelba2);
			accountMelba2.addTransaction(transactionMelba3);
			transactionRepository.save(transactionMelba3);


			clientRepository.save(client2);
			client2.addAccount(accountGuillermo1);

			accountRepository.save(accountGuillermo1);
			accountGuillermo1.addTransaction(transactionGuillermo1);
			transactionRepository.save(transactionGuillermo1);
		});
	}

}
