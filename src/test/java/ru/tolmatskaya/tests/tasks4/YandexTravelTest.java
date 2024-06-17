package ru.tolmatskaya.tests.tasks4;

import org.junit.Test;
import ru.tolmatskaya.tests.tasks4.base.BaseTests;


public class YandexTravelTest extends BaseTests {
    @Test
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
    public void test2() {
        pageManager.getStartPage()
                .titleOnThePage()
                .enterTextIntoInputField("Москва")
                .selectDate("Июль", "1")
                .selectDepartureDate("Июль", "7")
                .findHotel()
                .titleOnThePage()
                .addFilters()
                .selectFoodOption("Завтрак")
                .selectAccommodationType("Гостиница")
                .setPriceRange(0, 30000);
    }

    @Test
    public void test3() {
        pageManager.getStartPage()
                .titleOnThePage()
                .clickOnAvia()
                .titleOnThePage()
                .fromInput("Москва")
                .toInput("Санкт-Петербург")
                .selectDate("Июль", "1");
    }




}
