package hassani.abdellatif.backend.dtos;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private Long accountSource;
    private Long accountDestination;
    private double amount;
    private String description;
}
