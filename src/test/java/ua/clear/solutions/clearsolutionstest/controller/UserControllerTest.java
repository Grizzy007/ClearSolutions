package ua.clear.solutions.clearsolutionstest.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ua.clear.solutions.clearsolutionstest.entity.User;
import ua.clear.solutions.clearsolutionstest.entity.dto.UserDto;
import ua.clear.solutions.clearsolutionstest.util.JsonUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(5)
    void testGetUsers() throws Exception {
        List<User> userList = new ArrayList<>();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(1)
    void testCreateUser() throws Exception {
        UserDto userDto = new UserDto("asdada@email.com", "John", "Johns",
                LocalDate.of(2000, 1, 1), null, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(userDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Order(6)
    void testDeleteUser() throws Exception {
        Integer userId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/users/{id}/delete", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }


    @Test
    @Order(2)
    void testPartialUpdate() throws Exception {
        Integer userId = 1;
        String email = "new-email@example.com";
        String phoneNumber = "1234567890";

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/users/{id}/partialUpdate", userId)
                        .param("email", email)
                        .param("phoneNumber", phoneNumber))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(3)
    void testFullUpdate() throws Exception {
        Integer userId = 1;
        UserDto userDto = new UserDto("asdada@gmail.ua", "John", "Johns",
                LocalDate.of(2000, 1, 1), "+3803456789", null);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/{id}/fullUpdate", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.toJson(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    void testGetInRange() throws Exception {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2005, 12, 31);
        UserDto userDto1 = new UserDto("asdada@gmail.ua", "John", "Johns",
                LocalDate.of(2000, 1, 1), "+3803456789", null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/getInRange")
                        .param("from", fromDate.toString())
                        .param("to", toDate.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}