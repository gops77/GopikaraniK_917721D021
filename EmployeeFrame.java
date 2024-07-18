import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.sql.*;

public class EmployeeFrame extends Frame {
  
    Button button1, button2, button3;
                public EmployeeFrame() {
                    button1 = new Button("NEW BILL");
                    button2 = new Button("PRINT BILL");
                    button3 = new Button("STOCK LIST");
            
                    add(button1);
                    add(button2);
                    add(button3);
            
                    button1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new newbill();
                        }
                    });
            
                    button2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                           new billprint();
                        }
                    });
            
                    button3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                          new stocklist();
                        }
                    });
            
                    setLayout(new FlowLayout());
                    setTitle("Employee-Main");
                    setSize(400, 200);
                    setVisible(true);
            
                    addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    });
                }
        }

    class stocklist extends Frame {
    TextArea productTextArea;
    Connection conn;

    stocklist() {
        productTextArea = new TextArea(10, 40);
        productTextArea.setEditable(false);
        add(productTextArea);

    
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/billingsystem", "root", "08669");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("ID\tName\tQuantity\tStatus\n");
            sb.append("--------------------------------------------------------------------\n");
    
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT id, name, availableQuantity FROM Product")) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int quantity = rs.getInt("availableQuantity");
                    if(quantity<=5)
                    sb.append(id).append("\t").append(name).append("\t").append(quantity).append("\t").append("Critical Level").append("\n");
                    else
                    sb.append(id).append("\t").append(name).append("\t").append(quantity).append("\t").append("Normal Level").append("\n");
                }
                productTextArea.setText(sb.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        

        setLayout(new FlowLayout());
        setTitle("Product List");
        setSize(500, 300);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            dispose();
        }
    });
    }
    }
    
    class billprint extends Frame {
            TextField textField;
            Button submitButton;
            TextArea textArea;
            Label l;
        
            public billprint() {
                
                textField = new TextField(20);
                submitButton = new Button("Submit");
                textArea = new TextArea(10, 40);
                l=new Label("Bill no.");
               
                l.setBounds(50, 50, 100, 20); 
                textField.setBounds(160, 50, 200, 30);
                submitButton.setBounds(370, 50, 80, 30);
                textArea.setBounds(50, 100, 400, 150);
        
                add(textField);
                add(submitButton);
                add(textArea);
                add(l);
        
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        textArea.setText("");
                       String str= Bill.getBillFromDB(Integer.parseInt(textField.getText()));
                        textArea.setText(str);
                    }
                });
        
                setLayout(null);
                setTitle("Bill Print");
                setSize(500, 300);
                setVisible(true);
        
      
                addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        dispose();
                    }
                });
    }
}

    class newbill extends Frame {
        TextField productField, quantityField;
        TextArea invoiceArea;
        Button addButton, continueButton;
       
        public newbill() {

            productField = new TextField();
            productField.setBounds(150, 50, 200, 30);
            quantityField = new TextField();
            quantityField.setBounds(150, 100, 200, 30);
            invoiceArea = new TextArea();
            invoiceArea.setBounds(50, 150, 300, 200);
            addButton = new Button("Add");
            addButton.setBounds(50, 370, 100, 30);
            continueButton = new Button("Continue Bill");
            continueButton.setBounds(250, 370, 100, 30);
    
            add(new Label("Product:")).setBounds(50, 50, 100, 30);
            add(productField);
            add(new Label("Quantity:")).setBounds(50, 100, 100, 30);
            add(quantityField);
            add(invoiceArea);
            add(addButton);
            add(continueButton);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String product = productField.getText();
                    String quantity = quantityField.getText();
                    int numquantity=Integer.valueOf(quantity);
                    int avaqty=Integer.valueOf(Product.getProductDetailById(Integer.valueOf(product), "availablequantity"));
                    if(avaqty>=Integer.valueOf(quantity)){
                    Product.updateProduct(Integer.valueOf(product),"availableQuantity",avaqty-numquantity);
                    invoiceArea.append(product + " " + quantity + "\n");
                    productField.setText("");
                    quantityField.setText("");
                    }
                    else{
                        new popupinsufficient(avaqty);
                        productField.setText("");
                        quantityField.setText("");
                    }
                }
            });
    
            continueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   
                    new printfunction(invoiceArea);
                     
                }
            });
    
            setLayout(null);
            setSize(400, 500);
            setTitle("Employee - Invoice Printing");
            setVisible(true);
    
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                dispose();
                }
    });
}
}

class printfunction extends Frame {
    TextField tf1,tf21,tf22,tf23;
    Button b1,b2;
    public printfunction(TextArea tf3){
        
        tf1=new TextField();
        tf1.setBounds(200,150,200,50);
        add(new Label("Existing Customer(Enter id):")).setBounds(200, 50, 150, 30);
        add(new Label("New Customer:")).setBounds(500, 50, 150, 30);
        tf21=new TextField();
        tf21.setBounds(500,150,200,50);
        add(new Label("Name:")).setBounds(400, 170, 100, 30);
        tf22=new TextField();
        tf22.setBounds(500,200,200,50);
        add(new Label("Phone number:")).setBounds(400, 220, 100, 30);
        tf23=new TextField();
        tf23.setBounds(500,250,200,50);
        add(new Label("Membership(Y/N):")).setBounds(400, 270, 100, 30);
        
        b1=new Button("Submit existing");
        b1.setBounds(250, 220, 100,30);
        b2=new Button("Submit new");
        b2.setBounds(550, 320, 100,30);
        add(tf1);
        add(tf21);
        add(tf22);
        add(tf23);
        add(b1);
        add(b2);
        
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
              int id=Integer.parseInt(tf1.getText());
                Bill b=new Bill(id, App.datetemp);
                String[] str5=(tf3.getText()).split("\\s");
                for(int i=0;i<str5.length;i+=2){
                    b.productidQuantity.put(Integer.valueOf(str5[i]),Integer.valueOf(str5[i+1]));
                }
                Customer c=Customer.getCustomerDetails(id);
                String name =c.name;
                String pno=c.phoneNo;
                tf1.setText("");
                new finalprint(name,pno,b,c);  
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String name=tf21.getText();
                String pno=tf22.getText();
                String membershipstatus=tf23.getText();
            
                int lastid= Customer.insertCustomer(name,pno,membershipstatus);
                Customer c=Customer.getCustomerDetails(lastid);

                Bill b=new Bill(lastid, App.datetemp);
                String[] str5=(tf3.getText()).split("\\s");
                for(int i=0;i<str5.length;i+=2){
                   
                    b.productidQuantity.put(Integer.valueOf(str5[i]),Integer.valueOf(str5[i+1]));
                   
                }
                tf21.setText("");
                tf22.setText("");
                tf23.setText("");
             
                new finalprint(name,pno,b,c);
            }
        });
        setLayout(null);
        setSize(1000, 1000);
        setTitle("Select Customer");
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }
}


class popupinsufficient extends Frame {
    Label l;
    public popupinsufficient(int qty){
        l=new Label("Insufficient Quantity...Available Quantity:"+qty);
        add(l);
        setLayout(new FlowLayout());
        setSize(1000, 1000);
        setTitle("Warning!!!");
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}


class finalprint extends Frame {
    public finalprint(String name,String pno,Bill b,Customer c){
        File file=new File(App.datetemp+"_"+name);
        FileWriter fw;
        try {
            fw = new FileWriter(file,true);
            fw.write("GENERAL STORES\n");
            fw.write("Customer name :"+name+"     Customer phone:"+pno+"\n");
            fw.write("Item     Quantity     Amount"+"\n");
              LinkedHashMap<Integer , Integer> pq=b.productidQuantity;
              for(Map.Entry<Integer,Integer> e:pq.entrySet()){
                Integer pp=e.getKey();
                int qty=e.getValue();
               
            int sum=Integer.valueOf(Product.getProductDetailById(pp,"price"))*qty;
            double tax=sum*(Double.valueOf(Product.getProductDetailById(pp,"tax"))/100.0);
            fw.write(Product.getProductDetailById(pp,"name")+"              "+qty+"              "+sum+"\n");
            b.totalPrice=b.totalPrice+sum;
            b.totalTax=b.totalTax+tax;
        }
        //
        if(b.totalPrice>1000&&b.totalPrice<3000)
        b.discount=5;
        else if(b.totalPrice>3000)
        b.discount=10;
        fw.write("\n");
        fw.write("TOTAL AMOUNT:"+String.valueOf(b.totalPrice)+"\n");
        double save=(b.totalPrice*(b.discount/100.0));
        b.rewardsdiscount=c.points;
        b.netamount=b.totalPrice-(int)save-(int)b.rewardsdiscount;
        int lastbillno= Bill.addBill(b);
        
        fw.write("TOTAL TAX AMOUNT:"+String.valueOf(b.totalTax)+"\n");
        fw.write("TOTAL SAVINGS:"+String.valueOf(save)+"\n");
        fw.write("TOTAL SAVINGS BY REWARD POINTS:"+String.valueOf(b.rewardsdiscount)+"\n");
        fw.write("NET AMOUNT PAYABLE:"+String.valueOf(b.netamount+"\n"));
        fw.close();

        } catch (IOException e1) {
            
            e1.printStackTrace();
        }
        
    Label l=new Label("Bill saved in text file Sucessfully!!");
    add(l);
    setLayout(new FlowLayout());
    setSize(1000, 1000);
    setTitle("Final Message");
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
