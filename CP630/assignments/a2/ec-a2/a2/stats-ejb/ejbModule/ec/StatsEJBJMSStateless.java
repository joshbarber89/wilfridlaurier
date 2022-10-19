package ec;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;


/**
 * Session Bean implementation class StatsEJBJMSStatelesss
 */
@Stateless
@LocalBean
public class StatsEJBJMSStateless implements StatsEJBJMSStatelessLocal {

    /**
     * Default constructor. 
     */
    public StatsEJBJMSStateless() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void produce(String message) throws NamingException, JMSException  {
        Connection connection = null;
        try {
            System.out.println("Create JNDI Context");
            Context context = ContextUtil.getInitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            connection = connectionFactory.createConnection("quickstartUser", "quickstartPwd1!");
            
            System.out.println("Create session");
            Session session = connection.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Destination queue = (Destination) context.lookup("jms/queue/test");
            
            System.out.println("Start connection");
            connection.start();
            
            System.out.println("Create producer");
            MessageProducer producer = session.createProducer(queue);
            
            System.out.println("Create a message");
            Message msg = session.createTextMessage(message);
            
            System.out.println("Send message");
            producer.send(msg);

        } finally {
            if (connection != null) {
                System.out.println("close the connection");
                connection.close();
            }
        }
        System.out.println("producer done");
	}

	@Override
	public void publish(String message) throws Exception {
		TopicConnection connection = null;
		System.out.println("Create JNDI Context");
		Context context = ContextUtil.getInitialContext();
		TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
		connection = connectionFactory.createTopicConnection("quickstartUser", "quickstartPwd1!");
		    
		System.out.println("Create topic session");
		TopicSession topicSession = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		    
		Topic topic = (Topic) context.lookup("jms/topic/test");
		    
		System.out.println("Start topic connection");
		connection.start();
		                           
		TopicPublisher topicPublisher = topicSession.createPublisher(topic);
		topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		                                                                       
		TextMessage m = topicSession.createTextMessage();
		m.setText(message);
		                      
		topicPublisher.publish(m);
		System.out.println("Message published: " + m.getText());
		connection.close();
		
		System.out.println("publisher done");
	}
}
