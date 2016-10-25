package models;

import com.ywkj.nest.ddd.ValueObject;

public class CartItem extends ValueObject {
    private int count;
    private Commodity commodity;

    public CartItem(int count, Commodity commodity) {
        this.count = count;
        this.commodity = commodity;
    }

    public int getCount() {
        return count;
    }

    public Commodity getCommodity() {
        return commodity;
    }
}
