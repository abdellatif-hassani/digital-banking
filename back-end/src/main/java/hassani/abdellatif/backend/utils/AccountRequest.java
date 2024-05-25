package hassani.abdellatif.backend.utils;

import lombok.Data;

@Data
public class AccountRequest {
    private String type;
    private double balance;
    private double overDraft;
    private double interestRate;
}

