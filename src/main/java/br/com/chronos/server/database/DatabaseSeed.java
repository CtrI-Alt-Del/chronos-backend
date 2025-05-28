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
import br.com.chronos.core.global.domain.records.Date;
import br.com.chronos.core.global.domain.records.Id;
import br.com.chronos.core.global.domain.records.Logical;
import br.com.chronos.core.global.interfaces.providers.AuthenticationProvider;
import br.com.chronos.core.hour_bank.domain.records.HourBankTransaction;
import br.com.chronos.core.hour_bank.domain.records.fakers.HourBankTransactionFaker;
import br.com.chronos.core.hour_bank.interfaces.HourBankTransactionsRepository;
import br.com.chronos.core.work_schedule.domain.entities.WorkdayLog;
import br.com.chronos.core.work_schedule.domain.entities.fakers.WorkdayLogFaker;
import br.com.chronos.core.work_schedule.interfaces.repositories.WorkdayLogsRepository;
import br.com.chronos.core.work_schedule.domain.records.fakers.DayOffScheduleFaker;
import br.com.chronos.core.work_schedule.interfaces.repositories.DayOffSchedulesRepository;
import br.com.chronos.core.portal.domain.abstracts.Solicitation;
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
  private AuthenticationProvider authenticationProvider;

  @Autowired
  private WorkdayLogsRepository workdayLogsRepository;

  @Autowired
  private JustificationTypeRepository justificationTypeRepository;

  @Autowired
  private SolicitationsRepository solicitationsRepository;

  public void run(String... args) throws Exception {
    if (!isEnable)
      return;

    Array<Collaborator> collaborators = Array.createAsEmpty();
    var managers = fakeManagers(6);
    var employees = fakeEmployees(6);
    var inactiveEmployees = fakeInactiveEmployees(2);
    employees = employees.addArray(inactiveEmployees);

    var managerTest = fakeManagerTest();
    var employeeTest = fakeEmployeeTest();

    collaborators
        .addArray(managers)
        .addArray(employees)
        .add(managerTest)
        .add(employeeTest);
    collaboratorsRepository.addMany(collaborators);

    addManyDayOffSchedules(collaborators);

    var workdayLogs = fakeWorkdayLogs(collaborators.removeLastItem().list(), "normal_day");
    workdayLogs.addArray(fakeWorkdayLogs(List.of(employeeTest), "absence"));
    workdayLogs.addArray(fakeWorkdayAbsencesForEmployees(employees));
    workdayLogs.addArray(fakeWorkdayAbsencesForManagers(managers));
    workdayLogsRepository.addMany(workdayLogs);

    var workLeaveSolicitations = fakeWorkLeaveSolicitations(collaborators);
    solicitationsRepository.addMany(workLeaveSolicitations);

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

  private Array<WorkdayLog> fakeWorkdayLogs(List<Collaborator> collaborators, String workdayStatus) {
    Array<WorkdayLog> workdayLogs = Array.createAsEmpty();
    for (var collaborator : collaborators) {
      var dto = WorkdayLogFaker
          .fakeDto(collaborator.getId().toString())
          .setWorkloadSchedule(collaborator.getWorkload().value())
          .setStatus(workdayStatus);
      workdayLogs.add(new WorkdayLog(dto));
    }
    return workdayLogs;
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
      fakeDto.setRole("manager").setSector("comercial");
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

  private Array<Collaborator> fakeInactiveEmployees(int count) {
    var fakeDtos = CollaboratorFaker.fakeManyDto(count);
    return Array.createFrom(fakeDtos.list(), (fakeDto) -> {
      fakeDto.setRole("employee").setActive(false);
      return new Collaborator(fakeDto);
    });
  }

  private Array<WorkdayLog> fakeWorkdayAbsencesForEmployees(Array<Collaborator> employees) {
    Array<WorkdayLog> workdayAbsences = Array.createAsEmpty();
    for (var index = 0; index < employees.size().value(); index++) {
      var collaborator = employees.item(index);
      switch (index) {
        case 0:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 1))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 2))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 3))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 4))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 5))
              .setStatus("absence")));
          break;
        case 1:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 1))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 2))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 3))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 4, 4))
              .setStatus("absence")));
          break;
        case 2:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 2, 1))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 2, 2))
              .setStatus("absence")));
          break;
        case 3:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 1, 1))
              .setStatus("absence")));
          break;
        default:
          break;
      }
    }
    return workdayAbsences;
  }

  private Array<WorkdayLog> fakeWorkdayAbsencesForManagers(Array<Collaborator> managers) {
    Array<WorkdayLog> workdayAbsences = Array.createAsEmpty();
    for (var index = 0; index < managers.size().value(); index++) {
      var collaborator = managers.item(index);
      switch (index) {
        case 1:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 1))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 3, 2))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 4, 4))
              .setStatus("absence")));
          break;
        case 2:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 2, 1))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 2, 2))
              .setStatus("absence")));
          break;
        case 3:
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 1, 1))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 1, 2))
              .setStatus("absence")));
          workdayAbsences.add(new WorkdayLog(WorkdayLogFaker
              .fakeDto(collaborator.getId().toString())
              .setDate(LocalDate.of(2025, 1, 3))
              .setStatus("absence")));
          break;
        default:
          break;
      }
    }
    return workdayAbsences;
  }

  private Array<WorkLeaveSolicitation> fakeWorkLeaveSolicitations(Array<Collaborator> collaborators) {
    Array<WorkLeaveSolicitation> solicitations = Array.createAsEmpty();

    for (var index = 0; index < collaborators.size().value(); index++) {
      var collaborator = collaborators.item(index);
      switch (index) {
        case 0:
          solicitations.add(fakeWorkLeaveSolicitation(
              collaborator.getId(),
              Date.create(2025, 5, 2),
              Date.create(2025, 5, 7),
              Logical.createAsTrue()));
          break;
        case 1:
          solicitations.add(fakeWorkLeaveSolicitation(
              collaborator.getId(),
              Date.create(2025, 5, 9),
              Date.create(2025, 5, 15),
              Logical.createAsTrue()));
          break;
        case 2:
          solicitations.add(fakeWorkLeaveSolicitation(
              collaborator.getId(),
              Date.create(2025, 5, 20),
              Date.create(2025, 5, 30),
              Logical.createAsTrue()));
          break;
        case 3:
          solicitations.add(fakeWorkLeaveSolicitation(
              collaborator.getId(),
              Date.create(2025, 5, 1),
              Date.create(2025, 5, 5),
              Logical.createAsFalse()));
          solicitations.add(fakeWorkLeaveSolicitation(
              collaborator.getId(),
              Date.create(2025, 5, 10),
              Date.create(2025, 5, 15),
              Logical.createAsFalse()));
          break;
        default:
          break;
      }
    }
    return solicitations;
  }

  private WorkLeaveSolicitation fakeWorkLeaveSolicitation(
      Id senderId,
      Date startedAt,
      Date endedAt,
      Logical isVacation) {
    var sender = new ResponsibleAggregateDto().setId(senderId.toString());
    var dto = WorkLeaveSolicitationFaker
        .fakeDto()
        .setSenderResponsible(sender)
        .setStartedAt(startedAt.value())
        .setEndedAt(endedAt.value())
        .setIsVacation(isVacation.value());
    return new WorkLeaveSolicitation(dto);
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
