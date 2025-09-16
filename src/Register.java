import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Register {

	
    private Scene scene;

	
		private BorderPane registerLayout;
		private StackPane title;
		private Label go,query,registerText,emailLabel,passwordLabel,confpassLabel, dobLabel, genderLabel, oldAccount;
		private TextField emailTF;
		private PasswordField pwPF, pwPFcon;
		private DatePicker dob;
		private RadioButton male, female;
		private ToggleGroup gender;
		private Button regBtn;
		private Hyperlink logins;
	    private Stage primaryStage;
	    private CheckBox agree ;
	    VBox registerMain, regbox, whiteBox;
	
	    String email,password,conPW, conDob, conGender;
		LocalDate dobs;

		public Scene registerScene(Stage stage) {
			this.primaryStage = stage;
			registerPage(); 
		    setEvent(stage); 
		    scene = new Scene(registerLayout, 1920, 1080); 
			return scene; 
		}


	    
	    
	    
	    
	public void registerPage(){
		registerLayout = new BorderPane();
		
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

        registerText = new Label("Register");
        registerText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");

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

        confpassLabel = new Label("Confirm Password");
        pwPFcon = new PasswordField();
        pwPFcon.setMaxWidth(300);

        dobLabel = new Label("Date of Birth");
        dob = new DatePicker();
        dob.setMaxWidth(150);

        genderLabel = new Label("Gender");
        male = new RadioButton("Male");
        female = new RadioButton("Female");

        gender = new ToggleGroup();
        male.setToggleGroup(gender);
        female.setToggleGroup(gender);

        HBox genders = new HBox(10, male, female);
        genders.setAlignment(Pos.CENTER_LEFT);

        agree = new CheckBox("I agree to");
        Hyperlink terms = new Hyperlink("Terms and Conditions");
        HBox termsBox = new HBox(1, agree, terms);
        termsBox.setAlignment(Pos.CENTER_LEFT);

        regBtn = new Button("Register");
        regBtn.setStyle("-fx-background-color: #f69a2d; -fx-text-fill: white; -fx-font-weight: bold;");
        regBtn.setPrefWidth(300);
        
        logins = new Hyperlink("Here!");
        logins.setStyle("-fx-font-weight: bold;");
        
        oldAccount = new Label("Already have an account? Sign in");
        HBox log = new HBox(1, oldAccount, logins);
        log.setAlignment(Pos.CENTER);

        regbox = new VBox(10, registerText, sep, emailLabel, emailTF, passwordLabel, pwPF, confpassLabel, pwPFcon, dobLabel, dob, genderLabel, genders, termsBox, regBtn, log);
        regbox.setPadding(new Insets(20));
        regbox.setAlignment(Pos.TOP_LEFT);

        whiteBox = new VBox(regbox);
        whiteBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 10, 0.5, 0, 0);");
        whiteBox.setMaxWidth(400);
        whiteBox.setMaxHeight(900);
        whiteBox.setAlignment(Pos.CENTER);
        
        
        registerMain = new VBox(50, title, whiteBox);
        registerMain.setAlignment(Pos.CENTER);
        registerMain.setPadding(new Insets(50));
        
        
        registerLayout.setCenter(registerMain);
        registerLayout.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1B1A20, #323345);");
        
	}
	
	
	Circle managerCircle,shopperCircle;
	Label managerLogo, managerRole, managerDesc, shopperLogo, shopperRole,shopperDesc;
	Button managerBtn, shopperBtn;
	Scene selectRolePage;
	
	private void selRole(){
        Label go = new Label("Go Go");
        go.setStyle("-fx-font-size: 75px; -fx-text-fill: white; -fx-font-style: italic; -fx-font-weight: bold;");

        go.setTranslateX(-20);
        go.setTranslateY(0);

        Label query = new Label("Query");
        query.setStyle("-fx-font-size: 100px; -fx-text-fill: #f69a2d; -fx-font-style: italic; -fx-font-weight: bold;");

        query.setTranslateX(20);
        query.setTranslateY(50);

        StackPane title = new StackPane();
        title.getChildren().addAll(go, query);
        title.setAlignment(Pos.CENTER);

        managerCircle = new Circle(50);
        managerCircle.setStyle("-fx-fill: #415261;");

        managerLogo = new Label("m");
        managerLogo.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        StackPane managerProfile = new StackPane(managerCircle, managerLogo);
        
        managerRole = new Label("Manager");
        managerRole.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox mLine = new HBox();
        mLine.setMinWidth(250);
        mLine.setMinHeight(1);
        mLine.setStyle("-fx-background-color: #E3E3E3;");

        managerDesc = new Label("Manage products and deliveries, be the ruler!");

        managerBtn = new Button("Register as Manager");
        managerBtn.setStyle("-fx-background-color: #f69a2d; -fx-text-fill: white; -fx-font-weight: bold;");
        managerBtn.setMinWidth(300);
        managerBtn.setMinHeight(50);
        managerBtn.setOnAction(e ->{
            String role = "Manager";
            email = emailTF.getText();
	        password = pwPF.getText();
	        dobs = dob.getValue();
	        conDob = (dobs != null) ? dobs.toString() : null;
	        conGender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : null;
            try{
                if(GogoQueryDatabase.addUser(conDob, email, password, conPW, role)){
                    showSuccess();
                    primaryStage.setScene(new Login().loginScene(primaryStage));}
                else{
                    showError("Register Failed", "Register Error", "Registration failed, please try again");}}
            catch (SQLException ex){
                showError("Register Failed", "Register Error", "Something went wrong.");}});


        VBox managerBox = new VBox(10, managerProfile, managerRole, mLine , managerDesc, managerBtn);
        managerBox.setAlignment(Pos.CENTER);
        managerBox.setStyle("-fx-background-color: white; -fx-padding: 20;");
        managerBox.setMinWidth(500);

        shopperCircle = new Circle(50);
        shopperCircle.setStyle("-fx-fill: #7A425C;");

        shopperLogo = new Label("s");
        shopperLogo.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        StackPane shopperAvatar = new StackPane(shopperCircle, shopperLogo);

        shopperRole = new Label("Shopper");
        shopperRole.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox sLine = new HBox();
        sLine.setMinWidth(250);
        sLine.setMinHeight(1);
        sLine.setStyle("-fx-background-color: #E3E3E3;");

        shopperDesc = new Label("Search products, manage your cart, go shopping!");

        shopperBtn = new Button("Register as Shopper");
        shopperBtn.setStyle("-fx-background-color: #f69a2d; -fx-text-fill: white; -fx-font-weight: bold;");
        shopperBtn.setMinWidth(300);
        shopperBtn.setMinHeight(50);
        shopperBtn.setOnAction(e ->{
            String role = "Shopper";
            try{
                if(GogoQueryDatabase.addUser(conDob, email, password, conPW, role)){
                	showSuccess();
        			primaryStage.setScene(new Login().loginScene(primaryStage));}
                
                else{
                    showError("Register Failed", "Register Error", "Registration failed, please try again");}}
            catch (SQLException ex){
                showError("Register Failed", "Register Error", "Something went wrong.");}});

        VBox shopperBox = new VBox(10, shopperAvatar, shopperRole, sLine, shopperDesc, shopperBtn);
        shopperBox.setAlignment(Pos.CENTER);
        shopperBox.setStyle("-fx-background-color: white; -fx-padding: 20;");
        shopperBox.setMinWidth(500);

        HBox rolesBox = new HBox(50, managerBox, shopperBox);
        rolesBox.setAlignment(Pos.CENTER);

        VBox mainBox = new VBox(50, title, rolesBox);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(50));
        mainBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 0%, #1B1A20, #323345);");
        
        selectRolePage = new Scene(mainBox , 1920 , 1080);
        ;}
	
	

	
	
	
	
	public void setEvent(Stage stage){
		
		logins.setOnAction(e -> stage.setScene(new Login().loginScene(primaryStage)));

		regBtn.setOnAction(e -> {
	           email = emailTF.getText();
	           password = pwPF.getText();
	           conPW = pwPFcon.getText();
	           dobs = dob.getValue();
	           conDob = (dobs != null) ? dobs.toString() : null;
	           conGender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : null;

	            if(email.isEmpty()){
	                showError("Register Failed", "Register Error", "Email must be filled");}
	            else if(!email.endsWith("@gomail.com")){
	                showError("Register Failed", "Register Error", "Email must end with '@gomail.com'");}

	            else {
	                try {
	                    if(GogoQueryDatabase.checkEmail(email)){
	                        showError("Register Failed", "Register Error", "Email already exists");}}
	                catch (SQLException ex) {
	                    throw new RuntimeException(ex);
	                    }
	                }if(password.isEmpty()){
	                showError("Register Failed", "Register Error", "Password must be filled");
	                }else if(!alphanumeric(password)){
	                showError("Register Failed", "Register Error", "Password must be alphanumeric");
	                }else if(!password.equals(conPW)){
	                showError("Register Failed", "Register Error", "Passwords don't match");
	                } else if(conDob == null){
	                showError("Register Failed", "Register Error", "Please select date of birth");
	                } else if(Period.between(dobs, LocalDate.now()).getYears() < 17){
	                showError("Register Failed", "Register Error", "You must be at least 17 years old");
	                } else if((!male.isSelected() && !female.isSelected())){
	                showError("Register Failed", "Register Error", "Select your gender");
	                } else if(!agree.isSelected()){
	                showError("Register Failed", "Register Error", "You must agree to terms and conditions");
	                } else
						try {
							if(!email.isEmpty() && email.endsWith("@gomail.com") && !GogoQueryDatabase.checkEmail(email) && !password.isEmpty()
									&& alphanumeric(password) && password.equals(conPW) && !(conDob == null) && !(Period.between(dobs, LocalDate.now()).getYears() < 17) 
									&& (male.isSelected() || female.isSelected()) && agree.isSelected() ){
							selRole();
							
							stage.setScene(selectRolePage);}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}});

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
	        alert.setTitle("Register Information");
	        alert.setHeaderText("Register success!");
	        alert.setContentText("please log in with your newly created account.");
	        alert.showAndWait();
	        }
	
	    private boolean alphanumeric(String name) {
	        boolean hasLetter = false;
	        boolean hasDigit = false;
	        for (char c : name.toCharArray()) {
	            if (Character.isLetter(c)) {
	                hasLetter = true;}
	            else if (Character.isDigit(c)) {
	                hasDigit = true;}
	            if (hasLetter && hasDigit) {
	                return true;}}
	        return false;
	        }



}



