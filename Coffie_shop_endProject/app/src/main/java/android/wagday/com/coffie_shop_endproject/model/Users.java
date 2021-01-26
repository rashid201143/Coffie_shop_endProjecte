package android.wagday.com.coffie_shop_endproject.model;

public class Users
{    private int id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String password;
    private String nameLog;
    private String imges;
    private String bloack_full;
    private String block_like;
    private String block_comment;




    public String getNameLog() {
        return nameLog;
    }

    public void setNameLog(String nameLog) {
        this.nameLog = nameLog;
    }

    public Users(int id, String first_name, String last_name, String phone, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
   // public Users()

    public Users(int id, String first_name, String last_name, String phone, String email, String password, String nameLog,String imges) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.nameLog = nameLog;
        this.imges=imges;
    }

    public Users(String first_name, String last_name, String phone, String email, String nameLog, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.nameLog=nameLog;
        this.password = password;
    }

    public Users(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getBloack_full() {
        return bloack_full;
    }

    public void setBloack_full(String bloack_full) {
        this.bloack_full = bloack_full;
    }

    public String getBlock_like() {
        return block_like;
    }

    public void setBlock_like(String block_like) {
        this.block_like = block_like;
    }

    public String getBlock_comment() {
        return block_comment;
    }

    public void setBlock_comment(String block_comment) {
        this.block_comment = block_comment;
    }

    public String getImges() {
        return imges;
    }

    public void setImges(String imges) {
        this.imges = imges;
    }
}