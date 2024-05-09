package hassani.abdellatif.backend;

import hassani.abdellatif.backend.dtos.BankAccountDTO;
import hassani.abdellatif.backend.dtos.CurrentBankAccountDTO;
import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.entities.BankAccount;
import hassani.abdellatif.backend.enums.AccountStatus;
import hassani.abdellatif.backend.exceptions.CustomerNotFoundException;
import hassani.abdellatif.backend.mappers.BankAccountMapperImpl;
import hassani.abdellatif.backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

import static java.util.Arrays.stream;

@SpringBootApplication
public class BackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService, BankAccountMapperImpl bankAccountMapper) {
        return args -> {
            stream(new int[]{8000, 2000, 2334, 19000}).forEach(c -> {
                bankAccountService.saveCustomer(new CustomerDTO(1L, "John", "Doe"));
                //create a new bank account
                CurrentBankAccountDTO bc = new CurrentBankAccountDTO(null, c, new Date(), AccountStatus.CREATED, null, 0.0);
                try {
                    bankAccountService.saveCurrentBankAccount(bc.getBalance(), bc.getOverDraft(), 1L);
                } catch (CustomerNotFoundException e) {
                    throw new RuntimeException(e);
                }

            });
            System.out.println("App running on port 8080...");
        };
    }
}
