import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;


public class Manager {
	    private final StackPane sp;
	    private Label welcome;
	    private Window currentWindow;

	    public Manager(Stage primaryStage) {
	        this.sp = new StackPane();

	        welcome = new Label("Welcome to GoGoQuery Manager 2.0");
	        welcome.setStyle("-fx-font-size: 48px;");
	        welcome.setAlignment(Pos.CENTER);

	        VBox welcomeLayout = new VBox(welcome);
	        welcomeLayout.setAlignment(Pos.CENTER);

	        sp.getChildren().add(welcomeLayout);

	        
	        MenuBar menuBar = new MenuBar();
	        Menu menuPage = new Menu("Menu");

	        MenuItem add = new MenuItem("Add Item");
	        add.setOnAction(e -> addItem());

	        MenuItem manage = new MenuItem("Manage Queue");
	        manage.setOnAction(e -> queueManager());

	        MenuItem logout = new MenuItem("Logout");
	        logout.setOnAction(e -> {
	            primaryStage.close();
	            new Main().start(primaryStage);});

	        menuPage.getItems().addAll(add, manage, logout);
	        menuBar.getMenus().add(menuPage);
	        
	        
	        BorderPane managerLayout = new BorderPane();
	        managerLayout.setTop(menuBar);
	        managerLayout.setCenter(sp);

	        Scene scene = new Scene(managerLayout, 1920, 1080);
	        primaryStage.setTitle("GoGoQuery Manager 2.0");
	        primaryStage.setScene(scene);
	        primaryStage.show();}




	    private void addItem() {
	        if (currentWindow != null) {
	            sp.getChildren().remove(currentWindow);
	        }
	        
	        Window showPopUp = new Window("Add Item");
	        showPopUp.getLeftIcons().clear();
	        showPopUp.setPrefSize(1720 , 880 );
	        showPopUp.setMaxSize(1720 , 880 );
	        
	       
	        VBox addLayout = new VBox(10);
	        addLayout.setAlignment(Pos.CENTER_LEFT);

	        Label title = new Label("Add Item");
	        title.setStyle("-fx-font-size: 48px;");

	        Label name = new Label("Item Name: ");
	        name.setStyle("-fx-font-size: 24px;");
	        TextField nameField = new TextField();
	        nameField.setMinSize(300, 30);

	        Label desc = new Label("Item Desc: ");
	        desc.setStyle("-fx-font-size: 24px;");
	        TextArea descField = new TextArea();
	        descField.setWrapText(true);
	        descField.setPrefSize(300, 200);

	        Label category = new Label("Item Category: ");
	        category.setStyle("-fx-font-size: 24px;");
	        TextField categoryField = new TextField();
	        categoryField.setMinSize(300, 30);

	        Label price = new Label("Item Price: ");
	        price.setStyle("-fx-font-size: 24px;");
	        TextField priceField = new TextField();
	        priceField.setMinSize(300, 30);

	        Label quantity = new Label("Quantity: ");
	        quantity.setStyle("-fx-font-size: 24px;");
	        Spinner<Integer> quantitySpinner = new Spinner<>(1, 300, 1);
	        quantitySpinner.setMinSize(300, 30);

	        Button add = new Button("Add Item");
	        add.setStyle("-fx-background-color: #5CB85C; -fx-text-fill: white; -fx-font-size: 24px;");
	        add.setOnAction(e -> {
	            String itemName = nameField.getText();
	            String itemDesc = descField.getText();
	            String itemCat = categoryField.getText();
	            String priceInp = priceField.getText();
	            int itemQuant = quantitySpinner.getValue();

	            double itemPrice;
	            try {
	                itemPrice = Double.parseDouble(priceInp);
	            } catch (NumberFormatException ex) {
	                showError("Error", "Insert error!", "Item price must be a valid number.");
	                return;
	            }

	            if (itemName.isEmpty() || itemDesc.isEmpty() || itemCat.isEmpty() || itemPrice == 0 || itemQuant == 0) {
	                showError("Error", "Insert error!", "All fields must be filled out.");
	            } else if (itemName.length() < 5 || itemName.length() > 70) {
	                showError("Error", "Insert error!", "Item name must be between 5 and 70 characters.");
	            } else if (itemDesc.length() < 10 || itemDesc.length() > 255) {
	                showError("Error", "Insert error!", "Item description must be between 10 and 255 characters.");
	            } else if (itemPrice < 0.50 || itemPrice > 900000) {
	                showError("Error", "Insert error!", "Item price must be between $0.50 and $900,000.");
	            } else {
	                GogoQueryDatabase.insertItem(itemName, itemCat, itemPrice, itemDesc, itemQuant);
	                showSuccess();
	                nameField.clear();
	                descField.clear();
	                categoryField.clear();
	                priceField.clear();
	                quantitySpinner.getValueFactory().setValue(1);
	            }
	        });

	        GridPane gp = new GridPane();
	        gp.setHgap(20);
	        gp.setVgap(10);
	        gp.addRow(0, name, nameField);
	        gp.addRow(1, desc, descField);
	        gp.addRow(2, category, categoryField);
	        gp.addRow(3, price, priceField);
	        gp.addRow(4, quantity, quantitySpinner);
	        gp.add(add, 1, 5);
	        gp.setAlignment(Pos.CENTER_LEFT);

	        addLayout.getChildren().addAll(title, gp);
	        showPopUp.getContentPane().getChildren().add(addLayout);
	        sp.getChildren().clear();
	        sp.getChildren().add(showPopUp);
	        

	        welcome.setVisible(false);
	        currentWindow = showPopUp;
	    }
	    
	    
	    private void queueManager() {
	    	
	        if (currentWindow != null) {
	            sp.getChildren().remove(currentWindow);
	        }
	    	
	        Window showPopUp = new Window("Queue Manager");
	        showPopUp.getLeftIcons().clear();
	        showPopUp.setPrefSize(1720 , 880 );
	        showPopUp.setMaxSize(1720 , 880 );

	        Label title = new Label("Queue Manager");
	        title.setStyle("-fx-font-size: 48px;");

	        TableView<Transaction> table = new TableView<>();
	        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        table.setPrefSize(1000, 650);

	        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
	        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

	        TableColumn<Transaction, Integer> customerIdCol = new TableColumn<>("Customer ID");
	        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

	        TableColumn<Transaction, String> customerEmailCol = new TableColumn<>("Customer Email");
	        customerEmailCol.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

	        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
	        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

	        TableColumn<Transaction, Double> amountCol = new TableColumn<>("Amount");
	        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

	        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
	        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

	        table.getItems().setAll(GogoQueryDatabase.listTransaction());

	        table.getColumns().addAll(idCol, customerIdCol, customerEmailCol, dateCol, amountCol, statusCol);

	        Button sendBtn = new Button("Send Package");
	        sendBtn.setStyle("-fx-background-color: #5CB85C; -fx-text-fill: white; -fx-font-size: 20px;");
	        sendBtn.setOnAction(e -> {
	            Transaction selected = table.getSelectionModel().getSelectedItem();
	            if(selected == null){
	                showError("Reference Error", "Reference error due to no transaction selected", "Please select a transaction");}
	            else if(!"In Queue".equals(selected.getStatus())){
	                showError("Reference Error", "Reference error due to invalid transaction status", "Please select a transaction that is still \"In Queue\"");}
	            else{
	            	GogoQueryDatabase.updateTransactionStatus(selected.getId(), "Sent");
	                table.getItems().setAll(GogoQueryDatabase.listTransaction());
	                table.getSelectionModel().clearSelection();}});
	        sendBtn.setPadding(new Insets(10));
	        
	        
	        VBox tableLayout = new VBox(table);
	        tableLayout.setAlignment(Pos.CENTER);

	        VBox queueLayout = new VBox(10, title, tableLayout, sendBtn);
	        queueLayout.setAlignment(Pos.CENTER_LEFT);
	        
	        showPopUp.getContentPane().getChildren().add(queueLayout);
	        sp.getChildren().clear();
	        sp.getChildren().add(showPopUp);
	        

	        welcome.setVisible(false);
	        currentWindow = showPopUp;
	        }

	    private void showError(String Title, String Header, String Message){
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle(Title);
	        alert.setHeaderText(Header);
	        alert.setContentText(Message);
	        alert.showAndWait();
	        }

	    private void showSuccess(){
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText("Insert Success!");
	        alert.setContentText("Item added to product catalog.");
	        alert.showAndWait();}
}
