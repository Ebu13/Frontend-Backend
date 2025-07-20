package beans;

import dao.MessageDAO;
import dao.UserDAO;
import entities.Message;
import entities.User;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class MessageBean implements Serializable {
    private static final Logger LOGGER = Logger.getLogger(MessageBean.class.getName());

    private Message message = new Message();
    private List<Message> messages;
    private String senderUsername;
    private String receiverUsername;
    private String messageText;

    private final MessageDAO messageDAO;
    private final UserDAO userDAO;
    
    private List<User> allUsers;

    public MessageBean() {
        messageDAO = new MessageDAO();
        userDAO = new UserDAO();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<Message> getMessages() {
        if (messages == null) {
            loadMessages(new User());
        }
        return messages;
    }

    public void loadMessages(User user) {
        try {
            messages = messageDAO.getAllMessagesWithReceiver(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error loading messages", ex);
        }
    }

    public void sendMessage(User user) {
        try {
            // Find sender and receiver based on usernames
            User sender = userDAO.getUserByUsername(user.getUsername());
            User receiver = userDAO.getUserByUsername(receiverUsername);

            // Set message details
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setMessageText(messageText);
            message.setSendDate(new Timestamp(System.currentTimeMillis()));
            message.setIsRead(false);

            // Save message
            messageDAO.sendMessage(message);

            // Clear message object after sending
            message = new Message();
            messages = null; // Refresh message list to update view

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error sending message", ex);
        }
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    
    // Tüm kullanıcıları getiren metod
    public List<User> getAllUsers() {
        if (allUsers == null) {
            allUsers = userDAO.getAllUsers();
        }
        return allUsers;
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }
    
    
}
