package services.budiles;

import apis.ProductDto;
import com.ywkj.nest.core.identifier.IdentifierGenerator;
import com.ywkj.nest.core.utils.MapUtils;
import com.ywkj.nest.ddd.IBuilder;
import com.ywkj.nest.ddd.RepositoryLoader;
import models.Commodity;

/**
 * Created by Jove on 2016/9/30.
 */
public class CommodityBudler implements IBuilder<Commodity> {
    private ProductDto dto;

    public CommodityBudler(ProductDto dto) {
        this.dto = dto;
    }

    @Override
    public Commodity build(Class<Commodity> commodityClass) {
        Commodity commodity = null;

        if (org.springframework.util.StringUtils.isEmpty(dto.getId())) {
            IdentifierGenerator generator = new IdentifierGenerator();
            String id = generator.generate(commodityClass);
            dto.setId(id);
            commodity = MapUtils.map(dto, commodityClass);
        } else {
            IBuilder<Commodity> builder = new RepositoryLoader<>(dto.getId());
            commodity = builder.build(Commodity.class);
            MapUtils.map(dto, commodity);
        }
        return commodity;
    }
}
