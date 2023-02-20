package org.ait.project.module.servicetester;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;

import lombok.Getter;

@Getter
public abstract class ServiceInternalTester implements ServiceInternalTesterInterface {

	private EasyRandom easyRandom = new EasyRandom(getEasyRandomParam());

	@BeforeEach
	public void beforeEachTest() {
		generateTestParameterValues();
		generateRequests();
		mockServices();
	}

	private EasyRandomParameters getEasyRandomParam() {
		EasyRandomParameters params = new EasyRandomParameters();
		params.collectionSizeRange(4, 6);
		return params;
	}
}
