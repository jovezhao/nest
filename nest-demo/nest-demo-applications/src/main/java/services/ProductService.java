package services;

import apis.IProductService;
import apis.ProductDto;
import com.ywkj.nest.core.PageList;
import com.ywkj.nest.core.utils.MapUtils;
import com.ywkj.nest.ddd.*;
import models.Commodity;
import models.ShoppingCart;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import queries.IProductQuery;

import java.util.List;

/**
 * Created by Jove on 2016/9/22.
 * <p>
 * 应用服务层职责：
 * 1. dto与领域模型的互相转换
 * 2. 处理权限，日志，事务
 * 3. 封装业务逻辑
 * 4. 定义外部系统服务契约
 * 5. 面向用例编程
 * 6. 发布事件
 */
@Service
public class ProductService implements IProductService {
    @Autowired
    IProductQuery productQuery;
    @Autowired
    private Mapper mapper;

    @Autowired
    private EventBus eventBus;

    private String getUserId() {
        return "testid";
    }


    public void addInCart(String commodityId) throws BeanNotFoundException {
        //获取具体的商品信息
        IBuilder<Commodity> builder = new RepositoryLoader<>(Commodity.class);
        Commodity commodity = builder.build(commodityId);
        //通过用户获取用户的购物车
        IBuilder<ShoppingCart> cartIBuilder = new RepositoryLoader<>(ShoppingCart.class);
        ShoppingCart cart = cartIBuilder.build(getUserId());
        cart.add(commodity, 1);

       eventBus.publish(ServiceEvent.createEvent("addInCart", commodityId));
    }

    @Override
    public PageList<ProductDto> getProducts(String title, int startIndex, int count) {
        PageList<Commodity> commodityPageList = productQuery.getProducts(title, startIndex, count);

        PageList<ProductDto> pageList = new PageList<>();
        pageList.setPageSize(commodityPageList.getPageSize());

        pageList.setTotalCount(commodityPageList.getTotalCount());

        List<ProductDto> productDtoList = MapUtils.mapList(commodityPageList.getList(), ProductDto.class);
        pageList.setList(productDtoList);
        return pageList;
    }

    @Override
    public void newProduct(ProductDto dto) throws BeanNotFoundException {
//        IBuilder<Commodity> builder = new CommodityBudler(dto);
//        Commodity commodity = builder.build(Commodity.class);
    }
}
