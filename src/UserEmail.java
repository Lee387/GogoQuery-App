
public class UserEmail {
    private static UserEmail instance;
    private String email;

    public static UserEmail getInstance(){
        if(instance == null){
            instance = new UserEmail();}
        return instance;}

    public void setEmail(String email){
        this.email = email;}

    public String getEmail(){
        return email;}
}
