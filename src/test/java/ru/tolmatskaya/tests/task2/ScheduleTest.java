package ru.tolmatskaya.tests.task2;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.tolmatskaya.tests.task2.base.BaseTests;

public class ScheduleTest extends BaseTests {

    @Test
    @DisplayName("Тестирование страницы расписания на сайте Мосполитеха")
    public void test() {
        pageManager.getStartPage()
                .titleOnThePage()
                .clickOnScheduleLink()
                .titleOnThePage()
                .clickLookOnTheShedule()
                .titleOnThePage()
                .enterGroup("234-221")
                .foundGroupsCount()
                .clickGroup("234-221");
    }
}
