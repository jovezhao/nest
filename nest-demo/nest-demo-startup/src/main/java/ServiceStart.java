import apis.IProductService;
import apis.ProductDto;
import com.ywkj.nest.core.PageList;
import com.ywkj.nest.core.utils.SpringUtils;
import com.ywkj.nest.ddd.BeanNotFoundException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Jove on 2016/9/22.
 */
public class ServiceStart {
    public static void main(String[] args) throws IOException, BeanNotFoundException {
        //加载服务配置并且启动服务
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{
                "classpath*:applicationContext.xml"
        });
        SpringUtils.setApplicationContext(applicationContext);

        IProductService service = SpringUtils.getInstance(IProductService.class);
        // region 这里直接模拟客户端调用接口
        service.addInCart("commodityId");
        // endregion

        // region 这里模拟查询列表
        PageList<ProductDto> productDtoPageList = service.getProducts("test", 0, 3);
        //endregion

//        TestDDD testDDD = new TestDDD();
//        Thread thread1 = new Thread(testDDD);
//        thread1.setName("t1");
//        thread1.start();
//        Thread thread2 = new Thread(testDDD);
//        thread2.setName("t2");
//        thread2.start();
//        System.in.read();
//        testDDD.stop();

        ProductDto dto = new ProductDto();
        dto.setTitle("tttt");
        dto.setId("commodityId");
        dto.setPrice(1010);

        service.newProduct(dto);
    }
}

//class TestDDD implements Runnable {
//    private volatile boolean status;
//
//    public void stop() {
//        this.status = false;
//    }
//
//    @Override
//    public void run() {
//        this.status = true;
//        while (status) {
//            System.out.println(Thread.currentThread().getName() + " : ffffff");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
