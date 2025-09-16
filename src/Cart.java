import java.sql.SQLException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Cart {
	    private String email = UserEmail.getInstance().getEmail();
	    private Label totalAmount;
	    private Label productCounter;
	    private HBox productCounterBox;
	    private ListView<getCart> viewProduct;

	    public Cart(Stage primaryStage) throws SQLException {
	        String username = email.split("@")[0];

	        Label usernameLabel = new Label(username);
	        usernameLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: #f69a2d; -fx-font-style: italic;");

	        Label cartLabel = new Label("'s Cart");
	        cartLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: white; -fx-font-style: italic;");

	        HBox cartTitle = new HBox(usernameLabel, cartLabel);
	        cartTitle.setAlignment(Pos.CENTER_LEFT);
	        cartTitle.setPadding(new Insets(20));
	        cartTitle.setSpacing(10);

	        VBox listView = new VBox(productListViewWithCount());
	        listView.setAlignment(Pos.TOP_LEFT);
	        listView.setMinWidth(850);

	        Label billing = new Label("Billing summary");
	        billing.setStyle("-fx-font-size: 15px; -fx-text-fill: #B6A8A7; -fx-font-weight: bold;");

	        Label total = new Label("Total : ");
	        total.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

	        totalAmount = new Label("$" + GogoQueryDatabase.totalPrice(email));
	        totalAmount.setStyle("-fx-font-size: 25px; -fx-text-fill: #FFAD1F; -fx-font-weight: bold;");

	        HBox totalBox = new HBox(total, totalAmount);
	        totalBox.setAlignment(Pos.CENTER_LEFT);
	        totalBox.setSpacing(10);

	        Label taxLabel = new Label("*Tax and delivery cost included");
	        taxLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #655E63; -fx-font-weight: bold;");

	        HBox wLine = new HBox();
	        wLine.setStyle("-fx-background-color: #80808F;");
	        wLine.setMinHeight(2);
	        wLine.setMaxWidth(Double.MAX_VALUE);

	        Button checkoutBtn = new Button("Checkout Items");
	        checkoutBtn.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #f69a2d; -fx-background-radius: 5px;");
	        checkoutBtn.setMinWidth(275);
	        checkoutBtn.setOnAction(e -> {
	            try {
	                boolean confirm = showConfirmation("Checkout Confirmation", "Are you sure you want to checkout your cart?", "Please confirm your choice.");
	                if (confirm) {
	                    GogoQueryDatabase.checkout(email);
	                    updateProductCount(productCounter, productCounterBox);
	                    updateTotalPrice(totalAmount);
	                    ObservableList<getCart> updatedCartItems = GogoQueryDatabase.getCart(email);
	                    viewProduct.setItems(updatedCartItems);
	                    showInfo();}
	            } catch (SQLException ex) {
	                ex.printStackTrace();}});

	        VBox billingBox = new VBox(10, billing, totalBox, taxLabel);
	        billingBox.setAlignment(Pos.CENTER_LEFT);

	        VBox summaryBox = new VBox(10, billingBox, wLine, checkoutBtn);
	        summaryBox.setAlignment(Pos.CENTER);
	        summaryBox.setPadding(new Insets(20));
	        summaryBox.setStyle("-fx-background-color: #303243; -fx-background-radius: 10;");
	        summaryBox.setMinWidth(350);
	        summaryBox.setMaxHeight(200);

	        HBox productView = new HBox(30, listView, summaryBox);
	        productView.setAlignment(Pos.TOP_CENTER);
	        productView.setPadding(new Insets(20));

	        
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

	        title.setOnMouseClicked(event -> {
	            try {
	            	primaryStage.setScene(new Home().homeScene(primaryStage));
	            } catch (Exception e) {
	                e.printStackTrace();}});

	        TextField search = new TextField();
	        search.setPromptText("Search items in GoGoQuery Store");
	        search.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #545877");
	        search.setMinWidth(750);
	        search.setMinHeight(30);

	        Button searchBut = new Button("Search");
	        searchBut.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #7278B2; -fx-border-radius: 10");
	        searchBut.setMinHeight(30);

	        StackPane searchPane = new StackPane();
	        searchPane.getChildren().addAll(search, searchBut);
	        StackPane.setAlignment(searchBut, Pos.CENTER_RIGHT);
	        StackPane.setMargin(searchBut, new Insets(0, 0, 0, 0));

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

	        Button logoutBtn = new Button("Logout");
	        logoutBtn.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #FF2121;");
	        logoutBtn.setMinHeight(30);
	        logoutBtn.setOnAction(e ->{
	            primaryStage.close();
	            new Main().start(primaryStage);});

	        navbar.getChildren().addAll(titleSearch, cartBtn, logoutBtn);
	        
	        VBox root = new VBox(navbar, cartTitle, productView);
	        root.setAlignment(Pos.TOP_CENTER);
	        root.setSpacing(20);

	        GridPane r = new GridPane();
	        r.addRow(0, root);
	        r.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1B1A20, #323345);");
	        r.setAlignment(Pos.TOP_CENTER);

	        Scene scene = new Scene(r, 1920, 1080);
	        primaryStage.setTitle("My Cart");
	        primaryStage.setScene(scene);
	        primaryStage.show();}


	    public VBox productListViewWithCount() throws SQLException {
	        Label productCounterStart = new Label("Showing ");
	        productCounterStart.setStyle("-fx-font-size: 14px; -fx-text-fill: #707387; -fx-font-weight: bold;");


	        productCounter = new Label(String.valueOf(GogoQueryDatabase.getCart(email).size()));
	        productCounter.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFAD1F; -fx-font-weight: bold;");

	        Label productCounterEnd = new Label(" products");
	        productCounterEnd.setStyle("-fx-font-size: 14px; -fx-text-fill: #707387; -fx-font-weight: bold;");

	        productCounterBox = new HBox(productCounterStart, productCounter, productCounterEnd);
	        productCounterBox.setSpacing(5);
	        productCounterBox.setPadding(new Insets(10, 0, 10, 0));
	        productCounterBox.setAlignment(Pos.CENTER_LEFT);

	        viewProduct = new ListView<>();
	        ObservableList<getCart> cartItems;

	        try {
	            cartItems = GogoQueryDatabase.getCart(email);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            cartItems = FXCollections.observableArrayList();}

	        viewProduct.setMinWidth(850);
	        viewProduct.setMinHeight(750);
	        viewProduct.setStyle("-fx-background-color: transparent;");
	        viewProduct.setItems(cartItems);

	        Label emptyMessage = new Label("Your cart is empty!");
	        emptyMessage.setStyle("-fx-font-size: 24px; -fx-text-fill: #707387; -fx-font-style: italic;");
	        VBox placeholderBox = new VBox(emptyMessage);
	        placeholderBox.setSpacing(10);
	        placeholderBox.setPadding(new Insets(20, 0, 0, 20));
	        placeholderBox.setAlignment(Pos.TOP_LEFT);
	        placeholderBox.setStyle("-fx-background-color: transparent;");

	        viewProduct.setPlaceholder(placeholderBox);

	        viewProduct.setCellFactory(param -> new ListCell<>() {
	            @Override
	            protected void updateItem(getCart cartItem, boolean empty) {
	                super.updateItem(cartItem, empty);

	                if (empty || cartItem == null) {
	                    setText(null);
	                    setGraphic(null);
	                    setStyle("-fx-background-color: transparent;");}
	                else {
	                    Region imagePlaceholder = new Region();
	                    imagePlaceholder.setPrefSize(150, 150);
	                    imagePlaceholder.setStyle("-fx-background-color: #666; -fx-background-radius: 10;");

	                    Label nameLabel = new Label(cartItem.getName());
	                    nameLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold;");

	                    Label priceLabel = new Label("$" + cartItem.getPrice());
	                    priceLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: #FFAD1F; -fx-font-weight: bold;");

	                    VBox productDetails = new VBox(5, nameLabel, priceLabel);
	                    productDetails.setAlignment(Pos.CENTER_LEFT);

	                    int stock;
	                    try {
	                        stock = GogoQueryDatabase.itemStock(cartItem.getUid());
	                    } catch (SQLException e) {
	                        throw new RuntimeException(e);}

	                    Spinner<Integer> quantitySpinner = new Spinner<>(0, stock, cartItem.getQty());
	                    quantitySpinner.setPrefSize(75, 30);
	                    quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
	                        try {
	                            if (newValue == 0) {
	                            	GogoQueryDatabase.removeItem(email, cartItem.getUid());
	                                viewProduct.getItems().remove(cartItem);
	                                viewProduct.refresh();
	                                updateProductCount(productCounter, productCounterBox);
	                                updateTotalPrice(totalAmount);}
	                            else {
	                            	GogoQueryDatabase.updateQuantity(email, cartItem.getUid(), newValue);
	                                updateTotalPrice(totalAmount);}

	                        } catch (SQLException e) {
	                            e.printStackTrace();}});

	                    Button removeButton = new Button("x");
	                    removeButton.setAlignment(Pos.CENTER);
	                    removeButton.setMinWidth(30);
	                    removeButton.setStyle("-fx-font-size: 12px; -fx-background-color: #FF2121; -fx-text-fill: white; -fx-font-weight: bold;");
	                    removeButton.setOnAction(event -> {
	                        try {
	                            boolean confirm = showConfirmation("Item Removal Confirmation", "Do you want to remove this item from your cart?", "Please confirm your choice.");
	                            if (confirm) {
	                            	GogoQueryDatabase.removeItem(email, cartItem.getUid());
	                                viewProduct.getItems().remove(cartItem);
	                                viewProduct.refresh();
	                                updateProductCount(productCounter, productCounterBox);
	                                updateTotalPrice(totalAmount);}
	                        } catch (SQLException e) {
	                            e.printStackTrace();}});

	                    VBox qtyBox = new VBox(50, removeButton, quantitySpinner);
	                    qtyBox.setAlignment(Pos.CENTER_RIGHT);

	                    HBox productLayout = new HBox(10, imagePlaceholder, productDetails, qtyBox);
	                    productLayout.setAlignment(Pos.CENTER_LEFT);
	                    HBox.setHgrow(productDetails, Priority.ALWAYS);
	                    productLayout.setPadding(new Insets(10));
	                    productLayout.setStyle("-fx-background-color: #303243; -fx-padding: 10; -fx-spacing: 10;");

	                    productLayout.setMinHeight(150);

	                    setGraphic(productLayout);
	                    setStyle("-fx-background-color: transparent;");}}});

	        VBox root = new VBox();
	        root.setAlignment(Pos.TOP_LEFT);

	        if (!cartItems.isEmpty()) {
	            root.getChildren().add(productCounterBox);}
	        root.getChildren().add(viewProduct);

	        return root;}

	    private void updateProductCount(Label productCounter, HBox productCounterBox) {
	        try {
	            int count = GogoQueryDatabase.getCart(email).size();
	            productCounter.setText(String.valueOf(count));
	            if (count == 0) {
	                ((VBox) productCounterBox.getParent()).getChildren().remove(productCounterBox);}
	        } catch (SQLException e) {
	            e.printStackTrace();
	            productCounter.setText("0");}}

	    private void updateTotalPrice(Label totAmount) {
	        try {
	            double totalPrice = GogoQueryDatabase.totalPrice(email);
	            totAmount.setText("$" + totalPrice);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            totAmount.setText("$0.00");}}


	    private boolean showConfirmation(String Title, String Header, String Message){
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	        alert.setTitle(Title);
	        alert.setHeaderText(Header);
	        alert.setContentText(Message);
	        Optional<ButtonType> result = alert.showAndWait();
	        return result.isPresent() && result.get() == ButtonType.OK;}

	    private void showInfo(){
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Transaction Information");
	        alert.setHeaderText("Transaction success!");
	        alert.setContentText("Your order is now in queue.");
	        alert.showAndWait();
	        
	    }
	    



	}

