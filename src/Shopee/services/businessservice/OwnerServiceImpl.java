package Shopee.services.businessservice;

import Shopee.models.Product;
import Shopee.services.cartservice.CartServiceImpl;
import Shopee.util.writefile.WriteProductFile;
import Shopee.views.HomePageView;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static Shopee.services.businessservice.ProductServiceImpl.updateCartFile;


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

    public Object displayMenuAndOwnerChoice() {
        Scanner scanner = new Scanner(System.in);
        int searchType;
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
                            System.out.println("Danh sách giỏ hàng đang có của shop");
                            cartProducts.forEach(System.out::println);
                            System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Quay lại");
                            searchType = scanner.nextInt();
                            CartServiceImpl.handleSearchType(cartProducts, searchType);
                        }
                        else {
                            System.out.println("Danh sách sản phẩm trống.");
                            System.out.println("----------------------------------------");
                        }
                        break;
                    case 1:
                        ProductServiceImpl CartInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> Products = CartInService.getCartProductList(shopName);
                        System.out.println("Danh sách sản phẩm đang có trong giỏ hàng của shop");
                        Products.forEach(System.out::println);
                        System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Quay lại");
                        searchType = scanner.nextInt();
                        CartServiceImpl.handleSearchType(Products, searchType);

                        while (true) {
                            System.out.print("Nhập ID sản phẩm: ");
                            int checkproductID = scanner.nextInt();
                            scanner.nextLine();

                            boolean exists = false;
                            for (Product product : Products) {
                                if (product.getProductID() == checkproductID) {
                                    exists = true;
                                    break;
                                }
                            }

                            if (exists) {
                                System.out.println("ID sản phẩm đã tồn tại trong giỏ hàng. Vui lòng nhập ID khác.");
                            }
                            else {
                                System.out.println("ID sản phẩm là duy nhất. Tiếp tục tạo sản phẩm mới.");

                                System.out.println("Mời nhập tên sản phẩm mới:");
                                String productName = scanner.nextLine();
                                System.out.println("Mời nhập giá sản phẩm mới:");
                                double producrPrice = scanner.nextDouble();
                                System.out.println("Mời nhập số lượng sản phẩm mới:");
                                int productAmount = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Mời nhập mô tả sản phẩm mới:");
                                String productDescribe = scanner.nextLine();
                                Product newProduct = new Product(checkproductID, productName, producrPrice, productAmount, productDescribe);
                                Products.add(newProduct);
                                Products.forEach(System.out::println);
                                updateCartFile(shopName, Products);
                                break;
                            }
                        }
                        break;
                    case 2:
                        ProductServiceImpl CartShopInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> removeProducts = CartShopInService.getCartProductList(shopName);
                        if (removeProducts == null || removeProducts.isEmpty())
                            System.out.println("Không có sản phẩm để xoá");
                        System.out.println("Danh sách sản phẩm trong giỏ hàng:");
                        for (int i = 0; i < removeProducts.size(); i++)
                            System.out.println(i + 1 + ". " + removeProducts.get(i));
                        System.out.print("Nhập số thứ tự của sản phẩm cần xoá khỏi giỏ hàng: ");
                        int removeIndex = scanner.nextInt();
                        if (removeIndex >= 1 && removeIndex <= removeProducts.size()){
                            Product productToRemove = removeProducts.get(removeIndex - 1);
                            removeProducts.remove(productToRemove);
                            System.out.println(productToRemove + " đã được xoá khỏi giỏ hàng.");
                            System.out.println("Danh sách sản phẩm sau khi xoá:");
                            removeProducts.forEach(System.out::println);
                            updateCartFile(shopName, removeProducts);
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
        for (int i = 0; i < editProducts.size(); i++) {
            System.out.println((i + 1) + ". " + editProducts.get(i));
        }

        System.out.print("Nhập số thứ tự của sản phẩm muốn sửa: ");
        int choiceIndex = scanner.nextInt();
        scanner.nextLine();

        if (choiceIndex >= 1 && choiceIndex <= editProducts.size()) {
            Product productToEdit = editProducts.get(choiceIndex - 1);

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
            productToEdit.setProductDescribe(newDescription);

            System.out.println("Thông tin sản phẩm sau khi sửa đổi:");
            System.out.println(productToEdit);

            updateCartFile(shopName, editProducts);
        }
        else
            System.out.println("Lựa chọn không hợp lệ.");
        return editProducts;
    }
}
