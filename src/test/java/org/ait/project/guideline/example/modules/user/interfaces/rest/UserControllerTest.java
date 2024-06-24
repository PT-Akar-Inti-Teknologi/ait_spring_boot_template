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
    void testFlowUser_Success() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        /** User Access Token **/
        fetchAccessToken();

        /** Endpoint Add User **/
        MockHttpServletRequestBuilder requestAddUser =
                buildPostRequest("/user","{\n" +
                        "    \"name\":\"TESTING\",\n" +
                        "    \"email\":\"test@integration.org\",\n" +
                        "    \"amount\":100,\n" +
                        "    \"rolesId\":[2]\n" +
                        "}", getAccessToken());

        getMockMvc().perform(requestAddUser)
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

        /** Endpoint Get All Users **/
        MockHttpServletRequestBuilder requestAllUsers =
                buildGetRequest("/user/all", getAccessToken());

        MvcResult mvcResultAll = getMockMvc().perform(requestAllUsers)
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
                .andExpect(jsonPath("$.response_output.list.content[0].id").exists())
                .andExpect(jsonPath("$.response_output.list.content[0].name").exists())
                .andReturn();

        String content = mvcResultAll.getResponse().getContentAsString();
        ResponseTemplate<ResponseCollection<UserRes>> responseTemplate = objectMapper.readValue(content, new TypeReference<>() {});
        String id = responseTemplate.getResponseOutput().getList().getContent().get(0).getId();

        /** Endpoint Detail User **/
        MockHttpServletRequestBuilder requestDetailUser =
                buildGetRequest("/user/" + id, getAccessToken());

        MvcResult mvcResultDetail = getMockMvc().perform(requestDetailUser)
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

        /** Endpoint Update User **/
        MockHttpServletRequestBuilder requestBuilder =
                buildPutRequest("/user/" + id,"{\n" +
                        "    \"name\":\"TESTING\",\n" +
                        "    \"email\":\"test@integration.org\",\n" +
                        "    \"amount\":11500,\n" +
                        "    \"rolesId\":[2]\n" +
                        "}", getAccessToken());

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

        /** Endpoint Add Balance **/
        MockHttpServletRequestBuilder requestBalance =
                buildPutRequest("/user/addBalance/" + id, "{\n" +
                        "    \"name\":\"TESTING\",\n" +
                        "    \"email\":\"test@integration.org\",\n" +
                        "    \"amount\":13000,\n" +
                        "    \"rolesId\":[2]\n" +
                        "}", getAccessToken());

        getMockMvc().perform(requestBalance)
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

        /** Endpoint Delete User **/
        MockHttpServletRequestBuilder requestDelete =
                buildDeleteRequest("/user/" + id, getAccessToken());

        getMockMvc().perform(requestDelete)
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

        content = mvcResultDetail.getResponse().getContentAsString();
        ResponseTemplate<ResponseDetail<UserRes>> responseTemplate2 = objectMapper.readValue(content, new TypeReference<>() {});
        id = responseTemplate2.getResponseOutput().getDetail().getId();
        String name = responseTemplate2.getResponseOutput().getDetail().getName();

        UserAit expectedUser = userRepository.findById(Integer.parseInt(id)).orElseThrow(UserNotFoundException::new);
        assertEquals(expectedUser.getId(), Integer.parseInt(id));
        assertEquals(expectedUser.getName(), name);
    }
}