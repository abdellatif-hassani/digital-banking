package hassani.abdellatif.backend.services;

import hassani.abdellatif.backend.dtos.*;
import hassani.abdellatif.backend.entities.*;
import hassani.abdellatif.backend.enums.AccountStatus;
import hassani.abdellatif.backend.enums.OperationType;
import hassani.abdellatif.backend.exceptions.BalanceNotSufficientException;
import hassani.abdellatif.backend.exceptions.BankAccountNotFoundException;
import hassani.abdellatif.backend.exceptions.CustomerNotFoundException;
import hassani.abdellatif.backend.jwt.JwtUtil;
import hassani.abdellatif.backend.mappers.BankAccountMapperImpl;
import hassani.abdellatif.backend.repositories.AccountOperationRepository;
import hassani.abdellatif.backend.repositories.BankAccountRepository;
import hassani.abdellatif.backend.repositories.CustomerRepository;
import hassani.abdellatif.backend.utils.AccountRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    private JwtUtil jwtUtil;

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }



    @Override
    public BankAccountDTO getBankAccount(Long accountId) {
        try {
            BankAccount bankAccount = bankAccountRepository.findById(accountId)
                    .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));

            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        } catch (BankAccountNotFoundException ex) {
            // Handle the exception and return a response to the client
            System.out.println("Account is not found");
            return new BankAccountDTO(); // or any other appropriate response
        }
    }


    @Override
    public void debit(Long accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(Long accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public void transfer(Long accountSource, Long accountDestination, double amount)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount sourceAccount = bankAccountRepository.findById(accountSource)
                .orElseThrow(() -> new BankAccountNotFoundException("Source account not found"));
        BankAccount destinationAccount = bankAccountRepository.findById(accountDestination)
                .orElseThrow(() -> new BankAccountNotFoundException("Destination account not found"));

        if (sourceAccount.getBalance() < amount) {
            throw new BalanceNotSufficientException("Insufficient balance");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        destinationAccount.setBalance(destinationAccount.getBalance() + amount);

        bankAccountRepository.save(sourceAccount);
        bankAccountRepository.save(destinationAccount);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }




    @Override
    public List<AccountOperationDTO> accountHistory(Long accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(Long accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public BankAccount createAccount(AccountRequest accountRequest, String token) {
        String email = jwtUtil.extractUsername(token);
        System.out.println("****************"+email);
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        if ("current".equalsIgnoreCase(accountRequest.getType())) {
            CurrentAccount currentAccount = new CurrentAccount();
            currentAccount.setBalance(accountRequest.getBalance());
            currentAccount.setOverDraft(accountRequest.getOverDraft());
            currentAccount.setStatus(AccountStatus.CREATED);
            currentAccount.setCustomer(customer);  // Set customer
            return bankAccountRepository.save(currentAccount);
        } else if ("saving".equalsIgnoreCase(accountRequest.getType())) {
            SavingAccount savingAccount = new SavingAccount();
            savingAccount.setBalance(accountRequest.getBalance());
            savingAccount.setInterestRate(accountRequest.getInterestRate());
            savingAccount.setStatus(AccountStatus.CREATED);
            savingAccount.setCustomer(customer);  // Set customer
            return bankAccountRepository.save(savingAccount);
        }
        throw new IllegalArgumentException("Invalid account type");
    }

    @Override
    public List<BankAccountDTO> getCustomerAccounts(Long customerId) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByCustomerId(customerId);
        return bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
    }


}
