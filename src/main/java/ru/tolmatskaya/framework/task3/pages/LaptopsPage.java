package ru.tolmatskaya.framework.task3.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static org.junit.Assert.assertEquals;

public class LaptopsPage extends BasePage{
    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//div[@data-auto-themename='listDetailed']")
    private List<WebElement> productList;

    @FindBy(xpath = "//div[@data-filter-value-id='resale_resale']//label")
    private WebElement resaleFilter;

    @FindBy(xpath = "//h2[text()='Популярные предложения']")
    private WebElement titleResale;

    @FindBy(xpath = "//input[@id='range-filter-field-glprice_1ae4385e_min']")
    private WebElement priceInputField;

    public LaptopsPage checkLaptopsPage() {
        Assert.assertEquals("Страница ноутбуки не открылась", "Ноутбуки", title.getText());
        //printAltAttributes(nameLaptops);
        return pageManager.getLaptopsPage();
    }
    public LaptopsPage logProducts() {
        for (int i = 0; i < 5 && i < productList.size(); i++) {
            WebElement product = productList.get(i);
            moveToElement(product);

            String title = product.findElement(By.xpath(".//h3")).getText();
            String price = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]")).getText();
            List<WebElement> discountElements = product.findElements(By.xpath(".//div[@data-auto='resale-badge']/span"));
            boolean isDiscount = !discountElements.isEmpty();

            System.out.println(title);
            System.out.println(price);
        }
        return this;
    }


    public LaptopsPage enterPrice(String price) {
        //moveToElement(priceInputField);
       // Очистить поле ввода, если там уже есть значение
        priceInputField.sendKeys(price); // Ввести новое значение
        return this;
    }



}
