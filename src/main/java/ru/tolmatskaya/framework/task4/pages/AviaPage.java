package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AviaPage extends BasePage {
    private static final Logger logger = Logger.getLogger(StartPage.class);
    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-controls, 'react-aria') and @aria-expanded='true']")
    private WebElement from;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-controls, 'react-aria') and @aria-expanded='true']")
    private WebElement to;

    @FindBy(xpath = "//button[@aria-label='Очистить поле']")
    private WebElement clearButton;


    @FindBy(xpath = "//div[@data-qa='labelPlaceholder' and text()='Туда']")
    private WebElement toButton;

    @FindBy(xpath = "//*[@id='suggest-0']")
    private WebElement elementFrom;

    @FindBy(xpath = "//*[@id='suggest-0']")
    private WebElement elementTo;
    @FindBy(xpath = "//button[@type='submit']/div[text()='Найти']")
    private WebElement findButton;

    @Step("Проверяем присутсвие заголовка на странице")
    public AviaPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок",
                "20 миллионов путешественников каждый месяц ищут у нас авиабилеты, бронируют отели и туры",
                title.getText().trim());
        logger.info("На странице присутствует заголовок");
        return pageManager.getAviaPage();
    }

    @Step("Заполняем откуда - '{fromIn}'")
    public AviaPage fromInput(String fromIn) {
        clearButton.click();
        wait.until(ExpectedConditions.visibilityOf(from));
        from.clear();
        from.sendKeys(fromIn);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        elementFrom.click();
        logger.info("Выбран город вылета");
        return pageManager.getAviaPage();
    }

    @Step("Заполняем куда - '{toIn}'")
    public AviaPage toInput(String toIn) {
        to.clear();
        to.sendKeys(toIn);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        elementTo.click();
        logger.info("Выбран город прилета");
        return pageManager.getAviaPage();
    }

    @Step("Заполняем дату перелета '{monthName}' '{dayOfMonth}'")
    public AviaPage selectDate(String monthName, String dayOfMonth) {
        Map<String, String> monthsMap = new HashMap<>();
        monthsMap.put("Январь", "01");
        monthsMap.put("Февраль", "02");
        monthsMap.put("Март", "03");
        monthsMap.put("Апрель", "04");
        monthsMap.put("Май", "05");
        monthsMap.put("Июнь", "06");
        monthsMap.put("Июль", "07");
        monthsMap.put("Август", "08");
        monthsMap.put("Сентябрь", "09");
        monthsMap.put("Октябрь", "10");
        monthsMap.put("Ноябрь", "11");
        monthsMap.put("Декабрь", "12");

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String day = dayOfMonth.length() == 1 ? "0" + dayOfMonth : dayOfMonth;
        String month = monthsMap.get(monthName);

        if (month == null) {
            throw new IllegalArgumentException("Неверное имя месяца: " + monthName);
        }

        String dateXpath = String.format("//div[@data-qa='day-%d-%s-%s']", year, month, day);
        WebElement dayElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(dateXpath)));
        dayElement.click();
        logger.info("Выбрана дата заезда");
        return this;
    }
    @Step("Кликаем на кнопку найти")
    public SearchAviaPage findAvia(){
        waitUntilElementToBeClickable(findButton).click();
        logger.info("Переход на страницу поиска");
        return pageManager.getSearchAviaPage();
    }


}
