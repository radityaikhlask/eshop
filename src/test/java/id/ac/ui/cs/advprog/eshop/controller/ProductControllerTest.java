package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerStandaloneTest {

    private MockMvc mockMvc;
    private ProductService service;

    @BeforeEach
    void setup() {
        service = mock(ProductService.class);

        ProductController controller = new ProductController();
        // inject mock into private field "service"
        ReflectionTestUtils.setField(controller, "service", service);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createProductPage_returnsCreateProductView() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createProductPost_redirectsToList_andCallsServiceCreate() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Apple")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service).create(any(Product.class));
    }

    @Test
    void productListPage_returnsProductListView() throws Exception {
        Product p = new Product();
        p.setProductId("P1");
        when(service.findAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void deleteProduct_redirectsToList_andCallsServiceDelete() throws Exception {
        mockMvc.perform(get("/product/delete/P1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("../list"));

        verify(service).delete("P1");
    }

    @Test
    void editProductPage_returnsEditProductView() throws Exception {
        Product p = new Product();
        p.setProductId("P1");
        when(service.findById("P1")).thenReturn(p);

        mockMvc.perform(get("/product/edit/P1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void editProductPost_redirectsToList_andCallsServiceUpdate() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "P1")
                        .param("productName", "Apple")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service).update(any(Product.class));
    }
}