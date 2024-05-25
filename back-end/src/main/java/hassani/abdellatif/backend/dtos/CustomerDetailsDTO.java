package hassani.abdellatif.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDetailsDTO {
    private CustomerDTO customer;
    private List<BankAccountDTO> bankAccounts;
}
