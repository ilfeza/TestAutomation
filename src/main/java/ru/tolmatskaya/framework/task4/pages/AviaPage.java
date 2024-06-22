package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AviaPage extends BasePage {
    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-controls, 'react-aria') and @aria-expanded='true']")
    private WebElement from;

    @FindBy(xpath = "//input[@role='combobox' and contains(@aria-controls, 'react-aria') and @aria-expanded='true']")
    private WebElement to;

    @FindBy(xpath = "//button[@aria-label='Очистить поле']")
    private WebElement clearButton;
    @FindBy(xpath = "//div[@class='GxV0a' and text()='Санкт-Петербург']")
    private WebElement sElement;
    @FindBy(xpath = "//div[@class='GxV0a' and text()='Москва']")
    private WebElement moscowElement;

    @FindBy(xpath = "//div[@data-qa='labelPlaceholder' and text()='Туда']")
    private WebElement toButton;

    @Step("Проверяем присутсвие заголовка на странице")
    public AviaPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок",
                "20 миллионов путешественников каждый месяц ищут у нас авиабилеты, бронируют отели и туры",
                title.getText().trim());
        return pageManager.getAviaPage();
    }

    @Step("Заполняем откуда - '{fromIn}'")
    public AviaPage fromInput(String fromIn) {
        clearButton.click();
        wait.until(ExpectedConditions.visibilityOf(from));
        from.clear();
        from.sendKeys(fromIn);

        return pageManager.getAviaPage();
    }

    @Step("Заполняем куда - '{toIn}'")
    public AviaPage toInput(String toIn) {
        moscowElement.click();
        to.clear();
        to.sendKeys(toIn);
        return pageManager.getAviaPage();
    }

    @Step("Заполняем дату перелета '{monthName}' '{dayOfMonth}'")
    public StartPage selectDate(String monthName, String dayOfMonth) {
        sElement.click();
        WebElement labelPlaceholder = toButton;

        labelPlaceholder.click();

        String monthXPath = "//div[@data-qa='controlPlaceholder' and text()='" + monthName + "']";

        WebElement monthElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(monthXPath)
        ));
        monthElement.click();

        int monthNumber = convertMonthNameToNumber(monthName);
        int day = Integer.parseInt(dayOfMonth);
        String formattedDayOfMonth = String.format("%02d", day);
        String dataQaAttribute = String.format("day-2024-%02d-%s", monthNumber, formattedDayOfMonth);
        System.out.println("dataQaAttribute: " + dataQaAttribute);

        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-qa='" + dataQaAttribute + "']")
        ));
        dayElement.click();

        return pageManager.getStartPage();
    }



}
