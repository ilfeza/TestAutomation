package ru.tolmatskaya.framework.task1.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class StartPage extends BasePage {

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


    public StartPage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок", "LambdaTest Sample App", titleTodoList.getText());
        return pageManager.getStartPage();
    }

    public StartPage remainingOnThePage() {
        Assert.assertEquals("Отсутствует текст: 5 of 5 remaining", "5 of 5 remaining", numberOfRemaining.getText());
        return pageManager.getStartPage();
    }

    // Элемент списка не зачеркнут
    public StartPage verifyItemNotDone(String itemName) {
        for (WebElement item : listOfItems) {
            WebElement label = item.findElement(By.tagName("span"));
            if (label.getText().equals(itemName)) {
                Assert.assertTrue("Элемент списка не должен быть зачеркнут", label.getAttribute("class").contains("done-false"));
                return pageManager.getStartPage();
            }
        }
        Assert.fail("Элемент списка с текстом " + itemName + " не найден на странице");
        return pageManager.getStartPage();
    }

    // Метод для установки галочки у указанного элемента по его тексту и проверки изменения класса
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
        Assert.fail("Чекбокс с текстом " + itemName + " не найден на странице");
        return pageManager.getStartPage();
    }
    // Метод для добавления нового элемента списка
    public StartPage addNewItem(String itemName) {
        inputAddTodo.sendKeys(itemName);
        addButton.click();
        return pageManager.getStartPage();
    }
}
