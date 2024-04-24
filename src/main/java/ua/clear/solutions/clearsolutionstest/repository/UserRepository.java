package ua.clear.solutions.clearsolutionstest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.clear.solutions.clearsolutionstest.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByBirthdayBetween(LocalDate from, LocalDate to);
}
