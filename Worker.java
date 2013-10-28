import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {
  private final static String QUEUE_NAME = "hello";

  public static void main(String[] args) throws java.io.IOException, java.lang.InterruptedException  {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    QueueingConsumer consumer = new QueueingConsumer(channel);
    boolean autoAck = false;
    channel.basicConsume(QUEUE_NAME, autoAck, consumer);

    while(true){
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(" [x] Received '" + message + "'");
      doWork(message);
      System.out.println(" [x] Finished");
      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }
  }
  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
      if (ch == '.') Thread.sleep(1000);
    }
  }
}