package ec;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.NamingException;

import ec.StatsEJBSingleton;

public class StatsMDBAdd  {
    
    @EJB
    private static StatsEJBSingleton sbsl = new StatsEJBSingleton();
    
    private static TopicConnection connection = null;
    
    public static void main(String[] args) throws NamingException, JMSException {
         TopicConnection connection = null;
        try {
            System.out.println("Create JNDI Context");
            Context context = ContextUtil.getInitialContext();
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            connection = connectionFactory.createTopicConnection("quickstartUser", "quickstartPwd1!");
            
            System.out.println("Create topic session");
            TopicSession topicSession = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            
            Topic topic = (Topic) context.lookup("jms/topic/test");
            
            System.out.println("Start topic connection");
            connection.start();
            
            System.out.println("Subscriber is waiting");
            TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);
            
            TextMessage message = (TextMessage) topicSubscriber.receive();
                                                                    
            System.out.println("Message received: " + message.getText());
            
            try {
	            float fl = Float.parseFloat(message.getText()); 
	            
	            if (!Float.isNaN(fl)) {
	            	System.out.println("Inserted data: " + message.getText());
	            	sbsl.addData((double)fl);            	           	
	            }
            }
            catch (Exception ex) {
            	System.out.println("Not valid data: " + message.getText());
            }
                                                                     
            connection.close();
                        
        } finally {
            if (connection != null) {
                System.out.println("close the connection");
                connection.close();
            }
            System.out.println("subscriber done");
        }
    }
}