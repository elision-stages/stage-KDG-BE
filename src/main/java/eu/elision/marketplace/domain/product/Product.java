package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;

import java.util.ArrayList;
import java.util.List;

/**
 * This product contains the info of a product
 */
public class Product
{
    private double price;
    private Vendor vendor;
    private String description;
    private List<String> images;

    private List<DynamicAttributeValue<?>> attributes;

    public Product() {
        this.images = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    public Product(double price, Vendor vendor, String description, List<String> images, List<DynamicAttributeValue<?>> attributes) {
        this.price = price;
        this.vendor = vendor;
        this.description = description;
        this.images = images;
        this.attributes = attributes;
    }

    public double getPrice() {
        return this.price;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getImages() {
        return this.images;
    }

    public List<DynamicAttributeValue<?>> getAttributes() {
        return this.attributes;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setAttributes(List<DynamicAttributeValue<?>> attributes) {
        this.attributes = attributes;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        if (Double.compare(this.getPrice(), other.getPrice()) != 0) return false;
        final Object this$vendor = this.getVendor();
        final Object other$vendor = other.getVendor();
        if (this$vendor == null ? other$vendor != null : !this$vendor.equals(other$vendor)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$images = this.getImages();
        final Object other$images = other.getImages();
        if (this$images == null ? other$images != null : !this$images.equals(other$images)) return false;
        final Object this$attributes = this.getAttributes();
        final Object other$attributes = other.getAttributes();
        if (this$attributes == null ? other$attributes != null : !this$attributes.equals(other$attributes))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $price = Double.doubleToLongBits(this.getPrice());
        result = result * PRIME + (int) ($price >>> 32 ^ $price);
        final Object $vendor = this.getVendor();
        result = result * PRIME + ($vendor == null ? 43 : $vendor.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $images = this.getImages();
        result = result * PRIME + ($images == null ? 43 : $images.hashCode());
        final Object $attributes = this.getAttributes();
        result = result * PRIME + ($attributes == null ? 43 : $attributes.hashCode());
        return result;
    }

    public String toString() {
        return "Product(price=" + this.getPrice() + ", vendor=" + this.getVendor() + ", description=" + this.getDescription() + ", images=" + this.getImages() + ", attributes=" + this.getAttributes() + ")";
    }
}
