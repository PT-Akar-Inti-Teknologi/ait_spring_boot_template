package org.ait.project.module.mappertester;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;

import lombok.Getter;

@Getter
public abstract class EntityMapperTester<D, E> 
       implements MapperTester<D, E> {

	private EasyRandom easyRandom = new EasyRandom();
	
	@BeforeEach
	protected void setUp() {
		generateDto();
		generateEntity();
	}
}
