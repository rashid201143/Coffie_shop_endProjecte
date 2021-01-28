package android.wagday.com.coffie_shop_endproject.model;

public class Product
{
    private int id;
    private String name;
    private int price;
    private int size_id;
    private int dep_id;
    private String img1;
    private String img2;
    private String img3;
    private String other_details;
    public Product() {}

    public Product(int id, String name, int price, int size_id, int dep_id, String img1,String img2,String img3, String other_details) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.size_id = size_id;
        this.dep_id = dep_id;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.other_details = other_details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public int getDep_id() {
        return dep_id;
    }

    public void setDep_id(int dep_id) {
        this.dep_id = dep_id;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }
    public String getImg3() {
        return img3;
    }

    public void setImg3(String img1) {
        this.img3 = img1;
    }
    public String getImg2() {
        return img2;
    }

    public void setImg2(String img1) {
        this.img2 = img1;
    }

    public String getOther_details() {
        return other_details;
    }

    public void setOther_details(String other_details) {
        this.other_details = other_details;
    }


}
