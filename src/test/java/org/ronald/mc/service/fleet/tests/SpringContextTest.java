package org.ronald.mc.service.fleet.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ronald.mc.service.fleet.FleetServiceApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FleetServiceApplication.class)
public class SpringContextTest {

  @Test
  public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    assertDoesNotThrow(() -> {});
  }
}
