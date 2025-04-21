package br.com.chronos.core.work_schedule.domain.entities.fakers;

import br.com.chronos.core.global.domain.records.fakers.TimeFaker;
import br.com.chronos.core.work_schedule.domain.dtos.TimePunchDto;
import br.com.chronos.core.work_schedule.domain.entities.TimePunch;

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
        .setFirstClockIn(firstClockIn.value())
        .setFirstClockOut(firstClockOut.value())
        .setSecondClockIn(secondClockIn.value())
        .setSecondClockOut(secondClockOut.value());
  }
}
