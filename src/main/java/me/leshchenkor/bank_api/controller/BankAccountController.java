package me.leshchenkor.bank_api.controller;

import me.leshchenkor.bank_api.dto.OperationListDTO;
import me.leshchenkor.bank_api.dto.TransferRequestDTO;
import me.leshchenkor.bank_api.entity.BankAccount;
import me.leshchenkor.bank_api.entity.Operation;
import me.leshchenkor.bank_api.exception.AccountBalanceChangeException;
import me.leshchenkor.bank_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankAccountController {
    @Autowired
    AccountService accountService;

    @PostMapping(value = "/create")
    //  @Operation(summary = "Создание нового аккаунта")
    public ResponseEntity<Object> createAccount(@RequestBody BankAccount acc) {
        return ResponseEntity.ok(accountService.createAccount(acc));
    }

    @GetMapping("{id}")
    public ResponseEntity<BankAccount> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.findById(id));
    }

    @DeleteMapping("/delete/{user_id}")
    //    @ApiOperation(value = "Удаление аккаунта")
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long user_id) {
        accountService.deleteAccount(user_id);
        return ResponseEntity.ok().body("Success");
    }

    @GetMapping(value = "/accounts")
    //    @ApiOperation(value = "Получение списка всех аккаунтов")
    public ResponseEntity<List<BankAccount>> readAccounts() {
        final List<BankAccount> bankAccounts = accountService.readAllAccounts();
        return bankAccounts != null && !bankAccounts.isEmpty()
                ? new ResponseEntity<>(bankAccounts, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//------------------------------------------------------------------------------------------------------

    @GetMapping(value = "/getBalance/{userId}")
//    @ApiOperation(value = "Получение баланса по id")
    public double readBankAccount(@PathVariable(value = "userId") Long id) {
        return accountService.getBalanceByID(id);
    }

    @PutMapping(value = "/putMoney")
    public ResponseEntity<BankAccount> putMoneyById(@RequestBody Operation operation)
            throws AccountBalanceChangeException {
        return ResponseEntity.ok(accountService.putMoney(operation.getUser_id(),
                operation.getAmount(), operation.getDescription()));
    }

    @PostMapping(value = "/takeMoney")
    //    @ApiOperation(value = "Снятие средств")
    public ResponseEntity<Object> takeMoneyById(@RequestBody Operation operation)
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
    public List<Operation> getOperations(@RequestBody OperationListDTO listDTO) {
        return accountService.getOperationList(listDTO);
    }
}
