package ru.tolmatskaya.framework.task2.pages;

public class PageManager {
    private static PageManager INSTANCE = null;

    private StartPage startPage;
    private RaspisaniyaPage raspisaniyaPage;
    private  GroupSchedulePage groupSchedulePage;

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

    public RaspisaniyaPage getRaspisaniyaPage() {
        if(raspisaniyaPage == null){
            raspisaniyaPage = new RaspisaniyaPage();
        }
        return raspisaniyaPage;
    }

    public GroupSchedulePage getGroupSchedulePage() {
        if (groupSchedulePage == null){
            groupSchedulePage = new GroupSchedulePage();
        }
        return groupSchedulePage;
    }
}