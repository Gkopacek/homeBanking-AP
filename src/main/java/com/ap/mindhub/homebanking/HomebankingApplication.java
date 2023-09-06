package com.ap.mindhub.homebanking;

import com.ap.mindhub.homebanking.models.*;
import com.ap.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

@Bean
public CommandLineRunner initData(ClientRepository clientRepository,
								  AccountRepository accountRepository,
								  TransactionRepository transactionRepository,
								  LoanRepository loanRepository,
								  ClientLoanRepository clientLoanRepository,
						          CardRepository cardRepository         ){
		return (args -> {
			//Se crean los clientes y las cuentas de prueba
			Client client1 = new Client("Melba","Morel","melba@mindhub.com",passwordEncoder.encode("12345"), RoleType.CLIENT);

			LocalDate actualdate1 = LocalDate.now();
			LocalDate tomorrow = actualdate1.plusDays(1);
			Account accountMelba1 = new Account("VIN001",actualdate1,5000);
			Account accountMelba2 = new Account("VIN002",tomorrow,7500);

			Client client2 = new Client("Guillermo","Kopacek","kopacek5@gmail.com",passwordEncoder.encode("098765"), RoleType.ADMIN);
			Account accountGuillermo1 = new Account("VIN003",actualdate1,1000000);


			LocalDateTime actualDatetime1 = LocalDateTime.now();
			LocalDateTime tomorrowDateTime = actualDatetime1.plusDays(32);
			LocalDate fiveYearsDate = actualdate1.plusYears(5);
			Transaction transactionMelba1 = new Transaction(-300,"Compra en el chino",actualDatetime1, TransactionType.DEBIT);

			//Se crean las transacciones de prueba
			Transaction transactionMelba2 = new Transaction(4000, "deposito suledo", actualDatetime1, TransactionType.CREDIT);
			Transaction transactionMelba3 = new Transaction(-4000, "gastos varios", actualDatetime1, TransactionType.DEBIT);

			Transaction transactionGuillermo1 = new Transaction(100000, "deposito sueldo", tomorrowDateTime, TransactionType.CREDIT);

			//Se crean la lista de enteros para colocar en payments
			List<Integer> hipotecarioPayments = List.of(12,24,36,48,60);
			List<Integer> personalPayments = List.of(6,12,24);
			List<Integer> automotivePayments = List.of(6,12,24,36);

			//Se Crean los tipos de prestamos de prueba
			Loan loanMortgage = new Loan("Mortgage", 500000, hipotecarioPayments);
			Loan loanPersonal = new Loan("Personal", 100000, personalPayments);
			Loan loanAutomotive = new Loan("automotive", 300000, automotivePayments);


			ClientLoan loanMelba1 =  new ClientLoan(400000, 60, client1, loanMortgage);

			//Préstamo Personal, 50.000, 12 cuotas
			ClientLoan loanMelba2 =  new ClientLoan(50000,12,client1,loanPersonal);

			//Préstamo Personal, 100.000, 24 cuotas
			ClientLoan loanGuillermo1 = new ClientLoan(100000, 24, client2, loanPersonal);

			//Préstamo Automotriz, 200.000, 36 cuotas
			ClientLoan loanGuillermo2 = new ClientLoan(200000, 36, client2, loanAutomotive);

			//Creando tarjeta  GOLD  para Melba Morell
			Card debitCardGoldMelba1 = new Card(client1,CardType.DEBIT,"0912 3487 6501 1276",150,LocalDate.now().plusYears(5), LocalDate.now(), CardColor.GOLD);

			//Creando tarjeta  TITANIUM  para Melba Morell
			Card creditCardTitaniumMelba1 = new Card(client1,CardType.CREDIT,"1234 0987 7601 4589", 315, LocalDate.now().plusYears(5), LocalDate.now(), CardColor.TITANIUM);

			//Creando tarjeta  SILVER  para el segundo cliente
			Card creditCardSilverGuille1 = new Card(client2, CardType.CREDIT,"7685 9450 9123 0032", 567, LocalDate.now().plusYears(5), LocalDate.now(), CardColor.SILVER);

			//Se persisten los objetos creados en la base de datos H2 que vive en memoria



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

			loanRepository.save(loanMortgage);
			loanRepository.save(loanPersonal);
			loanRepository.save(loanAutomotive);


			client1.addClientLoan(loanMelba1);
			clientLoanRepository.save(loanMelba1);
			client1.addClientLoan(loanMelba2);
			clientLoanRepository.save(loanMelba2);

			client2.addClientLoan(loanGuillermo1);
			clientLoanRepository.save(loanGuillermo1);
			client2.addClientLoan(loanGuillermo2);
			clientLoanRepository.save(loanGuillermo2);

			client1.addCard(debitCardGoldMelba1);
			cardRepository.save(debitCardGoldMelba1);
			client1.addCard(creditCardTitaniumMelba1);
			cardRepository.save(creditCardTitaniumMelba1);
			client2.addCard(creditCardSilverGuille1);
			cardRepository.save(creditCardSilverGuille1);

		});
	}

}

