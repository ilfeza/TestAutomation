package ru.tolmatskaya.tests.task1;

import org.junit.Test;
import ru.tolmatskaya.framework.task1.pages.StartPage;
import ru.tolmatskaya.tests.task1.base.BaseTests;

public class TodoListTest extends BaseTests {

    @Test
    public void test() {
        pageManager.getStartPage()
                .titleOnThePage()
                .remainingOnThePage()
                .verifyItemNotDone("First Item")
                .checkCheckbox("First Item")
                .verifyItemNotDone("Second Item")
                .checkCheckbox("Second Item")
                .verifyItemNotDone("Third Item")
                .checkCheckbox("Third Item")
                .verifyItemNotDone("Fourth Item")
                .checkCheckbox("Fourth Item")
                .verifyItemNotDone("Fifth Item")
                .checkCheckbox("Fifth Item")
                .addNewItem("Sixth Item")
                .verifyItemNotDone("Sixth Item")
                .checkCheckbox("Sixth Item");
    }
}
