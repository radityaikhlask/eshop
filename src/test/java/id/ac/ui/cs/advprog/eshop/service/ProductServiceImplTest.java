package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository repo;
    private ProductServiceImpl service;

    @BeforeEach
    void setup() {
        repo = mock(ProductRepository.class);
        service = new ProductServiceImpl();
        ReflectionTestUtils.setField(service, "productRepository", repo);
    }

    @Test
    void create_whenProductIdNull_generatesId_andCallsRepository() {
        Product p = new Product();
        p.setProductId(null);
        p.setProductName("Apple");
        p.setProductQuantity(10);

        Product created = service.create(p);

        assertNotNull(created.getProductId(), "ProductId should be generated when null");
        verify(repo, times(1)).create(p);
    }

    @Test
    void create_whenProductIdExists_keepsSameId_andCallsRepository() {
        Product p = new Product();
        p.setProductId("P1");
        p.setProductName("Apple");
        p.setProductQuantity(10);

        Product created = service.create(p);

        assertEquals("P1", created.getProductId());
        verify(repo, times(1)).create(p);
    }

    @Test
    void findAll_convertsIteratorToList() {
        Product p1 = new Product();
        p1.setProductId("P1");
        Product p2 = new Product();
        p2.setProductId("P2");

        when(repo.findAll()).thenReturn(List.of(p1, p2).iterator());

        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("P1", result.get(0).getProductId());
        assertEquals("P2", result.get(1).getProductId());
        verify(repo, times(1)).findAll();
    }

    @Test
    void findById_callsRepository() {
        Product p = new Product();
        p.setProductId("P1");
        when(repo.findById("P1")).thenReturn(p);

        Product found = service.findById("P1");

        assertNotNull(found);
        assertEquals("P1", found.getProductId());
        verify(repo, times(1)).findById("P1");
    }

    @Test
    void update_callsRepositoryUpdate() {
        Product p = new Product();
        p.setProductId("P1");

        service.update(p);

        verify(repo, times(1)).update(p);
    }

    @Test
    void delete_callsRepositoryDelete() {
        service.delete("P1");
        verify(repo, times(1)).delete("P1");
    }
}