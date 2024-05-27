package Shopee.services.managerservice;

import Shopee.Util.ReadCustomerFile;
import Shopee.Util.ReadOwnerFile;
import Shopee.models.Customer;
import Shopee.models.Owner;
import Shopee.services.businessservice.CustomerServiceImpl;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AdminManagerImpl implements AdminService {
    private static volatile AdminManagerImpl adminService;
    public AdminManagerImpl() {}
    public static AdminManagerImpl getAdminService() {
        if (adminService == null){
            synchronized (CustomerServiceImpl.class){
                if (adminService == null){
                    adminService = new AdminManagerImpl();
                }
            }
        }
        return adminService;
    }

    public Object displayMenuAndGetAdminChoice() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến trình quản của admin:");
            System.out.println("0. Danh sách Customer User");
            System.out.println("1. Danh sách Owner User");
            System.out.println("2. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("Danh sách Customer User");
                        displayCustomerUserList();
                        break;
                    case 1:
                        System.out.println("Danh sách Owner User");
                        displayOwnerUserList();
                        break;
                    case 2:
                        HomePageView homePageView = HomePageView.gethomePageView();
                        homePageView.displayHomePage();
                    default:
                        System.out.println("Mời chọn lại");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Hãy chọn các số có trong danh sách");
                scanner.nextLine();
            }
            catch (NoSuchElementException e) {
                System.out.println("Không có dữ liệu đầu vào.");
            }
        } while (true);
    }

    private static ArrayList<Customer> CusUserList;
    private static ArrayList<Owner> OwnUserList;
    static {
        CusUserList = ReadCustomerFile.readCustomersFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\CustomerUserList.txt");
        OwnUserList = ReadOwnerFile.readOwnersFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\OwnerUserList.txt");
    }

    @Override
    public void displayCustomerUserList() {
        if (CusUserList != null && !CusUserList.isEmpty()) {
            System.out.println("Danh sách Customer");
            CusUserList.forEach(System.out::println);
        }
        else
            System.out.println("Danh sách Customer trống");
    }

    @Override
    public void displayOwnerUserList() {
        if (OwnUserList != null && !OwnUserList.isEmpty()) {
            System.out.println("Danh sách Owner");
            OwnUserList.forEach(System.out::println);
        }
        else
            System.out.println("Danh sách Owner trống");

    }
}
