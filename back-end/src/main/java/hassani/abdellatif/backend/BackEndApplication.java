package hassani.abdellatif.backend;

import hassani.abdellatif.backend.dtos.BankAccountDTO;
import hassani.abdellatif.backend.dtos.CurrentBankAccountDTO;
import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.dtos.SavingBankAccountDTO;
import hassani.abdellatif.backend.entities.AuthEntity;
import hassani.abdellatif.backend.entities.BankAccount;
import hassani.abdellatif.backend.enums.AccountStatus;
import hassani.abdellatif.backend.exceptions.BalanceNotSufficientException;
import hassani.abdellatif.backend.exceptions.BankAccountNotFoundException;
import hassani.abdellatif.backend.exceptions.CustomerNotFoundException;
import hassani.abdellatif.backend.mappers.BankAccountMapperImpl;
import hassani.abdellatif.backend.repositories.AuthRepository;
import hassani.abdellatif.backend.services.BankAccountService;
import hassani.abdellatif.backend.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Base64;

import static java.util.Arrays.stream;

@SpringBootApplication
public class BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService,
                                        AuthRepository authRepository,
                                        CustomerService customerService, BankAccountMapperImpl bankAccountMapper) {
        return args -> {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            keyGen.init(256);
//            SecretKey secretKey = keyGen.generateKey();
//            String base64EncodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//            System.out.println("Base64 Encoded Key: " + base64EncodedKey);
              authRepository.save(new AuthEntity(null, "hassani@gmail.com", "hassani"));
              customerService.saveCustomer(new CustomerDTO(null, "Abdellatif Hassani", "hassani@gmail.com"));
            stream(new String[]{"John", "Doe", "Smith", "Brown", "Taylor", "Evans", "Wilson", "Thomas", "Harris", "Martin"}).forEach(name -> {
                CustomerDTO customerDTO = new CustomerDTO(null, name, name+"gmail.com");
                CustomerDTO createdCustomerDTO = customerService.saveCustomer(customerDTO);
                try {
                    CurrentBankAccountDTO currentBankAccountDTO1 = bankAccountService.saveCurrentBankAccount(1000, 500, createdCustomerDTO.getId());
                    SavingBankAccountDTO savingBankAccountDTO1 = bankAccountService.saveSavingBankAccount(1400, 200, createdCustomerDTO.getId());
                    // Adding list of operations to the current bank account
                    stream(new String[]{"Deposit", "Withdrawal", "Withdrawal", "Deposit", "Deposit", "Withdrawal", "Deposit"}).forEach(operation -> {
                        try {
                            if (operation.equals("Deposit")){
                                bankAccountService.debit(currentBankAccountDTO1.getId(), Math.random()*100, operation);
                                bankAccountService.debit(savingBankAccountDTO1.getId(), Math.random()*100, operation);
                            }
                            else if (operation.equals("Withdrawal")){
                                bankAccountService.credit(currentBankAccountDTO1.getId(), Math.random()*100, operation);
                                bankAccountService.credit(savingBankAccountDTO1.getId(), Math.random()*100, operation);
                            }
                        } catch (BankAccountNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (BalanceNotSufficientException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (CustomerNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        };
    }
}
