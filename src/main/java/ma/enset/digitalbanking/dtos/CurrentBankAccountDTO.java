package ma.enset.digitalbanking.dtos;

import lombok.Data;
import ma.enset.digitalbanking.enums.AccountStatus;

import java.util.Date;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {
    private double overDraft;
}