package co.lunarlunacy.accountabilibuddy.daos;

/**
 * Created by willepstein on 1/23/16.
 */
public interface CRUD<T> {

    long insert(T x);

    T get(long id);

    void update(T x);

    void delete(long id);

    void delete(T x);

}
