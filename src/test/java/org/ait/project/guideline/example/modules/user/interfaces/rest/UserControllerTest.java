package org.ait.project.guideline.example.modules.user.interfaces.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ait.project.guideline.example.BaseIntegrationTest;
import org.ait.project.guideline.example.modules.permission.dto.response.LoginRes;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.exception.UserNofFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author febrihasan
 */
class UserControllerTest extends BaseIntegrationTest {
    
    @Autowired
    private UserRepository userRepository;

    @Test
    void addUser() throws Exception {
        // WHEN
        fetchAccessToken();
        // addUser
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{}", getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                // content type
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Matchers.containsString("response_schema")))
                .andExpect(content().string(Matchers.containsString("response_code")))
                .andExpect(content().string(Matchers.containsString("response_message")))
                // response body / output
                .andExpect(jsonPath("$.response_output.detail.id").exists())
                .andExpect(jsonPath("$.response_output.detail.name").exists())
                .andReturn();
    }

    @Test
    void getUsers() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseCollection<LoginRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        int id = responseTemplate.getResponseOutput().getList().getContent().get(0).getId();
        String name = responseTemplate.getResponseOutput().getList().getContent().get(0).getName();

        MockHttpServletRequestBuilder requestBuilder2 =
                buildGetRequest("/user", getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder2)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                // content type
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Matchers.containsString("response_schema")))
                .andExpect(content().string(Matchers.containsString("response_code")))
                .andExpect(content().string(Matchers.containsString("response_message")))
                .andReturn();

        UserAit expectedUser = userRepository.findById(id).orElseThrow(UserNofFoundException::new);
        assertEquals(expectedUser.getId(), id);
        assertEquals(expectedUser.getName(), name);
    }

    @Test
    void getDetailUser() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<LoginRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        int id = responseTemplate.getResponseOutput().getDetail().getId();
        String name = responseTemplate.getResponseOutput().getDetail().getName();
        
        MockHttpServletRequestBuilder requestBuilder2 =
                buildGetRequest("/user/" + id, getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder2)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                // content type
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Matchers.containsString("response_schema")))
                .andExpect(content().string(Matchers.containsString("response_code")))
                .andExpect(content().string(Matchers.containsString("response_message")))
                .andReturn();

        UserAit expectedUser = userRepository.findById(id).orElseThrow(UserNofFoundException::new);
        assertEquals(expectedUser.getId(), id);
        assertEquals(expectedUser.getName(), name);
    }

    @Test
    void fetchUser() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildGetRequest("/user/fetch", getAccessToken());
    }

    @Test
    void updateUser() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<LoginRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        int id = responseTemplate.getResponseOutput().getDetail().getId();

        MockHttpServletRequestBuilder requestBuilder2 =
                buildGetRequest("/user/" + id, getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder2)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletRequestBuilder requestBuilder3 =
                buildPutRequest("/user/" + id, "{}", getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder3)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                // content type
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Matchers.containsString("response_schema")))
                .andExpect(content().string(Matchers.containsString("response_code")))
                .andExpect(content().string(Matchers.containsString("response_message")))
                .andReturn();
    }

    @Test
    void addBalance() throws Exception {
        // WHEN
        fetchAccessToken();
        // addUser
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{}", getAccessToken());

        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<LoginRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        int id = responseTemplate.getResponseOutput().getDetail().getId();

        MockHttpServletRequestBuilder requestBuilder2 =
                buildGetRequest("/user/" + id, getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder2)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletRequestBuilder requestBuilder3 =
                buildPostRequest("/user/addBalance/" + id, "{}", getAccessToken());
    }

    @Test
    void deleteUser() throws Exception {
        // WHEN
        fetchAccessToken();
        // addUser
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{}", getAccessToken());

        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<LoginRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        int id = responseTemplate.getResponseOutput().getDetail().getId();

        // Delete
        MockHttpServletRequestBuilder requestBuilder2 =
                buildDeleteRequest("/user/" + id, getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder2)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                // content type
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Matchers.containsString("response_schema")))
                .andExpect(content().string(Matchers.containsString("response_code")))
                .andExpect(content().string(Matchers.containsString("response_message")))
                .andReturn();
    }
}