package com.ms.usermanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.usermanagement.model.User;
import com.ms.usermanagement.util.EncryptDecrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * As we know there is only one business controller so we have single Test class other we can divide test cases based
 * on different controllers logic
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebMvc
class UsermanagementApplicationIntegrationTests {

    @Value("${app.url}")
    private String baseUrl;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser("fareedmahar@gmail.com")
    @Test
    void getAllUsers() throws Exception {
        List<User> users = Stream.of(
                new User(UUID.fromString("68877038-0a08-4e8b-a920-57d2d5a165e9"), "fareed", EncryptDecrypt.encrypt("password"), "fareed@gmail.com","ROLE_ADMIN"),
                new User(UUID.randomUUID(), "Client1", EncryptDecrypt.encrypt("pwd1"), "client1@gmail.com","ROLE_USER"),
                new User(UUID.randomUUID(), "Client2", EncryptDecrypt.encrypt("pwd2"), "client2@gmail.com","ROLE_USER"),
                new User(UUID.randomUUID(), "Client3", EncryptDecrypt.encrypt("pwd3"), "client3@gmail.com","ROLE_USER")
        ).collect(Collectors.toList());
        String url = baseUrl + "/user/list";
        MvcResult mvcResult = mvc.perform(get(url)).
                andDo(print()).
                andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @WithMockUser("fareedmahar@gmail.com")
    @Test
    void registerUser() throws Exception {
        User user = new User(UUID.randomUUID(), "fareed", EncryptDecrypt.encrypt("password"), "fareed9@gmail.com","ROLE_ADMIN");
        String url = baseUrl + "/user/registration";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @WithMockUser("fareedmahar@gmail.com")
    @Test
    void verifyEmailNotBlank() throws Exception {
        User user = new User(UUID.randomUUID(), "fareed", EncryptDecrypt.encrypt("password"), "","ROLE_USER");
        String url = baseUrl + "/user/registration";
        mvc.perform(MockMvcRequestBuilders.post(url).content(mapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}

