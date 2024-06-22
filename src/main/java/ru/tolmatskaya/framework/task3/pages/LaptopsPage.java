package ru.tolmatskaya.framework.task3.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.tolmatskaya.framework.task1.pages.StartPage;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LaptopsPage extends BasePage{
    private static final Logger logger = Logger.getLogger(LaptopsPage.class);
    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//div[@data-auto-themename='listDetailed']")
    private List<WebElement> productList;


    @FindBy(xpath = "//h2[text()='Популярные предложения']")
    private WebElement titleResale;

    @FindBy(xpath = "//label[contains(text(), 'Цена, ₽ от')]/following-sibling::div//input[@type='text']")
    private WebElement priceInputField;

    @FindBy(xpath = "//label[contains(text(), 'Цена, ₽ до')]/following-sibling::div//input[@type='text']")
    private WebElement maxPriceInputField;

    @Step("Проверяем, открылась ли страница с ноутбуками")
    public LaptopsPage checkLaptopsPage() {
        Assert.assertEquals("Страница ноутбуки не открылась", "Ноутбуки", title.getText());
        //printAltAttributes(nameLaptops);
        return pageManager.getLaptopsPage();
    }

    @Step("Выводим в лог первые 5 ноутбуков и их цены")
    public LaptopsPage logProducts() {
        for (int i = 0; i < 5 && i < productList.size(); i++) {
            WebElement product = productList.get(i);
            moveToElement(product);

            String title = product.findElement(By.xpath(".//h3")).getText();
            String price = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
            List<WebElement> discountElements = product.findElements(By.xpath(".//div[@data-auto='resale-badge']/span"));
            boolean isDiscount = !discountElements.isEmpty();

            //System.out.println(title);
            //System.out.println(price);
            logger.info("Название товара: "+title+" Цена: "+price);
        }
        return this;
    }
    /*

    @Step("Вводим минимальную стоимость ноутбука")
    public LaptopsPage enterPrice(String price) {
        //moveToElement(priceInputField);
        priceInputField.sendKeys(price);
        return this;
    }

     */
/*
    @Step("Вводим максимальную стоимость ноутбука")
    public LaptopsPage enterMaxPrice(String price) {
        //moveToElement(priceInputField);
        maxPriceInputField.sendKeys(price);
        return this;
    }

 */

    @Step("Проверяем, что цены первых 5 ноутбуков в диапазоне minPrice и maxPrice")
    public LaptopsPage inputPrice(double minPrice, double maxPrice) {
        priceInputField.sendKeys(Double.toString(minPrice));
        maxPriceInputField.sendKeys(Double.toString(maxPrice));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 5 && i < productList.size(); i++) {

            WebElement product = productList.get(i);

            waitUntilElementToBeVisible(product);
            moveToElement(product);

            String priceText = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
            double price = Double.parseDouble(priceText.replaceAll("[^\\d,]", "").replace(",", "."));

            double tolerance = 0.01;

            assertTrue("Цена " + price + " находится вне диапазона " + minPrice + " - " + maxPrice,
                    price >= minPrice && price <= maxPrice);
        }
        logger.info("Цены первых 5 ноутбуков находятся в заданном диапазоне");
        return this;
    }

}
