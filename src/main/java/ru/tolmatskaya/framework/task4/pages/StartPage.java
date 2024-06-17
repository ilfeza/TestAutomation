package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StartPage extends BasePage {
    @FindBy(xpath = "//h1")
    private WebElement title;
    @FindBy(xpath = "//input[@role='combobox']")
    private WebElement inputField;
    @FindBy(xpath = "//*[@aria-label='Дата заезда']")
    private WebElement dataLabel;
    @FindBy(xpath = "//div[@class='GxV0a' and text()='Москва']")
    private WebElement moscowElement;
    @FindBy(xpath = "//button[@type='submit' and @tabindex='0']//div[text()='Найти']")
    private WebElement findButton;
    @FindBy(xpath = "//span[text()='Авиа']")
    private WebElement aviaButton;

    @Step("Проверяем, присутсвует ли заголовок на странице")
    public StartPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок", "20 миллионов путешественников каждый месяц бронируют у нас отели, билеты и туры", title.getText().trim());
        return pageManager.getStartPage();
    }

    @Step("Вводим в окно 'Куда вы хотите поезать?' '{text}'")
    public StartPage enterTextIntoInputField(String text) {
        inputField.clear();
        inputField.sendKeys(text);
        return pageManager.getStartPage();
    }

    @Step("Вводим дату заезда")
    public StartPage selectDate(String monthName, String dayOfMonth) {
        moscowElement.click();

        WebElement monthsList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.monthsList")));

        WebElement monthElement = monthsList.findElement(By.xpath("//div[contains(@class, '_3Tq3s') and text()='" + monthName + "']"));
        monthElement.click();

        WebElement monthDays = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.jIWhq")));
        int monthNumber = convertMonthNameToNumber(monthName);
        int day = Integer.parseInt(dayOfMonth);
        String formattedDayOfMonth = String.format("%02d", day);
        String dataQaAttribute = String.format("day-2024-%02d-%s", monthNumber, formattedDayOfMonth);
        System.out.println("dataQaAttribute: " + dataQaAttribute);

        System.out.println("//div[@data-qa='" + dataQaAttribute + "']");
        WebElement dayElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@data-qa='" + dataQaAttribute + "']")
        ));
        dayElement.click();

        return pageManager.getStartPage();
    }

    @Step("Вводим дату выезда")
    public StartPage selectDepartureDate(String monthName, String dayOfMonth) {
        WebElement monthsList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.monthsList")));

        WebElement monthElement = monthsList.findElement(By.xpath("//div[contains(@class, '_3Tq3s') and text()='" + monthName + "']"));
        monthElement.click();

        WebElement monthDays = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.jIWhq")));
        int monthNumber = convertMonthNameToNumber(monthName);
        int day = Integer.parseInt(dayOfMonth);
        String formattedDayOfMonth = String.format("%02d", day);
        String dataQaAttribute = String.format("day-2024-%02d-%s", monthNumber, formattedDayOfMonth);
        System.out.println("dataQaAttribute: " + dataQaAttribute);

        WebElement dayElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@data-qa='" + dataQaAttribute + "']")
        ));
        dayElement.click();

        return pageManager.getStartPage();
    }

    @Step("Кликаем на кнопку найти")
    public HotelPage findHotel(){
        waitUntilElementToBeClickable(findButton).click();
        return pageManager.getHotelPage();
    }

    @Step("Кликаем на страницу авиабилетов")
    public AviaPage clickOnAvia(){
        aviaButton.click();
        return pageManager.getAviaPage();
    }



}
