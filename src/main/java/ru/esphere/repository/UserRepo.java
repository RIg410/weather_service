package ru.esphere.repository;

import ru.esphere.model.User;

import java.util.Optional;

public interface UserRepo {
    Optional<User> getByLogin(String login);
}
