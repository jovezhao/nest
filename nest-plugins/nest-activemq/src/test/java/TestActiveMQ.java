import com.ywkj.nest.activemq.ActiveChannelProvider;
import com.ywkj.nest.ddd.event.EventBus;
import com.ywkj.nest.ddd.event.IEventHandler;
import com.ywkj.nest.ddd.event.ServiceEvent;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhaofujun on 2017/3/29.
 */
public class TestActiveMQ {

    @Test
    public void doPubsh() {
        EventBus eventBus = new EventBus();
        eventBus.setProvider(new ActiveChannelProvider("tcp://127.0.0.1:61616", 1));
        for (int i = 0; i < 100; i++) {
            eventBus.publish(ServiceEvent.createEvent("event", "test" + i));

        }


    }

    @Test
    public void doHand() throws IOException {
        EventBus eventBus = new EventBus();
        ActiveChannelProvider activeChannelProvider = new ActiveChannelProvider("tcp://127.0.0.1:61616", 1);
        eventBus.setProvider(activeChannelProvider);
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
        System.in.read();

    }

    @Test
    public void doHand1() throws IOException {
        EventBus eventBus = new EventBus();
        ActiveChannelProvider activeChannelProvider = new ActiveChannelProvider("tcp://127.0.0.1:61616", 1);
        eventBus.setProvider(activeChannelProvider);
        eventBus.registerHandler(new IEventHandler() {
            @Override
            public String getEventName() {
                return "event";
            }

            @Override
            public void handle(Object data) {
                System.out.println(data + "22222");
                throw new RuntimeException("测试");
            }
        });
        System.in.read();

    }
}
