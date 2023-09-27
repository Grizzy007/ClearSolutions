package ua.clear.solutions.clearsolutionstest.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    @Column(name = "Email")
    private String email;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Last_Name")
    private String lastName;
    @Column(name = "Birthday")
    private LocalDate birthday;
    @Nullable
    @Column(name = "Address")
    private String address;
    @Nullable
    @Column(name = "Phone_Number")
    private String phoneNumber;

    public User(String email, String firstName, String lastName, LocalDate birthday,
                @Nullable String address, @Nullable String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        if (birthday.isBefore(LocalDate.now())) {
            this.birthday = birthday;
        } else {
            throw new IllegalArgumentException("Birthday must be earlier than today!!!");
        }
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
