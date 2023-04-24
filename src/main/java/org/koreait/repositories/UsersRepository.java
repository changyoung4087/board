package org.koreait.repositories;

import org.koreait.entities.QUsers;
import org.koreait.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UsersRepository extends JpaRepository<Users, Long>, QuerydslPredicateExecutor {

    Users findByUserId (String userId);
    /**
     * 가임된 회원 여부 체크
     * @param userId
     * @return
     */
    default boolean userExists(String userId) {
        QUsers users = QUsers.users;

        boolean exists = exists(users.userId.eq(userId));

        return exists;
    }
}

