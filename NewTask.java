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
    for (int i=0; i<10; i=i+1) {
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    }

    channel.close();
    connection.close();
  }

  private static String getMessage(String[] strings){
      if (strings.length < 1)
          return "processing";
      return joinStrings(strings, " ");
  }

  private static String joinStrings(String[] strings, String delimiter) {
      int length = strings.length;
      if (length == 0) return "";
      StringBuilder words = new StringBuilder(strings[0]);
      for (int i = 1; i < length; i++) {
          words.append(delimiter).append(strings[i]);
      }
      return words.toString();
  }
}
