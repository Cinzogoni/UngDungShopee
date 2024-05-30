package Shopee.services.userservice;

import Shopee.util.readfile.ReadAdminFile;
import Shopee.util.readfile.ReadCustomerFile;
import Shopee.util.readfile.ReadOwnerFile;
import Shopee.models.Admin;
import Shopee.models.Customer;
import Shopee.models.Owner;
import Shopee.services.businessservice.CustomerServiceImpl;
import Shopee.services.businessservice.OwnerServiceImpl;
import Shopee.services.managerservice.AdminManagerImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService{
    private static volatile UserServiceImpl userService;
    public UserServiceImpl() {}
    public static UserServiceImpl getuserService() {
        if (userService == null){
            synchronized (UserServiceImpl.class){
                if (userService == null){
                    userService = new UserServiceImpl();
                }
            }
        }
        return userService;
    }
    private static ArrayList<Customer> CusUserList;
    private static ArrayList<Owner> OwnUserList;
    private static ArrayList<Admin> AdminUserList;
    static {
        CusUserList = ReadCustomerFile.readCustomersFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\CustomerUserList.txt");
        OwnUserList = ReadOwnerFile.readOwnersFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\OwnerUserList.txt");
        AdminUserList = ReadAdminFile.readAdminsFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\AdminMangerList");
    }
    private boolean passwordIsValid(String password) {
        String regex = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[^\\w\\d\\s:])([^\\s]){8,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean phoneNumberIsValid(String phoneNumber) {
        String regex = "\"^(\\\\d{3}[- .]?){2}\\\\d{4}$\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    @Override
    public Object login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập tên người dùng: ");
        String username = scanner.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String password = scanner.nextLine();

        if (!passwordIsValid(password)) {
            System.out.println("Mật khẩu không hợp lệ. Mật khẩu phải có từ 8 đến 16 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
            return null;
        }

        for (Customer customer : CusUserList) {
            if (customer.getCustomerName().equals(username) && customer.getCustomerPassword().equals(password)) {
                System.out.println("Đăng nhập thành công với vai trò Customer.");
                return CustomerServiceImpl.getCustomerService().displayMenuAndGetCustomerChoice();
            }
        }

        for (Owner owner : OwnUserList) {
            if (owner.getShopownerID().equals(username) && owner.getShopownerPassword().equals(password)) {
                System.out.println("Đăng nhập thành công với vai trò Owner.");
                return OwnerServiceImpl.getOwnerService(owner.getShopownerName()).displayMenuAndOwnerChoice();
            }
        }

        for (Admin admin : AdminUserList) {
            if (admin.getAdminID().equals(username) && admin.getAdminPassword().equals(password)) {
                System.out.println("Đăng nhập thành công với vai trò Admin.");
                return AdminManagerImpl.getAdminService().displayMenuAndGetAdminChoice();
            }
        }

        System.out.println("Tên người dùng hoặc mật khẩu không đúng.");
        return null;
    }

    @Override
    public Object register() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Chọn hình thức đăng ký (Khách hàng/ Đại lý): ");
        String choice = scanner.nextLine();
        if (choice.equals("Khách hàng")) {

            System.out.print("Nhập tên người dùng: ");
            String customerName = scanner.nextLine();

            System.out.print("Nhập số điện thoại, ví dụ: 038 647 9893");
            String customerPhoneNumber = scanner.nextLine();
            if (!phoneNumberIsValid(customerPhoneNumber)){
                System.out.println("Hãy nhập lại số điẹn thoại");
                return null;
            }

            System.out.print("Nhập địa chỉ: ");
            String customerAddress = scanner.nextLine();

            System.out.print("Nhập mật khẩu: ");
            String customerPassword = scanner.nextLine();
            if (!passwordIsValid(customerPassword)) {
                System.out.println("Mật khẩu không hợp lệ. Mật khẩu phải có từ 8 đến 16 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
                return null;
            }

            Customer newCustomer = new Customer(customerName, customerPhoneNumber, customerAddress, customerPassword);
            CusUserList.add(newCustomer);
            saveToFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\CustomerUserList.txt", newCustomer);
            System.out.println("Chúc mừng bạn đã trở thành khách hàng.");
        }
        else if (choice.equals("Đại lý")) {
            System.out.print("Nhập ID cho shop: ");
            String ownerID = scanner.nextLine();

            System.out.print("Nhập số tên cho shop: ");
            String ownerName = scanner.nextLine();

            System.out.print("Nhập sổ điện thoại cho shop, ví dụ: 038 647 9893");
            String ownerNumber = scanner.nextLine();
            if (!phoneNumberIsValid(ownerNumber)){
                System.out.println("Hãy nhập lại số điẹn thoại");
                return null;
            }

            System.out.print("Nhập địa chỉ cho shop: ");
            String ownerAddress = scanner.nextLine();

            System.out.print("Nhập mật khẩu: ");
            String ownerPassword = scanner.nextLine();

            if (!passwordIsValid(ownerPassword)) {
                System.out.println("Mật khẩu không hợp lệ. Mật khẩu phải có từ 8 đến 16 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt.");
                return null;
            }

            Owner newOwner = new Owner(ownerID, ownerName, ownerNumber, ownerAddress, ownerPassword);
            OwnUserList.add(newOwner);
            saveToFile("D:\\06-Java\\00-UngDungShopee\\src\\Shopee\\database\\OwnerUserList.txt", newOwner);
            System.out.println("Chúc mừng bạn đã trở thành đại lý.");
        }
        else
            System.out.println("Vai trò không hợp lệ. Vui lòng nhập 'Khách hàng' hoặc 'Đại lý'.");
        return null;
    }

    private void saveToFile(String filePath, Object user) {
        try (FileWriter fw = new FileWriter(filePath, true);
             PrintWriter pw = new PrintWriter(fw)) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                pw.println(customer.getCustomerName() + " - " + customer.getCustomerPhoneNumber() + " - " + customer.getCustomerAddress() + " - " + customer.getCustomerPassword());
            }
            else if (user instanceof Owner) {
                Owner owner = (Owner) user;
                pw.println(owner.getShopownerID() + " - " + owner.getShopownerNumber() + " - " + owner.getShopownerAddress() + " - " + owner.getShopownerPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
