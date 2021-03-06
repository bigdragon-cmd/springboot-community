package org.dgut.community.repository.user;

import org.dgut.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNameAndUserPassword(String name, String password);
    User findByUserName(String userName);
    List<User> findByUserNameLike(String userName);
}
