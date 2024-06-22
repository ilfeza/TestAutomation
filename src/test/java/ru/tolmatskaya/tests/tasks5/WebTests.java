package ru.tolmatskaya.tests.tasks5;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.tolmatskaya.framework.task5.pages.StartPage;
import ru.tolmatskaya.tests.tasks5.base.BaseTests;

public class WebTests extends BaseTests {
    @Test
    @DisplayName("Проверка, что при нажатии на кнопку отправки образца запроса результат такой же как и через API")
    public void test(){
        StartPage startPage = new StartPage();
        startPage.checkOpenPage()
                .clickOnButtonAndCheckAPI("List users", "get")
                .clickOnButtonAndCheckAPI("Single user", "get")
                .clickOnButtonAndCheckAPI("Single user not found", "get")
                .clickOnButtonAndCheckAPI("List <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource>", "get")
                .clickOnButtonAndCheckAPI("Single <resource> not found", "get")
                .clickOnButtonAndCheckAPI("Create", "post")
                .clickOnButtonAndCheckAPI("Update", "put")
                .clickOnButtonAndCheckAPI("Update", "patch")
                .clickOnButtonAndCheckAPI("Delete", "delete")
                .clickOnButtonAndCheckAPI("Register - successful", "post")
                .clickOnButtonAndCheckAPI("Register - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Login - successful", "post")
                .clickOnButtonAndCheckAPI("Login - unsuccessful", "post")
                .clickOnButtonAndCheckAPI("Delayed response", "get");
    }
}
