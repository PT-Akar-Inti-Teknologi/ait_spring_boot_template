package org.ait.project.module.mappertester;

import org.junit.jupiter.api.Test;

/**
 * tester for Mappers
 *
 * @author AIT Java Collective
 *
 * @param <E> Entity Class
 * @param <D> DTO Class
 */

public interface MapperTester<D, E> {
	
	/**
	 * JUnit test for Dto To Entity function
	 */
	@Test
	public void testMapDtoToEntity();
	
	/**
	 * JUnit test for Entity To Dto function
	 */
	@Test
	public void testMapEntityToDto();

	
	/**
	 * getter for Entity to test against result DTO
	 * @return entity to test against the mapping result DTO
	 */
	public E getEntity();
	
	/**
	 * getter for DTO to test against result Entity
	 * @return dto to test against the mapping result entity
	 */
	public D getDto();
	
	public void generateEntity();
	
	public void generateDto();
	
	

}
