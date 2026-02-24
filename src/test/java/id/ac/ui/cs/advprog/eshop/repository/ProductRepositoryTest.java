package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-4600-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-4600-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9896");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct1 = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct1.getProductId());

        assertTrue(productIterator.hasNext());
        Product savedProduct2 = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct2.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditSuccess() {
        Product product = new Product();
        product.setProductId("6f123");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("6f123");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(20);

        Product updateResult = productRepository.update(updatedProduct);
        assertNotNull(updateResult, "Update should return updated product when found");

        Product result = productRepository.findById("6f123");
        assertNotNull(result);
        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditNotFound() {
        Product product = new Product();
        product.setProductId("existing-id");
        product.setProductName("Exists");
        product.setProductQuantity(1);
        productRepository.create(product);

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("wrong-id");
        nonExistentProduct.setProductName("Ghost");
        nonExistentProduct.setProductQuantity(999);

        Product result = productRepository.update(nonExistentProduct);

        assertNull(result, "Update should return null when product ID not found");
        // Pastikan data yang ada tidak berubah
        assertNotNull(productRepository.findById("existing-id"));
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId("delete-me");
        product.setProductName("To Delete");
        product.setProductQuantity(1);
        productRepository.create(product);

        productRepository.delete("delete-me");

        Product result = productRepository.findById("delete-me");
        assertNull(result);
    }

    @Test
    void testDeleteNonExistent_whenRepoEmpty() {
        productRepository.delete("non-existent-id");

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testFindByIdNotFound_whenRepoNotEmpty() {
        Product product = new Product();
        product.setProductId("exists");
        product.setProductName("A");
        product.setProductQuantity(1);
        productRepository.create(product);

        Product result = productRepository.findById("missing");

        assertNull(result, "findById should return null when ID not found even if repo not empty");
    }

    @Test
    void testDeleteNonExistent_whenRepoNotEmpty_shouldNotRemoveAnything() {
        Product product = new Product();
        product.setProductId("exists");
        product.setProductName("A");
        product.setProductQuantity(1);
        productRepository.create(product);

        productRepository.delete("missing");

        // Pastikan yang ada tetap ada
        assertNotNull(productRepository.findById("exists"));
    }
}