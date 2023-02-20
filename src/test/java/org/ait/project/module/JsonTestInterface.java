package org.ait.project.module;

import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.databind.DatabindException;

/**
 * 
 * interface untuk standar tes JSON pada DTO
 * 
 * @author AIT Java Collective
 *
 */
public interface JsonTestInterface {

	public void serializationTest() throws JSONException, IOException;
	
	public void deserializationTest() throws IOException, DatabindException;
	
	
}
