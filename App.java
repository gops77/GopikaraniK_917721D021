import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;


public class App  extends Frame{

     Map<String,List<Bill>> dateBill ;
     static Map<Integer,Customer>clist=new HashMap<>();
     static Map<String,Product>plist=new HashMap<>();
     Button adminButton, employeeButton;
     static String datetemp="July 14";

     public App() {
         adminButton = new Button("Administrator");
         adminButton.setBounds(250, 150, 150, 50);
         employeeButton = new Button("Employee");
         employeeButton.setBounds(100, 200, 150, 50);
 
         add(adminButton);
         add(employeeButton);
 
         adminButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new checkAdminFrame();
             }
         });
 
         employeeButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new checkEmployeeFrame();
             }
         });
 
         setLayout(null);
         setSize(1000, 1000);
         setTitle("Supermarket Application");
         setVisible(true);
         addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    class checkAdminFrame extends Frame{
        TextField tf1,tf2;
         Button b1;
         checkAdminFrame(){
            
             tf1=new TextField();
             tf1.setBounds(200,100,200,50);
             add(new Label("Enter User name:")).setBounds(50, 100, 150, 30);
             tf2=new TextField();
             tf2.setBounds(200,200,200,50);
             add(new Label("Enter Password:")).setBounds(50, 200, 150, 30);
             b1=new Button("Submit");
             b1.setBounds(200, 300, 100,30);
             add(tf1);
             add(tf2);
             add(b1);
             
             b1.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                    if(tf2.getText().equals("123")&&tf1.getText().equals("admin")){
                    new AdminFrame();
                    
                }
                else
                  new popuperror();
                   tf1.setText("");
                   tf2.setText("");
                  
                 }
             });
             setLayout(null);
             setSize(800, 800);
             setTitle("Security Check Admin");
             setVisible(true);
     
             addWindowListener(new WindowAdapter() {
                 public void windowClosing(WindowEvent e) {
                     dispose();
                 }
             });
    
         }
    
    
     }  

     class checkEmployeeFrame extends Frame{
        TextField tf1,tf2;
         Button b1;
         checkEmployeeFrame(){
            
             tf1=new TextField();
             tf1.setBounds(200,100,200,50);
             add(new Label("Enter Username:")).setBounds(50, 100, 150, 30);
             tf2=new TextField();
             tf2.setBounds(200,200,200,50);
             add(new Label("Enter Password:")).setBounds(50, 200, 150, 30);
             b1=new Button("Submit");
             b1.setBounds(200, 300, 100,30);
             add(tf1);
             add(tf2);
             add(b1);
             
             b1.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                    if(tf2.getText().equals("321")&&tf1.getText().equals("employee1")){
                        new EmployeeFrame();
                        
                    }
                    else
                      new popuperror();
                       tf1.setText("");
                       tf2.setText("");
                   
                 }
             });
             setLayout(null);
             setSize(800, 800);
             setTitle("Security check Employee");
             setVisible(true);
     
             addWindowListener(new WindowAdapter() {
                 public void windowClosing(WindowEvent e) {
                     dispose();
                 }
             });
    
         }
    
    
     }  
     class popuperror extends Frame{
        popuperror(){
            add(new Label("Enter valid Username and password!!!")).setBounds(50, 100, 200, 50);
            setLayout(null);
             setSize(200, 200);
             setTitle("Security check Employee");
             setVisible(true);
     
             addWindowListener(new WindowAdapter() {
                 public void windowClosing(WindowEvent e) {
                     dispose();
                 }
             });
        }
     }
    
    


    public static void main(String[] args) {
        new App();
        
        
    }
}

