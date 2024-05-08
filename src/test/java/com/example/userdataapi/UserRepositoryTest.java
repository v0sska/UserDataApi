package com.example.userdataapi;

import com.example.userdataapi.entities.Users;
import com.example.userdataapi.repositories.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void findByBirthDateBetween_ShouldReturnUsers() {
        // Given
        Users user1 = new Users();
        user1.setEmail("user1@qq.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setBirthDate(LocalDate.of(1990, 1, 1));
        entityManager.persist(user1);

        Users user2 = new Users();
        user2.setEmail("user2@qq.com");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setBirthDate(LocalDate.of(1995, 5, 15));
        entityManager.persist(user2);

        Users user3 = new Users();
        user3.setFirstName("Alice");
        user3.setLastName("Johnson");
        user3.setEmail("user3@qq.com");
        user3.setBirthDate(LocalDate.of(1985, 8, 20));
        entityManager.persist(user3);

        entityManager.flush();

        // When
        LocalDate startDate = LocalDate.of(1990, 1, 1);
        LocalDate endDate = LocalDate.of(1995, 12, 31);
        List<Users> users = usersRepository.findByBirthDateBetween(startDate, endDate);

        // Then
        assertThat(users).hasSize(2);
        assertThat(users).contains(user1, user2);
    }

}
