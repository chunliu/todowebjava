package com.azurelabs.todowebjava;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TodoControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void creatTodoItem() throws Exception {
        TodoItem item = new TodoItem();
        item.setName("Task 1");
        item.setIsCompleted(false);

        mvc.perform(post("/api/todo")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(item)))
        .andDo(print())
        .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getHomePage_Status200() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk());
    }
}