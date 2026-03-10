package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

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

class CarControllerStandaloneTest {

    private MockMvc mockMvc;
    private CarService carService;
    private CarController controller;

    @BeforeEach
    void setup() {
        carService = mock(CarService.class);

        controller = new CarController();
        ReflectionTestUtils.setField(controller, "carService", carService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createCarPage_returnsCreateCarView() {
        Model model = new ConcurrentModel();

        String view = controller.createCarPage(model);

        org.junit.jupiter.api.Assertions.assertEquals("createCar", view);
        org.junit.jupiter.api.Assertions.assertTrue(model.containsAttribute("car"));
    }

    @Test
    void createCarPost_redirectsToListCar_andCallsServiceCreate() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Ferrari")
                        .param("carColor", "Red"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).create(any(Car.class));
    }

    @Test
    void carListPage_returnsCarListView() throws Exception {
        Car car = new Car();
        car.setCarId("C1");
        when(carService.findAll()).thenReturn(List.of(car));

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"));
    }

    @Test
    void editCarPage_whenCarExists_returnsEditCarView() throws Exception {
        Car car = new Car();
        car.setCarId("C1");
        when(carService.findById("C1")).thenReturn(Optional.of(car));

        mockMvc.perform(get("/car/editCar/C1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void editCarPage_whenCarMissing_redirectsToListCar() throws Exception {
        when(carService.findById("MISSING")).thenReturn(Optional.empty());

        mockMvc.perform(get("/car/editCar/MISSING"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/car/listCar"));
    }

    @Test
    void editCarPost_redirectsToListCar_andCallsServiceUpdate() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .param("carId", "C1")
                        .param("carName", "Updated")
                        .param("carColor", "Blue"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).update(eq("C1"), any(Car.class));
    }

    @Test
    void deleteCar_redirectsToListCar_andCallsServiceDeleteById() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "C1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).deleteById("C1");
    }
}