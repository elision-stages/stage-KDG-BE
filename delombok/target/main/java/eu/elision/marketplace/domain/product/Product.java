package eu.elision.marketplace.domain.product;

import eu.elision.marketplace.domain.product.category.attributes.value.DynamicAttributeValue;
import eu.elision.marketplace.domain.users.Vendor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This product contains the info of a product
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double price;
    @ManyToOne
    private Vendor vendor;
    private String description;
    @ElementCollection
    private List<String> images;
    @OneToMany
    private List<DynamicAttributeValue<?>> attributes;

    public Product() {
        this.images = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public double getPrice() {
        return this.price;
    }

    @SuppressWarnings("all")
    public Vendor getVendor() {
        return this.vendor;
    }

    @SuppressWarnings("all")
    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings("all")
    public List<String> getImages() {
        return this.images;
    }

    @SuppressWarnings("all")
    public List<DynamicAttributeValue<?>> getAttributes() {
        return this.attributes;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setPrice(final double price) {
        this.price = price;
    }

    @SuppressWarnings("all")
    public void setVendor(final Vendor vendor) {
        this.vendor = vendor;
    }

    @SuppressWarnings("all")
    public void setDescription(final String description) {
        this.description = description;
    }

    @SuppressWarnings("all")
    public void setImages(final List<String> images) {
        this.images = images;
    }

    @SuppressWarnings("all")
    public void setAttributes(final List<DynamicAttributeValue<?>> attributes) {
        this.attributes = attributes;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        if (Double.compare(this.getPrice(), other.getPrice()) != 0) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$vendor = this.getVendor();
        final Object other$vendor = other.getVendor();
        if (this$vendor == null ? other$vendor != null : !this$vendor.equals(other$vendor)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$images = this.getImages();
        final Object other$images = other.getImages();
        if (this$images == null ? other$images != null : !this$images.equals(other$images)) return false;
        final Object this$attributes = this.getAttributes();
        final Object other$attributes = other.getAttributes();
        if (this$attributes == null ? other$attributes != null : !this$attributes.equals(other$attributes)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $price = Double.doubleToLongBits(this.getPrice());
        result = result * PRIME + (int) ($price >>> 32 ^ $price);
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
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

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "Product(id=" + this.getId() + ", price=" + this.getPrice() + ", vendor=" + this.getVendor() + ", description=" + this.getDescription() + ", images=" + this.getImages() + ", attributes=" + this.getAttributes() + ")";
    }

    @SuppressWarnings("all")
    public Product(final Long id, final double price, final Vendor vendor, final String description, final List<String> images, final List<DynamicAttributeValue<?>> attributes) {
        this.id = id;
        this.price = price;
        this.vendor = vendor;
        this.description = description;
        this.images = images;
        this.attributes = attributes;
    }
}
