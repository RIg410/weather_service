package ru.esphere.repository.Impl;

import org.springframework.stereotype.Repository;
import ru.esphere.model.User;
import ru.esphere.repository.UserRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserRepo implements UserRepo {
    private final Map<String, User> users;

    public InMemoryUserRepo() {
        users = new HashMap<>();
        users.put("rig410", new User("rig410", "$2a$10$sbJFA55v.h.eNQ0/w.2.mu7C.a/sDewz8D8EYdRd1ZpykdKDPBySm"));//password1
        users.put("user", new User("user", "$2a$10$MTohxy7u8nU3iYrfVbLVtecLm0ba.T5khl8dk75L3TedYYFXEd/C."));//user
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.ofNullable(users.get(login));
    }
}
