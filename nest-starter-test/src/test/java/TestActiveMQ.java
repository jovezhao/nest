import com.jovezhao.nest.activemq.ActiveMQChannelProvider;
import com.jovezhao.nest.activemq.ActiveMQProviderConfig;
import com.jovezhao.nest.ddd.event.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by zhaofujun on 2017/3/29.
 */
public class TestActiveMQ {

    @Before
    public void before() {

        ChannelProvider channelProvider = new ActiveMQChannelProvider();
        ActiveMQProviderConfig providerConfig = new ActiveMQProviderConfig();
        providerConfig.setBrokers("tcp://127.0.0.1:61616");
        channelProvider.setProviderConfig(providerConfig);

        EventChannelItem eventChannelItem = new EventChannelItem();
        eventChannelItem.setEventName("event1");
        eventChannelItem.setChannelProvider(channelProvider);
        EventChannelManager.put(eventChannelItem);

    }

    @Test
    public void publish() {
        for (int i = 0; i < 100; i++) {
            EventBus.publish("event1", "test" + i);
        }
    }

    @Test
    public void register() throws IOException {
        EventBus.registerHandler(new EventHandler<String>() {

            @Override
            public String getEventName() {
                return "event1";
            }

            @Override
            public Class<String> getTClass() {
                return String.class;
            }

            @Override
            public void handle(String data) throws Exception {
                System.out.println(data);
            }
        });
        System.in.read();

    }


}
