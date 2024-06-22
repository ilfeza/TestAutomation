package ru.tolmatskaya.tests.tasks5.base;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.tolmatskaya.framework.managers.DriverManager;
import ru.tolmatskaya.framework.managers.InitManager;

public class BaseTest {
    private static final DriverManager driverManager = DriverManager.getInstance();

    @BeforeClass
    public static void beforeClass(){
        InitManager.initFramework();
        driverManager.getDriver().get("https://reqres.in/");
    }
    @AfterClass
    public static void after() {
        InitManager.quitFramework();
    }
}
