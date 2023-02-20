package org.ait.project.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.BooleanUtils;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.Setter;

public abstract class JsonTester<T> implements JsonTestInterface {

	public enum JsonTestType {
		GENERATED,
		GIVEN

	}

	@Getter
	@Setter
	private JsonTestType jsonTestType;
	
	@Getter
	@Setter
	private Class<T> typeToTest;
	
	@Getter
	@Setter
	private T objectToTest;
	
	protected ObjectMapper om = buildObjectMapper();
	
	private Boolean generateSampleFile;
	
	private FileOutputStream fos;
	
	private FileInputStream generatedExpected;
	
	private FileInputStream givenExpected;
	
	
	protected void writeSampleFile() throws IOException  {
		generateSampleFile = Boolean.TRUE;
		ObjectWriter prettyWriter = om.writerWithDefaultPrettyPrinter();
		EasyRandomParameters erp = new EasyRandomParameters();
		erp.collectionSizeRange(1, 4);
		EasyRandom er = new EasyRandom(erp);
		T created = er.nextObject(typeToTest);
		objectToTest = created;
		byte[] forFile = prettyWriter.writeValueAsBytes(objectToTest);
		fos = new FileOutputStream(getGeneratedFilePath(), false);
		fos.write(forFile);
		fos.close();
	}
	
	private ObjectMapper buildObjectMapper() {
		ObjectMapper objectMapper =  new ObjectMapper();
		objectMapper.registerModules(new JavaTimeModule());
		return objectMapper;
	}

	private String getGeneratedFilePath() throws IOException {
		String folderPath = "target/generated-test-sources/samples/json/";
		String generatedFilePath = folderPath + typeToTest.getSimpleName().toLowerCase() +".json";
		new File(folderPath).mkdirs();
		new File(generatedFilePath).createNewFile();

		return generatedFilePath;
	}
	
	private String getGivenFilePath() {
		return "src/test/resources/samples/json/"+ typeToTest.getSimpleName().toLowerCase() +".json";
	}

	protected InputStream getSourceJson() throws IOException {
		if (BooleanUtils.isTrue(generateSampleFile)) {
			generatedExpected = new FileInputStream(getGeneratedFilePath());
			return generatedExpected;
		} else {
			return givenExpected;
		}
	}
	
	@BeforeEach
	protected void setupTest() throws IOException {
		
		if (typeToTest == null) {
			throw new RuntimeException("Java type to test hasn't been declared yet. set type before running super");
		}
		
		if (jsonTestType == null) {
			throw new RuntimeException("Json Test Type hasn't been declared yet. set json testing type before running super");
		}
		
		switch (jsonTestType) {
		case GIVEN:
			checkTestSampleFile();
			checkTestSampleObject();
			break;
		case GENERATED:
		default:
			writeSampleFile();
			break;
		}
	}
	

	private void checkTestSampleObject() {
		if (objectToTest == null) {
			throw new RuntimeException("test sample object is needed. please declare");
		}
	}

	private void checkTestSampleFile() throws FileNotFoundException {
		
		if (!new File(getGivenFilePath()).exists()) {
			throw new FileNotFoundException("file not found on path ["+ getGivenFilePath() +"]");
		} else {
			givenExpected = new FileInputStream(getGivenFilePath());
		}
	}
	
}
