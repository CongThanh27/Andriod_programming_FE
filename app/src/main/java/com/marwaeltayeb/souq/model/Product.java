package com.marwaeltayeb.souq.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    private int productId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("priceold")
    private double priceold;
    @SerializedName("quantity")
    private int productQuantity;
    @SerializedName("sold")
    private int sold;
    @SerializedName("supplier")
    private String productSupplier;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("image")
    private String productImage;
    @SerializedName("description")
    private String description;
    @SerializedName("trademark")
    private String trademark;
    @SerializedName("origin")
    private String origin;
    @SerializedName("sex")
    private String sex;
    @SerializedName("skinproblems")
    private String skinproblems;
    @SerializedName("addtocart")
    private String addtocart;
    @SerializedName("addtofavorite")
    private String addtofavorite;
    @SerializedName("share")
    private int share;
    @SerializedName("rain")
    private int rain;
    @SerializedName("active")
    private int active;
    @SerializedName("isFavourite")
    private int isFavourite;
    @SerializedName("isInCart")
    private int isInCart;
    // Include child Parcelable objects
    private Product mInfo;

    public Product(String productName, double productPrice, double priceold, int productQuantity, String productSupplier, String productCategory, String description, String trademark, String origin, String sex, String skinproblems) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.priceold = priceold;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
        this.description = description;
        this.trademark = trademark;
        this.origin = origin;
        this.sex = sex;
        this.skinproblems = skinproblems;
    }

   public  Product(){}

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public double getPriceold() {
        return priceold;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getSold() {
        return sold;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getDescribe() {
        return description;
    }

    public String getTrademark() {
        return trademark;
    }

    public String getSex() {
        return sex;
    }

    public String getSkinproblems() {
        return skinproblems;
    }

    public String getAddtocart() {
        return addtocart;
    }

    public String getAddtofavorite() {
        return addtofavorite;
    }

    public int getShare() {
        return share;
    }

    public int getRain() {
        return rain;
    }

    public String getOrigin() {
        return origin;
    }
    public int isFavourite() {
        return isFavourite;
    }

    public int isInCart() {
        return isInCart;
    }
    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite ? 1 : 0;
    }

    public void setIsInCart(boolean isInCart) {
        this.isInCart = isInCart ? 1 : 0;
    }

    // Write the values to be saved to the `Parcel`.
    @Override
    public void writeToParcel(Parcel out, int flags) {
        //được sử dụng để đóng gói các giá trị của đối tượng Product vào một Parcel
        out.writeInt(productId);
        out.writeString(productName);
        out.writeDouble(productPrice);
        out.writeDouble(priceold);
        out.writeInt(productQuantity);
        out.writeInt(sold);
        out.writeString(productSupplier);
        out.writeString(productCategory);
        out.writeString(productImage);
        out.writeString(description);
        out.writeString(trademark);
        out.writeString(origin);
        out.writeString(sex);
        out.writeString(skinproblems);
        out.writeString(addtocart);
        out.writeString(addtofavorite);
        out.writeInt(share);
        out.writeInt(rain);
        out.writeInt(active);
        out.writeInt(isFavourite);
        out.writeInt(isInCart);
        out.writeParcelable(mInfo, flags);
    }

    // Retrieve the values written into the `Parcel`.
    private Product(Parcel in) {
        //được sử dụng để tạo ra đối tượng Product từ Parcel
        productId = in.readInt();
        productName = in.readString();
        productPrice = in.readDouble();
        priceold = in.readDouble();
        productQuantity = in.readInt();
        sold = in.readInt();
        productSupplier = in.readString();
        productCategory = in.readString();
        productImage = in.readString();
        description= in.readString();
        trademark= in.readString();
        origin= in.readString();
        sex= in.readString();
        skinproblems= in.readString();
        addtocart= in.readString();
        addtofavorite= in.readString();
        share = in.readInt();
        rain = in.readInt();
        active = in.readInt();
        isFavourite = in.readInt();
        isInCart = in.readInt();
        mInfo = in.readParcelable(Product.class.getClassLoader());
    }
    @Override
    public int describeContents() {
        return 0;
    }
//cho phép đối tượng của lớp này có thể được truyền qua Intent hoặc được lưu trữ và khôi phục lại trạng thái của nó.
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {

        // This simply calls our new constructor and
        // passes along `Parcel`, and then returns the new object!
        //Phương thức tạo createFromParcel(Parcel in) được gọi để tạo đối tượng Product từ Parcel được truyền vào.
        //Parcel ở đây là một đối tượng dùng để đóng gói dữ liệu của đối tượng Product
        //và gửi nó qua các thành phần khác của ứng dụng Android.
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }// trả về một mảng mới của đối tượng Product với kích thước được chỉ định.
    };
}
