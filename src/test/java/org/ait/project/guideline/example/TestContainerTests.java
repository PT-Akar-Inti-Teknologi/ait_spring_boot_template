package org.ait.project.guideline.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.junit.jupiter.api.Test;
import org.mockserver.model.Header;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.ait.project.guideline.example.MockServerContainerExtension.mockServerClient;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Testcontainers
@AutoConfigureMockMvc
class TestContainerTests extends AbstractClass{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserCore userCore;

    @Test
    void getAllUsers() throws Exception{
        UserReq req = new UserReq();
        req.setName("test");
        req.setEmail("test");
        req.setAmount(BigDecimal.ZERO);
        req.setRolesId(List.of());

        userCore.addUser(req);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/user/all")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void insertUser() throws Exception {
        UserReq req = new UserReq();
        req.setName("test2");
        req.setEmail("test2");
        req.setAmount(BigDecimal.ZERO);
        req.setRolesId(List.of());

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String body = ow.writeValueAsString(req);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/user")
                .content(body)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(requestBuilder).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void mockServerTest() {
        mockServerClient.when(
                request().withMethod("GET").withPath("/api/user/all")
        ).respond(response().withStatusCode(200).withHeader(new Header("Content-Type", "application/json;charset=utf-8"))
                .withBody(json("""
                        {"response_schema":{"response_code":"PMRK-200","response_message":"Success"},"response_output":{"detail":{"customerName":"test","customerEmail":"test","orderId":21,"orderDetails":[{"row_detail_id":1,"id":21,"product_name":"Laptop","product_price":100,"product_qty":1,"product_total":100}],"ttlPrice":100}}}
                        """)));

        String response = restTemplate.getForObject("http://localhost:" + mockServerClient.getPort() + "/api/user/all", String.class);

        System.out.print(response);
        mockServerClient.verify(request().withMethod("GET").withPath("/api/user/all"), VerificationTimes.atLeast(1));
    }
}
