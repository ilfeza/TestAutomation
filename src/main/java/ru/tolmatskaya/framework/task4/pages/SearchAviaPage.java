package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchAviaPage extends BasePage{
    private static final Logger logger = Logger.getLogger(StartPage.class);
    @FindBy(xpath = "//span[text()='Лучшая цена']/ancestor::div[3]")
    private WebElement searchAvia;

    @FindBy(xpath = "//span[text()='Купить']/ancestor::div[3]")
    private WebElement buyAvia;

    @Step("Кликаем на билет")
    public SearchAviaPage findAvia(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
