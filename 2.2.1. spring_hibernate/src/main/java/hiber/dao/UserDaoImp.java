package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    private String getCarByModelSeries = "FROM Car car WHERE car.series =:car_series AND car.model=:car_model";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user, Car car) {
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.getCurrentSession().save(car);
        user.setCar(car);
        car.setUser(user);
    }

    @Override
    public void getCarByModelSeries(String model, int series) {                 // Пункт 5
        Session session = sessionFactory.openSession();
        Query<Car> query = session.createQuery(getCarByModelSeries, Car.class);
        query.setParameter("car_model", model);
        query.setParameter("car_series", series);
        Car car = query.uniqueResult();
        if (car == null) {
            System.out.println("Такой машины не существует");
        } else {
            User user = car.getUser();
            System.out.println(user);
        }


    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

}
