package repositories;

import com.ywkj.nest.ddd.IRepository;
import domain.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Jove on 2016/9/22.
 */
@Repository("ShoppingCart_Repository")
public class ShoppingRepository implements IRepository<ShoppingCart> {
    static HashMap<String, ShoppingCart> hashMap = new HashMap<>();

    static {
        hashMap.put("testid", new ShoppingCart());
    }

    @Override
    public ShoppingCart getEntityById(String id) {
        return hashMap.get(id);
    }

    @Override
    public void save(ShoppingCart shoppingCart) {
        hashMap.put(shoppingCart.getId(), shoppingCart);
    }

    @Override
    public void remove(ShoppingCart shoppingCart) {
        hashMap.remove(shoppingCart.getId());
    }


}
