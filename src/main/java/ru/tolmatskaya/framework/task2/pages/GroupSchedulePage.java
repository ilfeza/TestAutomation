package ru.tolmatskaya.framework.task2.pages;

import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.tolmatskaya.framework.task1.pages.StartPage;

import java.util.List;

public class GroupSchedulePage extends BasePage {
    private static final Logger logger = Logger.getLogger(GroupSchedulePage.class);
    @FindBy(xpath = "//h4[contains(text(), 'Расписание занятий')]")
    private WebElement title;

    @FindBy(xpath = "//input[@class='groups']")
    private WebElement groupsInput;

    @FindBy(xpath = "//div[contains(@class, 'found-groups')]/*")
    private List<WebElement> foundGroups;

    @FindBy(xpath = "//div[@class='schedule' and not(contains(@style, 'display: none;'))]")
    private WebElement schedule;

    @FindBy(xpath = "//div[contains(@class, 'schedule-day_today')]/div[contains(@class, 'title')]")
    private WebElement dayToday;

    @Step("Проверяем заголовок Расписание занятий")
    public GroupSchedulePage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок ", "Расписание занятий", title.getText());
        logger.info("Нужный заголовк присутсвует на странице");
        return pageManager.getGroupSchedulePage();
    }

    @Step("Вводим номер группы в поле поиска")
    public GroupSchedulePage enterGroup(String group) {
        groupsInput.sendKeys(group);
        logger.info("Введена группа");
        return pageManager.getGroupSchedulePage();
    }

    @Step("Проверяем, что в результате поиск отображается только одна искомая группа")
    public GroupSchedulePage foundGroupsCount() {
        //System.out.println(foundGroups.size());
        Assert.assertEquals("Количество найденных групп не соответствует ожидаемому", 1, foundGroups.size());
        logger.info("В результате поиска отображается 1 искомая группа");
        return pageManager.getGroupSchedulePage();
    }

    @Step("Нажимаем на группу")
    public GroupSchedulePage clickGroup(String groupId) {
        WebElement groupElement = null;
        for (WebElement element : foundGroups) {
            if (element.getAttribute("id").equals(groupId)) {
                groupElement = element;
                break;
            }
        }
        groupElement.click();
        Assert.assertNotNull(schedule);
        logger.info("Выбор группы");
        return pageManager.getGroupSchedulePage();
    }

    @Step("Проверяем, что текущий день недели выделен цветом")
    public GroupSchedulePage todayHighlight(){
        if (getCurrentDayOfWeek() != "Воскресенье") {
            Assert.assertEquals("Текущий день недели не выделен цветом", getCurrentDayOfWeek(), dayToday.getText());
        }
        logger.info("Текущий день недели выделен цветом");
        return pageManager.getGroupSchedulePage();
    }
}
