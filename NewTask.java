import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class NewTask {
  private final static String QUEUE_NAME = "hello";
  public static void main(String[] argv) throws java.io.IOException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = getMessage(argv);
    for (int i=0; i<1000; i=i+1) {
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    }

    channel.close();
    connection.close();
  }
}
