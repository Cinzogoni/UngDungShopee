package Shopee.services.businessservice;

import Shopee.models.Owner;
import Shopee.models.Product;
import Shopee.services.cartservice.CartServiceImpl;
import Shopee.util.readfile.ReadOwnerFile;
import Shopee.views.HomePageView;

import java.util.*;

import static Shopee.services.businessservice.ProductServiceImpl.getInstanceProduct;
import static Shopee.services.businessservice.ProductServiceImpl.updateCartFile;


public class OwnerServiceImpl implements ProductService {
    private static volatile OwnerServiceImpl ownerService;
    private String shopName;
    private String shopID;
    private String shopPhoneNumber;
    private String shopAddress;

    public static OwnerServiceImpl getOwnerService(String shopName) {
        if (ownerService == null){
            synchronized (CustomerServiceImpl.class){
                if (ownerService == null){
                    ownerService = new OwnerServiceImpl();
                    ownerService.shopName = shopName;

                    Owner ownerInfo = ownerService.getOwnerInfoFromDatabase(shopName);
                    if (ownerInfo != null){
                        ownerService.shopID = ownerInfo.getShopownerID();
                        ownerService.shopPhoneNumber = ownerInfo.getShopownerNumber();
                        ownerService.shopAddress = ownerInfo.getShopownerAddress();
                    }
                }
            }
        }
        return ownerService;
    }
    private Owner getOwnerInfoFromDatabase(String shopName) {
        String filePath = "src/Shopee/database/OwnerUserList.txt";
        ArrayList<Owner> owners = ReadOwnerFile.readOwnersFile(filePath);
        for (Owner owner : owners) {
            if (owner.getShopownerName().equals(shopName)) {
                return owner;
            }
        }
        return null;
    }

    public Object displayMenuAndOwnerChoice() {
        Scanner scanner = new Scanner(System.in);
        int searchType;
        do {
            System.out.println("Chào mừng đến trình quản lý của " +shopName+ ".");
            System.out.println("0. Thông tin người dùng " +shopName+ ".");
            System.out.println("1. Danh sách sản phẩm của " +shopName+ ".");
            System.out.println("2. Thêm sản phẩm mới vào giỏ hàng của shop");
            System.out.println("3. Xoá sản phẩm khỏi giỏ hàng của shop");
            System.out.println("4. Sửa thông tin sản phẩm trong giỏ hàng của shop");
            System.out.println("5. Thoát");

            System.out.print("Mời chọn chức năng: ");
            try {
                int choose = scanner.nextInt();
                scanner.nextLine();
                switch (choose) {
                    case 0:
                        System.out.println("1. Tiếp tục xem, 2. Quay lại");
                        int chooseinfo = scanner.nextInt();
                        scanner.nextLine();
                        switch (chooseinfo){
                            case 1:
                                OwnerServiceImpl ownerInfo = OwnerServiceImpl.getOwnerService(shopName);
                                Owner info = ownerInfo.getOwnerInfoFromDatabase(shopName);
                                assert info != null;
                                System.out.println("ID người dùng: " +info.getShopownerID());
                                System.out.println("Tên người dùng: " +info.getShopownerName());
                                System.out.println("Số điện thoại người dùng: " +info.getShopownerNumber());
                                System.out.println("Địa chỉ người dùng: " +info.getShopownerAddress());
                                System.out.println("--------------------------------------------------");
                                break;
                            case 2:
                                displayMenuAndOwnerChoice();
                                break;
                            default:
                                break;
                        }
                        break;
                    case 1:
                        ProductServiceImpl ChoiceCartInService = getInstanceProduct();
                        ArrayList<Product> cartProducts = ChoiceCartInService.getCartProductList(shopName);
                        if (!cartProducts.isEmpty()) {
                            cartProducts.sort(Comparator.comparing(Product::getProductName));

                            int offset3 = 0;
                            int limit3 = 5;
                            int totalProducts3 = cartProducts.size();
                            int totalPages3 = (int) Math.ceil((double) totalProducts3 / limit3);
                            boolean continuePaging3 = true;

                            while (continuePaging3) {
                                int pageStart = offset3 * limit3;
                                int pageEnd = Math.min(pageStart + limit3, totalProducts3);
                                System.out.println("Danh sách sản phẩm từ " + (pageStart + 1) + " đến " + pageEnd + " trong tổng số " + totalProducts3);
                                for (int i = pageStart; i < pageEnd; i++) {
                                    System.out.println((i + 1) + ". " + cartProducts.get(i));
                                }

                                System.out.println("Chọn trang: (B: Trang trước, N: Trang sau, S: Tìm kiếm, Q: Quay lại)");
                                String pageChoice = scanner.next();

                                switch (pageChoice.toUpperCase()) {
                                    case "B":
                                        if (offset3 > 0) offset3--;
                                        else System.out.println("Đây là trang đầu tiên.");
                                        break;
                                    case "N":
                                        if (offset3 < totalPages3 - 1) offset3++;
                                        else System.out.println("Đây là trang cuối cùng.");
                                        break;
                                    case "S":
                                        System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Theo giá ,4: Quay lại");
                                        if (cartProducts != null && !cartProducts.isEmpty()){
                                            cartProducts.sort(Comparator.comparing(Product::getProductName));
                                            System.out.println("Danh sách giỏ hàng đang có của shop");
                                            cartProducts.forEach(System.out::println);
                                            searchType = scanner.nextInt();
                                            ArrayList<Product> searchedProducts = new ArrayList<>(cartProducts);
                                            CartServiceImpl search = new CartServiceImpl();
                                            search.handleSearchType(cartProducts, searchType, searchedProducts, shopName);
                                        }
                                        else {
                                            System.out.println("Danh sách sản phẩm trống.");
                                            System.out.println("----------------------------------------");
                                        }
                                        break;
                                    case "Q":
                                        continuePaging3 = false;
                                        break;
                                    default:
                                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                                }
                            }
                        }
                        else
                            System.out.println("Danh sách sản phẩm trống");
                        break;
                    case 2:
                        ProductServiceImpl CartInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> addNewProducts = CartInService.getCartProductList(shopName);
                        addNewProducts.sort(Comparator.comparing(Product::getProductName));

                        int offset4 = 0;
                        int limit4 = 5;
                        int totalProducts4 = addNewProducts.size();
                        int totalPages4 = (int) Math.ceil((double) totalProducts4 / limit4);
                        boolean continuePaging4 = true;

                        while (continuePaging4) {
                            int pageStart = offset4 * limit4;
                            int pageEnd = Math.min(pageStart + limit4, totalProducts4);
                            System.out.println("Hiển thị sản phẩm từ " + (pageStart + 1) + " đến " + pageEnd + " trong tổng số " + totalProducts4);
                            for (int i = pageStart; i < pageEnd; i++) {
                                System.out.println((i + 1) + ". " + addNewProducts.get(i));
                            }
                            System.out.println("Chọn trang: (B: Trang trước, N: Trang sau, A: Thêm sản phẩm mới, Q: Quay lại)");
                            String pageChoice = scanner.next();

                            switch (pageChoice.toUpperCase()) {
                                case "B":
                                    if (offset4 > 0) offset4--;
                                    else System.out.println("Đây là trang đầu tiên.");
                                    break;
                                case "N":
                                    if (offset4 < totalPages4 - 1) offset4++;
                                    else System.out.println("Đây là trang cuối cùng.");
                                    break;
                                case "A":
                                    System.out.println("Danh sách sản phẩm đang có trong giỏ hàng của shop");
                                    System.out.println("Tìm kiếm sản phẩm, 1: Theo tên, 2: Mô tả, 3: Theo giá , 4: Quay lại");
                                    addNewProducts.forEach(System.out::println);
                                    searchType = scanner.nextInt();
                                    ArrayList<Product> searchedProducts = new ArrayList<>(addNewProducts);
                                    CartServiceImpl search = new CartServiceImpl();
                                    search.handleSearchType(addNewProducts, searchType, searchedProducts, shopName);

                                    System.out.println("Thêm sản phẩm mới: ");
                                    while (true) {
                                        System.out.print("Nhập ID sản phẩm: ");
                                        int checkproductID = scanner.nextInt();
                                        scanner.nextLine();

                                        boolean exists = false;
                                        for (Product product : addNewProducts) {
                                            if (product.getProductID() == checkproductID) {
                                                exists = true;
                                                break;
                                            }
                                        }

                                        if (exists) {
                                            System.out.println("ID sản phẩm đã tồn tại trong giỏ hàng. Vui lòng nhập ID khác.");
                                        } else {
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
                                            addNewProducts.add(newProduct);
                                            addNewProducts.forEach(System.out::println);
                                            updateCartFile(shopName, addNewProducts);
                                            break;
                                        }
                                    }
                                case "Q":
                                    continuePaging4 = false;
                                    break;
                                default:
                                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                            }
                        }
                    case 3:
                        ProductServiceImpl CartShopInService = ProductServiceImpl.getInstanceProduct();
                        ArrayList<Product> removeProducts = CartShopInService.getCartProductList(shopName);
                        if (removeProducts == null || removeProducts.isEmpty())
                            System.out.println("Không có sản phẩm để xoá");
                        System.out.println("Danh sách sản phẩm trong giỏ hàng c:");
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
                    case 4:
                        editCartProduct();
                        break;
                    case 5:
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
