import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Bill {

       static int i = 0;
       int billNo ;
       int totalPrice ;
       double totalTax ;
       double discount ;
       double rewardsdiscount;
       int netamount;
       String date ;
       int cid ;
       LinkedHashMap<Integer , Integer> productidQuantity ;


       Bill (int cid ,String date ){
              this.cid=cid ;
              this.date = date ;
              this.totalPrice =0;
              this.discount = 0;
              this.rewardsdiscount=0;
              this.netamount=0;
              this.totalTax =0;
              this.productidQuantity = new LinkedHashMap<>();
        
              this.billNo = ++i;
             }
    private static final String URL =  "jdbc:mysql://localhost:3306/billingsystem";
    private static final String USER = "root";
    private static final String PASSWORD = "08669";

    public static int addBill(Bill b) {
      String insertBillQuery = "INSERT INTO Bill (dates, customerId, totalPrice, totalTax, discount,rewardsdiscount,netamount) VALUES (?, ?, ?, ?, ?,?,?)";
      String insertBillProductQuery = "INSERT INTO BillProduct (billNo, productId, quantity) VALUES (?, ?, ?)";
      String updateCustomerPointsQuery = "UPDATE Customer SET points = ? WHERE id = ?";
      int lastid = -1;

  
      try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
           PreparedStatement billStmt = connection.prepareStatement(insertBillQuery, Statement.RETURN_GENERATED_KEYS);
           PreparedStatement billProductStmt = connection.prepareStatement(insertBillProductQuery);
           PreparedStatement updateCustomerPointsStmt = connection.prepareStatement(updateCustomerPointsQuery)) {
  
          connection.setAutoCommit(false);
          Customer customer = Customer.getCustomerDetails(b.cid);
        if (customer == null) {
            throw new SQLException("Customer not found.");
        }

        // Apply points as discount if available
        double discount = b.discount;
        if (customer.points > 0) {
            b.rewardsdiscount= customer.points;
            customer.points=0; // reset points after applying as discount
        }
  
    
          billStmt.setString(1, b.date);
          billStmt.setInt(2, b.cid);
          billStmt.setInt(3, b.totalPrice);
          billStmt.setDouble(4, b.totalTax);
          billStmt.setDouble(5, discount);
          billStmt.setInt(6,(int) b.rewardsdiscount);
          billStmt.setInt(7,b.netamount);

          billStmt.executeUpdate();
  
       
          ResultSet rs = billStmt.getGeneratedKeys();
          if (rs.next()) {
              lastid = rs.getInt(1);
          } else {
              throw new SQLException("Failed to retrieve billNo.");
          }
  
         
          for (Map.Entry<Integer, Integer> entry : b.productidQuantity.entrySet()) {
            int key=entry.getKey();
            int value=entry.getValue();
              billProductStmt.setInt(1, lastid);
              billProductStmt.setInt(2, key);
              billProductStmt.setInt(3, value);
              billProductStmt.addBatch();
          }
          billProductStmt.executeBatch();

          if (customer.membership==true) {
            int newPoints = (int) (b.totalPrice * 0.1); // adding 10% of bill value as points
            customer.points=newPoints;
        }

       
        updateCustomerPointsStmt.setInt(1, customer.points);
        updateCustomerPointsStmt.setInt(2, customer.id);
        updateCustomerPointsStmt.executeUpdate();
  
          connection.commit();
          System.out.println("Bill added successfully.");
  
      } catch (SQLException e) {
          e.printStackTrace();
          
      }
      return lastid;
  }

  public static String getBillFromDB(int billNo) {
    String billQuery = "SELECT * FROM bill WHERE billNo = ?";
    String billProductQuery = "SELECT bp.*, p.name, p.price, p.tax FROM billproduct bp JOIN product p ON bp.productId = p.id WHERE bp.billNo = ?";
    StringBuilder billDetails = new StringBuilder();

    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement billStmt = connection.prepareStatement(billQuery);
         PreparedStatement billProductStmt = connection.prepareStatement(billProductQuery)) {

        billStmt.setInt(1, billNo);
        ResultSet billRs = billStmt.executeQuery();

        if (billRs.next()) {
            String date = billRs.getString("dates");
            int customerId = billRs.getInt("customerId");
            int totalPrice = billRs.getInt("totalPrice");
            double totalTax = billRs.getDouble("totalTax");
            double discount = billRs.getDouble("discount");

            billDetails.append("GENERAL STORES\n")
                       .append("Date: ").append(date).append("   Customer ID: ").append(customerId).append("\n\n")
                       .append("Item           Quantity            Amount\n");

            billProductStmt.setInt(1, billNo);
            ResultSet billProductRs = billProductStmt.executeQuery();

            while (billProductRs.next()) {
                String productName = billProductRs.getString("name");
                int quantity = billProductRs.getInt("quantity");
                int productPrice = billProductRs.getInt("price");
                double productTax = billProductRs.getDouble("tax");
                double amount = productPrice * quantity;

                billDetails.append(String.format("%-15s %-15d %.2f\n", productName, quantity, amount));
            }

            billDetails.append("\n")
                       .append("Total Amount: ").append(totalPrice).append("\n")
                       .append("Total Tax: ").append(totalTax).append("\n")
                       .append("Discount: ").append(discount).append("\n");
        } else {
            billDetails.append("No bill found with the given ID.\n");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return "Error occurred while fetching the bill details.";
    }

    return billDetails.toString();
}
   
  




    public static int getLastBillNumber() {
      String query = "SELECT MAX(billNo) AS lastBillNo FROM bill";
      int lastBillNo = -1;
  
      try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
           PreparedStatement stmt = connection.prepareStatement(query)) {
  
          ResultSet rs = stmt.executeQuery();
          if (rs.next()) {
              lastBillNo = rs.getInt("lastBillNo");
          }
  
      } catch (SQLException e) {
          e.printStackTrace();
      }
  
      return lastBillNo;
  }

}


