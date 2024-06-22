package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchAviaPage extends BasePage{
    private static final Logger logger = Logger.getLogger(StartPage.class);
    @FindBy(xpath = "//span[text()='Без багажа']")
    private WebElement searchAvia;

    @FindBy(xpath = "//a[contains(@href, '/avia/redirect') and @target='_blank']")
    private WebElement buyAvia;

    @Step("Кликаем на билет")
    public SearchAviaPage findAvia(){
        waitUntilElementToBeClickable(searchAvia).click();
        logger.info("Выбран билет");
        return this;
    }

    @Step("Кликаем на купить")
    public SearchAviaPage buyAvia(){
        waitUntilElementToBeClickable(buyAvia).click();
        String originalTab = getTab();
        moveToNewTab();

        String newTab = getTab();
        Assert.assertNotEquals("Не удалось открыть новую вкладку", originalTab, newTab);
        logger.info("Переход на страницу покупки билета");
        return this;
    }
}
