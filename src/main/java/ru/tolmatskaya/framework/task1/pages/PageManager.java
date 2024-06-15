package ru.tolmatskaya.framework.task1.pages;

public class PageManager {
    private static PageManager INSTANCE = null;

    private StartPage startPage;

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

}