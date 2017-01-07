package myclass;


public class Food {
    private String idFood, nameFood, linkImg;
    private int price;

    public Food() {
    }

    public Food(String idFood, String nameFood, String linkImg, int price) {
        this.idFood = idFood;
        this.nameFood = nameFood;
        this.linkImg = linkImg;
        this.price = price;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setFood(Food f) {
        this.idFood = f.idFood;
        this.nameFood = f.nameFood;
        this.linkImg = f.linkImg;
        this.price = f.price;
    }

    @Override
    public String toString() {
        return "Food{" +
                "idFood='" + idFood + '\'' +
                ", nameFood='" + nameFood + '\'' +
                ", linkImg='" + linkImg + '\'' +
                ", price=" + price +
                '}';
    }
}
