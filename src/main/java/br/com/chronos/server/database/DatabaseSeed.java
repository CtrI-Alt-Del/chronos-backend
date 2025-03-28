package br.com.chronos.server.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

import br.com.chronos.core.modules.auth.domain.entities.Account;
import br.com.chronos.core.modules.auth.domain.entities.fakers.AccountFaker;
import br.com.chronos.core.modules.auth.interfaces.repositories.AccountsRepository;
import br.com.chronos.core.modules.collaboration.domain.entities.Collaborator;
import br.com.chronos.core.modules.collaboration.domain.entities.fakers.CollaboratorFaker;
import br.com.chronos.core.modules.collaboration.interfaces.repositories.CollaboratorsRepository;
import br.com.chronos.core.modules.global.domain.records.Array;
import br.com.chronos.core.modules.global.domain.records.Id;
import br.com.chronos.core.modules.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.modules.work_schedule.domain.entities.fakers.WorkScheduleFaker;
import br.com.chronos.core.modules.work_schedule.interfaces.repositories.WorkSchedulesRepository;

@Component
public class DatabaseSeed implements CommandLineRunner {
  @Value("${database.seed.enabled}")
  private boolean isEnable;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private WorkSchedulesRepository workSchedulesRepository;

  @Autowired
  private AccountsRepository accountsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  public void run(String... args) throws Exception {
    if (!isEnable)
      return;

    var workSchedules = WorkScheduleFaker.fakeMany(3);
    workSchedulesRepository.addMany(workSchedules);

    Array<Collaborator> collaborators = Array.createAsEmpty();
    var admin = fakeAdmin();
    var managers = fakeManagers(6);
    var employees = fakeEmployees(12);

    var managerTest = fakeManagers(1).firstItem();
    var employeeTest = fakeEmployees(1).firstItem();

    collaborators
        .addArray(managers)
        .addArray(employees)
        .add(managerTest)
        .add(employeeTest);
    collaboratorsRepository.addMany(collaborators, workSchedules.firstItem().getId());

    var employeeAccountTest = fakeEmployeeAccountTest(employeeTest.getId());
    var managerAccountTest = fakeManagerAccountTest(managerTest.getId());
    var accounts = fakeAccounts(collaborators.removeLastItem().removeLastItem());
    accounts
        .add(admin)
        .add(employeeAccountTest)
        .add(managerAccountTest);
    accountsRepository.addMany(accounts);
  }

  private Account fakeAdmin() {
    var fakeDto = AccountFaker.fakeDto();
    fakeDto.setRole("admin");
    return new Account(fakeDto);
  }

  private Array<Collaborator> fakeManagers(int count) {
    var fakeDtos = CollaboratorFaker.fakeManyDto(count);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("manager");
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
          .setSector(collaborator.getSector().toString())
          .setCollaboratorId(collaborator.getId().toString());
      authenticationProvider.register(accountDto);
      return new Account(accountDto);
    });
  }

  private Account fakeEmployeeAccountTest(Id collaboratorId) {
    var dto = AccountFaker
        .fakeDto()
        .setEmail("chronos.employee@gmail.com")
        .setRole("employee")
        .setPassword("123456")
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
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(dto);
    return new Account(dto);
  }
}
