package ru.tolmatskaya.framework.task4.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
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
    @FindBy(xpath = "//span[contains(text(), 'Искать цены в')]")
    private WebElement searchButton;

    @FindBy(xpath = "//span[@data-qa='price']")
    private List<WebElement> allHotels;

    @FindBy(xpath = "//input[contains(@value, '0 ₽')]")
    private WebElement minPriceInput;

    @FindBy(xpath = "//input[contains(@value, '60 000+ ₽')]")
    private WebElement maxPriceInput;


    public HotelPage titleOnThePage() {
        assertEquals("Страница поиска отеля не открылась", "Популярные", title.getText().trim());
        return pageManager.getHotelPage();
    }

    public HotelPage addFilters(){
        waitUntilElementToBeClickable(allFilters);
        allFilters.click();
        return pageManager.getHotelPage();
    }

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
    public HotelPage setPriceRange(int minPrice, int maxPrice) {
        minPriceInput.clear();
        minPriceInput.sendKeys(String.valueOf(minPrice));

        // Очищаем и вводим значения для максимальной цены
        maxPriceInput.clear();
        maxPriceInput.sendKeys(String.valueOf(maxPrice));

        // Нажимаем кнопку поиска
        searchButton.click();


        for (WebElement priceElement : allHotels) {
            String priceText = priceElement.getText();

            String cleanPriceText = priceText.replaceAll("[^0-9]", "");
            int priceValue = Integer.parseInt(cleanPriceText);

            assertEquals("Цена не меньше " + maxPrice, true, priceValue < maxPrice);
        }

        return this;
    }


}
