package ru.tolmatskaya.framework.task2.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class StartPage extends BasePage {
    private static final Logger logger = Logger.getLogger(StartPage.class);
    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//a[@class='user-nav__item-link' and @href='/obuchauschimsya/raspisaniya/']")
    private WebElement schedulesButton;

    @Step("Проверяем, что на странице присутствует заголовок")
    public StartPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок", "Московский Политех", title.getText());
        logger.info("Нужный заголов присутсвует на странице");
        return pageManager.getStartPage();
    }

    @Step("Кликаем на расписание")
    public RaspisaniyaPage clickOnScheduleLink() {
        waitUntilElementToBeClickable(schedulesButton).click();
        logger.info("Переход на страницу с расписанием");
        return pageManager.getRaspisaniyaPage();
    }

}
