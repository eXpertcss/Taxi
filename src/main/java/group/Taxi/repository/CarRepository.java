package group.Taxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import group.Taxi.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}
