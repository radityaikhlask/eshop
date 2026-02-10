package id.ac.ui.cs.advprog.eshop.repository;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
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
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
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
        productRepository.update(updatedProduct);

        Product result = productRepository.findById("6f123");
        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testEditNotFound() {
        Product product = new Product();
        product.setProductId("existing-id");
        productRepository.create(product);

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("wrong-id");
        nonExistentProduct.setProductName("Ghost");
        Product result = productRepository.update(nonExistentProduct);

        assertNull(result);
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId("delete-me");
        productRepository.create(product);

        productRepository.delete("delete-me");
        Product result = productRepository.findById("delete-me");
        assertNull(result);
    }

    @Test
    void testDeleteNonExistent() {
        productRepository.delete("non-existent-id");
        // Should not throw exception; repository remains empty
        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }
}

