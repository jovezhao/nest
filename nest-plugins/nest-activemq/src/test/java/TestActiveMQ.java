import com.jovezhao.nest.activemq.ActiveChannelProvider;
import com.jovezhao.nest.ddd.event.EventBus;
import com.jovezhao.nest.ddd.event.IEventHandler;
import com.jovezhao.nest.ddd.event.ServiceEvent;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhaofujun on 2017/3/29.
 */
public class TestActiveMQ {

    @Test
    public void doPubsh() {
        EventBus eventBus = new EventBus();
        eventBus.setProvider(new ActiveChannelProvider("failover:(tcp://192.168.2.6:61616,tcp://192.168.2.6:61617)?randomize=true", 1));
        for (int i = 0; i < 100; i++) {
            eventBus.publish(ServiceEvent.createEvent("event", "test" + i));

        }


    }

    @Test
    public void doHand() throws IOException {
        EventBus eventBus = new EventBus();
        ActiveChannelProvider activeChannelProvider = new ActiveChannelProvider("tcp://127.0.0.1:61616", 1);
        eventBus.setProvider(activeChannelProvider);
//        for (int i = 0; i < 2; i++) {
        eventBus.registerHandler(new IEventHandler() {
            @Override
            public String getEventName() {
                return "event";
            }

            @Override
            public void handle(Object data) {
                System.out.println(data);
            }
        });
//        }
        System.in.read();

    }

    @Test
    public void doHand1() throws IOException {
        EventBus eventBus = new EventBus();
        ActiveChannelProvider activeChannelProvider = new ActiveChannelProvider("failover:(tcp://192.168.2.6:61616,tcp://192.168.2.6:61617)?randomize=true", 1);
        eventBus.setProvider(activeChannelProvider);
        eventBus.registerHandler(new IEventHandler() {
            @Override
            public String getEventName() {
                return "event";
            }

            @Override
            public void handle(Object data) {
                System.out.println(data + "22222");
//                throw new RuntimeException("测试");
            }
        });
        System.in.read();

    }
}
