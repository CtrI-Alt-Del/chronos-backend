package br.com.chronos.core.modules.work_schedule.domain.entities.fakers;

import br.com.chronos.core.modules.global.domain.records.fakers.IdFaker;
import br.com.chronos.core.modules.global.domain.records.fakers.TimeFaker;
import br.com.chronos.core.modules.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.modules.work_schedule.domain.entities.TimePunch;

public class TimePunchFaker {
  public static TimePunch fake() {
    return new TimePunch(fakeDto());
  }

  public static TimePunchDto fakeDto() {
    return new TimePunchDto()
        .setId(IdFaker.fakeDto())
        .setFirstClockIn(TimeFaker.fakeDto())
        .setSecondClockIn(TimeFaker.fakeDto())
        .setFirstClockOut(TimeFaker.fakeDto())
        .setSecondClockOut(TimeFaker.fakeDto());
  }
}
