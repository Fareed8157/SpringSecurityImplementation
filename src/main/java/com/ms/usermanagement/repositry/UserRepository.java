package com.ms.usermanagement.repositry;

import com.ms.usermanagement.model.User;

public interface UserRepository extends BaseRepository<User> {
    User findByEmail(String email);
}
