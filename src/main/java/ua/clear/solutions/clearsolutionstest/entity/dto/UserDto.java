package ua.clear.solutions.clearsolutionstest.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.clear.solutions.clearsolutionstest.entity.User;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String email;
    private LocalDate birthday;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public UserDto(String email, String firstName, String lastName, LocalDate birthday, String phoneNumber, String address) {
        this.email = email;
        this.birthday = birthday;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setBirthday(user.getBirthday());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());

        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setBirthday(userDto.getBirthday());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAddress(userDto.getAddress().orElse(null));
        user.setPhoneNumber(userDto.getPhoneNumber().orElse(null));

        return user;
    }

    public Optional<String> getAddress() {
        if (address == null) {
            return Optional.empty();
        }
        return Optional.of(address);
    }

    public Optional<String> getPhoneNumber() {
        if (phoneNumber == null) {
            return Optional.empty();
        }
        return Optional.of(phoneNumber);
    }
}