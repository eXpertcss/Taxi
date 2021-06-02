package group.Taxi.model;


import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String carVIN;

    @Column(nullable = false)
    @NotNull
    private String carNumber;

    @Column(nullable = false)
    @NotNull
    private String carModel;

    @Column(nullable = false)
    @NotNull
    private String carCategory;

    @Column(nullable = false)
    @NotNull
    private String carColor;

    @Column(nullable = false)
    @NotNull
    private String carModelYear;

    @OneToOne(mappedBy = "car", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Driver driver;

    public Car() {
    }

    public Car(String carVIN, String carNumber, String carModel, String carCategory, String carColor, String carModelYear) {
        this.carVIN = carVIN;
        this.carNumber = carNumber;
        this.carModel = carModel;
        this.carCategory = carCategory;
        this.carColor = carColor;
        this.carModelYear = carModelYear;
//        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarModelYear() {
        return carModelYear;
    }

    public void setCarModelYear(String carModelYear) {
        this.carModelYear = carModelYear;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carVIN='" + carVIN + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carCategory='" + carCategory + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carModelYear='" + carModelYear + '\'' +
                '}';
    }
}
