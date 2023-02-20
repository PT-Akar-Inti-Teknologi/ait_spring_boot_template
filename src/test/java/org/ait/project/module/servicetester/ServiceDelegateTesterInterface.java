package org.ait.project.module.servicetester;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDelegateTesterInterface<DS, E> {
	
	public JpaRepository<?, ?> getRepository();
	
	public DS getService();
	
	public void generateEntity();
	
	public E getEntity();
	
	public void generateTestParameterValues();
	
	public Map<?, ?> getTestParameterValues();
	
	
	

}
