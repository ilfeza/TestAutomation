package ru.tolmatskaya.tests.task3.base;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.tolmatskaya.framework.managers.DriverManager;
import ru.tolmatskaya.framework.managers.InitManager;
import ru.tolmatskaya.framework.task3.pages.PageManager;


public class BaseTests {
    private final DriverManager driverManager = DriverManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        InitManager.initFramework();
    }

    @Before
    public void before(){
        String baseUrl = "https://market.yandex.ru";
        driverManager.getDriver().get(baseUrl);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
