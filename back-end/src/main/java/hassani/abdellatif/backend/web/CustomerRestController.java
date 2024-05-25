package hassani.abdellatif.backend.web;

import hassani.abdellatif.backend.dtos.BankAccountDTO;
import hassani.abdellatif.backend.dtos.CustomerDTO;
import hassani.abdellatif.backend.dtos.CustomerDetailsDTO;
import hassani.abdellatif.backend.exceptions.CustomerNotFoundException;
import hassani.abdellatif.backend.services.BankAccountService;
import hassani.abdellatif.backend.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
@RequestMapping("/api/customers")
public class CustomerRestController {
    private BankAccountService bankAccountService;
    private CustomerService customerService;

    @GetMapping({"", "/"})
    public List<CustomerDTO> customers(){
        return customerService.listCustomers();
    }


    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return customerService.searchCustomers("%"+keyword+"%");
    }


    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return customerService.getCustomer(customerId);
    }


    @PostMapping("")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.saveCustomer(customerDTO);
    }


    @PutMapping("/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return customerService.updateCustomer(customerDTO);
    }


    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
    }

    @GetMapping("/{customerId}/accounts")
    public List<BankAccountDTO> getCustomerAccounts(@PathVariable Long customerId){
        return bankAccountService.getCustomerAccounts(customerId);
    }

    @GetMapping("/{customerId}/details")
    public CustomerDetailsDTO getCustomerDetails(@PathVariable Long customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO = customerService.getCustomer(customerId);
        List<BankAccountDTO> bankAccounts = bankAccountService.getCustomerAccounts(customerId);
        return new CustomerDetailsDTO(customerDTO, bankAccounts);
    }


}
