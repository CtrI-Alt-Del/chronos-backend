package br.com.chronos.server.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import br.com.chronos.core.collaboration.interfaces.CollaboratorsRepository;
import br.com.chronos.core.global.domain.dtos.ResponsibleAggregateDto;
import br.com.chronos.core.global.domain.records.Array;
import br.com.chronos.core.global.domain.records.CollaborationSector;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.fakers.HourBankTransactionFaker;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.entities.fakers.TimePunchFaker;
import br.com.chronos.core.work_schedule.domain.entities.fakers.WorkdayLogFaker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.domain.records.fakers.DayOffScheduleFaker;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.portal.domain.entities.WorkLeaveSolicitation;
import br.com.chronos.core.portal.domain.entities.fakers.JustificationTypeFaker;
import br.com.chronos.core.portal.domain.entities.fakers.WorkLeaveSolicitationFaker;
import br.com.chronos.core.portal.interfaces.repositories.JustificationTypeRepository;
import br.com.chronos.core.portal.interfaces.repositories.SolicitationsRepository;

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
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  public void run(String... args) throws Exception {
    if (!isEnable)
      return;

    Array<Collaborator> collaborators = Array.createAsEmpty();
    var managers = fakeManagers(6);
    var employees = fakeEmployees(12);
    var managerTest = fakeManagerTest();
    var employeeTest = fakeEmployeeTest();

    collaborators
        .addArray(managers)
        .addArray(employees)
        .add(managerTest)
        .add(employeeTest);
    collaboratorsRepository.addMany(collaborators);

    addManyWorkLeaveSolicitations(collaborators);
    addManyDayOffSchedules(collaborators);
    addWorkdayLog(collaborators.removeLastItem().lastItem(), "normal_day");
    addWorkdayLog(employeeTest, "absence");

    var hourBankTransactions = fakeHourBankTransactions();
    hourBankTransactionsRepository.removeAll(employeeTest.getId());
    hourBankTransactionsRepository.addMany(hourBankTransactions, employeeTest.getId());

    var adminAccountTest = fakeAdminAccountTest();
    var managerAccountTest = fakeManagerAccountTest(managerTest.getId());
    var employeeAccountTest = fakeEmployeeAccountTest(employeeTest.getId());
    var accounts = fakeAccounts(collaborators.removeLastItem());
    accounts
        .add(adminAccountTest)
        .add(employeeAccountTest)
        .add(managerAccountTest);
    accountsRepository.addMany(accounts);

    addJustificationTypes();
  }

  private void addJustificationTypes() {
    var withAttachment = JustificationTypeFaker.fakeWithAttachment();
    var withoutAttachment = JustificationTypeFaker.fakeWithoutAttachment();
    justificationTypeRepository.add(withAttachment);
    justificationTypeRepository.add(withoutAttachment);
  }

  private Array<WorkLeaveSolicitation> addManyWorkLeaveSolicitations(Array<Collaborator> collaborators) {
    Array<WorkLeaveSolicitation> workLeaveSolicitations = Array.createAsEmpty();

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(true)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 1))
            .setEndedAt(LocalDate.of(2025, 6, 10))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(0).getId().toString()))));

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(false)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 20))
            .setEndedAt(LocalDate.of(2025, 6, 22))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(1).getId().toString()))));

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(true)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 15))
            .setEndedAt(LocalDate.of(2025, 6, 20))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(2).getId().toString()))));

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(true)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 29))
            .setEndedAt(LocalDate.of(2025, 7, 3))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(3).getId().toString()))));

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(false)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 1))
            .setEndedAt(LocalDate.of(2025, 6, 18))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(4).getId().toString()))));

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(true)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 1))
            .setEndedAt(LocalDate.of(2025, 6, 18))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(5).getId().toString()))));

    workLeaveSolicitations.add(new WorkLeaveSolicitation(
        WorkLeaveSolicitationFaker.fakeDto()
            .setIsVacation(true)
            .setStatus("APPROVED")
            .setStartedAt(LocalDate.of(2025, 6, 20))
            .setEndedAt(LocalDate.of(2025, 6, 29))
            .setSenderResponsible(new ResponsibleAggregateDto()
                .setId(collaborators.item(6).getId().toString()))));

    for (var workLeaveSolicitation : workLeaveSolicitations.list()) {
      solicitationsRepository.add(workLeaveSolicitation);
    }
    return workLeaveSolicitations;
  }

  private void addWorkdayLog(Collaborator collaborator, String workdayStatus) {
    var dto = WorkdayLogFaker.fakeDto(collaborator.getId().toString())
        .setWorkloadSchedule(collaborator.getWorkload().value())
        .setResponsible(new ResponsibleAggregateDto().setId(collaborator.getId().toString()))
        .setTimePunch(TimePunchFaker.fakeDto()
            .setFirstClockIn(null)
            .setFirstClockOut(null)
            .setSecondClockIn(null)
            .setSecondClockOut(null))
        .setStatus(workdayStatus);
    workdayLogsRepository.add(new WorkdayLog(dto));
  }

  private Account fakeAdminAccountTest() {
    var fakeDto = AccountFaker.fakeDto();
    fakeDto
        .setId("7d87b4a3-3ce5-485f-a218-717d46ae812a")
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
      fakeDto.setSector(CollaborationSector.Sector.COMERCIAL.toString());
      return new Collaborator(fakeDto);
    });
  }

  private Array<Collaborator> fakeEmployees(int count) {
    var fakeDtos = CollaboratorFaker.fakeManyDto(count);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("employee");
      fakeDto.setSector(CollaborationSector.Sector.COMERCIAL.toString());
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
            HourBankTransactionFaker
                .fakeDto()
                .setTime(LocalTime.of(1, 0))
                .setDateTime(LocalDateTime.of(2025, 4, 27, 3, 0))
                .setOperation("CREDIT"),
            HourBankTransactionFaker
                .fakeDto()
                .setTime(LocalTime.of(0, 10))
                .setDateTime(LocalDateTime.of(2025, 4, 26, 0, 10))
                .setOperation("CREDIT"),
            HourBankTransactionFaker
                .fakeDto()
                .setTime(LocalTime.of(0, 10))
                .setDateTime(LocalDateTime.of(2025, 4, 25, 0, 10))
                .setOperation("CREDIT"),
            HourBankTransactionFaker
                .fakeDto()
                .setTime(LocalTime.of(0, 10))
                .setDateTime(LocalDateTime.of(2025, 4, 24, 0, 10))
                .setOperation("CREDIT"),
            HourBankTransactionFaker
                .fakeDto()
                .setTime(LocalTime.of(0, 10))
                .setDateTime(LocalDateTime.of(2025, 4, 23, 0, 10))
                .setOperation("DEBIT")))
        .map(HourBankTransaction::create);
  }

  private void addManyDayOffSchedules(Array<Collaborator> collaborators) {
    for (var collaborator : collaborators.list()) {
      var dayOffSchedule = DayOffScheduleFaker.fake();
      dayOffSchedulesRepository.add(dayOffSchedule, collaborator.getId());
    }
  }

  private Collaborator fakeEmployeeTest() {
    var fakeDto = CollaboratorFaker.fakeDto();
    fakeDto
        .setId("56ab2a6d-39f7-4fba-a001-f33ccbe39dfe")
        .setRole("employee");
    return new Collaborator(fakeDto);
  }

  private Collaborator fakeManagerTest() {
    var fakeDto = CollaboratorFaker.fakeDto();
    fakeDto
        .setId("9b1febb2-60e2-4369-8143-b69429bf4fb3")
        .setRole("manager");
    return new Collaborator(fakeDto);
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
