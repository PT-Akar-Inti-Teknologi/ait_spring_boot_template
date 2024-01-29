package org.ait.project.guideline.example;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PostgresContainerExtension.class)
@ExtendWith(MockServerContainerExtension.class)
public abstract class AbstractClass {
}
