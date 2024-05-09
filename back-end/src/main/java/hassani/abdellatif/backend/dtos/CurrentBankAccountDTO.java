package hassani.abdellatif.backend.dtos;

import hassani.abdellatif.backend.enums.AccountStatus;
import lombok.*;

import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class CurrentBankAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;
}
