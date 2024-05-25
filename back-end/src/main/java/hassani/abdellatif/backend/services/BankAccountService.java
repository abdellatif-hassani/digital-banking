package hassani.abdellatif.backend.services;

import hassani.abdellatif.backend.dtos.*;
import hassani.abdellatif.backend.entities.BankAccount;
import hassani.abdellatif.backend.entities.CurrentAccount;
import hassani.abdellatif.backend.exceptions.BalanceNotSufficientException;
import hassani.abdellatif.backend.exceptions.BankAccountNotFoundException;
import hassani.abdellatif.backend.exceptions.CustomerNotFoundException;
import hassani.abdellatif.backend.utils.AccountRequest;

import java.util.List;

public interface BankAccountService {
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    Object getBankAccount(Long accountId) throws BankAccountNotFoundException;
    void debit(Long accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(Long accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(Long accountIdSource, Long accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    List<AccountOperationDTO> accountHistory(Long accountId);
    AccountHistoryDTO getAccountHistory(Long accountId, int page, int size) throws BankAccountNotFoundException;

    public BankAccount createAccount(AccountRequest accountRequest, String token);

    List<BankAccountDTO> getCustomerAccounts(Long customerId);
}
