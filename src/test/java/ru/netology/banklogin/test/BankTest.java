package ru.netology.banklogin.test;

import org.junit.jupiter.api.*;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQL;
import ru.netology.banklogin.page.Login;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.banklogin.data.SQL.cleanAuthCodes;
import static ru.netology.banklogin.data.SQL.cleanDatabase;

public class BankTest {
    Login login;
    DataHelper.AuthInfo authInfo = DataHelper.getAuthInfoTestData();
@AfterAll
static void tearDownAll() {
    cleanDatabase();
}

@AfterEach
    void tearDown() {
    cleanAuthCodes();
}

@BeforeEach
    void setUp() {
    login = open("http://localhost:9999", Login.class);
}

@Test
@DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
    var verificationPage = login.validLogin(authInfo);
    var verificationCode = SQL.getVerificationCode();
    verificationPage.validVerify(verificationCode.getCode());
}

@Test
@DisplayName("Should get error notification if user is not exist in base")
void shouldGetErrorNotidicationIfLoginWithRandomUserWithoutAddingToBase() {
    var authInfo = DataHelper.generateRandomUser();
    login.login(authInfo);
    login.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
}
@Test
@DisplayName("Should get error notification if login with exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
    var verificationPage = login.validLogin(authInfo);
    var verificationCode = DataHelper.generateRandomVerificationCode();
    verificationPage.verify(verificationCode.getCode());
    verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
}
    @Test
    @DisplayName("Should block user after three invalid password attempts")
    void shouldBlockUserAfterThreeInvalidPasswordAttempts() {
        var validUser = DataHelper.getAuthInfoTestData();
        var invalidPasswordUser =
                new DataHelper.AuthInfo(validUser.getLogin(), "wrong-password");

        // 1-я попытка
        login.login(invalidPasswordUser);
        login.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

        // 2-я попытка
        login.login(invalidPasswordUser);
        login.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

        // 3-я попытка
        login.login(invalidPasswordUser);
        login.verifyErrorNotification("Ошибка! Пользователь заблокирован");
    }
}
