import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class Customer {

    static int i=0;
    int id ;
    String name ;
    String phoneNo ;
    Boolean membership ;
    int points ;
    Map<Bill,String> billwithdate ;

    Customer (String name ,String phoneNo ){
        this.id=i++;
        this.name = name;
        this.phoneNo = phoneNo;
        this.membership = false;
        this.points =0;
        this.billwithdate = new HashMap<>();
    }

    public Customer(int id, String name, String phoneNo, Boolean membership, int points) {
      this.id = id;
      this.name = name;
      this.phoneNo = phoneNo;
      this.membership = membership;
      this.points = points;
  
  }

    private static Connection connect() {
        
      String url = "jdbc:mysql://localhost:3306/billingsystem";
      String username = "root";
      String password = "08669";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static int insertCustomer(String name, String phone,String membershipstatus) {
      String sql = "INSERT INTO Customer(name, phoneNo, membership, points) VALUES(?, ?, ?, ?)";
      int lastid = -1;

      try (Connection conn = Customer.connect(); 
           PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

          pstmt.setString(1, name);
          pstmt.setString(2, phone);
          if(membershipstatus.equals("Y"))
          pstmt.setBoolean(3, true);
          else
          pstmt.setBoolean(3, false);
          pstmt.setInt(4, 0);
          pstmt.executeUpdate();

          ResultSet rs = pstmt.getGeneratedKeys();
          if (rs.next()) {
              lastid = rs.getInt(1);
          } else {
              throw new SQLException("Failed to retrieve customer ID.");
          }
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }

      return lastid;
  }


    public static int getLastBillNumber() {
      String query = "SELECT MAX(billNo) AS lastBillNo FROM customer";
      int lastBillNo = -1;
  
      try (Connection conn = Customer.connect();PreparedStatement pstmt = conn.prepareStatement(query)) {
  
          ResultSet rs = pstmt.executeQuery();
          if (rs.next()) {
              lastBillNo = rs.getInt("lastBillNo");
          }
  
      } catch (SQLException e) {
          e.printStackTrace();
      }
  
      return lastBillNo;
  }

    
    public static void updateCustomer(int customerId, boolean newMembershipStatus, int additionalPoints){
     
        String updateQuery = "UPDATE Customer SET membership = ?, points = points + ? WHERE id = ?";

        try (Connection connection = Customer.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            
            preparedStatement.setBoolean(1, newMembershipStatus);
            preparedStatement.setInt(2, additionalPoints);
            preparedStatement.setInt(3, customerId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully.");
            } else {
                System.out.println("Customer not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteCustomer(int customerId) {
      String query = "DELETE FROM customers WHERE id = ?";

      try (Connection connection = Customer.connect();
      PreparedStatement preparedStatement = connection.prepareStatement(query)) {
          
          preparedStatement.setInt(1, customerId);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
              System.out.println("Customer deleted successfully.");
          } else {
              System.out.println("No customer found with the given ID.");
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }



  public static Customer getCustomerDetails(int customerId) {
    String customerQuery = "SELECT id, name, phoneNo, membership, points FROM Customer WHERE id = ?";
    Customer customer = null;
    try (Connection connection = Customer.connect();PreparedStatement preparedStatement = connection.prepareStatement(customerQuery)){
        preparedStatement .setInt(1, customerId);
        ResultSet rs = preparedStatement .executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String phoneNo = rs.getString("phoneNo");
            boolean membership = rs.getBoolean("membership");
            int points = rs.getInt("points");
            customer = new Customer(id, name, phoneNo, membership, points);  
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return customer;
}


}

