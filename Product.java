import java.sql.*;

public class Product {
       static int i=0;
      int id;
      String name;
      int price ;
      int availableQuantity ;
      double tax ;
     
      Product(String name , int price, int availableQuantity,double tax ){
              this.name = name ;
              this.price = price ;
              this.availableQuantity = availableQuantity;
              this.tax=tax;
              this.id=++i;
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

    public static void insertProduct(Product product) {
        String sql = "INSERT INTO Product(name, price, availableQuantity, tax) VALUES(?,?,?,?)";

        try (Connection conn = Product.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.name);
            pstmt.setInt(2, product.price);
            pstmt.setInt(3, product.availableQuantity);
            pstmt.setDouble(4, product.tax);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateProduct(int productId, String fieldToUpdate, int newValue) {
      String query = null;

      if ("price".equalsIgnoreCase(fieldToUpdate)) {
          query = "UPDATE product SET price = ? WHERE id = ?";
      } 
      else if ("availablequantity".equalsIgnoreCase(fieldToUpdate)) {
          query = "UPDATE product SET availableQuantity = ? WHERE id = ?";
      }
       else {
          System.out.println("Invalid field to update.");
          return;
      }

      try (Connection conn = Product.connect();
           PreparedStatement preparedStatement = conn.prepareStatement(query)) {
          
          preparedStatement.setInt(1, newValue);
          preparedStatement.setInt(2, productId);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
              System.out.println("Product updated successfully.");
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }

  public  static void deleteProduct(int productId) {
      String query = "DELETE FROM product WHERE id = ?";

      try (Connection conn = Product.connect();
      PreparedStatement preparedStatement = conn.prepareStatement(query)) {
          
          preparedStatement.setInt(1, productId);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
              System.out.println("Product deleted successfully.");
          } else {
              System.out.println("No product found with the given ID.");
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }
 public static String getProductDetailById(int productId, String detail) {
        String query = "";
        switch (detail.toLowerCase()) {
            case "name":
                query = "SELECT name FROM product WHERE id = ?";
                break;
            case "price":
                query = "SELECT price FROM product WHERE id = ?";
                break;
            case "tax":
                query = "SELECT tax FROM product WHERE id = ?";
                break;
            case "availablequantity":
                query="SELECT availablequantity FROM product WHERE id = ?";
            break;
            default:
                throw new IllegalArgumentException("Invalid detail requested: " + detail);
        }

        try (Connection conn = Product.connect();
        PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                switch (detail.toLowerCase()) {
                    case "name":
                        return rs.getString("name");
                    case "price":
                        return String.valueOf(rs.getInt("price"));
                    case "tax":
                        return String.valueOf(rs.getDouble("tax"));
                    case "availablequantity":
                        return String.valueOf(rs.getInt("availablequantity"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

       
}

