package models;

import com.ywkj.nest.ddd.IRepository;
import org.springframework.stereotype.Repository;


import java.util.HashMap;

/**
 * Created by Jove on 2016/9/22.
 */
@Repository("Product_Repository")
public class ProductRepository implements IRepository<Product> {
    static HashMap<String, Product> hashMap = new HashMap<>();

    @Override
    public Product getEntityById(String id) {
        return hashMap.get(id);
    }

    @Override
    public void save(Product product) {
        hashMap.put(product.getId(), product);
    }

    @Override
    public void remove(Product product) {
        hashMap.remove(product.getId());
    }
}
