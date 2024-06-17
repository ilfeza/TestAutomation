package ru.tolmatskaya.framework.task2.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RaspisaniyaPage extends BasePage{
    @FindBy(xpath = "//h1[@class='h1 hero__title color-white animate' and @data-animate='']")
    private WebElement title;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/' and @target='_blank' and contains(@class, 'btn') and contains(@class, 'text-button')]")
    private WebElement lookOnTheWebsite;

    @Step("Проверяем, что на странице присутствует заголовк Расписнаия")
    public RaspisaniyaPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок ", "Расписания", title.getText());
        return pageManager.getRaspisaniyaPage();
    }

    @Step("Кликаем на просмотр расписания на сайте")
    public GroupSchedulePage clickLookOnTheShedule() {
        scrollToElementJs(lookOnTheWebsite);
        waitUntilElementToBeClickable(lookOnTheWebsite).click();
        moveToNewTab();
        return pageManager.getGroupSchedulePage();
    }

}
