package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HotelPage extends BasePage{
    private static final Logger logger = Logger.getLogger(HotelPage.class);
    @FindBy(xpath = "//div[text()='Популярные']")
    private WebElement title;
    @FindBy(xpath = "//button[contains(., 'Все фильтры')]")
    private WebElement allFilters;
    @FindBy(xpath = "//h2[text()='Питание']/following-sibling::div/label")
    private List<WebElement> foodOptions;
    @FindBy(xpath = "//button[contains(span/span, 'Искать цены')]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@data-qa='price']")
    private List<WebElement> allHotels;

    @FindBy(xpath = "//input[contains(@value, '0 ₽')]")
    private WebElement minPriceInput;

    @FindBy(xpath = "//span[text()='до']/following-sibling::input")
    private WebElement maxPriceInput;

    @FindBy(xpath = "//button[contains(., 'Популярные')]")
    private WebElement filters;

    @FindBy(xpath = "//button[@type='button' and @data-index='1' and @data-qa='cheap-first-option' and text()='Сначала дешевле']")
    private WebElement buttonLowToHigh;


    @Step("Проверяем наличие заголовка на странице")
    public HotelPage titleOnThePage() {
        assertEquals("Страница поиска отеля не открылась", "Популярные", title.getText().trim());
        logger.info("Нужный заголовок находится на странице");
        return pageManager.getHotelPage();
    }

    @Step("Кликаем на кнопку фильтров")
    public HotelPage addFilters(){
        waitUntilElementToBeClickable(allFilters);
        allFilters.click();
        logger.info("Выбраны фильтрация");
        return pageManager.getHotelPage();
    }

    @Step("Выбираем тип питания - '{foodType}'")
    public HotelPage selectFoodOption(String foodType) {
        String xpath = "//h2[text()='Питание']/following-sibling::div//label[.//div[text()='" + foodType + "']]";
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        boolean elementExists = element.isDisplayed();
        Assert.assertTrue("Элемент с текстом '" + foodType + "' не найден на странице или не отображается", elementExists);
        String food = "//div[contains(text(), '" + foodType + "')]";
        WebElement elementFood = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(food)));
        elementFood.click();
        logger.info("Выбран тип питания");
        return this;
    }

    @Step("Выбираем тип размещения - '{accommodationType}'")
    public HotelPage selectAccommodationType(String accommodationType) {
        String xpath = "//h2[text()='Тип размещения']/following-sibling::div//label[.//div[text()='" + accommodationType + "']]";
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

        boolean elementExists = element.isDisplayed();
        Assert.assertTrue("Элемент с текстом '" + accommodationType + "' не найден на странице или не отображается", elementExists);
        String accomadation = "//div[contains(text(), '" + accommodationType + "')]";
        WebElement elementFood = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accomadation)));
        elementFood.click();
        logger.info("Выбран тип размещения");

        return this;
    }


    @Step("Устанавливаем диапазон цен от '{minPrice}' до '{maxPrice}'")
    public HotelPage setPriceRange(int minPrice, int maxPrice) {
        minPriceInput.clear();
        minPriceInput.sendKeys(String.valueOf(minPrice));
        String currentValue = maxPriceInput.getAttribute("value");
        maxPriceInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        maxPriceInput.sendKeys(Keys.DELETE);
        maxPriceInput.sendKeys(String.valueOf(maxPrice));
        maxPriceInput.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        waitUntilElementToBeClickable(searchButton);

        searchButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (WebElement priceElement : allHotels) {
            String priceText = priceElement.getText().trim();
            if (priceText.isEmpty()) {
                continue;
            }
            String cleanPriceText = priceText.replaceAll("[^0-9]", "");
            if (!cleanPriceText.isEmpty()) {
                int priceValue = Integer.parseInt(cleanPriceText);
                //System.out.println(priceValue);
                Assert.assertTrue("Цена не должна быть больше " + maxPrice, priceValue <= maxPrice);
            }
        }

        logger.info("Цены находятся в звдвнном диапазоне");

        return this;
    }

    @Step("Проверка фильтрации")
    public HotelPage filterLowToHigh(){

        waitUntilElementToBeClickable(filters).click();
        buttonLowToHigh.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        int previousPrice = -1;
        int count = 0;
        for (WebElement priceElement : allHotels) {
            if (count >= 5) {
                break;
            }
            String priceText = priceElement.getText().trim();
            if (priceText.isEmpty()) {
                continue;
            }
            String cleanPriceText = priceText.replaceAll("[^0-9]", "");
            if (!cleanPriceText.isEmpty()) {
                int currentPrice = Integer.parseInt(cleanPriceText);
                //System.out.println(currentPrice);
                if (previousPrice != -1) {
                    Assert.assertTrue("Цены должны идти по возрастанию: " + previousPrice + " <= " + currentPrice, previousPrice <= currentPrice);
                }
                previousPrice = currentPrice;
            }
            count++;
        }

        logger.info("Цены идут по возрастанию");
        return this;
    }


}
