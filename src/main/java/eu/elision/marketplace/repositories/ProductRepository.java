package eu.elision.marketplace.repositories;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
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
     * Find all products from given vendor id
     *
     * @param vendor given vendor
     * @return the products of given vendor
     */
    Collection<Product> findProductsByVendor(Vendor vendor);
}