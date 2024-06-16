package ru.tolmatskaya.tests.task2;

import org.junit.Test;
import ru.tolmatskaya.tests.task2.base.BaseTests;

public class ScheduleTest extends BaseTests {

    @Test
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
