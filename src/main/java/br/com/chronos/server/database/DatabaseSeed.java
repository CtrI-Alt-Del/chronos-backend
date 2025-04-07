package br.com.chronos.server.database;

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
import br.com.chronos.core.work_schedule.domain.entities.fakers.CollaboratorScheduleFaker;
import br.com.chronos.core.work_schedule.domain.records.CollaboratorSchedule;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.work_schedule.interfaces.repositories.WeekdaySchedulesRepository;
import br.com.chronos.core.work_schedule.use_cases.CreateCollaboratorScheduleUseCase;

@Component
public class DatabaseSeed implements CommandLineRunner {
  @Value("${database.seed.enabled}")
  private boolean isEnable;

  @Autowired
  private CollaboratorsRepository collaboratorsRepository;

  @Autowired
  private WeekdaySchedulesRepository weekdaySchedulesRepository;

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
    collaboratorsRepository.addMany(collaborators);

    var collaboratorSchedules = fakeCollaboratorSchedules(collaborators);
    addManyCollaboratorSchedules(collaboratorSchedules);

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
    fakeDto
        .setRole("admin")
        .setEmail("chronos.admin@gmail.com")
        .setPassword("123456");
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

  private Array<CollaboratorSchedule> fakeCollaboratorSchedules(Array<Collaborator> collaborators) {
    return collaborators.map((collaborator) -> {
      return CollaboratorScheduleFaker.fake(collaborator.getId().toString());
    });
  }

  private Account fakeEmployeeAccountTest(Id collaboratorId) {
    var dto = AccountFaker
        .fakeDto()
        .setEmail("chronos.employee@gmail.com")
        .setRole("employee")
        .setPassword("123456")
        .setSector(CollaborationSector.Sector.COMERCIAL.toString())
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
        .setSector(CollaborationSector.Sector.COMERCIAL.toString())
        .setCollaboratorId(collaboratorId.toString());
    authenticationProvider.register(dto);
    return new Account(dto);
  }

  private void addManyCollaboratorSchedules(Array<CollaboratorSchedule> collaboratorSchedules) {
    var useCase = new CreateCollaboratorScheduleUseCase(weekdaySchedulesRepository, dayOffSchedulesRepository);
    for (var collaboratorSchedule : collaboratorSchedules.list()) {
      useCase.execute(
          collaboratorSchedule.collaboratorId().toString(),
          collaboratorSchedule.weekSchedule().map((dayOff) -> dayOff.getDto()).list(),
          collaboratorSchedule.daysOffSchedule().getDto());
    }
  }
}
