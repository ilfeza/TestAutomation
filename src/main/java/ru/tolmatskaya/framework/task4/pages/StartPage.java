package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StartPage extends BasePage {
    private static final Logger logger = Logger.getLogger(StartPage.class);
    @FindBy(xpath = "//h1")
    private WebElement title;
    @FindBy(xpath = "//input[@role='combobox']")
    private WebElement inputField;
    @FindBy(xpath = "//*[@aria-label='Дата заезда']")
    private WebElement dataLabel;
    @FindBy(xpath = "//*[@id='suggest-0']")
    private WebElement elementInHotels;
    @FindBy(xpath = "//button[@type='submit']/div[text()='Найти']")
    private WebElement findButton;
    @FindBy(xpath = "//span[text()='Авиа']")
    private WebElement aviaButton;

    @Step("Проверяем, присутсвует ли заголовок на странице")
    public StartPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок", "20 миллионов путешественников каждый месяц бронируют у нас отели, билеты и туры", title.getText().trim());
        logger.info("Нужный заголовок присутствует на странице");
        return pageManager.getStartPage();
    }

    @Step("Вводим в окно 'Куда вы хотите поехать?' '{text}'")
    public StartPage enterTextIntoInputField(String text) {
        inputField.clear();
        inputField.sendKeys(text);
        //кликаем по 1 элементу в списке отелей
        elementInHotels.click();
        logger.info("Выбран город поездки");
        return pageManager.getStartPage();
    }

    @Step("Вводим дату заезда")
    public StartPage selectDate(String monthName, String dayOfMonth) {

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


    @Step("Вводим дату выезда")
    public StartPage selectDepartureDate(String monthName, String dayOfMonth) {
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
        logger.info("Выбрана дата выезда");
        return this;
    }

    @Step("Кликаем на кнопку найти")
    public HotelPage findHotel(){
        waitUntilElementToBeClickable(findButton).click();
        logger.info("Переход на страницу поиска");
        return pageManager.getHotelPage();
    }

    @Step("Кликаем на страницу авиабилетов")
    public AviaPage clickOnAvia(){
        aviaButton.click();
        logger.info("Переход на страницу авиабилетов");
        return pageManager.getAviaPage();
    }



}
