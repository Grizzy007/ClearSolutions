package ua.clear.solutions.clearsolutionstest.service;

import ua.clear.solutions.clearsolutionstest.entity.User;
import ua.clear.solutions.clearsolutionstest.entity.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User create(UserDto userDto);

    List<User> getAll();

    List<User> findByBirthdayRange(LocalDate from, LocalDate to);

    User update(String id, String email, String phoneNumber);

    User updateAllFields(String id, UserDto userDto);

    User delete(Integer id);
}
