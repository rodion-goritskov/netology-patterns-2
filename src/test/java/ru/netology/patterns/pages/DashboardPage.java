package ru.netology.patterns.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage {
    public SelenideElement title() {
        return $x("//h2[contains(.,'Личный кабинет')]");
    }

}
