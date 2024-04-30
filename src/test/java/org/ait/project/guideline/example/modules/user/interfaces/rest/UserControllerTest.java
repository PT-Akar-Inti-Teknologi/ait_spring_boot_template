package org.ait.project.guideline.example.modules.user.interfaces.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ait.project.guideline.example.BaseIntegrationTest;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.UserAit;
import org.ait.project.guideline.example.modules.user.model.jpa.repository.UserRepository;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.ait.project.guideline.example.shared.exception.UserNotFoundException;
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
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{\n" +
                        "    \"name\":\"TESTING\",\n" +
                        "    \"email\":\"test@integration.org\",\n" +
                        "    \"amount\":100,\n" +
                        "    \"rolesId\":[2]\n" +
                        "}", getAccessToken());

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
        //WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildGetRequest("/user/all", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
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

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseCollection<UserRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        String id = responseTemplate.getResponseOutput().getList().getContent().get(0).getId();
        String name = responseTemplate.getResponseOutput().getList().getContent().get(0).getName();

        UserAit expectedUser = userRepository.findById(Integer.parseInt(id)).orElseThrow(UserNotFoundException::new);
        assertEquals(expectedUser.getId(), Integer.parseInt(id));
        assertEquals(expectedUser.getName(), name);
    }

    @Test
    void getDetailUser() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{\n" +
                        "    \"name\":\"TESTING\",\n" +
                        "    \"email\":\"test@integration.org\",\n" +
                        "    \"amount\":100,\n" +
                        "    \"rolesId\":[2]\n" +
                        "}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        String id = responseTemplate.getResponseOutput().getDetail().getId();
        String name = responseTemplate.getResponseOutput().getDetail().getName();

        // WHEN
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

        UserAit expectedUser = userRepository.findById(Integer.parseInt(id)).orElseThrow(UserNotFoundException::new);
        assertEquals(expectedUser.getId(), Integer.parseInt(id));
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
                buildPostRequest("/user","{\n" +
                        "    \"name\":\"TESTING\",\n" +
                        "    \"email\":\"test@integration.org\",\n" +
                        "    \"amount\":100,\n" +
                        "    \"rolesId\":[1]\n" +
                        "}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        String id = responseTemplate.getResponseOutput().getDetail().getId();

        MockHttpServletRequestBuilder requestBuilder2 =
                buildPutRequest("/user/" + id,"{\n" +
                        "    \"name\":\"TESTING2\",\n" +
                        "    \"email\":\"test2@integration.org\",\n" +
                        "    \"amount\":200,\n" +
                        "    \"rolesId\":[1,2]\n" +
                        "}", getAccessToken());

        // THEN
        MvcResult mvcResult2 = getMockMvc().perform(requestBuilder2)
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

        String content2 = mvcResult2.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate2 = objectMapper.readValue(content2, new TypeReference<>() {});
        String id2 = responseTemplate2.getResponseOutput().getDetail().getId();

        UserAit expectedUser = userRepository.findById(Integer.parseInt(id)).orElseThrow(UserNotFoundException::new);
        assertEquals(expectedUser.getId(), Integer.parseInt(id2));
    }

    @Test
    void addBalance() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{\n" +
                        "    \"name\":\"TESTING3\",\n" +
                        "    \"email\":\"test3@integration.org\",\n" +
                        "    \"amount\":300,\n" +
                        "    \"rolesId\":[1]\n" +
                        "}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        String id = responseTemplate.getResponseOutput().getDetail().getId();

        // WHEN
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

        // WHEN
        MockHttpServletRequestBuilder requestBuilder3 =
                buildPutRequest("/user/addBalance/" + id, "{\n" +
                        "    \"name\":\"TESTING3\",\n" +
                        "    \"email\":\"test3@integration.org\",\n" +
                        "    \"amount\":13000,\n" +
                        "    \"rolesId\":[1]\n" +
                        "}", getAccessToken());

        MvcResult mvcResult3 = getMockMvc().perform(requestBuilder3)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        String content2 = mvcResult3.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate2 = objectMapper.readValue(content2, new TypeReference<>() {});
        String id2 = responseTemplate2.getResponseOutput().getDetail().getId();

        UserAit expectedUser = userRepository.findById(Integer.parseInt(id2)).orElseThrow(UserNotFoundException::new);
        assertEquals(expectedUser.getId(), Integer.parseInt(id2));
    }

    @Test
    void deleteUser() throws Exception {
        // WHEN
        fetchAccessToken();
        MockHttpServletRequestBuilder requestBuilder =
                buildPostRequest("/user","{\n" +
                        "    \"name\":\"TESTING4\",\n" +
                        "    \"email\":\"test4@integration.org\",\n" +
                        "    \"amount\":400,\n" +
                        "    \"rolesId\":[2,1]\n" +
                        "}", getAccessToken());

        // THEN
        MvcResult mvcResult = getMockMvc().perform(requestBuilder)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        String id = responseTemplate.getResponseOutput().getDetail().getId();

        // WHEN
        MockHttpServletRequestBuilder requestBuilder2 =
                buildGetRequest("/user/" + id, getAccessToken());

        // THEN
        getMockMvc().perform(requestBuilder2)
                // print response
                .andDo(print())
                // status response
                .andExpect(status().isOk())
                .andReturn();

        // WHEN
        MockHttpServletRequestBuilder requestBuilder3 =
                buildDeleteRequest("/user/" + id, getAccessToken());

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
}