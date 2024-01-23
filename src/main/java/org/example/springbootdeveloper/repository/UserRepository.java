package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Spring data jpa는 메서드 명명 규칙에 맞춰 메서드를 선언하면 자동으로 쿼리를 생성함
    Optional<User> findByEmail(String email);


}
