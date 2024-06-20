
import java.util.*;
import java.sql.*;
public class Main {


    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Chuong trinh quan ly thong tin tai khoan ngan hang ---");
            System.out.println("1. Tim kiem");
            System.out.println("2. Them khach hang moi");
            System.out.println("3. Sua thong tin");
            System.out.println("4. Quan ly so du");
            System.out.println("5. Thong ke");
            System.out.println("6. Thoat");

            System.out.print("Nhap lua chon cua ban: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    xu_ly.Search();
                    break;
                case 2:
                    Scanner input = new Scanner(System.in);
                    System.out.print("Nhap ma khach hang: ");
                    String MaKH = input.nextLine();
                    System.out.print("nhap ten khach hang: ");
                    String TenKH = input.nextLine();
                    System.out.print("nhap so du: ");
                    double So_du = input.nextDouble();
                    input.nextLine() ;
                    System.out.print("nhap so dien thoai: ");
                    String SDT = input.nextLine();
                    khach_hang obj= new khach_hang(TenKH, So_du, MaKH, SDT);
                    xu_ly.add(obj);
                    break;
                case 3:
                    xu_ly.Edit();
                    break;
                case 4:
                    xu_ly.Balance();
                    break;
                case 5:
                    xu_ly.Statistics();
                    break;
                case 6:
                    System.out.println("Thoat chuong trinh!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}