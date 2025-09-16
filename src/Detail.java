import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Detail {
    private Product product;

    public Detail(Product pr, Stage primaryStage) throws SQLException{
        this.product = pr;

        Region placeHolder = new Region();
        placeHolder.setPrefSize(300 , 300);
        placeHolder.setStyle("-fx-background-color: #666; -fx-background-radius: 10;");

        Label nameLabel = new Label(product.getName());
        nameLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: white; -fx-font-weight: bold;");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(383);

        Label price = new Label("$ " + product.getPrice());
        price.setStyle("-fx-font-size: 45px; -fx-text-fill: #f69a2d; -fx-font-weight: bold;");

        Label cat = new Label("Category :");
        cat.setStyle("-fx-font-size: 20px; -fx-text-fill: #707387; -fx-font-weight: bold;");

        Label category = new Label(product.getCategory());
        category.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFAD1F; -fx-font-weight: bold;");

        HBox cats = new HBox(5, cat, category);

        Label itemDetail = new Label("Item Detail");
        itemDetail.setStyle("-fx-font-size: 20px; -fx-text-fill: #FFAD1F; -fx-font-weight: bold;");

        Label specification = new Label("Specification :\n\n"+product.getDesc());
        specification.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-font-weight: bold;");
        specification.setWrapText(true);
        specification.setMaxWidth(383);

        HBox lineDetail = new HBox();
        lineDetail.setStyle("-fx-background-color: #FFAD1F;");
        lineDetail.setMinHeight(2);

        VBox midBox = new VBox(10, nameLabel, price, cats, itemDetail, lineDetail, specification);
        midBox.setAlignment(Pos.TOP_LEFT);

        Label bestSell = new Label("Best Seller");
        bestSell.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: linear-gradient(to right, #B265E2, #34346D); -fx-padding: 5px 10px; -fx-background-radius: 5px;");
        bestSell.setMinWidth(315);
        bestSell.setMinHeight(55);

        Label quantity = new Label("Set item quantity");
        quantity.setStyle("-fx-font-size: 15px; -fx-text-fill: #B6A8A7; -fx-font-weight: bold;");

        Spinner<Integer> qtySpinner = new Spinner<>(1, product.getStock(), 1);
        qtySpinner.setStyle("-fx-font-size: 15px;");
        qtySpinner.setMaxWidth(110);

        Label stock = new Label("Stock :");
        stock.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label curStock = new Label(String.valueOf(product.getStock()));
        curStock.setStyle("-fx-font-size: 18px; -fx-text-fill: #f69a2d; -fx-font-weight: bold;");

        HBox stockBox = new HBox(5, stock, curStock);	

        HBox spinnerBox = new HBox(10, qtySpinner, stockBox);
        spinnerBox.setAlignment(Pos.CENTER_LEFT);

        Button addCart = new Button("Add to Cart");
        addCart.setStyle("-fx-font-size: 15px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #f69a2d; -fx-background-radius: 5px;");
        addCart.setMinWidth(275);
        addCart.setOnAction(e -> {
            try{
                String email = UserEmail.getInstance().getEmail();
                int itemID = product.getId();
                int qty = qtySpinner.getValue();

                String add = ("The item " + product.getName() + " has been added to your cart with the quantity of "+qty+"unit(s).");
                String update = (product.getName() + "is already in your cart ["+ GogoQueryDatabase.getQuantity(email, itemID) +"]. The quantity has been updated to "+ (GogoQueryDatabase.getQuantity(email, itemID)+ qty) +"unit(s).");
                String invalid = ("There are "+ product.getStock() +" units left in stock for this item, and you already have "+ GogoQueryDatabase.getQuantity(email, itemID)+" units in your cart. The quantity in your cart has been adjusted to the maximum available stock.");

                String q = GogoQueryDatabase.addToCart(email, itemID, qty);
                if(q.equals("added")){
                    showInfo("Item Added to Cart", "Item Added Successfully",add);}
                else if(q.equals("updated")){
                    showInfo("Item Already in Cart", "Quantity Updated", update);}
                else if (q.equals("not enough stock")){
                    showInfo("Quantity Exceeds Stock", "Not enough stock available", invalid);}
            }catch (SQLException ex){
                ex.printStackTrace();}});

        VBox qtyBox = new VBox(5, quantity, spinnerBox);
        qtyBox.setAlignment(Pos.CENTER_LEFT);

        VBox shopBox = new VBox(75, qtyBox, addCart);
        shopBox.setAlignment(Pos.CENTER);
        shopBox.setStyle("-fx-background-color: #373745; -fx-padding: 10px; -fx-border-color: #6B6B76; -fx-border-width: 2px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        shopBox.setMinWidth(315);
        shopBox.setMinHeight(220);

        VBox bestBox = new VBox(25, bestSell, shopBox);

        HBox detailPage = new HBox(35, placeHolder, midBox, bestBox);
        detailPage.setMaxSize(1920, 1080);
        detailPage.setAlignment(Pos.TOP_CENTER);

        
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

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #7278B2; -fx-border-radius: 10");
        searchBtn.setMinHeight(30);

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
        
        
        
        VBox detailLayout = new VBox(50, navbar, detailPage);
        detailLayout.setAlignment(Pos.TOP_CENTER);
        detailLayout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1B1A20, #323345);");

        Scene scene = new Scene(detailLayout, 1920, 1080);
        primaryStage.setTitle("ProductDetail");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    

    private void showInfo(String Title, String Header, String Message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Message);
        alert.showAndWait();
    }
}
