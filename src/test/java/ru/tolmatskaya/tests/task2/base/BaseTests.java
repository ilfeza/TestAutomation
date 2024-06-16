package ru.tolmatskaya.tests.task2.base;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.tolmatskaya.framework.managers.DriverManager;
import ru.tolmatskaya.framework.managers.InitManager;
import ru.tolmatskaya.framework.task2.pages.PageManager;


public class BaseTests {
    private final DriverManager driverManager = DriverManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        InitManager.initFramework();
    }

    @Before
    public void before(){
        String baseUrl = "https://mospolytech.ru/";
        driverManager.getDriver().get(baseUrl);
    }

    @AfterClass
    public static void after(){
        try {
            Thread.sleep(5000); // задержка на 5 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InitManager.quitFramework();
    }
}
