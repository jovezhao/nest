package queries;

import com.ywkj.nest.core.PageList;
import models.Commodity;
import models.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jove on 2016/9/22.
 */
@Repository
public class ProductQuery implements IProductQuery {

    @Override
    public PageList<Commodity> getProducts(String title, int startIndex, int count) {
            PageList<Commodity> pageList = new PageList<>();
        pageList.setTotalCount(100);
        pageList.setPageSize(count);
        List<Commodity> list = new ArrayList<>();
        Product product = new Product();
        product.setId("fff");
        product.setPic("pic");
        product.setTitle("title");


        Commodity commodity = product.act(Commodity.class, null);
        commodity.setPrice(110);
        list.add(commodity);
        pageList.setList(list);
        return pageList;
    }
}
