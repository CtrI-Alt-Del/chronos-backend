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
    var firstClockIn = TimeFaker.fake();
    var firstClockOut = firstClockIn.plusHours(2);
    var secondClockIn = firstClockOut.plusHours(2);
    var secondClockOut = secondClockIn.plusHours(2);

    return new TimePunchDto()
        .setId(IdFaker.fakeDto())
        .setFirstClockIn(firstClockIn.value())
        .setFirstClockOut(firstClockOut.value())
        .setSecondClockIn(secondClockIn.value())
        .setSecondClockOut(secondClockOut.value());
  }
}
