package ru.tolmatskaya.tests.tasks4;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.tolmatskaya.tests.tasks4.base.BaseTests;


public class YandexTravelTest extends BaseTests {
    @Test
    @DisplayName("Проверка функциональности поиска отеля")
    public void test1() {
        pageManager.getStartPage()
                .titleOnThePage()
                .enterTextIntoInputField("Москва")
                .selectDate("Июль", "1")
                .selectDepartureDate("Июль", "7")
                .findHotel()
                .titleOnThePage();
    }

    @Test
    @DisplayName("Проверка фильтрации отелей")
    public void test2() {
        pageManager.getStartPage()
                .titleOnThePage()
                .enterTextIntoInputField("Москва")
                .selectDate("Июль", "7")
                .selectDepartureDate("Июль", "10")
                .findHotel()
                .titleOnThePage()
                .addFilters()
                .selectFoodOption("Завтрак")
                .selectAccommodationType("Гостиница")
                .setPriceRange(0, 30000)
                .filterLowToHigh();
    }

    @Test
    @DisplayName("Тестирование поиска и выбора авиабилета”")
    public void test3() {
        pageManager.getStartPage()
                .titleOnThePage()
                .clickOnAvia()
                .titleOnThePage()
                .fromInput("Москва")
                .toInput("Санкт-Петербург")
                .selectDate("Июль", "1")
                .findAvia()
                .findAvia()
                .buyAvia();
    }




}
