package ru.tolmatskaya.framework.task2.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.tolmatskaya.framework.task1.pages.StartPage;

public class RaspisaniyaPage extends BasePage{
    private static final Logger logger = Logger.getLogger(RaspisaniyaPage.class);
    @FindBy(xpath = "//h1[@class='h1 hero__title color-white animate' and @data-animate='']")
    private WebElement title;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/' and @target='_blank' and contains(@class, 'btn') and contains(@class, 'text-button')]")
    private WebElement lookOnTheWebsite;

    @Step("Проверяем, что на странице присутствует заголовк Расписнаия")
    public RaspisaniyaPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок ", "Расписания", title.getText());
        logger.info("Нужный заголовок присутствует на странице");
        return pageManager.getRaspisaniyaPage();
    }

    @Step("Кликаем на просмотр расписания на сайте")
    public GroupSchedulePage clickLookOnTheShedule() {
        scrollToElementJs(lookOnTheWebsite);
        waitUntilElementToBeClickable(lookOnTheWebsite).click();
        moveToNewTab();
        logger.info("Переход на страницу с просмотром расписания на сайте");
        return pageManager.getGroupSchedulePage();
    }

}
