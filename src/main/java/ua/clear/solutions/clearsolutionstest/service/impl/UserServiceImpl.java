package ua.clear.solutions.clearsolutionstest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.clear.solutions.clearsolutionstest.entity.User;
import ua.clear.solutions.clearsolutionstest.entity.dto.UserDto;
import ua.clear.solutions.clearsolutionstest.repository.UserRepository;
import ua.clear.solutions.clearsolutionstest.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final String EMAIL_PATTERN = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserDto userDto) {
        User userToCreate = UserDto.toUser(userDto);
        if (userToCreate.getEmail().matches(EMAIL_PATTERN)) {
            userRepository.save(userToCreate);
            return userToCreate;
        } else {
            throw new IllegalArgumentException("Incorrect email!");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findByBirthdayRange(LocalDate from, LocalDate to) {

        return userRepository.findByBirthdayBetween(from, to);
    }

    @Override
    public User update(String id, String email, String phoneNumber) {
        Optional<User> userToUpdate = userRepository.findById(id);
        if (userToUpdate.isPresent()) {
            if (email.matches(EMAIL_PATTERN)) {
                userToUpdate.get().setEmail(email);
                userToUpdate.get().setPhoneNumber(phoneNumber);
                userRepository.save(userToUpdate.get());
                return userToUpdate.get();
            } else {
                throw new IllegalArgumentException("Illegal argument for user");
            }
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public User updateAllFields(String id, UserDto userDto) {
        Optional<User> userFullUpdate = userRepository.findById(id);
        if (userFullUpdate.isPresent()) {
            if ((LocalDate.now().minusYears(userDto.getBirthday().getYear()).getYear()) >= 18 &&
                    userDto.getEmail().matches(EMAIL_PATTERN)) {
                userFullUpdate.get().setEmail(userDto.getEmail());
                userFullUpdate.get().setFirstName(userDto.getFirstName());
                userFullUpdate.get().setLastName(userDto.getLastName());
                userFullUpdate.get().setBirthday(userDto.getBirthday());
                userFullUpdate.get().setAddress(userDto.getAddress().orElse(null));
                userFullUpdate.get().setPhoneNumber(userDto.getPhoneNumber().orElse(null));
                userRepository.save(userFullUpdate.get());
                return userFullUpdate.get();
            } else {
                throw new IllegalArgumentException("Illegal argument for user");
            }
        } else {
            throw new NoSuchElementException("User not found");
        }
    }

    @Override
    public User delete(Integer id) {
        Optional<User> userToDelete = userRepository.findById(String.valueOf(id));
        if (userToDelete.isPresent()) {
            userRepository.delete(userToDelete.get());
            return userToDelete.get();
        } else {
            throw new NoSuchElementException("User not found");
        }
    }
}
