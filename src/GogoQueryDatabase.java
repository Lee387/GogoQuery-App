import java.sql.*;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class GogoQueryDatabase {
	
	 private final static String USERNAME = "root";
	 private final static String PASSWORD = "";
	 private final static String CONNECTION = "jdbc:mysql://localhost:3306/gogoquery";
	 
	 public ResultSet rs;
	 public ResultSetMetaData rsm;
	 
	 private static Connection con;
	 private Statement st;
	 private static GogoQueryDatabase connect;
	 
	 
	    public static Connection connect() throws SQLException{
	        return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);}
	 
	 public static GogoQueryDatabase getInstance() {
		 if(connect == null) return new GogoQueryDatabase();
		 return connect;
		 
	 }
	 
	 private GogoQueryDatabase() {
	    try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		 	 st = con.createStatement();
		 
		} catch (Exception e) {
			e.printStackTrace();
		}  
	 }
	 
	    public static boolean addUser(String dob, String email, String password, String gender, String role) throws SQLException{
	        String query = "INSERT INTO MsUser (UserDOB, UserEmail, UserPassword, UserGender, UserRole) VALUES (?, ?, ?, ?, ?)";
	        try(Connection con = connect();PreparedStatement ps = con.prepareStatement(query)){
	            ps.setString(1, dob);
	            ps.setString(2, email);
	            ps.setString(3, password);
	            ps.setString(4, gender);
	            ps.setString(5, role);
	            return ps.executeUpdate() > 0;}}
	 
	 
	 
	 public static boolean validateLogin(String email, String password) throws SQLException{
	        String query = "SELECT COUNT(*) FROM MsUser WHERE UserEmail = ? AND UserPassword = ?";
	        try(Connection con = connect();PreparedStatement ps = con.prepareStatement(query)){
	            ps.setString(1, email);
	            ps.setString(2, password);
	            ResultSet rs = ps.executeQuery();
	            if(rs.next()){
	                return rs.getInt(1) >0;}}
	        return false;}
	 
	 public static String userRole(String email) throws SQLException {
	        String query = "SELECT UserRole FROM MsUser WHERE UserEmail = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setString(1, email);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getString("UserRole");}
	                else {
	                    throw new SQLException("Role not found for email: " + email);}}}}
	 
	 public static boolean checkEmail(String email) throws SQLException{
	        String query = "SELECT COUNT(*) FROM MsUser WHERE UserEmail = ?";
	        try(Connection con = connect();PreparedStatement ps = con.prepareStatement(query)){
	            ps.setString(1, email);
	            ResultSet rs = ps.executeQuery();
	            if(rs.next()){
	                return rs.getInt(1) > 0;}}
	        return false;}
	 
	 
	 public static int getUserID(String email) throws SQLException {
	        String query = "SELECT UserID FROM MsUser WHERE UserEmail = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setString(1, email);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("UserID");}
	            else {
	                throw new SQLException("User not found for email: " + email);}}}

	 
	 public static ObservableList<Product> Products() throws SQLException {
	        String query = "SELECT ItemID, ItemName, ItemCategory, ItemPrice, ItemDesc, ItemStock FROM MsItem";
	        ObservableList<Product> products = FXCollections.observableArrayList();

	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                products.add(new Product(
	                        rs.getInt("ItemID"),
	                        rs.getString("ItemName"),
	                        rs.getString("ItemCategory"),
	                        rs.getDouble("ItemPrice"),
	                        rs.getString("ItemDesc"),
	                        rs.getInt("ItemStock")));}
	        } catch (SQLException e) {
	            e.printStackTrace();}
	        return products;}
	 
	 
	 
	 
	    public static int itemStock(int itemId) throws SQLException {
	        String query = "SELECT ItemStock FROM MsItem WHERE ItemID = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, itemId);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("ItemStock");}}
	        return 0;}
	    
	    
	    
	    
	    public static ObservableList<Product> filterProducts(String category, String searchText) throws SQLException {
	        StringBuilder query = new StringBuilder("SELECT ItemID, ItemName, ItemCategory, ItemPrice, ItemDesc, ItemStock FROM MsItem WHERE ItemStock > 0");

	        List<Object> filter = new ArrayList<>();
	        if (category != null && !category.equals("Select a category")) {
	            query.append(" AND ItemCategory = ?");
	            filter.add(category);}
	        if (searchText != null && !searchText.isEmpty()) {
	            query.append(" AND LOWER(ItemName) LIKE ?");
	            filter.add("%" + searchText + "%");
	            }
	        ObservableList<Product> products = FXCollections.observableArrayList();
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query.toString())) {
	            for (int i = 0; i < filter.size(); i++) {
	                ps.setObject(i + 1, filter.get(i));}
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    products.add(new Product(
	                            rs.getInt("ItemID"),
	                            rs.getString("ItemName"),
	                            rs.getString("ItemCategory"),
	                            rs.getDouble("ItemPrice"),
	                            rs.getString("ItemDesc"),
	                            rs.getInt("ItemStock")));}}}
	        return products;}
	    
	    
	    
	    
	    
	    public static List<String> itemCategory() throws SQLException {
	        String query = "SELECT DISTINCT ItemCategory FROM MsItem";
	        List<String> category = new ArrayList<>();
	        category.add("Select a category");

	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	            	category.add(rs.getString("ItemCategory"));}}
	        return category;}
	    
	    
	    
	    
	    public static ObservableList<getCart> getCart(String email) throws SQLException {
	        String query = "SELECT MsItem.ItemID, MsItem.ItemName, MsItem.ItemPrice, MsCart.Quantity " +
	                "FROM MsItem JOIN MsCart ON MsItem.ItemID = MsCart.ItemID " +
	                "WHERE MsCart.UserID = ?";
	        ObservableList<getCart> cart = FXCollections.observableArrayList();

	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, getUserID(email));
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    cart.add(new getCart(
	                            rs.getInt("ItemID"),
	                            rs.getInt("ItemID"),
	                            rs.getString("ItemName"),
	                            rs.getDouble("ItemPrice"),
	                            rs.getInt("Quantity")));}}
	        } catch (SQLException e) {
	            e.printStackTrace();}
	        return cart;}
	    
	    
	    
	    public static String addToCart(String email, int itemID, int quantity) throws SQLException {
	    	Connection con = connect();
	        String MsUserID = "SELECT UserID FROM MsUser WHERE UserEmail = ?";
	        String itemStock = "SELECT ItemStock FROM MsItem WHERE ItemID = ?";
	        String qty  = "SELECT Quantity FROM MsCart WHERE UserID = ? AND ItemID = ?";
	        String updateCart = "UPDATE MsCart SET Quantity = ? WHERE UserID = ? AND ItemID = ?";
	        String insertCart = "INSERT INTO MsCart (UserID, ItemID, Quantity) VALUES (?, ?, ?)";


	        PreparedStatement psUserID = con.prepareStatement(MsUserID);
	        psUserID.setString(1, email);
	        ResultSet rsUserID = psUserID.executeQuery();

	        if (!rsUserID.next()) {
	            throw new SQLException("User not found for email: " + email);}
	        int userID = rsUserID.getInt("UserID");

	        PreparedStatement psStock = con.prepareStatement(itemStock);
	        psStock.setInt(1, itemID);
	        ResultSet rsStock = psStock.executeQuery();

	        if (!rsStock.next()) {
	            throw new SQLException("Item not found for itemID: " + itemID);}
	        int stock = rsStock.getInt("ItemStock");

	        PreparedStatement psCart = con.prepareStatement(qty);
	        psCart.setInt(1, userID);
	        psCart.setInt(2, itemID);
	        ResultSet rsCart = psCart.executeQuery();

	        if (rsCart.next()) {
	            int currentQuantity = rsCart.getInt("Quantity");
	            int newQuantity = currentQuantity + quantity;

	            if (newQuantity > stock) {
	                newQuantity = stock;}

	            PreparedStatement psUpdateCart = con.prepareStatement(updateCart);
	            psUpdateCart.setInt(1, newQuantity);
	            psUpdateCart.setInt(2, userID);
	            psUpdateCart.setInt(3, itemID);
	            psUpdateCart.executeUpdate();

	            if (newQuantity == stock) {
	                return "not enough stock";}

	            return "updated";}
	        else {
	            PreparedStatement psInsert = con.prepareStatement(insertCart);
	            psInsert.setInt(1, userID);
	            psInsert.setInt(2, itemID);
	            psInsert.setInt(3, quantity);
	            psInsert.executeUpdate();

	            return "added";}}
 
	    
	    
	    public static int getQuantity(String email, int itemID) throws SQLException {
	        String query = "SELECT Quantity FROM MsCart WHERE UserID = ? AND ItemID = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, getUserID(email));
	            ps.setInt(2, itemID);
	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("Quantity");}
	            else {
	                return 0;}
	            }
	        }
	    
	    
	    
	    
	    public static void removeItem(String email, int itemID) throws SQLException {
	        String query = "DELETE FROM MsCart WHERE UserID = ? AND ItemID = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, getUserID(email));
	            ps.setInt(2, itemID);
	            ps.executeUpdate();
	            }
	        }
	    
	    public static void updateQuantity(String email, int itemID, int quantity) throws SQLException {
	        String query = "UPDATE MsCart SET Quantity = ? WHERE UserID = ? AND ItemID = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, quantity);
	            ps.setInt(2, getUserID(email));
	            ps.setInt(3, itemID);
	            ps.executeUpdate();}}
	    
	    public static double totalPrice(String email) throws SQLException {
	        double totalPrice = 0.0;
	        String query = "SELECT SUM(p.ItemPrice * c.Quantity) AS total " + "FROM MsCart c " + "JOIN MsItem p ON c.ItemID = p.ItemID "+ "JOIN MsUser u ON c.UserID = u.UserID " + "WHERE u.UserEmail = ?";

	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setString(1, email);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {totalPrice = rs.getDouble("total");}}}
	        return totalPrice;}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public static void checkout(String email) throws SQLException {
	        String insertTH = "INSERT INTO TransactionHeader (UserID, DateCreated, Status) VALUES (?, CURDATE(), 'In Queue')";
	        String insertTD = "INSERT INTO TransactionDetail (TransactionID, ItemID, Quantity) SELECT ?, ItemID, Quantity FROM MsCart WHERE UserID = ? GROUP BY ItemID";
	        String deleteCartQuery = "DELETE FROM MsCart WHERE UserID = ?";
	        String updateStockQuery = "UPDATE MsItem SET ItemStock = ItemStock - (SELECT Quantity FROM MsCart WHERE MsCart.ItemID = MsItem.ItemID AND MsCart.UserID = ?) WHERE EXISTS (SELECT 1 FROM MsCart WHERE MsCart.ItemID = MsItem.ItemID AND MsCart.UserID = ?)";

	        try (Connection con = connect();PreparedStatement psTH = con.prepareStatement(insertTH, Statement.RETURN_GENERATED_KEYS); 
	        		PreparedStatement psTD = con.prepareStatement(insertTD); PreparedStatement psDQ = con.prepareStatement(deleteCartQuery); 
	        		PreparedStatement psSQ = con.prepareStatement(updateStockQuery)) {
	        	
	            psTH.setInt(1, getUserID(email));
	            psTH.executeUpdate();

	            try (ResultSet rs = psTH.getGeneratedKeys()) {
	                if (rs.next()) {
	                    int transactionID = rs.getInt(1);

	                    psTD.setInt(1, transactionID);
	                    psTD.setInt(2, getUserID(email));
	                    psTD.executeUpdate();}}

	            psSQ.setInt(1, getUserID(email));
	            psSQ.setInt(2, getUserID(email));
	            psSQ.executeUpdate();
	            psDQ.setInt(1, getUserID(email));
	            psDQ.executeUpdate();}}
	    
	    
	    
	    
	    
	    
	    public static List<Transaction> listTransaction() {
	        List<Transaction> transaction = new ArrayList<>();
	        String query = " SELECT th.TransactionID AS id, th.UserID AS customerId, mu.UserEmail AS customerEmail, th.DateCreated AS date, (SELECT SUM(td.Quantity * mi.ItemPrice) FROM TransactionDetail td JOIN MsItem mi ON td.ItemID = mi.ItemID WHERE td.TransactionID = th.TransactionID) AS amount, th.Status AS status FROM TransactionHeader th JOIN MsUser mu ON th.UserID = mu.UserID;";

	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	            	transaction.add(new Transaction(
	                        rs.getInt("id"),
	                        rs.getInt("customerId"),
	                        rs.getString("customerEmail"),
	                        rs.getString("date"),
	                        rs.getDouble("amount"),
	                        rs.getString("status")));}
	        } catch (SQLException e) {
	            e.printStackTrace();}

	        return transaction;}

	    public static void updateTransactionStatus(int transactionId, String newStatus) {
	        String query = "UPDATE TransactionHeader SET Status = ? WHERE TransactionID = ?";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {

	            ps.setString(1, newStatus);
	            ps.setInt(2, transactionId);

	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();}}

	    public static void insertItem(String name, String category, double price, String desc, int stock) {
	        String query = "INSERT INTO MsItem (ItemName, ItemCategory, ItemPrice, ItemDesc, ItemStock) VALUES (?, ?, ?, ?, ?)";
	        try (Connection con = connect();PreparedStatement ps = con.prepareStatement(query)) {
	        	ps.setString(1, name);
	        	ps.setString(2, category);
	        	ps.setDouble(3, price);
	        	ps.setString(4, desc);
	        	ps.setInt(5, stock);
	        	ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            }
	        }
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    

