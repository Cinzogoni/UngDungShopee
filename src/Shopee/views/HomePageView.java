package Shopee.views;

import Shopee.services.businessservice.CustomerServiceImpl;
import Shopee.services.userservice.UserServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HomePageView {
    private static volatile HomePageView homepageView;
    public HomePageView() {}
    public static HomePageView gethomePageView() {
        if (homepageView == null){
            synchronized (CustomerServiceImpl.class){
                if (homepageView == null){
                    homepageView = new HomePageView();
                }
            }
        }
        return homepageView;
    }

    public void displayHomePage() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến trang trủ");
            System.out.println("0. Đăng nhập");
            System.out.println("1. Đăng ký");
            System.out.println("Mời chọn chức năng: ");

            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("Mời đăng nhập");
                        UserServiceImpl.getuserService().login();
                        break;
                    case 1:
                        System.out.println("Mời đăng ký");
                        UserServiceImpl.getuserService().register();
                        break;
                    default:
                        System.out.println("Mời chọn lại");
                    }
                }
            catch (InputMismatchException e) {
                System.out.println("Hãy chọn các số có trong danh sách");
                scanner.nextLine();
            }
        }
        while (true);
    }
}

