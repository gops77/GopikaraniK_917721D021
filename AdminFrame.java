
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class AdminFrame extends Frame {
  
    Button addProductButton, salesAnalysisButton, addChangeDateButton;
    Button deleteoptionbutton, purchaseanalysisbutton, viewInventoryButton;

    public AdminFrame() {
        addProductButton = new Button("Add New Product");
        addProductButton.setBounds(100, 100, 150, 50);
        salesAnalysisButton = new Button("Sales Analysis");
        salesAnalysisButton.setBounds(100, 200, 150, 50);
        addChangeDateButton = new Button("Change Date");
        addChangeDateButton.setBounds(100, 300, 150, 50);
        deleteoptionbutton = new Button("Delete Options");
        deleteoptionbutton.setBounds(100, 400, 150, 50);
        purchaseanalysisbutton = new Button("Purchase Analysis");
        purchaseanalysisbutton.setBounds(100, 500, 150, 50);
        viewInventoryButton = new Button("View Inventory");
        viewInventoryButton.setBounds(100, 600, 150, 50);

    
        add(addProductButton);
        add(salesAnalysisButton);
        add(addChangeDateButton);
        add(deleteoptionbutton);
        add(purchaseanalysisbutton);
        add(viewInventoryButton);

      
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductFrame();
            }
        });

        salesAnalysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new printSalesAnalysis();
            }
        });

        addChangeDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new changeDateFrame();
            }
        });

      
        deleteoptionbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new deleteoptions();
            }
        });

        purchaseanalysisbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new DeleteProductFrame();
            }
        });

        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              new stocklist();
            }
        });

        setLayout(null);
        setSize(400, 700); 
        setTitle("Administrator");
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    }

    class deleteoptions extends Frame {
        Button button1, button2, button3;
    
        public deleteoptions() {
            button1 = new Button("Product Delete");
            button2 = new Button("Customer Delete");
            button3 = new Button("Bill Delete");

            add(button1);
            add(button2);
            add(button3);
      
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ProductDelete();
                }
            });
    
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new CustomerDelete();
                }
            });
    
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   new BillDelete();
                }
            });

            setLayout(new FlowLayout());
            setTitle("DeleteOptions");
            setSize(400, 200);
            setVisible(true);
    
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }
    }

    class CustomerDelete extends Frame {
        TextField idField;
        Button submitButton;
        TextArea resultArea;
    
        public CustomerDelete() {
            Label idLabel = new Label("Customer ID:");
            idLabel.setBounds(50, 50, 100, 30);
            idField = new TextField();
            idField.setBounds(160, 50, 150, 30);
    
            submitButton = new Button("Submit");
            submitButton.setBounds(320, 50, 80, 30);
    
            resultArea = new TextArea();
            resultArea.setBounds(50, 100, 350, 150);
    
            add(idLabel);
            add(idField);
            add(submitButton);
            add(resultArea);
    
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int customerId = Integer.parseInt(idField.getText());
                  
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/billingsystem", "root", "08669");
                         PreparedStatement stmt = connection.prepareStatement("DELETE FROM Customer WHERE id = ?")) {
                        stmt.setInt(1, customerId);
                        int rowsAffected = stmt.executeUpdate();
                        if (rowsAffected > 0) {
                            resultArea.setText("Customer deleted successfully.");
                        } else {
                            resultArea.setText("Customer ID not found.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        resultArea.setText("Error deleting customer.");
                    }
                }
            });
    
            setLayout(null);
            setSize(450, 300);
            setTitle("Delete Customer");
            setVisible(true);

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }
}

class ProductDelete extends Frame {
    TextField idField;
    Button submitButton;
    TextArea resultArea;

    public ProductDelete() {
        Label idLabel = new Label("Product ID:");
        idLabel.setBounds(50, 50, 100, 30);
        idField = new TextField();
        idField.setBounds(160, 50, 150, 30);

        submitButton = new Button("Submit");
        submitButton.setBounds(320, 50, 80, 30);

        resultArea = new TextArea();
        resultArea.setBounds(50, 100, 350, 150);

        add(idLabel);
        add(idField);
        add(submitButton);
        add(resultArea);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int productId = Integer.parseInt(idField.getText());

                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/billingsystem", "root", "08669");
                     PreparedStatement stmt = connection.prepareStatement("DELETE FROM Product WHERE id = ?")) {
                    stmt.setInt(1, productId);
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        resultArea.setText("Product deleted successfully.");
                    } else {
                        resultArea.setText("Product ID not found.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error deleting product.");
                }
            }
        });

        setLayout(null);
        setSize(450, 300);
        setTitle("Delete Product");
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}


class BillDelete extends Frame {
    TextField idField;
    Button submitButton;
    TextArea resultArea;

    public BillDelete() {
        Label idLabel = new Label("Bill ID:");
        idLabel.setBounds(50, 50, 100, 30);
        idField = new TextField();
        idField.setBounds(160, 50, 150, 30);

        submitButton = new Button("Submit");
        submitButton.setBounds(320, 50, 80, 30);

        resultArea = new TextArea();
        resultArea.setBounds(50, 100, 350, 150);

        add(idLabel);
        add(idField);
        add(submitButton);
        add(resultArea);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int billId = Integer.parseInt(idField.getText());
                try (Connection connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/billingsystem", "root", "08669");
                PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM Billproduct WHERE billNo = ?");PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM Bill WHERE billNo = ?")) {
                    stmt1.setInt(1, billId);
                    stmt2.setInt(1, billId);
                    int rowsAffected1 = stmt1.executeUpdate();
                    int rowsAffected2 = stmt2.executeUpdate();
                    if (rowsAffected1 > 0&&rowsAffected2>0) {
                        resultArea.setText("Bill deleted successfully.");
                    } else {
                        resultArea.setText("Bill ID not found.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    resultArea.setText("Error deleting bill.");
                }
            }
        });

        setLayout(null);
        setSize(450, 300);
        setTitle("Delete Bill");
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}


    class  changeDateFrame extends Frame {
        TextField tf1,tf2;
        Button b1;
        public changeDateFrame(){
           
            tf1=new TextField();
            tf1.setBounds(200,100,200,50);
            add(new Label("Enter the date to be Changed:")).setBounds(50, 100, 150, 30);
            tf2=new TextField();
            tf2.setBounds(200,200,200,50);
            add(new Label("Enter the Month to be Changed:")).setBounds(50, 200, 150, 30);
            b1=new Button("Submit");
            b1.setBounds(200, 300, 100,30);
            add(tf1);
            add(tf2);
            add(b1);
            
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                  App.datetemp=tf2.getText()+" "+tf1.getText();
                  tf1.setText("");
                  tf2.setText("");
                  new popupsuccess();
                }
            });
            setLayout(null);
            setSize(800, 800);
            setTitle("Change Date");
            setVisible(true);
    
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });

        }
    }

    class AddProductFrame extends Frame {
        TextField idField, nameField, priceField, quantityField, taxField;
        Button submitButton;
        

        public AddProductFrame() {
            idField = new TextField();
            idField.setBounds(150, 50, 200, 30);
            nameField = new TextField();
            nameField.setBounds(150, 100, 200, 30);
            priceField = new TextField();
            priceField.setBounds(150, 150, 200, 30);
            quantityField = new TextField();
            quantityField.setBounds(150, 200, 200, 30);
            taxField = new TextField();
            taxField.setBounds(150, 250, 200, 30);
            submitButton = new Button("Submit");
            submitButton.setBounds(150, 300, 200, 30);
            add(new Label("ID:")).setBounds(50, 50, 100, 30);
            add(idField);
            add(new Label("Name:")).setBounds(50, 100, 100, 30);
            add(nameField);
            add(new Label("Price:")).setBounds(50, 150, 100, 30);
            add(priceField);
            add(new Label("Quantity:")).setBounds(50, 200, 100, 30);
            add(quantityField);
            add(new Label("Tax:")).setBounds(50, 250, 100, 30);
            add(taxField);
            add(submitButton);
    
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Product p=new Product(nameField.getText(),Integer.valueOf(priceField.getText()),Integer.valueOf(quantityField.getText()),Integer.valueOf(taxField.getText()));
                    idField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    taxField.setText("");;
                    Product.insertProduct(p);
                    new popupsuccess();
                }
            });
    
            setLayout(null);
            setSize(400, 400);
            setTitle("Add New Product");
            setVisible(true);
    
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    dispose();
                }
            });
        }
}


class popupsuccess extends Frame {
    Label l;
    public popupsuccess(){
        l=new Label("SUCCESS!!!");
        add(l);
        setLayout(new FlowLayout());
        setSize(200, 200);
        setTitle("SUCCESS MESSAGE");
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}