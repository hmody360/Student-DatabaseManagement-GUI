


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import static javafx.scene.text.Font.font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

//This Class implements GUI featuers of the program.
public class Student_GUI extends Application {
    St_DB_Connector s = new St_DB_Connector();
 
//Method that invokes the main method to launch the program and contains all GUI components.    
 @Override
    public void start(Stage primaryStage) {
        
        TabPane TopBar = new TabPane(); //Define TabPane
        //------------------------------------------------------------------------------------------------------------------
        Label ourNames = new Label("===================================================\n"
                + "Participating Students     |        SID        |  Group  |            Email Address            |\n"
                                 + "Mohammed Sami Soqati |  441010207  |     2      |  s441010207@st.uqu.edu.sa  |\n"
                                 + "Omar Amjad Abouellil     |  441018030  |     2      |  s441018030@st.uqu.edu.sa  |\n"
                                 + "==================================================="); //names to be displayed on StudentAdd Tab
                                    
        Label ourNames2 = new Label("  ===================================================\n"
                + "  Participating Students     |        SID        |  Group  |            Email Address            |\n"
                                 + "  Mohammed Sami Soqati |  441010207  |     2      |  s441010207@st.uqu.edu.sa  |\n"
                                 + "  Omar Amjad Abouellil     |  441018030  |     2      |  s441018030@st.uqu.edu.sa  |\n"
                                 + "  ==================================================="); //names to be displayed on TableSearch Tab
        ourNames.setAlignment(Pos.CENTER);
        //------------------------------------------------------------------------------------------------------------------
        //Define all Panes in the Add Tab.
        BorderPane StudentAddPaneContainer = new BorderPane(); //table pane to contain Student add components and our names.
        HBox OurNamesPane = new HBox(); //HBox that will contain our names at the top.
        OurNamesPane.getChildren().add(ourNames); //adding our names to the HBox.
        GridPane StudentAddPane = new GridPane(); //define pane for Student Additon
        StudentAddPaneContainer.setTop(OurNamesPane); //setting OurNames pane at the top of the borderpane
        StudentAddPaneContainer.setCenter(StudentAddPane); //setting the studentAddpane in the middle of the border pane
        StudentAddPaneContainer.setTranslateX(180); //setting the postion of the borderpane in the middle
        StudentAddPaneContainer.setTranslateY(25); //setting the postion of the borderpane in the middle
        StudentAddPaneContainer.getCenter().setTranslateY(30); //setting the postion of the borderpane in the middle
        StudentAddPane.setAlignment(Pos.CENTER); //setting the postion of the StudentAddPane components in the middle
        //------------------------------------------------------------------------------------------------------------------
        //Define all components of adding a student.
        s.Sname = new TextField("");
        s.Sname.setPromptText("Enter Student's Full name"); //the hint text of text field
        s.Sname.setBorder(Border.stroke(Color.BLACK)); // add border
        s.Sname.setMinWidth(290);
        s.DPick = new DatePicker();
        s.DPick.setPromptText("Select Date of Birth"); //the hint text of date picker
        s.DPick.setBorder(Border.stroke(Color.BLACK)); // add border
        s.DPick.setMinWidth(290);
        s.DPick.setEditable(false); // make date picker uneditable by keyboard
        
        
        s.SGPA = new Slider(0.0, 4.0, 0.0); //slider will start from 0.0 and maximum value will be 4.0
        s.GPAValue = new Label("");
        s.GPAValue.setTextFill(Color.RED); // defult color for slider's label
        s.GPAValue.setFont(font("Verdana",FontWeight.BOLD,16));
        
        // connect the value of the SGPA slider to the value of GPAValue Label.
        s.GPAValue.textProperty().bind(Bindings.format("%.2f",s.SGPA.valueProperty()));
        
        s.SGPA.maxWidth(4.0);
        s.SGPA.minWidth(0.0);
        // enable the marks
        s.SGPA.setShowTickMarks(true);
        // enable the Labels
        s.SGPA.setShowTickLabels(true);
        // set Major tick unit
        s.SGPA.setMajorTickUnit(.5);
        
        Button SaveBtn = new Button("Save"); // creat button save
        SaveBtn.setMinWidth(50);
        SaveBtn.setTranslateX(100);
        Button ExitBtnAdd = new Button("Exit"); // creat button exit
        ExitBtnAdd.setMinWidth(50);
        ExitBtnAdd.setTranslateX(200);
        s.Status = new Label(""); // label for display the status of input user
        //------------------------------------------------------------------------------------------------------------------
        //Add all components of Add Student in the Pane.
        StudentAddPane.setAlignment(Pos.CENTER);
        StudentAddPane.add(new Label("Name:"), 0, 0);
        StudentAddPane.add(s.Sname, 1, 0);
        StudentAddPane.add(new Label("Date of Birth:"), 0, 1);
        StudentAddPane.add(s.DPick, 1, 1);
        StudentAddPane.add(new Label("GPA:"), 0, 2);
        StudentAddPane.add(s.SGPA, 1, 2);
        StudentAddPane.add(s.GPAValue, 2, 2);
        StudentAddPane.add(SaveBtn, 0, 4);
        StudentAddPane.add(ExitBtnAdd,1,4);
        StudentAddPane.add(s.Status, 1, 5);
        
        // using Hgap and Vgap
        StudentAddPane.setHgap(10);
        StudentAddPane.setVgap(10);
        StudentAddPane.setPadding(new Insets(10, 10, 10, 10));
        //------------------------------------------------------------------------------------------------------------------
        //Define all components of Browse table Tab.
        VBox SearchBarPane = new VBox(7);
        SearchBarPane.setPadding(new Insets(10,0,10,10));
        SearchBarPane.setAlignment(Pos.CENTER);
        
        s.Search = new TextField("");
        s.Search.setBorder(Border.stroke(Color.BLACK));
        s.Search.setMaxWidth(390);
        s.Search.setPromptText("Please enter a name to search for - \"don't write name to view all data\"");  //the hint text of text field
        Button SearchBtn = new Button("Search"); // creat button search
        SearchBtn.setMinWidth(80);
        Button RefreshBtn = new Button("Refresh"); // creat button refresh
        RefreshBtn.setMinWidth(80);
        Button ExitBtnView = new Button("Exit"); // creat button exit
        ExitBtnView.setMinWidth(80);
        s.Status_S = new Label("");
        s.Status_S.setPadding(new Insets(10,10,10,10));
        //Add all components of search Student in the Pane.
        SearchBarPane.getChildren().add(s.Search);
        SearchBarPane.getChildren().add(SearchBtn);
        SearchBarPane.getChildren().add(RefreshBtn);
        SearchBarPane.getChildren().add(ExitBtnView);
        SearchBarPane.getChildren().add(s.Status_S);
        //------------------------------------------------------------------------------------------------------------------
        //define panes that will be added to Browsing students Tab
        BorderPane TableSearchPane = new BorderPane(); 
        SplitPane TableSearchContainer = new SplitPane();
        TableSearchContainer.setDividerPositions(0.555f,0.5f);
        StackPane TablePane = new StackPane();
        TablePane.setPadding(new Insets(10,10,10,10));
        //------------------------------------------------------------------------------------------------------------------
        // Define the table view that will show up in the browsing students Tab.
         s.table = new TableView();
         //------------------------------------------------------------------------------------------------------------------
         //Adding components to the Tabs and defining the Tabs
         TablePane.getChildren().add(s.table);
        TableSearchPane.setTop(ourNames2);
        TableSearchPane.setCenter(SearchBarPane);
        TableSearchContainer.getItems().addAll(TableSearchPane,TablePane);

        Tab StudentAdd = new Tab("Add Student", new Pane(StudentAddPaneContainer));
        Tab TableSearch = new Tab("Browse all students", new Pane(TableSearchContainer));
        StudentAdd.setClosable(false);
        TableSearch.setClosable(false);
        
        
        
        TopBar.getTabs().add(StudentAdd);
        TopBar.getTabs().add(TableSearch);
        
        VBox TopContainer = new VBox(TopBar);
        
        s.createTable(); //invoke table creation method.
        //------------------------------------------------------------------------------------------------------------------
        //Lambda expressions to event handle in the program.
        s.SGPA.setOnMouseReleased(e->{
        s.SGPA.setValue(s.SGPA.valueProperty().get());
        s.GPAValue.setTextFill(s.setGPAColor(s.SGPA.getValue()));
        });
        
        SaveBtn.setOnAction(e-> s.checkInput());
        
        SearchBtn.setOnAction(e-> s.checkSearch());
        
        RefreshBtn.setOnAction(e-> s.checkSearch());
        
        TableSearch.setOnSelectionChanged(e->s.tableView());
        ExitBtnAdd.setOnAction(e->primaryStage.close());
        ExitBtnView.setOnAction(e->primaryStage.close());
        //------------------------------------------------------------------------------------------------------------------
        //Setting the scene and putting it in the primaryStage.
        Scene scene = new Scene(TopContainer, 770, 450);
        primaryStage.setTitle("Student Database Managment System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
// main method.
    public static void main(String[] args) {
        launch(args);
    }
    
}
