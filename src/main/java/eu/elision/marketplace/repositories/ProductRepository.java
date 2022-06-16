package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

/**
 * Jpa repository for products
 */
public interface ProductRepository extends JpaRepository<Product, Long>
{
    /**
     * Find all products from given vendor id
     *
     * @param id the id of the vendor
     * @return the products of given vendor
     */
    @Query("select p from Product p where p.vendor.id = ?1")
    Collection<Product> findAllByVendorId(Long id);

    /**
     * Find products by the vendor's email
     *
     * @param email the email of the vendor
     * @return a collection of products owned by vendor with given email
     */
    Collection<Product> findByVendorEmail(String email);

}