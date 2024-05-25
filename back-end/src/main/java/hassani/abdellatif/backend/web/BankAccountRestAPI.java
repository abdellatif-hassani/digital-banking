package hassani.abdellatif.backend.web;

import hassani.abdellatif.backend.dtos.*;
import hassani.abdellatif.backend.entities.BankAccount;
import hassani.abdellatif.backend.exceptions.BalanceNotSufficientException;
import hassani.abdellatif.backend.exceptions.BankAccountNotFoundException;
import hassani.abdellatif.backend.services.BankAccountServiceImpl;
import hassani.abdellatif.backend.utils.AccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/accounts")
public class BankAccountRestAPI {
    private BankAccountServiceImpl bankAccountService;

    public BankAccountRestAPI(BankAccountServiceImpl bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @GetMapping({"", "/"})
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }


    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable Long accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }


    @GetMapping("/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable Long accountId){
        return bankAccountService.accountHistory(accountId);
    }


    @GetMapping("/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable Long accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }


    @PostMapping("/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }


    @PostMapping("/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }


    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<?> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        try {
            this.bankAccountService.transfer(
                    transferRequestDTO.getAccountSource(),
                    transferRequestDTO.getAccountDestination(),
                    transferRequestDTO.getAmount());
            return ResponseEntity.ok().build();
        } catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(404).body("Destination account not found");
        } catch (BalanceNotSufficientException e) {
            return ResponseEntity.status(400).body("Insufficient balance");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest accountRequest, @RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);  // Remove "Bearer " prefix
        BankAccount bankAccount = bankAccountService.createAccount(accountRequest, jwtToken);
        return ResponseEntity.ok(bankAccount);
    }

}
