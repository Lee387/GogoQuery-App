import java.sql.SQLException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class Home {
	    private  Stage primaryStage;
	    private String email = UserEmail.getInstance().getEmail();
	    private String selectedCategory = "Select a category";
	    private TextField search = new TextField();
	    private ListView<Product> listProduct = new ListView<>();
	    private Label productCounter;
	    private Label descCounter;

	    private Scene scene;
	    private GridPane gp;

		public Scene homeScene(Stage stage){
			this.primaryStage = stage;
			homePage(); 
		    scene = new Scene(gp, 1920, 1080); 
		    return scene;
		}
		
	   


		public void homePage(){
	    	String username = email.split("@")[0];
	        Label welcomeLabel = new Label("Welcome,");
	        welcomeLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white; -fx-font-style: italic;");
	        Label usernameLabel = new Label(username);
	        usernameLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: #f69a2d; -fx-font-style: italic;");
	        HBox welcomeUser = new HBox(10, welcomeLabel, usernameLabel);
	        welcomeUser.setAlignment(Pos.CENTER_LEFT);
	        welcomeUser.setPadding(new Insets(10));

	        Label FilterLabel = new Label("Filter");
	        FilterLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #707387; -fx-font-weight: bold;");

	        Label categoryLabel = new Label("Category");
	        categoryLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #707387; -fx-font-weight: bold;");

	        Button applyBtn = new Button("Apply");
	        applyBtn.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #FF9C24; -fx-border-radius: 10;");
	        applyBtn.setMinHeight(30);
	        applyBtn.setMinWidth(70);
	        applyBtn.setOnAction(e -> {
	        	refreshList(selectedCategory, search.getText().trim().toLowerCase());});

	        ComboBox<String> categoryComboBox = new ComboBox<>();
	        categoryComboBox.setPromptText("Select a category");
	        categoryComboBox.setMinHeight(30);
	        categoryComboBox.setStyle("-fx-font-size: 10px; -fx-text-fill: #949BC7; -fx-font-weight: bold; -fx-background-color: #545877; -fx-border-radius: 10;");

	        categoryComboBox.setButtonCell(new ListCell<>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty || item == null) {
	                    setText(null);}
	                else {
	                    setText(item);
	                    setStyle("-fx-text-fill: #949BC7;");}}});

	        try {
	            List<String> categories = GogoQueryDatabase.itemCategory();
	            categoryComboBox.getItems().addAll(categories);
	        } catch (SQLException e) {
	            e.printStackTrace();}

	        categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue != null) {
	                selectedCategory = newValue;}});

	        categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue != null) {
	                categoryComboBox.setPromptText(null);}});
	        
	        
	        HBox button = new HBox(10, categoryComboBox, applyBtn);

	        VBox categoryBox = new VBox(10, categoryLabel, button);
	        categoryBox.setAlignment(Pos.CENTER_LEFT);
	        categoryBox.setPadding(new Insets(10));
	        categoryBox.setStyle("-fx-background-color: #303243; -fx-border-radius: 10; -fx-background-radius: 10;");
	        categoryBox.setMaxSize(250, 140);
	        categoryBox.setMinHeight(100);

	        VBox filterBox = new VBox(10, FilterLabel, categoryBox);
	        filterBox.setAlignment(Pos.TOP_LEFT);
	        filterBox.setPadding(new Insets(10));

	        try {
	        	productCounter = new Label(String.valueOf(GogoQueryDatabase.Products().size()) + " ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        productCounter.setStyle("-fx-font-size: 14px; -fx-text-fill: #EF9A24; -fx-font-weight: bold;");

	        descCounter = new Label();
	        descCounter.setStyle("-fx-font-size: 14px; -fx-text-fill: #707387; -fx-font-weight: bold;");
	        refreshList(selectedCategory, search.getText().trim().toLowerCase());

	        Label proCountStart = new Label("Showing ");
	        proCountStart.setStyle("-fx-font-size: 14px; -fx-text-fill: #707387; -fx-font-weight: bold;");

	        HBox proCountBox = new HBox(proCountStart, productCounter, descCounter);
	        proCountBox.setAlignment(Pos.CENTER_LEFT);
	        proCountBox.setPadding(new Insets(10));

	        listProduct.setMinWidth(850);
	        listProduct.setMinHeight(750);
	        listProduct.setPadding(new Insets(10));

	        listProduct.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
	        
	        listProduct.setCellFactory(e -> new ListCell<>() {
	            @Override
	            protected void updateItem(Product product, boolean empty) {
	                super.updateItem(product, empty);

	                if (empty || product == null) {
	                    setText(null);
	                    setGraphic(null);
	                    setStyle("-fx-background-color: transparent;");}
	                else {
	                    Region imagePlaceholder = new Region();
	                    imagePlaceholder.setPrefSize(150 , 150);
	                    imagePlaceholder.setStyle("-fx-background-color: #666; -fx-background-radius: 10;");

	                    Label nameLabel = new Label(product.getName());
	                    nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

	                    Label priceLabel = new Label("$" + product.getPrice());
	                    priceLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: #FFAD1F; -fx-font-weight: bold;");

	                    Label stockLabel = new Label(product.getStock() + " Left");
	                    stockLabel.setStyle(
	                            "-fx-font-size: 18px; " + "-fx-text-fill: white; " + "-fx-background-color: #FF2121; " + "-fx-padding: 2; " + "-fx-border-radius: 10; " + "-fx-font-weight: bold;");

	                    HBox priceStock = new HBox(10, priceLabel, stockLabel);
	                    priceStock.setAlignment(Pos.CENTER_LEFT);

	                    VBox productDetails = new VBox(5, nameLabel, priceStock);
	                    productDetails.setAlignment(Pos.CENTER_LEFT);

	                    HBox productLayout = new HBox(10, imagePlaceholder, productDetails);
	                    productLayout.setAlignment(Pos.CENTER_LEFT);
	                    productLayout.setPadding(new Insets(10));
	                    productLayout.setStyle(
	                            "-fx-background-color: #303243; " +  "-fx-padding: 10; " +  "-fx-spacing: 10;");

	                    productLayout.setMinHeight(150);

	                    productLayout.setOnMouseEntered(event ->
	                            productLayout.setStyle("-fx-background-color: #252633;"));

	                    productLayout.setOnMouseExited(event ->
	                            productLayout.setStyle("-fx-background-color: #303243;"));

	                    productLayout.setOnMouseClicked(event -> {
	                        try {
	                            new Detail(product, primaryStage);
	                        } catch (SQLException e) {
	                            e.printStackTrace();}});

	                    VBox productWrapper = new VBox(20,productLayout);
	                    productWrapper.setStyle("-fx-padding: 20;" + "-fx-spacing: 20;");
	                    setGraphic(productLayout);
	                    setStyle("-fx-background-color: transparent;");}}});

	        try {
	            List<Product> availableProducts = GogoQueryDatabase.Products();
	            availableProducts.removeIf(product -> product.getStock() < 1);
	            listProduct.getItems().addAll(availableProducts);
	        } catch (SQLException e) {
	            e.printStackTrace();}
	        
	        VBox productBox = new VBox(proCountBox, listProduct);

	        HBox productCatBox = new HBox(5, filterBox, productBox);
	        productCatBox.setAlignment(Pos.TOP_LEFT);
	        productCatBox.setPadding(new Insets(10));

	        VBox welcomeBox = new VBox(10, welcomeUser, productCatBox);
	        welcomeBox.setAlignment(Pos.TOP_LEFT);

	        HBox navbar = new HBox(10);
	        navbar.setAlignment(Pos.CENTER_LEFT);
	        navbar.setPadding(new Insets(10));
	        navbar.setStyle("-fx-background-color: #303243; -fx-background-radius: 50 0 0 50;");
	        navbar.setMaxHeight(100);
	        navbar.setMaxWidth(1150);

	        Label gogo = new Label("Go Go");
	        gogo.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-style: italic; -fx-font-weight: bold;");
	        gogo.setTranslateY(-5);
	        gogo.setTranslateX(17);

	        Label query = new Label("Query");
	        query.setStyle("-fx-font-size: 25px; -fx-text-fill: #f69a2d; -fx-font-style: italic; -fx-font-weight: bold;");
	        query.setTranslateY(7);
	        query.setTranslateX(34);

	        StackPane title = new StackPane();
	        title.getChildren().addAll(gogo, query);
	        title.setAlignment(Pos.CENTER);

	        search.setPromptText("Search items in GoGoQuery Store");
	        search.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #545877");
	        search.setMinWidth(750);
	        search.setMinHeight(30);

	        Button searchBtn = new Button("Search");
	        searchBtn.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #7278B2; -fx-border-radius: 10");
	        searchBtn.setMinHeight(30);
	        searchBtn.setOnAction(e -> {
	        	refreshList(selectedCategory, search.getText().trim().toLowerCase());});

	        StackPane searchPane = new StackPane();
	        searchPane.getChildren().addAll(search, searchBtn);
	        StackPane.setAlignment(searchBtn, Pos.CENTER_RIGHT);
	        StackPane.setMargin(searchBtn, new Insets(0, 0, 0, 0));

	        HBox titleSearch = new HBox(50, title, searchPane);

	        HBox line = new HBox();
	        line.setMaxHeight(50);
	        line.setMinWidth(2);
	        line.setStyle("-fx-background-color: #42455E;");

	        HBox batas = new HBox(55, titleSearch, line);
	        batas.setAlignment(Pos.CENTER_LEFT);

	        Button cartBtn = new Button("My Cart");
	        cartBtn.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: transparent;");
	        cartBtn.setMinHeight(30);
	        cartBtn.setOnAction(e -> {
	            try {
	                new Cart(primaryStage);
	            } catch (SQLException ex) {
	                ex.printStackTrace();}});

	        Button logoutBtn = new Button("Logout");
	        logoutBtn.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #FF2121;");
	        logoutBtn.setMinHeight(30);
	        logoutBtn.setOnAction(e ->{
	                primaryStage.close();
	                new Main().start(primaryStage);});

	        navbar.getChildren().addAll(batas, cartBtn, logoutBtn);
	        
	        
	        
	        gp = new GridPane();
	        gp.setPadding(new Insets(10));
	        gp.setHgap(10);
	        gp.setVgap(50);
	        gp.setAlignment(Pos.TOP_CENTER);
	        gp.setMaxWidth(1920);
	        gp.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1B1A20, #323345);");
	        gp.addRow(0, navbar);
	        gp.addRow(1, welcomeBox);

	        
	        primaryStage.setTitle("Home");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	    }
		    	
		    
	    
	    

	    private void refreshList(String category, String searchText) {
	        try {
	            ObservableList<Product> filter = GogoQueryDatabase.filterProducts(category, searchText);
	            listProduct.getItems().setAll(filter);

	            int count = filter.size();

	            TextFlow descFlow = new TextFlow();

	            boolean isSearchTextEmpty = (searchText == null || searchText.isEmpty());
	            boolean isCategoryDefault = (category == null || category.equals("Select a category"));

	            if (!isSearchTextEmpty && !isCategoryDefault) {
	            	descFlow.getChildren().addAll(
	                        DefaultText("for '"),
	                        coloredText(searchText),
	                        DefaultText("' and in '"),
	                        coloredText(category),
	                        DefaultText("' category"));}
	            else if (!isSearchTextEmpty) {
	            	descFlow.getChildren().addAll(
	                        DefaultText("for '"),
	                        coloredText(searchText),
	                        DefaultText("'"));}
	            else if (!isCategoryDefault) {
	            	descFlow.getChildren().addAll(
	                        DefaultText("in '"),
	                        coloredText(category),
	                        DefaultText("' category"));}
	            else {
	            	descFlow.getChildren().add(DefaultText("products"));}

	            productCounter.setText(count + " ");
	            descCounter.setText("");
	            descCounter.setGraphic(descFlow);
	        } catch (SQLException e) {
	            e.printStackTrace();}}

	    private Text coloredText(String text) {
	        Text colored = new Text(text);
	        colored.setStyle("-fx-fill: #EF9A24;");
	        return colored;}

	    private Text DefaultText(String text) {
	        Text defaultText = new Text(text);
	        defaultText.setStyle("-fx-fill: #707387;");
	        return defaultText;
	        
	    }
	    
	    
	    
	
	    
	    
	    
	    
	    
	    
	    }

