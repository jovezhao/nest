package models;

import com.ywkj.nest.ddd.AbstractRole;

/**
 * Created by Jove on 2016/9/22.
 */
public class Commodity extends AbstractRole<Product> {
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addInCart(ShoppingCart cart, int count) {
        cart.add(this, count);
    }
}
