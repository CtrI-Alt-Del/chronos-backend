package br.com.chronos.server.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import br.com.chronos.core.auth.domain.entities.Account;
import br.com.chronos.core.auth.domain.entities.fakers.AccountFaker;
import br.com.chronos.core.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.collaboration.domain.entities.fakers.CollaboratorFaker;
import br.com.chronos.core.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.fakers.HourBankTransactionFaker;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.work_schedule.domain.records.fakers.DayOffScheduleFaker;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;

@Component
public class DatabaseSeed implements CommandLineRunner {
  @Value("${database.seed.enabled}")
  private boolean isEnable;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private HourBankTransactionsRepository hourBankTransactionsRepository;

  @Autowired
  private DayOffSchedulesRepository dayOffSchedulesRepository;

  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  public void run(String... args) throws Exception {
    if (!isEnable)
      return;

    Array<Collaborator> collaborators = Array.createAsEmpty();
    var managers = fakeManagers(6);
    var employees = fakeEmployees(12);

    var adminTest = fakeAdmins(1).firstItem();
    var managerTest = fakeManagers(1).firstItem();
    var employeeTest = fakeEmployees(1).firstItem();

    collaborators
        .addArray(managers)
        .addArray(employees)
        .add(managerTest)
        .add(adminTest)
        .add(employeeTest);
    collaboratorsRepository.addMany(collaborators);

    addManyDayOffSchedules(collaborators);

    var hourBankTransactions = fakeHourBankTransactions();
    hourBankTransactionsRepository.addMany(hourBankTransactions, employeeTest.getId());

    var employeeAccountTest = fakeEmployeeAccountTest(employeeTest.getId());
    var adminAccountTest = fakeAdmin(adminTest.getId());
    var managerAccountTest = fakeManagerAccountTest(managerTest.getId());
    var accounts = fakeAccounts(collaborators.removeLastItem().removeLastItem().removeLastItem());
    accounts
        .add(adminAccountTest)
        .add(employeeAccountTest)
        .add(managerAccountTest);
    accountsRepository.addMany(accounts);
  }

  private Account fakeAdmin(Id collaboratorId) {
    var fakeDto = AccountFaker.fakeDto();
    fakeDto
        .setRole("admin")
        .setEmail("chronos.admin@gmail.com")
        .setPassword("123456")
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(fakeDto);
    return new Account(fakeDto);
  }

  private Array<Collaborator> fakeManagers(int count) {
    var fakeDtos = CollaboratorFaker.fakeManyDto(count);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("manager");
      return new Collaborator(fakeDto);
    });
  }

  private Array<Collaborator> fakeAdmins(int count) {
    var fakeDtos = CollaboratorFaker.fakeManyDto(count);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("admin");
      return new Collaborator(fakeDto);
    });
  }

  private Array<Collaborator> fakeEmployees(int count) {
    var fakeDtos = CollaboratorFaker.fakeManyDto(count);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("employee");
      return new Collaborator(fakeDto);
    });
  }

  private Array<Account> fakeAccounts(Array<Collaborator> collaborators) {
    return collaborators.map((collaborator) -> {
      var accountDto = AccountFaker
          .fakeDto()
          .setEmail(collaborator.getEmail().value())
          .setRole(collaborator.getRole().toString())
          .setCollaborationSector(collaborator.getSector().toString())
          .setCollaboratorId(collaborator.getId().toString());
      authenticationProvider.register(accountDto);
      return new Account(accountDto);
    });
  }

  private Array<HourBankTransaction> fakeHourBankTransactions() {
    return Array.create(
        List.of(
            HourBankTransactionFaker.fakeDto(),
            HourBankTransactionFaker.fakeDto(),
            HourBankTransactionFaker.fakeDto(),
            HourBankTransactionFaker.fakeDto(),
            HourBankTransactionFaker.fakeDto()))
        .map(HourBankTransaction::create);
  }

  private void addManyDayOffSchedules(Array<Collaborator> collaborators) {
    for (var collaborator : collaborators.list()) {
      var dayOffSchedule = DayOffScheduleFaker.fake();
      dayOffSchedulesRepository.add(dayOffSchedule, collaborator.getId());
    }
  }

  private Account fakeEmployeeAccountTest(Id collaboratorId) {
    var dto = AccountFaker
        .fakeDto()
        .setEmail("chronos.employee@gmail.com")
        .setRole("employee")
        .setPassword("123456")
        .setCollaborationSector(CollaborationSector.Sector.COMERCIAL.toString())
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(dto);
    return new Account(dto);
  }

  private Account fakeManagerAccountTest(Id collaboratorId) {
    var dto = AccountFaker
        .fakeDto()
        .setEmail("chronos.manager@gmail.com")
        .setRole("manager")
        .setPassword("123456")
        .setCollaborationSector(CollaborationSector.Sector.COMERCIAL.toString())
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(dto);
    return new Account(dto);
  }

}
