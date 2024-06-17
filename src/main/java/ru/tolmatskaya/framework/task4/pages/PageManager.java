package ru.tolmatskaya.framework.task4.pages;

public class PageManager {
    private static PageManager INSTANCE = null;
    private StartPage startPage;
    private HotelPage hotelPage;
    private AviaPage aviaPage;

    private PageManager(){

    }
    public static PageManager getInstance(){
        if (INSTANCE == null){
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public StartPage getStartPage() {
        if (startPage == null){
            startPage = new StartPage();
        }
        return startPage;
    }

    public HotelPage getHotelPage() {
        if (hotelPage == null){
            hotelPage = new HotelPage();
        }
        return hotelPage;
    }

    public AviaPage getAviaPage(){
        if (aviaPage == null){
            aviaPage = new AviaPage();
        }
        return aviaPage;
    }
}
