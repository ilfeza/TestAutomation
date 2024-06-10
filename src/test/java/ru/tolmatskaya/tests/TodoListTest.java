package ru.tolmatskaya.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.tolmatskaya.tests.base.BaseTests;

public class TodoListTest extends BaseTests {

    @Test
    public void test() {

        // шаг 1
        WebElement titleTodoList = driver.findElement(By.xpath("//h2"));
        Assert.assertEquals("На странице отсутствует заголовок", "LambdaTest Sample App", titleTodoList.getText());

        // шаг 2
        WebElement numberOfRemaining = driver.findElement(By.xpath("//span[@class='ng-binding']"));
        Assert.assertEquals("Отсутствует текст: 5 of 5 remaining", "5 of 5 remaining", numberOfRemaining.getText());

        // шаг 3
        WebElement firstTodoItem = driver.findElement(By.xpath("//ul[@class='list-unstyled']/li[1]"));
        String firstTodoClass = firstTodoItem.findElement(By.tagName("span")).getAttribute("class");
        Assert.assertEquals("Первый элемент списка не должен быть зачеркнут", "done-false", firstTodoClass);

        // шаг 4
        WebElement firstCheckbox = driver.findElement(By.name("li1"));
        firstCheckbox.click();
        String updatedFirstTodoClass = firstTodoItem.findElement(By.tagName("span")).getAttribute("class");
        Assert.assertEquals("Первый элемент списка должен быть зачеркнут", "done-true", updatedFirstTodoClass);
        WebElement updatedNumberOfRemaining = driver.findElement(By.xpath("//span[@class='ng-binding']"));
        Assert.assertEquals("4 of 5 remaining", updatedNumberOfRemaining.getText());

        // убрать чекбокс с 1 элемента
        firstCheckbox.click();

        //шаг 5
        for (int i = 1; i <= 5; i++) {
            WebElement todoItem = driver.findElement(By.xpath("//ul[@class='list-unstyled']/li[" + i + "]"));
            String expectedClassBefore = "done-false";
            String expectedClassAfter = "done-true";
            String todoClassBefore = todoItem.findElement(By.tagName("span")).getAttribute("class");
            Assert.assertEquals("Элемент списка не должен быть зачеркнут", expectedClassBefore, todoClassBefore);
            WebElement checkbox = todoItem.findElement(By.tagName("input"));
            checkbox.click();
            String todoClassAfter = todoItem.findElement(By.tagName("span")).getAttribute("class");
            Assert.assertEquals("Элемент списка должен быть зачеркнут", expectedClassAfter, todoClassAfter);
        }

        // шаг 6
        WebElement inputField = driver.findElement(By.id("sampletodotext"));
        WebElement addButton = driver.findElement(By.id("addbutton"));
        inputField.sendKeys("New Item");
        addButton.click();
        WebElement newTodoItem = driver.findElement(By.xpath("//ul[@class='list-unstyled']/li[6]"));
        String newTodoClass = newTodoItem.findElement(By.tagName("span")).getAttribute("class");
        Assert.assertEquals("Новый элемент списка не должен быть зачеркнут", "done-false", newTodoClass);
        WebElement updatedNumberOfRemainingAfterAdd = driver.findElement(By.xpath("//span[@class='ng-binding']"));
        Assert.assertEquals("1 of 6 remaining", updatedNumberOfRemainingAfterAdd.getText());

        // шаг 7
        WebElement newCheckbox = newTodoItem.findElement(By.tagName("input"));
        newCheckbox.click();
        String updatedNewTodoClass = newTodoItem.findElement(By.tagName("span")).getAttribute("class");
        Assert.assertEquals("Новый элемент списка должен быть зачеркнут", "done-true", updatedNewTodoClass);
        //дописать проверку общего числа элементов

    }


}
