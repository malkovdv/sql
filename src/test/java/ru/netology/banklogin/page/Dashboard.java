package ru.netology.banklogin.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class Dashboard {
    public final SelenideElement heading = $("[data-test-id=dashboard]");

    public Dashboard() {
        heading.shouldHave(text("Личный кабинет")).shouldBe(visible);
    }
}