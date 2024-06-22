package ru.tolmatskaya.framework.task1.pages;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import org.apache.log4j.Logger;

public class StartPage extends BasePage {
    private static final Logger logger = Logger.getLogger(StartPage.class);

    @FindBy(xpath = "//h2[contains(text(), 'LambdaTest Sample App')]")
    private WebElement titleTodoList;

    @FindBy(xpath = "//span[@class='ng-binding']")
    private WebElement numberOfRemaining;

    @FindBy(xpath = "//li[contains(@ng-repeat, 'sampletodo in sampleList.sampletodos')]")
    private List<WebElement> listOfItems;

    @FindBy(id = "sampletodotext")
    private WebElement inputAddTodo;

    @FindBy(id = "addbutton")
    private WebElement addButton;


    @Step("Проверка присутствия заголовка на странице")
    public StartPage titleOnThePage() {
        //logger.info("This is a debug message");
        Assert.assertEquals("На странице отсутствует заголовок", "LambdaTest Sample App", titleTodoList.getText());
        logger.info("Нужный заголовок присутствует на странице");
        return pageManager.getStartPage();
    }

    @Step("Проверка присутсвия текста 5 of 5 remaining")
    public StartPage remainingOnThePage() {
        Assert.assertEquals("Отсутствует текст: 5 of 5 remaining", "5 of 5 remaining", numberOfRemaining.getText());
        logger.info("На странице присутсвует текст 5 of 5 remaining");
        return pageManager.getStartPage();
    }

    @Step("Проверяем, что элемент списка '{itemName}' не зачеркнут")
    public StartPage verifyItemNotDone(String itemName) {
        for (WebElement item : listOfItems) {
            WebElement label = item.findElement(By.tagName("span"));
            if (label.getText().equals(itemName)) {
                Assert.assertTrue("Элемент списка не должен быть зачеркнут", label.getAttribute("class").contains("done-false"));
                return pageManager.getStartPage();
            }
        }
        logger.info("Элемент списка зачеркнут");
        Assert.fail("Элемент списка с текстом " + itemName + " не найден на странице");
        return pageManager.getStartPage();
    }


    // Метод для установки галочки у указанного элемента по его тексту и проверки изменения класса
    @Step("Ставим галочку на '{itemName}' и проверяем, что класс изменился на done-true")
    public StartPage checkCheckbox(String itemName) {
        for (WebElement item : listOfItems) {
            WebElement label = item.findElement(By.tagName("span"));
            if (label.getText().equals(itemName)) {
                WebElement checkbox = item.findElement(By.tagName("input"));
                checkbox.click();

                // Проверяем, что класс изменился на done-true после клика
                Assert.assertTrue("Элемент списка должен быть зачеркнут", label.getAttribute("class").contains("done-true"));
                return pageManager.getStartPage();
            }
        }
        logger.info("Чекбкс не найден на странице");
        Assert.fail("Чекбокс с текстом " + itemName + " не найден на странице");
        return pageManager.getStartPage();
    }
    // Метод для добавления нового элемента списка
    @Step("Добавляем новый элемент на страницу")
    public StartPage addNewItem(String itemName) {
        inputAddTodo.sendKeys(itemName);
        addButton.click();
        logger.info("Новый элемент добавлен на страницу");
        return pageManager.getStartPage();
    }
}
