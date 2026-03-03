package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryCarRepository implements CarRepository {

    private final List<Car> carData = new ArrayList<>();

    @Override
    public Car save(Car car) {
        if (car.getCarId() == null || car.getCarId().isBlank()) {
            car.setCarId(UUID.randomUUID().toString());
        }
        carData.add(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        // Return a copy to avoid accidental external modification
        return new ArrayList<>(carData);
    }

    @Override
    public Optional<Car> findById(String id) {
        return carData.stream()
                .filter(car -> car.getCarId().equals(id))
                .findFirst();
    }

    @Override
    public Car update(String id, Car updatedCar) {
        Car existing = findById(id).orElseThrow(
                () -> new IllegalArgumentException("Car with id " + id + " not found")
        );

        existing.setCarName(updatedCar.getCarName());
        existing.setCarColor(updatedCar.getCarColor());
        existing.setCarQuantity(updatedCar.getCarQuantity());
        return existing;
    }

    @Override
    public void deleteById(String id) {
        carData.removeIf(car -> car.getCarId().equals(id));
    }
}