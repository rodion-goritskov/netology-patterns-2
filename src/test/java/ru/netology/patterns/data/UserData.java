package ru.netology.patterns.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private String login;
    private String password;
    private UserStatus status;
}
