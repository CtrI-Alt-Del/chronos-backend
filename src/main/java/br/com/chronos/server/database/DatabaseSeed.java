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
    var managers = fakeManagers();
    var employees = fakeEmployees();

    accountsRepository.add(admin);
    collaborators.addArray(managers).addArray(employees);
    collaboratorsRepository.addMany(collaborators, workSchedules.firstItem().getId());

    var employeeAccountTest = fakeEmployeeAccountTest(employees.item(0).getId());
    var managerAccountTest = fakeManagerAccountTest(employees.item(1).getId());
    var accounts = fakeAccounts(collaborators.removeFirstItem().removeFirstItem());
    accounts.add(employeeAccountTest).add(managerAccountTest);
    accountsRepository.addMany(accounts);
  }

  private Account fakeAdmin() {
    var fakeDto = AccountFaker.fakeDto();
    fakeDto.setRole("admin");
    return new Account(fakeDto);
  }

  private Array<Collaborator> fakeManagers() {
    var fakeDtos = CollaboratorFaker.fakeManyDto(6);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("manager");
      return new Collaborator(fakeDto);
    });
  }

  private Array<Collaborator> fakeEmployees() {
    var fakeDtos = CollaboratorFaker.fakeManyDto(12);
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
        .setId("d7df66ad-8f98-4709-afb9-db3ab1e85b46")
        .setEmail("chronos.employee@gmail.com")
        .setRole("employee")
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(dto);
    return new Account(dto);
  }

  private Account fakeManagerAccountTest(Id collaboratorId) {
    var dto = AccountFaker
        .fakeDto()
        .setId("d7df66ad-8f98-4709-afb9-db3ab1e85b46")
        .setEmail("chronos.manager@gmail.com")
        .setPassword("123456")
        .setRole("manager")
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(dto);
    return new Account(dto);
  }
}
