import java.sql.*;

import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class Login {
	
	private Scene scene;
	private BorderPane loginLayout;
	private StackPane title;
	private Stage primaryStage;
	private Label go,query,loginText, emailLabel,passwordLabel,newAccount;
	private TextField emailTF;
	private PasswordField pwPF;
	
	private Button logBtn;
	private VBox loginBox, loginMain, whiteBox;
	private Hyperlink register;


	public Scene loginScene(Stage stage) {
		this.primaryStage = stage;
	    loginScene(); // Sets up the UI components
	    setEvent(stage); // Links actions to the UI components
	    scene = new Scene(loginLayout, 1920, 1080); // Creates the scen
	    return scene;
	}
	



	
	public void loginScene(){
		loginLayout = new BorderPane();
		
        go = new Label("Go Go");
        go.setStyle("-fx-font-size: 75px; -fx-text-fill: white; -fx-font-style: italic; -fx-font-weight: bold;");

        go.setTranslateX(-20);
        go.setTranslateY(0);

        query = new Label("Query");
        query.setStyle("-fx-font-size: 100px; -fx-text-fill: #f69a2d; -fx-font-style: italic; -fx-font-weight: bold;");

        query.setTranslateX(20);
        query.setTranslateY(50);

        title = new StackPane();
        title.getChildren().addAll(go, query);
        title.setAlignment(Pos.CENTER);

        loginText = new Label("Login");
        loginText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

        HBox sep = new HBox();
        sep.setMaxWidth(245);
        sep.setMinHeight(2);
        sep.setStyle("-fx-background-color: black;");

        emailLabel = new Label("Email");
        emailTF = new TextField();
        emailTF.setMaxWidth(300);

        passwordLabel = new Label("Password");
        pwPF = new PasswordField();
        pwPF.setMaxWidth(300);

        logBtn = new Button("Login");
        logBtn.setStyle("-fx-background-color: #f69a2d; -fx-text-fill: white; -fx-font-weight: bold;");
        logBtn.setPrefWidth(300);

        register = new Hyperlink("Here!");
        register.setStyle("-fx-font-weight: bold;");

        newAccount = new Label("Are you new? Register");
        HBox reg = new HBox(1, newAccount, register);
        reg.setAlignment(Pos.CENTER);

        loginBox = new VBox(10, loginText, sep, emailLabel, emailTF, passwordLabel, pwPF, logBtn, reg);
        loginBox.setPadding(new Insets(20));
        loginBox.setAlignment(Pos.TOP_LEFT);

        whiteBox = new VBox(loginBox);
        whiteBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 10, 0.5, 0, 0);");
        whiteBox.setMaxWidth(400);
        whiteBox.setMaxHeight(400);
        whiteBox.setAlignment(Pos.CENTER);

        loginMain = new VBox(50, title, whiteBox);
        loginMain.setAlignment(Pos.CENTER);
        loginMain.setPadding(new Insets(50));
        
        
        loginLayout.setCenter(loginMain);
        loginLayout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1B1A20, #323345);");

	
	}
	
	public void setEvent(Stage stage){
		
		register.setOnAction(e -> stage.setScene(new Register().registerScene(primaryStage)));
		
        logBtn.setOnAction(e -> {
            String email = emailTF.getText();
            String password = pwPF.getText();
            if (email.isEmpty() || password.isEmpty()) {
                showError("Invalid Login", "Log in failed", "Please fill in all fields");}
            else {
                try {
                    if (GogoQueryDatabase.validateLogin(email, password)) {
                        String role = GogoQueryDatabase.userRole(email);
                        UserEmail.getInstance().setEmail(email);
                        if ("Shopper".equals(role)) {
                            stage.setScene(new Home().homeScene(stage));}
                        else if ("Manager".equals(role)) {
                            new Manager(stage);}}
                    else {
                        showError("Invalid Login", "Wrong Credentials", "You entered a wrong email or password");}
                } catch (SQLException ex) {
                    ex.printStackTrace();}
                }
            }
        );
		
		
		
	}

	
	


	
	   private void showError(String Title, String Header, String Message){
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle(Title);
	        alert.setHeaderText(Header);
	        alert.setContentText(Message);
	        alert.showAndWait();
	        }
	

	
	
}
