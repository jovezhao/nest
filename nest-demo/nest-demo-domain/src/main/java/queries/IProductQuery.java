package queries;

import com.ywkj.nest.core.PageList;
import models.Commodity;

/**
 * Created by Jove on 2016/9/22.
 */
public interface IProductQuery {
    PageList<Commodity> getProducts(String title, int startIndex, int count);
}
