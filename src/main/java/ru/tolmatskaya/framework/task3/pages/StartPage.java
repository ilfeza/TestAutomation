package ru.tolmatskaya.framework.task3.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertEquals;

public class StartPage extends BasePage {
    @FindBy(xpath = "//meta[@property='og:url']")
    private WebElement urlPage;

    @FindBy(xpath = "//span[text()='Каталог']")
    private WebElement catalog;

    @FindBy(xpath = "(//a[@href='https://market.yandex.ru/special/electronics_dep'])[2]")
    private WebElement electronicsMenu;

    @FindBy(xpath = "//a[text()='Ноутбуки, планшеты и электронные книги']")
    private WebElement notebooksTabletsEBooksLink;

    @FindBy(xpath = "//a[text()='Ноутбуки']")
    private WebElement notebooksLink;

    @Step("Проверяем присутствие заголовка на странице")
    public StartPage checkMainPage() {
        assertEquals("Главная страница не открылась", "https://market.yandex.ru/", urlPage.getAttribute("content"));
        return pageManager.getStartPage();
    }

    @Step("Кликаем на каталог")
    public StartPage clickOnCatalog() {
        WebElement catalogButton = wait.until(ExpectedConditions.elementToBeClickable(catalog));
        catalogButton.click();
        return pageManager.getStartPage();
    }

    @Step("Кликаем на страницу с ноуутбуками")
    public LaptopsPage clickOnCatalogAndNavigateToNotebooks() {
        WebElement electronics = wait.until(ExpectedConditions.visibilityOf(electronicsMenu));
        assertEquals("Меню не открылось", "Электроника", electronics.getText());
        waitUntilElementToBeVisible(electronics);
        moveToElement(electronics);

        WebElement subCategory = wait.until(ExpectedConditions.visibilityOf(notebooksTabletsEBooksLink));
        assertEquals("Отсутствует текст Ноутбуки, планшеты и электронные книги","Ноутбуки, планшеты и электронные книги", subCategory.getText());
        moveToElement(subCategory);

        WebElement notebooks = wait.until(ExpectedConditions.visibilityOf(notebooksLink));
        assertEquals("Отсутсвет ссылка на ноутбуки", "Ноутбуки", notebooks.getText());
        notebooks.click();

        return pageManager.getLaptopsPage();
    }
}
