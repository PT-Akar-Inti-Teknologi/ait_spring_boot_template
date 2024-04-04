package org.ait.project.guideline.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

/**
 * @author febrihasan
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    //TODO this header will change depends the project like base
    private static final String HEADER_AUTHORIZATION = "Authorization";

    // Redis
    static RedisContainer redis = new RedisContainer("redis:7.2.4");
    // PostgreSQL
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");
    // MongoDB
    static MongoDBContainer mongodb = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        Startables.deepStart(redis, postgres, mongodb).join();

        // Redis
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", redis::getFirstMappedPort);

        // MongoDB
        registry.add("spring.data.mongodb.host", mongodb::getHost);
        registry.add("spring.data.mongodb.port", mongodb::getFirstMappedPort);

        // PostgreSQL
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    @Getter
    private MockMvc mockMvc;

    @Setter @Getter
    private String accessToken;
    @Setter @Getter
    private String refreshToken;

    protected ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate a token with user access login
     * The result to get access token and can access permission
     */
    public void fetchAccessToken() throws Exception {

        // WHEN
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                            "\"username\":\"admin\",\n" +
                            "\"password\":\"password\""
                        + "}");

        // GIVEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        // THEN
        ResponseTemplate<ResponseDetail<LoginRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        setAccessToken(responseTemplate.getResponseOutput().getDetail().getAccessToken());
        setRefreshToken(responseTemplate.getResponseOutput().getDetail().getRefreshToken());
    }

    /**
     * Builds a GET request with the specified URL and token.
     *
     * @param url   The URL to send the GET request to
     * @param token The authorization token to include in the request header
     * @return A {@link MockHttpServletRequestBuilder} object representing the built GET request
     */
    protected MockHttpServletRequestBuilder buildGetRequest(String url, String token) {
        return MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_AUTHORIZATION, token);
    }

    /**
     * Builds a POST request with the specified URL, content object, and token.
     *
     * @param url           The URL to send the POST request to
     * @param contentObject The content object to include in the request body
     * @param token         The authorization token to include in the request header
     * @return A {@link MockHttpServletRequestBuilder} object representing the built POST request
     * @throws Exception if an error occurs while serializing the content object to JSON
     */
    protected MockHttpServletRequestBuilder buildPostRequest(String url, String contentObject, String token) {
        return MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_AUTHORIZATION, token)
                .content(contentObject);
    }

    /**
     * Builds a PUT request with the specified URL, content object, and token.
     *
     * <p>This method is used to build a PUT request for sending data to the server.
     * It sets the content type to JSON, adds the authorization token to the request header,
     * and includes the content object in the request body.</p>
     *
     * @param url           The URL to send the PUT request to
     * @param contentObject The content object to include in the request body
     * @param token         The authorization token to include in the request header
     * @return A {@link MockHttpServletRequestBuilder} object representing the built PUT request
     * @throws Exception if an error occurs while serializing the content object to JSON
     */
    protected MockHttpServletRequestBuilder buildPutRequest(String url, String contentObject, String token) {
        return MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_AUTHORIZATION, token)
                .content(contentObject);
    }

    /**
     * Builds a DELETE request with the specified URL, content object, and token.
     *
     * @param url           The URL to send the POST request to
     * @param token         The authorization token to include in the request header
     * @return A {@link MockHttpServletRequestBuilder} object representing the built POST request
     * @throws Exception if an error occurs while serializing the content object to JSON
     */
    protected MockHttpServletRequestBuilder buildDeleteRequest(String url, String token) {
        return MockMvcRequestBuilders
                .delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HEADER_AUTHORIZATION, token);
    }
}
