package domain;

import com.ywkj.nest.ddd.EntityObject;
import com.ywkj.nest.ddd.ValueObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jove on 2016/9/22.
 */
public class ShoppingCart extends EntityObject {
    private Set<CartItem> items;

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }


    public void add(Commodity commodity, int count) {
        if (items == null)
            items = new HashSet<>();
        items.add(new CartItem(count, commodity));
    }
}

