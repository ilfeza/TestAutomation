package ru.tolmatskaya.tests.task3;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.tolmatskaya.tests.task3.base.BaseTests;


public class YandexMarketTest extends BaseTests {

    @Test
    @DisplayName("Проверка фильтрации товаров по цене")
    public void test() {
        pageManager.getStartPage()
                .checkMainPage()
                .clickOnCatalog()
                .clickOnCatalogAndNavigateToNotebooks()
                .checkLaptopsPage()
                .logProducts()
                .inputPrice(60000, 110000);
    }
}
