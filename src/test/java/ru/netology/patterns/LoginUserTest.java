package ru.netology.patterns;

import org.junit.jupiter.api.Test;
import ru.netology.patterns.data.UserData;
import ru.netology.patterns.data.UserDataGenerator;
import ru.netology.patterns.pages.DashboardPage;
import ru.netology.patterns.pages.LoginPage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginUserTest {
    @Test
    public void successfulLogin() {
        UserData user = UserDataGenerator
                .generateActiveUser();

        open("http://localhost:9999", LoginPage.class)
                .fillLogin(user.getLogin())
                .fillPassword(user.getPassword())
                .submit(DashboardPage.class)
                .title().shouldBe(visible);
    }

    @Test
    public void blockedUser() {
        UserData user = UserDataGenerator
                .generateBlockedUser();

        LoginPage.ErrorMessage errorMessage = open("/", LoginPage.class)
                .fillLogin(user.getLogin())
                .fillPassword(user.getPassword())
                .submit(LoginPage.class)
                .getError();

        new DashboardPage().title().shouldNotBe(visible);
        assertThat(errorMessage)
                .extracting("title", "content")
                .contains("Ошибка", "Ошибка! Пользователь заблокирован");
    }

    @Test
    public void invalidPassword() {
        UserData user = UserDataGenerator
                .generateActiveUser();

        LoginPage.ErrorMessage errorMessage = open("/", LoginPage.class)
                .fillLogin(user.getLogin())
                .fillPassword("invalid-password")
                .submit(LoginPage.class)
                .getError();

        new DashboardPage().title().shouldNotBe(visible);
        assertThat(errorMessage)
                .extracting("title", "content")
                .contains("Ошибка", "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    public void nonExistingLogin() {
        UserData user = UserDataGenerator
                .generateActiveUser();

        LoginPage.ErrorMessage errorMessage = open("/", LoginPage.class)
                .fillLogin(user.getLogin() + "wrong-login")
                .fillPassword(user.getPassword())
                .submit(LoginPage.class)
                .getError();

        new DashboardPage().title().shouldNotBe(visible);
        assertThat(errorMessage)
                .extracting("title", "content")
                .contains("Ошибка", "Ошибка! Неверно указан логин или пароль");
    }
}
