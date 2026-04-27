package ma.enset.digitalbanking.web;

import ma.enset.digitalbanking.dtos.CustomerDTO;
import ma.enset.digitalbanking.exceptions.CustomerNotFoundException;
import ma.enset.digitalbanking.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerRestController {

    private BankAccountService bankAccountService;

    public CustomerRestController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // ===== GET ALL CUSTOMERS =====
    @GetMapping
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }

    // ===== SEARCH CUSTOMERS =====
    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(
            @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        return bankAccountService.searchCustomers("%" + keyword + "%");
    }

    // ===== GET CUSTOMER BY ID =====
    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id)
            throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    // ===== CREATE CUSTOMER =====
    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    // ===== UPDATE CUSTOMER =====
    @PutMapping("/{customerId}")
    public CustomerDTO updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    // ===== DELETE CUSTOMER =====
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        bankAccountService.deleteCustomer(id);
    }
}