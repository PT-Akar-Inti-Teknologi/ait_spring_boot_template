package org.ait.project.module.servicetester;

import java.util.Map;

public interface ServiceInternalTesterInterface {
	
	public void generateRequests();
	
	public void generateTestParameterValues();
	
	public Map<?, ?> getTestParameterValues();
	
	public void mockServices();
	
}
