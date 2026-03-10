package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryCarRepositoryTest {

    private InMemoryCarRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryCarRepository();
    }

    @Test
    void save_whenIdIsNull_generatesNewId() {
        Car car = createCar(null, "Ferrari", "Red", 1);

        Car saved = repository.save(car);

        assertNotNull(saved.getCarId());
        assertFalse(saved.getCarId().isBlank());
    }

    @Test
    void save_whenIdIsBlank_generatesNewId() {
        Car car = createCar("  ", "Porsche", "Black", 2);

        Car saved = repository.save(car);

        assertNotNull(saved.getCarId());
        assertFalse(saved.getCarId().isBlank());
    }

    @Test
    void save_whenIdExists_keepsSameId() {
        Car car = createCar("CAR-1", "BMW", "Blue", 3);

        Car saved = repository.save(car);

        assertEquals("CAR-1", saved.getCarId());
    }

    @Test
    void findAll_returnsCopyAndNotOriginalList() {
        repository.save(createCar("CAR-1", "BMW", "Blue", 3));

        List<Car> externalList = repository.findAll();
        externalList.clear();

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void findById_whenFound_returnsCar() {
        repository.save(createCar("CAR-1", "BMW", "Blue", 3));

        Optional<Car> found = repository.findById("CAR-1");

        assertTrue(found.isPresent());
        assertEquals("BMW", found.get().getCarName());
    }

    @Test
    void findById_whenMissing_returnsEmpty() {
        Optional<Car> found = repository.findById("MISSING");

        assertTrue(found.isEmpty());
    }

    @Test
    void update_whenFound_updatesFields() {
        repository.save(createCar("CAR-1", "BMW", "Blue", 3));
        Car updatedCar = createCar("OTHER", "Audi", "White", 9);

        Car result = repository.update("CAR-1", updatedCar);

        assertEquals("CAR-1", result.getCarId());
        assertEquals("Audi", result.getCarName());
        assertEquals("White", result.getCarColor());
        assertEquals(9, result.getCarQuantity());
    }

    @Test
    void update_whenMissing_throwsIllegalArgumentException() {
        Car updatedCar = createCar("CAR-X", "Audi", "White", 9);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> repository.update("MISSING", updatedCar)
        );

        assertTrue(exception.getMessage().contains("MISSING"));
    }

    @Test
    void deleteById_whenFound_removesCar() {
        repository.save(createCar("CAR-1", "BMW", "Blue", 3));

        repository.deleteById("CAR-1");

        assertTrue(repository.findById("CAR-1").isEmpty());
    }

    @Test
    void deleteById_whenMissing_doesNothing() {
        repository.save(createCar("CAR-1", "BMW", "Blue", 3));

        repository.deleteById("NOT-EXIST");

        assertEquals(1, repository.findAll().size());
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