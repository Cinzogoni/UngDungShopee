package Shopee;

import Shopee.views.HomePageView;

public class MainRun {
    public static void main(String[] args) {
        HomePageView homePageCall = HomePageView.gethomePageView();
        homePageCall.displayHomePage();
    }
}