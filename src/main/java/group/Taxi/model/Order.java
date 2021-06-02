package group.Taxi.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "orders")
public class Order {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private Date date;

    @Column(nullable = false)
    @NotNull
    @JsonFormat(pattern="hh:mm")
    private Time time;

    @Column(nullable = false)
    @NotNull
    private double roadTime;

    @Column(nullable = false)
    @NotNull
    private double distance;

    @Column(nullable = false)
    @NotNull
    private double cost;

    @Column(nullable = false)
    @NotNull
    private String addressTo;

    @Column(nullable = false)
    @NotNull
    private String addressFrom;


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "dispatcher_id")
    private Dispatcher dispatcher;

    public Order() {
    }

    public Order(Date date, Time time, double roadTime, double distance, double cost, String addressTo, String addressFrom) {
        this.date = date;
        this.time = time;
        this.roadTime = roadTime;
        this.distance = distance;
        this.cost = cost;
        this.addressTo = addressTo;
        this.addressFrom = addressFrom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date.toString();
    }

    public Date getDateSql() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getRoadTime() {
        return roadTime;
    }

    public void setRoadTime(double roadTime) {
        this.roadTime = roadTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String streetTo) {
        this.addressTo = streetTo;
    }

//    public String getHouseTo() {
//        return houseTo;
//    }
//
//    public void setHouseTo(String houseTo) {
//        this.houseTo = houseTo;
//    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String streetFrom) {
        this.addressFrom = streetFrom;
    }

//    public String getHouseFrom() {
//        return houseFrom;
//    }
//
//    public void setHouseFrom(String houseFrom) {
//        this.houseFrom = houseFrom;
//    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


}
