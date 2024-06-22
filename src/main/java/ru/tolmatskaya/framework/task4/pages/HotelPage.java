package ru.tolmatskaya.framework.task4.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class HotelPage extends BasePage{
    @FindBy(xpath = "//div[text()='Популярные']")
    private WebElement title;
    @FindBy(xpath = "//button[contains(., 'Все фильтры')]")
    private WebElement allFilters;
    @FindBy(xpath = "//label[contains(@class, 'BS5dT')]")
    private List<WebElement> foodOptions;

    @FindBy(xpath = "//label[contains(@class, 'BS5dT')]")
    private List<WebElement> accommodationOptions;

    @FindBy(xpath = "//div[@class='EhCXF U5HI4 _274Q5 FhpbT']//input[@data-qa='control']")
    private List<WebElement> priceInputs;
    @FindBy(xpath = "//button[contains(., 'Искать цены в')]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@data-qa='price']")
    private List<WebElement> allHotels;

    @FindBy(xpath = "//input[contains(@value, '0 ₽')]")
    private WebElement minPriceInput;

    @FindBy(xpath = "//span[text()='до']/following-sibling::input")
    private WebElement maxPriceInput;


    @Step("Проверяем наличие заголовка на странице")
    public HotelPage titleOnThePage() {
        assertEquals("Страница поиска отеля не открылась", "Популярные", title.getText().trim());
        return pageManager.getHotelPage();
    }

    @Step("Кликаем на кнопку фильтров")
    public HotelPage addFilters(){
        waitUntilElementToBeClickable(allFilters);
        allFilters.click();
        return pageManager.getHotelPage();
    }

    @Step("Выбираем тип питания - '{foodType}'")
    public HotelPage selectFoodOption(String foodType) {
        for (WebElement option : foodOptions) {
            WebElement label = option.findElement(By.cssSelector(".aj7Gb"));
            if (label.getText().contains(foodType)) {
                option.click();
                break;
            }
        }
        return this;
    }

    @Step("Выбираем тип размещения - '{accommodationType}'")
    public HotelPage selectAccommodationType(String accommodationType) {
        for (WebElement option : accommodationOptions) {
            WebElement label = option.findElement(By.cssSelector(".aj7Gb"));
            if (label.getText().contains(accommodationType)) {
                option.click();
                break;
            }
        }
        return this;
    }


    @Step("Устанавливаем диапазон цен от '{minPrice}' до '{maxPrice}'")
    public HotelPage setPriceRange(int minPrice, int maxPrice) {
        minPriceInput.clear();
        minPriceInput.sendKeys(String.valueOf(minPrice));

        // Очищаем и вводим значения для максимальной цены
        String currentValue = maxPriceInput.getAttribute("value");

// Отмечаем весь текст в поле ввода
        maxPriceInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));

// Удаляем выделенный текст
        maxPriceInput.sendKeys(Keys.DELETE);
        maxPriceInput.sendKeys(String.valueOf(maxPrice));
        maxPriceInput.sendKeys(Keys.ENTER);

// Вставляем новое значение
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Нажимаем кнопку поиска
        waitUntilElementToBeVisible(searchButton);
        searchButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Проверяем цены на отелях
        for (WebElement priceElement : allHotels) {
            String priceText = priceElement.getText().trim();

            // Пропускаем элементы, у которых текст пустой
            if (priceText.isEmpty()) {
                continue;
            }

            // Извлекаем числовое значение цены
            String cleanPriceText = priceText.replaceAll("[^0-9]", "");

            // Проверяем, что цена не превышает максимальное значение
            if (!cleanPriceText.isEmpty()) {
                int priceValue = Integer.parseInt(cleanPriceText);
                Assert.assertTrue("Цена не должна быть больше " + maxPrice, priceValue <= maxPrice);
            }
        }

        return this;
    }



}
