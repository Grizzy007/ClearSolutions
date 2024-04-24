package ua.clear.solutions.clearsolutionstest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ua.clear.solutions.clearsolutionstest.entity.User;
import ua.clear.solutions.clearsolutionstest.entity.dto.UserDto;
import ua.clear.solutions.clearsolutionstest.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    private final UserServiceImpl userService;
    @Value("${min.required.age}")
    private int minAge;


    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users.stream().toList());
    }

    @PostMapping("users/create")
    public ResponseEntity<HttpStatus> createUser(@RequestBody UserDto userDto) {
        try {
            if ((LocalDate.now().minusYears(userDto.getBirthday().getYear()).getYear()) >= minAge) {
                userService.create(userDto);
            } else {
                throw new IllegalArgumentException("Age must be grater than " + minAge);
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal argument for user's field", ex);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("users/{id}/delete")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(value = "id") Integer id) {
        try {
            userService.delete(id);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("users/{id}/partialUpdate")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") Integer id, @RequestParam String email,
                                             @RequestParam String phoneNumber) {
        try {
            userService.update(String.valueOf(id), email, phoneNumber);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal argument for updating some field", ex);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("users/{id}/fullUpdate")
    public ResponseEntity<HttpStatus> fullUpdate(@PathVariable(value = "id") Integer id, @RequestBody UserDto userDto) {
        try {
            userService.updateAllFields(String.valueOf(id), userDto);
        } catch (NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal argument for updating fields", ex);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("users/getInRange")
    public ResponseEntity<List<User>> getInRange(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        try {
            if (from.isBefore(to)) {
                return ResponseEntity.ok(userService.findByBirthdayRange(from, to).stream().toList());
            } else {
                throw new IllegalArgumentException("Illegal arguments");
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal date arguments", ex);
        }
    }

}
