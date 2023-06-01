import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import static javafx.scene.text.Font.font;
import javafx.scene.text.FontWeight;

//Class that contains all input taken from the user and that is displayed to the user + all methods.
public class St_DB_Connector {  
    TextField Sname;
    DatePicker DPick;
    Slider SGPA;
    Label GPAValue;
    TextField Search=new TextField();
    Button SaveBtn;
    ObservableList<Student> StudentOList = FXCollections.observableArrayList();
    TableView table;
    Label Status;
    Label Status_S;
    
// method to invoke mySQL to add a student based on the user's input.
    public void addStudent() {
        try {
            Connection conn = connectDB();
            String sql = "insert into StudentsTBL_Omar_Mohammed(FullName,DateOfBirth,GPA) values(?,?,?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, Sname.getText());
            Date dob = Date.valueOf(DPick.getValue());
            pStmt.setDate(2, dob);
            pStmt.setFloat(3, (float)(Math.round(SGPA.getValue()*100)/100.0));
            int i = pStmt.executeUpdate();
            pStmt.close();
            conn.close();
        } catch (SQLException e) {
            Status.setText("- Error :" + e.getMessage());
        }
        

    }
    
    //Method to change GPALabel's color based on the value of SGPA's value.
    public Color setGPAColor(double gpa){
          if (gpa>=0&gpa<=1){
                return Color.RED;
            }else if(gpa>1&&gpa<=2){
                return Color.rgb(247, 166, 5);
            }else if(gpa>2&&gpa<=3){
                return Color.rgb(230, 222, 21);
            }else return Color.rgb(6, 214, 17);
    }
    
    // method to check if Sname or Search textfields value contains any number or special characters.
    public boolean isNameValid(String fname) {
       
        boolean Status;
        
            if (fname.matches("^[ A-Za-z]+$")) {
                Status = true;
            } else {
                Status = false;
                
            }
            return Status;
        }
    
    //method to check all input when clicking the Save Button in the Add Student Tab.
    public void checkInput(){
        Sname.setBackground(Background.fill(Color.WHITE));
        DPick.getEditor().setBackground(Background.fill(Color.WHITE));
        Status.setText("");
                LocalDate currentDate = java.time.LocalDate.now();
                
        if(Sname.getText().equals("")){
                Sname.setBackground(Background.fill(Color.rgb(255, 0, 0, 0.1)));
                Status.setTextFill(Color.RED);
                Status.setFont(font("Verdana",FontWeight.BOLD,12));
                Status.setText("- Name mustn't be null\n");
            }if(!(isNameValid(Sname.getText()))&&!Sname.getText().equals("")){
                Sname.setBackground(Background.fill(Color.rgb(255, 0, 0, 0.1)));
                Status.setTextFill(Color.RED);
                Status.setFont(font("Verdana",FontWeight.BOLD,12));
                Status.setText("- Name must only contain alphabet letters\n");
            }if(DPick.getValue()==null){
                DPick.getEditor().setBackground(Background.fill(Color.rgb(255, 0, 0, 0.1)));
                Status.setTextFill(Color.RED);
                Status.setFont(font("Verdana",FontWeight.BOLD,12));
                Status.setText(Status.getText()+"- Date can't be Empty\n");
            }if(DPick.getValue()!=null&&DPick.getValue().isAfter(currentDate)){
                DPick.getEditor().setBackground(Background.fill(Color.rgb(255, 0, 0, 0.1)));
                Status.setTextFill(Color.RED);
                Status.setFont(font("Verdana",FontWeight.BOLD,12));
                Status.setText(Status.getText()+"- Date can't be in the future\n");
            }if (isNameValid(Sname.getText())&&DPick.getValue()!=null&&!DPick.getValue().isAfter(currentDate)){
                addStudent();
                Status.setTextFill(Color.GREEN);
                Status.setFont(font("Verdana",FontWeight.BOLD,12));
                Status.setText(Sname.getText()+" Has been Added.");
                Sname.setText("");
                Sname.setBackground(Background.fill(Color.WHITE));
                DPick.setValue(null);
                DPick.setBackground(Background.fill(Color.WHITE));
                SGPA.setValue(0.0);
                GPAValue.setTextFill(Color.RED);
            }
        
    }
    
    // method to create the table and add all columns before adding data into the table.
    public void createTable(){
       
       TableColumn idColumn = new TableColumn("ID");
       TableColumn nameColumn = new TableColumn("Full Name");
       TableColumn DOBColumn = new TableColumn("Date Of Birth");
       TableColumn GPAColumn = new TableColumn("GPA");
       table.getColumns().addAll(idColumn,nameColumn,DOBColumn,GPAColumn);
       
       idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
       DOBColumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
       GPAColumn.setCellValueFactory(new PropertyValueFactory<>("GPA"));
       table.setItems(StudentOList);
        

        
    }
    
    //method that invokes mySQL to view All records of the table.
    public void tableView() {
                    
            
        try {
            Connection conn = connectDB();
            String sql = "select * from StudentsTBL_Omar_Mohammed";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            List<Student> StudentList = new ArrayList<Student>();
            while(rs.next()){
                Student s = new Student();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setDOB(rs.getString(3));
                s.setGPA(rs.getFloat(4));
                StudentList.add(s);
            }
            StudentOList.clear();
            StudentOList.addAll(StudentList);
            if(StudentOList.isEmpty()){
                Status_S.setTextFill(Color.RED);
                Status_S.setFont(font("Verdana",FontWeight.BOLD,12));
                Status_S.setText("- Table is Empty!");
            }else{
                Status_S.setText("");
            }
            
            pStmt.close();
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //method that takes input from Search textfield and invokes mySQL to search table and view it based on the input.
    public void tableSearch(String name){
        try {
            Connection conn = connectDB();
            String sql = "select * from StudentsTBL_Omar_Mohammed where FullName like ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            ResultSet rs = pStmt.executeQuery();
            ResultSetMetaData rd = rs.getMetaData();
            List<Student> StudentList = new ArrayList<Student>();
            while(rs.next()){
                Student s = new Student();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setDOB(rs.getString(3));
                s.setGPA(rs.getFloat(4));
                StudentList.add(s);
            }
            StudentOList.clear();
            StudentOList.addAll(StudentList);
            if(StudentOList.isEmpty()){
                Status_S.setTextFill(Color.RED);
                Status_S.setFont(font("Verdana",FontWeight.BOLD,12));
                Status_S.setText("- "+Search.getText()+" Is Not In The Table");
            }
            
            pStmt.close();
            rs.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //method to check input when clicking the Search Button in the Browse Student Table Tab.
    public void checkSearch(){
            Status_S.setText("");
            Search.setBackground(Background.fill(Color.WHITE));
            
                if(Search.getText().equals("")){
                    tableView();
                }else{
                    if(isNameValid(Search.getText())){
                   tableSearch("%"+Search.getText()+"%"); 
                }
                    else{
                        Search.setBackground(Background.fill(Color.rgb(255, 0, 0, 0.1)));
                        Status_S.setTextFill(Color.RED);
                        Status_S.setFont(font("Verdana",FontWeight.BOLD,12));
                        Status_S.setText("- Name must only contain alphabet letters");
                    }
                }
    }
    
    //method to connect to the database which is invoked in every method that invokes mySQL.
    public Connection connectDB() { 
        try {
            String url = "jdbc:mysql://localhost:3306/StudentsDB_GUI_Abouellil_Soqati";
            String username = "Abouellil_Soqati";
            String password = "omar9780O@";
           

            Connection con = DriverManager.getConnection(url, username, password); 

            return con; 
        } catch (SQLException e) {
           
            Status.setText("- Error :" + e.getMessage());
            throw new IllegalStateException("Can't cpnnect to the Database !!", e);
        }
    }
}
