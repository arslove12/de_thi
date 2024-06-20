import java.sql.*;
import java.util.*;
public class xu_ly {
    static Connection connection = null;

    public xu_ly() throws SQLException {
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=hung;integratedSecurity=true;"+
                "encrypt=true;trustServerCertificate=true;";

        connection = DriverManager.getConnection(connectionUrl);
    }
    public xu_ly(String databaseName) throws SQLException{
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName="+databaseName+";integratedSecurity=true;"+
                "encrypt=true;trustServerCertificate=true;";

        connection = DriverManager.getConnection(connectionUrl);
    }
    public Connection getConnection(){
        return connection;
    }
    public static void add(khach_hang obj ) throws SQLException {

        String sql = "INSERT INTO KhachHang (MaKH, TenKH, So_du, SDT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, obj.getMaKH());
            statement.setString(2, obj.getTenKH());
            statement.setDouble(3, obj.getSo_du());
            statement.setString(4, obj.getSDT());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Loi: " + e.getMessage());
        }
    }


    public static void Search() throws SQLException {
        Scanner input=new Scanner(System.in);
        boolean found=true;
        System.out.print("Nhap ma khach hang can tim: ");
        String MaKH = input.nextLine();
        String query = "select * from KhachHang where MaKH=?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1,MaKH);

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("Ma khach hang: " + resultSet.getString("MaKH"));
                System.out.println("so du : " + resultSet.getString("So_du"));
                System.out.println("ten khach hang: " + resultSet.getString("TenKH"));
                System.out.println("So dien thoai : " + resultSet.getString("SDT"));
                System.out.println("-----------------------------------");
                found = true;
            }
            if (!found) {
                System.out.println("Khong tim thay ket qua phu hop.");
            }
        } catch (SQLException e) {
            System.out.println("Loi" + e.getMessage());
        }
        if (resultSet != null) resultSet.close();
        if (pstmt != null) pstmt.close();
    }
    //Sua
    public static void Edit() throws SQLException {
        Scanner input=new Scanner(System.in);
        System.out.print("Nhap ma khach hang muon sua: ");
        String MaKH=input.nextLine();
        boolean lap=true;
        while(lap){
            System.out.print("""
                                 Ban muon sua thong tin nao
                                 1. Ten khach hang
                                 2. So Dien Thoai
                                 3. Thoat
                                 Moi chon:
                                 """);
            int chon=input.nextInt();
            input.nextLine();
            switch(chon){
                case 1:
                    System.out.print("Nhap ten khach hang moi: ");
                    String TenKH=input.nextLine();
                    String query="update KhachHang set TenKH=? where MaKH=?";
                    PreparedStatement pstmt=null;
                    try{
                        pstmt=connection.prepareStatement(query);
                        pstmt.setString(1,TenKH);
                        pstmt.setString(2,MaKH);
                        pstmt.executeUpdate();
                        System.out.println("Sua thanh cong!");
                    }
                    catch(SQLException e){
                        System.out.println("Loi"+e.getMessage());
                    }
                    if (pstmt != null) pstmt.close();
                    break;
                case 2:
                    System.out.print("So Dien Thoai: ");
                    String SDT=input.nextLine();
                    query="update KHachHang set SDT=? where MaKH=?";
                    pstmt = null;
                    try{
                        pstmt=connection.prepareStatement(query);
                        pstmt.setString(1,SDT);
                        pstmt.setString(2,MaKH);
                        pstmt.executeUpdate();
                        System.out.println("Sua thanh cong!");
                    }
                    catch(SQLException e){
                        System.out.println("Loi"+e.getMessage());
                    }
                    if (pstmt != null) pstmt.close();
                    break;
                case 3:
                    lap=false;
                    break;
                default:
                    System.out.print("Chon dung chuc nang: ");
            }
        }
    }
    public static void Balance() throws SQLException{
        Scanner input=new Scanner(System.in);
        System.out.print("nhap ma khach hang ban muon: ");
        String MaKH=input.nextLine();
        boolean lap=true;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (connection == null) {
            xu_ly connectionManager = new xu_ly(); // Tạo một thể hiện mới để thiết lập kết nối
        }
        while(lap) {
            System.out.print("""
                    chon chuc nang:
                    1. kiem tra so du
                    2. chuyen tien
                    3. nap tien
                    4. rut tien
                    5. thoat
                    lua chon cua ban:
                    """);
            int chon = input.nextInt();
            input.nextLine();
            switch (chon) {
                case 1:
                    boolean found=true;
                    String Sodu = "select So_du from KhachHang where MaKH=?";
                    try {
                        pstmt = connection.prepareStatement(Sodu);
                        pstmt.setString(1,MaKH);

                        resultSet = pstmt.executeQuery();
                        while (resultSet.next()) {
                            System.out.println("so du : " + resultSet.getString("So_du"));
                            System.out.println("-----------------------------------");
                            found = true;
                        }
                        if (!found) {
                            System.out.println("Khong tim thay ket qua phu hop.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Loi" + e.getMessage());
                    }
                    if (resultSet != null) resultSet.close();
                    if (pstmt != null) pstmt.close();
                    break;
                case 2:
                    System.out.print("Nhap ma khach hang nhan tien: ");
                    String MaKHNhan = input.nextLine();
                    System.out.print("Nhap so tien can chuyen: ");
                    double chuyenTien = input.nextDouble();
                    input.nextLine();
                    double totalBalance = 0.0;
                    boolean condi =true;
                    String tien = "select So_du from KhachHang where MaKH=?";
                    try {
                        pstmt = connection.prepareStatement(tien);
                        pstmt.setString(1,MaKH);

                        resultSet = pstmt.executeQuery();
                        while (resultSet.next()) {
                            double money = resultSet.getDouble("So_du");
                            totalBalance += money;
                            condi = true;
                        }
                        if (!condi) {
                            System.out.println("Khong tim thay ket qua phu hop.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Loi" + e.getMessage());
                    }
                    if (resultSet != null) resultSet.close();
                    if (pstmt != null) pstmt.close();
                    double currentBalance = totalBalance;
                    if (currentBalance < chuyenTien) {
                        System.out.println("So du khong du de chuyen tien.");
                        break;
                    }

                    // Update balances using transactions (unchanged)
                    try {
                        connection.setAutoCommit(false); // Start transaction

                        // Update sender's balance (withdraw)
                        String withdrawQuery = "UPDATE KhachHang SET So_du = So_du - ? WHERE MaKH = ?";
                        pstmt = connection.prepareStatement(withdrawQuery);
                        pstmt.setDouble(1, chuyenTien);
                        pstmt.setString(2, MaKH);
                        pstmt.executeUpdate();

                        // Update receiver's balance (deposit)
                        String depositQuery = "UPDATE KhachHang SET So_du = So_du + ? WHERE MaKH = ?";
                        pstmt = connection.prepareStatement(depositQuery);
                        pstmt.setDouble(1, chuyenTien);
                        pstmt.setString(2, MaKHNhan);
                        pstmt.executeUpdate();

                        connection.commit(); // Commit transaction if successful
                        System.out.println("Chuyen tien thanh cong!");
                    } catch (SQLException e) {
                        connection.rollback(); // Rollback transaction if error
                        System.out.println("Loi khi chuyen tien: " + e.getMessage());
                    } finally {
                        connection.setAutoCommit(true); // Reset auto-commit
                        if (pstmt != null) pstmt.close();
                    }
                    break;
                case 3:
                    System.out.print("Nhap so tien can nap: ");
                    double napTien = input.nextDouble();
                    input.nextLine();

                    // Update balance using transactions
                    try {
                        connection.setAutoCommit(false);

                        String depositQuery = "UPDATE KhachHang SET So_du = So_du + ? WHERE MaKH = ?";
                        pstmt = connection.prepareStatement(depositQuery);
                        pstmt.setDouble(1, napTien);
                        pstmt.setString(2, MaKH);
                        pstmt.executeUpdate();

                        connection.commit();
                        System.out.println("Nap tien thanh cong!");
                    } catch (SQLException e) {
                        connection.rollback();
                        System.out.println("Loi khi nap tien: " + e.getMessage());
                    } finally {
                        connection.setAutoCommit(true);
                        break;
                    }
                case 4:
                    System.out.print("Nhap so tien can rut: ");
                    double rutTien = input.nextDouble();
                    input.nextLine();
                    if (rutTien <= 0) {
                        System.out.println("So tien rut tien phai lon hon 0.");
                        break;
                    }
                    double total = 0.0;
                    boolean condic =true;
                    String accbalance  = "select So_du from KhachHang where MaKH=?";
                    try {
                        pstmt = connection.prepareStatement(accbalance);
                        pstmt.setString(1,MaKH);

                        resultSet = pstmt.executeQuery();
                        while (resultSet.next()) {
                            double money = resultSet.getDouble("So_du");
                            total += money;
                            condic = true;
                        }
                        if (!condic) {
                            System.out.println("Khong tim thay ket qua phu hop.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Loi" + e.getMessage());
                    }
                    if (resultSet != null) resultSet.close();
                    if (pstmt != null) pstmt.close();
                    double CurrentBalance = total;
                    if (CurrentBalance < rutTien) {
                        System.out.println("So du khong du de rut tien.");
                        break;
                    }

                    // Update balance using transactions
                    try {
                        connection.setAutoCommit(false); // Start transaction

                        // Update sender's balance (withdraw)
                        String withdrawQuery = "UPDATE KhachHang SET So_du = So_du - ? WHERE MaKH = ?";
                        pstmt = connection.prepareStatement(withdrawQuery);
                        pstmt.setDouble(1, rutTien);
                        pstmt.setString(2, MaKH);
                        pstmt.executeUpdate();

                        connection.commit(); // Commit transaction if successful
                        System.out.println("Rut tien thanh cong!");
                    } catch (SQLException e) {
                        connection.rollback(); // Rollback transaction if error
                        System.out.println("Loi khi rut tien: " + e.getMessage());
                    } finally {
                        connection.setAutoCommit(true); // Reset auto-commit
                        if (pstmt != null) pstmt.close();
                    }
                    break;
                case 6:
                    System.out.println("Thoat!");
                    return;
                default:
                    System.out.println("lua chon khong hop le!");
            }
        }
    }
    public static void Statistics() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Chon loai thong ke: \n" +
                "1. Tong so khach hang\n" +
                "2. Tong so du trung binh\n" +
                "3. Khach hang co so du cao nhat\n" +
                "4. Khach hang co so du thap nhat\n" +
                "5. Thoat\n" +
                "Lua chon cua ban: ");

        int choice = input.nextInt();
        input.nextLine();

        switch (choice) {
            case 1:
                countCustomers();
                break;
            case 2:
                averageBalance();
                break;
            case 3:
                highestBalance();
                break;
            case 4:
                lowestBalance();
                break;
            case 5:
                System.out.println("Thoat thong ke!");
                return;
            default:
                System.out.println("Lua chon khong hop le!");
        }
    }

    private static void countCustomers() throws SQLException {
        String query = "SELECT COUNT(*) AS total_customers FROM KhachHang";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (connection == null) {
            xu_ly connectionManager = new xu_ly();
        }
        try {
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();
            if (connection == null) {
                xu_ly connectionManager = new xu_ly();
            }
            if (resultSet.next()) {
                int totalCustomers = resultSet.getInt("total_customers");
                System.out.println("Tong so khach hang: " + totalCustomers);
            }
        } catch (SQLException e) {
            System.out.println("Loi khi thong ke: " + e.getMessage());
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }

    private static void averageBalance() throws SQLException {
        String query = "SELECT AVG(So_du) AS average_balance FROM KhachHang";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (connection == null) {
            xu_ly connectionManager = new xu_ly();
        }
        try {
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                double averageBalance = resultSet.getDouble("average_balance");
                System.out.println("Tong so du trung binh: " + averageBalance);
            }
        } catch (SQLException e) {
            System.out.println("Loi khi thong ke: " + e.getMessage());
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }
    private static void lowestBalance() throws SQLException {
        String query = "SELECT TOP 1 MaKH, TenKH, So_du FROM KhachHang ORDER BY So_du ASC";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (connection == null) {
            xu_ly connectionManager = new xu_ly();
        }
        try {
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String MaKH = resultSet.getString("MaKH");
                String TenKH = resultSet.getString("TenKH");
                double So_du = resultSet.getDouble("So_du");
                System.out.println("Khách hàng có số dư thấp nhất:");
                System.out.println("Mã KH: " + MaKH);
                System.out.println("Tên KH: " + TenKH);
                System.out.println("Số dư: " + So_du);
            } else {
                System.out.println("Không tìm thấy khách hàng nào!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thống kê: " + e.getMessage());
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }
    private static void highestBalance() throws SQLException {
        String query = "SELECT TOP 1 MaKH, TenKH, So_du FROM KhachHang ORDER BY So_du DESC";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        if (connection == null) {
            xu_ly connectionManager = new xu_ly();
        }
        try {
            pstmt = connection.prepareStatement(query);
            resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String MaKH = resultSet.getString("MaKH");
                String TenKH = resultSet.getString("TenKH");
                double So_du = resultSet.getDouble("So_du");
                System.out.println("Khach hang co so du cao nhat:");
                System.out.println("Ma KH: " + MaKH);
                System.out.println("Ten KH: " + TenKH);
                System.out.println("So du: " + So_du);
            }
        } catch (SQLException e) {
            System.out.println("Loi khi thong ke: " + e.getMessage());
        } finally {
            if (resultSet != null) resultSet.close();
            if (pstmt != null) pstmt.close();
        }
    }
}