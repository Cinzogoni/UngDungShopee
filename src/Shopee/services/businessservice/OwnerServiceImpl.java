package Shopee.services.businessservice;

import Shopee.models.Product;
import Shopee.services.cartservice.CartServiceImpl;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class OwnerServiceImpl implements ProductService {
    private static volatile OwnerServiceImpl ownerService;
    private String shopName;

    public static OwnerServiceImpl getOwnerService(String shopName) {
        if (ownerService == null){
            synchronized (CustomerServiceImpl.class){
                if (ownerService == null){
                    ownerService = new OwnerServiceImpl();
                    ownerService.shopName = shopName;
                }
            }
        }
        return ownerService;
    }
    ArrayList<Product> getOwnerList = new ArrayList<>();

    public Object displayMenuAndOwnerChoice() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Chào mừng đến trình quản lý của " + shopName + ".");
            System.out.println("0. Danh sách sản phẩm của shop");
            System.out.println("1. Thêm sản phẩm mới vào giỏ hàng của shop");
            System.out.println("2. Xoá sản phẩm khỏi giỏ hàng của shop");
            System.out.println("3. Sửa thông tin sản phẩm trong giỏ hàng của shop");
            System.out.println("4. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        ProductServiceImpl ChoiceCartInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> cartProducts = ChoiceCartInService.getCartProductList(shopName);
                        if (cartProducts != null && !cartProducts.isEmpty()){
                            cartProducts.forEach(System.out::println);
                        }
                        else {
                            System.out.println("Danh sách sản phẩm trống.");
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case 1:
                        //In ra danh sách sản phẩm

                        //Check sản phẩm và thêm mới vào danh sách

                        //Lưu sản phẩm đó vào danh sách cartshop (.txt)


                        break;
                    case 2:
                        ProductServiceImpl CartShopInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> removeProducts = CartShopInService.getCartProductList(shopName);
                        if (removeProducts == null || removeProducts.isEmpty())
                            System.out.println("Không có sản phẩm để xoá");
                        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
                        for (int i = 0; i < getOwnerList.size(); i++)
                            System.out.println(i + 1 + ". " + getOwnerList.get(i));
                        System.out.print("Nhập số thứ tự của sản phẩm cần xoá khỏi giỏ hàng: ");
                        int removeIndex = scanner.nextInt();
                        if (removeIndex >= 1 && removeIndex <= getOwnerList.size()){
                            Product productToRemove = getOwnerList.get(removeIndex - 1);
                            getOwnerList.remove(productToRemove);
                            System.out.println(productToRemove + " đã được xoá khỏi giỏ hàng.");
                            System.out.println("Danh sách sản phẩm sau khi xoá:");
                            getOwnerList.forEach(System.out::println);
                        }
                        else
                            System.out.println("Lựa chọn không hợp lệ.");
                        break;
                    case 3:
                        editCartProduct();
                        break;
                    case 4:
                        HomePageView homePageView = HomePageView.gethomePageView();
                        homePageView.displayHomePage();
                        break;
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

    @Override
    public ArrayList<Product> getCartProductList(String shopName) {
        return getCartProductList(shopName);
    }

    @Override
    public  ArrayList<Product> editCartProduct() {
        ProductServiceImpl ChoiceCartInService = ProductServiceImpl.getInstanceProduct();
        ArrayList<Product> editProducts = ChoiceCartInService.getCartProductList(shopName);
        Scanner scanner = new Scanner(System.in);
        if (editProducts.isEmpty()) {
            System.out.println("Không có sản phẩm nào để sửa.");
            return null;
        }
        System.out.println("Danh sách sản phẩm:");
        for (int i = 0; i < getOwnerList.size(); i++) {
            System.out.println((i + 1) + ". " + getOwnerList.get(i));
        }

        System.out.print("Nhập số thứ tự của sản phẩm muốn sửa: ");
        int choiceIndex = scanner.nextInt();
        scanner.nextLine();

        if (choiceIndex >= 1 && choiceIndex <= getOwnerList.size()) {
            Product productToEdit = getOwnerList.get(choiceIndex - 1);

            System.out.println("Thông tin sản phẩm cũ:");
            System.out.println(productToEdit);

            System.out.print("Nhập tên mới của sản phẩm: ");
            String newName = scanner.nextLine();
            System.out.print("Nhập giá mới của sản phẩm: ");
            double newPrice = scanner.nextDouble();
            System.out.print("Nhập số lượng mới của sản phẩm: ");
            int newAmount = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nhập mô tả mới của sản phẩm: ");
            String newDescription = scanner.nextLine();
            System.out.println("---------------------------------------");

            productToEdit.setProductName(newName);
            productToEdit.setProducrPrice(newPrice);
            productToEdit.setProductAmount(newAmount);
            productToEdit.setDescribe(newDescription);

            System.out.println("Thông tin sản phẩm sau khi sửa đổi:");
            System.out.println(productToEdit);
        }
        else
            System.out.println("Lựa chọn không hợp lệ.");
        return getOwnerList;
    }
}
