package com.humansreadcode.example.web;

import com.humansreadcode.example.Application;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserControllerIntTest {

    @Autowired
    private UserController userController;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

    @Test
    public void testGetUsersPage0Size20() throws Exception {

        final MvcResult mvcResult = mockMvc
                .perform(get(UserController.REQUEST_PATH_API_USERS).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.content[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$.content[0].firstName").value("User"))
                .andExpect(jsonPath("$.content[0].lastName").value("One"))
                .andExpect(jsonPath("$.content[1].email").value("user2@example.com"))
                .andExpect(jsonPath("$.content[1].firstName").value("User"))
                .andExpect(jsonPath("$.content[1].lastName").value("Two"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.first").value("true"))
                .andExpect(jsonPath("$.last").value("true"));
    }

    @Test
    public void testGetUsersPage0Size1() throws Exception {
        final MockHttpServletRequestBuilder getRequest = get(UserController.REQUEST_PATH_API_USERS)
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON);

        final MvcResult mvcResult = mockMvc
                .perform(getRequest)
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.content[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.first").value("true"))
                .andExpect(jsonPath("$.last").value("false"));
    }

    @Test
    public void testGetUsersPage1Size1() throws Exception {

        final MockHttpServletRequestBuilder getRequest = get("/api/users")
                .param("page", "1")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON);

        final MvcResult mvcResult = mockMvc
                .perform(getRequest)
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.content[0].email").value("user2@example.com"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.size").value(1))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.first").value("false"))
                .andExpect(jsonPath("$.last").value("true"));
    }

    @Test
    public void testGetUser() throws Exception {

        final MockHttpServletRequestBuilder getRequest = get(UserController.REQUEST_PATH_API_USERS +
                                                                     "/590f86d92449343841cc2c3f")
                .accept(MediaType.APPLICATION_JSON);

        final MvcResult mvcResult = mockMvc
                .perform(getRequest)
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.lastName").value("One"));
    }

    @Test
    public void testGetUserNotFound() throws Exception {

        final MvcResult mvcResult = mockMvc
                .perform(get(UserController.REQUEST_PATH_API_USERS + "/doesntexist").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.request().asyncStarted())
                .andReturn();

        mockMvc
                .perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound());
    }
}
