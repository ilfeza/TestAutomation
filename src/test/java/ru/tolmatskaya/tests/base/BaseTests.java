package ru.tolmatskaya.tests.base;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BaseTests {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Before
    public void before(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://lambdatest.github.io/sample-todo-app/");


    }

    @After
    public void after(){
        driver.quit();
    }
}
