package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    private CarRepository carRepository;
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        carService = new CarServiceImpl(carRepository);
    }

    @Test
    void create_delegatesToRepositorySave() {
        Car car = createCar("CAR-1", "BMW", "Blue", 3);
        when(carRepository.save(car)).thenReturn(car);

        Car result = carService.create(car);

        assertSame(car, result);
        verify(carRepository).save(car);
    }

    @Test
    void findAll_delegatesToRepositoryFindAll() {
        List<Car> cars = List.of(createCar("CAR-1", "BMW", "Blue", 3));
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.findAll();

        assertSame(cars, result);
        verify(carRepository).findAll();
    }

    @Test
    void findById_delegatesToRepositoryFindById() {
        Optional<Car> car = Optional.of(createCar("CAR-1", "BMW", "Blue", 3));
        when(carRepository.findById("CAR-1")).thenReturn(car);

        Optional<Car> result = carService.findById("CAR-1");

        assertTrue(result.isPresent());
        assertEquals("CAR-1", result.get().getCarId());
        verify(carRepository).findById("CAR-1");
    }

    @Test
    void update_delegatesToRepositoryUpdate() {
        Car updated = createCar("CAR-1", "Audi", "White", 5);
        when(carRepository.update("CAR-1", updated)).thenReturn(updated);

        Car result = carService.update("CAR-1", updated);

        assertSame(updated, result);
        verify(carRepository).update("CAR-1", updated);
    }

    @Test
    void deleteById_delegatesToRepositoryDeleteById() {
        carService.deleteById("CAR-1");

        verify(carRepository).deleteById("CAR-1");
    }

    private Car createCar(String id, String name, String color, int quantity) {
        Car car = new Car();
        car.setCarId(id);
        car.setCarName(name);
        car.setCarColor(color);
        car.setCarQuantity(quantity);
        return car;
    }
}