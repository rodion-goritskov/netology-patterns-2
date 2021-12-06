package ru.netology.patterns.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {
    private final SelenideElement loginField = $("input[name=login]");
    private final SelenideElement passwordField = $("input[name=password]");
    private final SelenideElement submitButton = $("button[data-test-id=action-login]");

    public LoginPage fillLogin(String login) {
        loginField.clear();
        loginField.sendKeys(login);

        return this;
    }

    public LoginPage fillPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);

        return this;
    }

    public <T>T submit(Class<T> expectedPageClass) {
        submitButton.click();
        return page(expectedPageClass);
    }

    public ErrorMessage getError() {
        return new ErrorMessage();
    }

    public static class ErrorMessage {
        private final SelenideElement messageRoot = $("div[data-test-id=error-notification]");

        public String getTitle() {
            return messageRoot.$(".notification__title").text();
        }

        public String getContent() {
            return messageRoot.$(".notification__content").text();
        }
    }
}
