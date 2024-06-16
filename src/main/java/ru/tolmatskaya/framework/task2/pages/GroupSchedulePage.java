package ru.tolmatskaya.framework.task2.pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class GroupSchedulePage extends BasePage {
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

    public GroupSchedulePage titleOnThePage() {
        Assert.assertEquals("На странице отсутствует заголовок ", "Расписание занятий", title.getText());
        return pageManager.getGroupSchedulePage();
    }

    public GroupSchedulePage enterGroup(String group) {
        groupsInput.sendKeys(group);
        return pageManager.getGroupSchedulePage();
    }

    public GroupSchedulePage foundGroupsCount() {
        //System.out.println(foundGroups.size());
        Assert.assertEquals("Количество найденных групп не соответствует ожидаемому", 1, foundGroups.size());
        return pageManager.getGroupSchedulePage();
    }

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
        return pageManager.getGroupSchedulePage();
    }

    public GroupSchedulePage todayHighlight(){
        if (getCurrentDayOfWeek() != "Воскресенье") {
            Assert.assertEquals("Текущий день недели не выделен цветом", getCurrentDayOfWeek(), dayToday.getText());
        }
        return pageManager.getGroupSchedulePage();
    }
}