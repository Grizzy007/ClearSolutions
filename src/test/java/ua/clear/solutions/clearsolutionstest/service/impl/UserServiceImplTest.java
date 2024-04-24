package ua.clear.solutions.clearsolutionstest.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.clear.solutions.clearsolutionstest.entity.User;
import ua.clear.solutions.clearsolutionstest.entity.dto.UserDto;
import ua.clear.solutions.clearsolutionstest.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Value("${min.required.age}")
    private int minAge;

    @Test
    void testCreateUserWithValidAge() {
        User user = new User("asdada@email.com", "John", "Johns",
                LocalDate.of(2000, 1, 1), null, null);
        user.setBirthday(LocalDate.now().minusYears(minAge + 1));
        User createdUser = userService.create(UserDto.fromUser(user));
        assertNotNull(createdUser);
    }

    @Test
    void testCreateUserWithInvalidAge() {
        User user = new User();
        user.setBirthday(LocalDate.now());
        assertThrows(NullPointerException.class, () -> {
            userService.create(UserDto.fromUser(user));
        });
    }

    @Test
    void invalidUserUpdate() {
        Integer id = 1;
        String email = "dfadf@e";
        String phone = "+380456789";

        assertThrows(IllegalArgumentException.class, () -> {
            userService.update(String.valueOf(id), email, phone);
        });
    }

    @Test
    void testDeleteUser() {
        User userToDelete = userService.create(UserDto.fromUser(new User("asdada@email.com", "John", "Johns",
                LocalDate.of(2000, 1, 1), null, null)));
        Integer userIdToDelete = userToDelete.getId();
        assertNotNull(userService.delete(userIdToDelete));
    }

    @Test
    void testSearchUsersByBirthDateRange() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(2005, 12, 31);

        List<User> users = userService.findByBirthdayRange(fromDate, toDate);

        for (User user : users) {
            LocalDate userBirthDate = user.getBirthday();
            assertTrue(userBirthDate.isAfter(fromDate) || userBirthDate.isEqual(fromDate));
            assertTrue(userBirthDate.isBefore(toDate) || userBirthDate.isEqual(toDate));
        }
    }
}