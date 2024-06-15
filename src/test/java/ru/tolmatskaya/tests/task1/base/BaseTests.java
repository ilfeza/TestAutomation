package ru.tolmatskaya.tests.task1.base;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import ru.tolmatskaya.framework.managers.DriverManager;
import ru.tolmatskaya.framework.managers.InitManager;
import ru.tolmatskaya.framework.task1.pages.PageManager;

public class BaseTests {

    private final DriverManager driverManager = DriverManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        InitManager.initFramework();
    }

    @Before
    public void before(){
        String baseUrl = "https://lambdatest.github.io/sample-todo-app/";
        driverManager.getDriver().get(baseUrl);
    }

    @AfterClass
    public static void after(){
        InitManager.quitFramework();
    }
}
