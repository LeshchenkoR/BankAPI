package me.leshchenkor.bank_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.leshchenkor.bank_api.dto.OperationListDTO;
import me.leshchenkor.bank_api.dto.TransferRequestDTO;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.entity.Operations;
import me.leshchenkor.bank_api.exception.AccountBalanceChangeException;
import me.leshchenkor.bank_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bank account controller")
@RestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    AccountService accountService;

    @Operation(summary = "Create new account")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create")
    public BankAccount createAccount(@RequestBody BankAccount acc) {
        return accountService.createAccount(acc);
    }

    @Operation(summary = "Find account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the account",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccount.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found",
                    content = @Content)})
    @GetMapping("{id}")
    public BankAccount getById(@Parameter(description = "id of account to be searched")
                               @PathVariable Long id) {
        return accountService.findById(id);
    }

    @Operation(summary = "Delete account")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long user_id) {
        accountService.deleteAccount(user_id);
        return ResponseEntity.ok().body("Success");
    }

    @Operation(summary = "Get all accounts")
    @GetMapping(value = "/accounts")
    public ResponseEntity<List<BankAccount>> readAccounts() {
        final List<BankAccount> bankAccounts = accountService.readAllAccounts();
        return bankAccounts != null && !bankAccounts.isEmpty()
                ? new ResponseEntity<>(bankAccounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//------------------------------------------------------------------------------------------------------

    @Operation(summary = "Get account balance by id")
    @GetMapping(value = "/getBalance/{userId}")
    public double readBankAccount(@PathVariable(value = "userId") Long id) {
        return accountService.getBalanceByID(id);
    }

    @PutMapping(value = "/putMoney")
    public ResponseEntity<BankAccount> putMoneyById(@RequestBody Operations operation)
            throws AccountBalanceChangeException {
        return ResponseEntity.ok(accountService.putMoney(operation.getUser_id(),
                operation.getAmount(), operation.getDescription()));
    }

    @PostMapping(value = "/takeMoney")
    //    @ApiOperation(value = "Снятие средств")
    public ResponseEntity<Object> takeMoneyById(@RequestBody Operations operation)
            throws AccountBalanceChangeException {
        return ResponseEntity.ok(accountService.takeMoney(operation.getUser_id(),
                operation.getAmount(), operation.getDescription()));
    }

    @PostMapping(value = "/transfer")
    //    @ApiOperation(value = "Перевод средств")
    public ResponseEntity<Object> transferMoney(@RequestBody TransferRequestDTO transferRequestDTO)
            throws AccountBalanceChangeException {
        return accountService.transferMoney(transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(), transferRequestDTO.getAmount());
    }

//    @GetMapping(value = "/history")
//    //    @ApiOperation(value = "Получение списка всех операций за период")
//    public ResponseEntity<List<Operation>> getOperationsBetweenDates
//            (@RequestParam(required = false ) Date dateFrom, @RequestParam(required = false ) Date dateTo) {
//        final List<Operation> operationBetweenDates = accountService.getOperationList(dateFrom, dateTo);
//        return operationBetweenDates != null && !operationBetweenDates.isEmpty()
//                ? new ResponseEntity<>(operationBetweenDates, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
    //----------------------------------------------------------------------------

    @PostMapping("/history")
    //    @ApiOperation(value = "Получение списка всех операций за период")
    public List<Operations> getOperations(@RequestBody OperationListDTO listDTO) {
        return accountService.getOperationList(listDTO);
    }
}
