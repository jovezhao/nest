package apis;

import com.ywkj.nest.core.PageList;
import com.ywkj.nest.ddd.BeanNotFoundException;

/**
 * Created by Jove on 2016/9/22.
 */
public interface IProductService {
    void addInCart(String commodityId) throws BeanNotFoundException;

    PageList<ProductDto> getProducts(String title,int startIndex,int count);

    void newProduct(ProductDto dto) throws BeanNotFoundException;
}
