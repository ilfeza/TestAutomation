package ru.tolmatskaya.tests.task3;

import org.junit.Test;
import ru.tolmatskaya.tests.task3.base.BaseTests;


public class YandexMarketTest extends BaseTests {

    @Test
    public void test() {
        pageManager.getStartPage()
                .checkMainPage()
                .clickOnCatalog()
                .clickOnCatalogAndNavigateToNotebooks()
                .checkLaptopsPage()
                //.logProducts()
                .enterPrice("200");
    }
}
