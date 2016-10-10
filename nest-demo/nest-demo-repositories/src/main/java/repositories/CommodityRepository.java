package repositories;

import com.ywkj.nest.ddd.IRoleRepository;
import domain.Commodity;
import domain.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jove on 2016/9/22.
 */
@Repository("Commodity_Repository")
public class CommodityRepository implements IRoleRepository<Commodity> {
    static HashMap<String, Commodity> hashMap = new HashMap<>();

    static {
        Commodity commodity = new Commodity();
        commodity.setPrice(100);
        commodity.setId("commodityId");
        Product product = new Product();
        product.setPic("pppiiiccc");
        commodity.setActor(product);
        hashMap.put("commodityId", commodity);
    }

    @Override
    public Commodity getEntityById(String id) {

        return hashMap.get(id);
    }

    @Override
    public void save(Commodity commodity) {
        hashMap.put(commodity.getId(), commodity);
    }

    @Override
    public void remove(Commodity commodity) {
        hashMap.remove(commodity.getId());
    }

    @Override
    public Set<String> getRoleIds(String actorId) {
        return null;
    }
}
