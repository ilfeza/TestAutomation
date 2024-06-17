package ru.tolmatskaya.framework.task4.pages;

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

    public AviaPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок",
                "20 миллионов путешественников каждый месяц ищут у нас авиабилеты, бронируют отели и туры",
                title.getText().trim());
        return pageManager.getAviaPage();
    }

    public AviaPage fromInput(String fromIn) {
        clearButton.click();
        wait.until(ExpectedConditions.visibilityOf(from));
        from.clear();
        from.sendKeys(fromIn);

        return pageManager.getAviaPage();
    }

    public AviaPage toInput(String toIn) {
        moscowElement.click();
        to.clear();
        to.sendKeys(toIn);
        return pageManager.getAviaPage();
    }

    public StartPage selectDate(String monthName, String dayOfMonth) {
        sElement.click();
        // Находим элемент label по атрибуту data-qa="labelPlaceholder" и кликаем по нему
        WebElement labelPlaceholder = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("label[data-qa='labelPlaceholder']")
        ));
        labelPlaceholder.click();

        // Определяем XPath для выбора месяца
        String monthXPath = "//div[@data-qa='controlPlaceholder' and text()='" + monthName + "']";

        // Кликаем на элемент выбранного месяца
        WebElement monthElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(monthXPath)
        ));
        monthElement.click();

        // Определяем номер месяца и день в месяце для формирования атрибута data-qa
        int monthNumber = convertMonthNameToNumber(monthName);
        int day = Integer.parseInt(dayOfMonth);
        String formattedDayOfMonth = String.format("%02d", day);
        String dataQaAttribute = String.format("day-2024-%02d-%s", monthNumber, formattedDayOfMonth);
        System.out.println("dataQaAttribute: " + dataQaAttribute);

        // Ищем и кликаем на элемент выбранной даты
        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-qa='" + dataQaAttribute + "']")
        ));
        dayElement.click();

        // Возвращаем страницу StartPage
        return pageManager.getStartPage();
    }

    private int convertMonthNameToNumber(String monthName) {
        switch (monthName.toLowerCase()) {
            case "январь": return 1;
            case "февраль": return 2;
            case "март": return 3;
            case "апрель": return 4;
            case "май": return 5;
            case "июнь": return 6;
            case "июль": return 7;
            case "август": return 8;
            case "сентябрь": return 9;
            case "октябрь": return 10;
            case "ноябрь": return 11;
            case "декабрь": return 12;
            default: throw new IllegalArgumentException("Неверное название месяца: " + monthName);
        }
    }


}
