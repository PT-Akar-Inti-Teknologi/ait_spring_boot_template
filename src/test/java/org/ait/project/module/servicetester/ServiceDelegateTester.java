package org.ait.project.module.servicetester;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;

import lombok.Getter;

@Getter
public abstract class ServiceDelegateTester<DS, E> 
       implements ServiceDelegateTesterInterface<DS, E> {

	private EasyRandom easyRandom = new EasyRandom();
	
	@BeforeEach
	public void beforeEachTest() {
		generateTestParameterValues();
		generateEntity();
	}


}
