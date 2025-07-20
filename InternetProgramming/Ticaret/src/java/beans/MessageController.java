package beans;

import dao.MessageDAO;
import entities.Message;
import entities.User;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class MessageController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(MessageController.class.getName());

    private List<Message> messages;
    private Message newMessage;
    private Message selectedMessage;
    private int pageNumber = 1;
    private int pageSize = 10; // Sayfa başına görüntülenecek mesaj sayısı

    private final MessageDAO messageDAO;

    public MessageController() {
        messageDAO = new MessageDAO();
    }

    @PostConstruct
    public void init() {
        refreshMessages();
        newMessage = new Message();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void loadMessages() {
        int offset = (pageNumber - 1) * pageSize;
        messages = messageDAO.getMessagesWithPagination(offset, pageSize);
    }

    public void nextPage() {
        pageNumber++;
        loadMessages();
    }

    public void previousPage() {
        if (pageNumber > 1) {
            pageNumber--;
            loadMessages();
        }
    }

    public void addMessage() {
        try {
            // Kullanıcı ID'leri ile User nesnelerini oluşturarak yeni mesajı gönder
            User sender = new User(newMessage.getSender().getUserId());
            User receiver = new User(newMessage.getReceiver().getUserId());
            newMessage.setSender(sender);
            newMessage.setReceiver(receiver);
            newMessage.setSendDate(new Timestamp(System.currentTimeMillis()));
            newMessage.setIsRead(false);

            messageDAO.sendMessage(newMessage);
            refreshMessages();
            newMessage = new Message(); // Gönderimden sonra formu temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Mesaj gönderilirken hata oluştu", ex);
        }
    }

    public void deleteMessage(int messageId) {
        try {
            messageDAO.deleteMessage(messageId);
            refreshMessages();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Mesaj silinirken hata oluştu: " + messageId, ex);
        }
    }

    public void updateMessage() {
        try {
            messageDAO.updateMessage(selectedMessage);
            refreshMessages();
            selectedMessage = null; // Güncellemeden sonra seçili mesajı temizle
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Mesaj güncellenirken hata oluştu: " + selectedMessage.getMessageId(), ex);
        }
    }

    public void editMessage(Message message) {
        setSelectedMessage(message); // Seçilen mesajı belirtilen mesaj olarak ayarla
    }

    private void refreshMessages() {
        try {
            loadMessages();
            LOGGER.info("Mesaj listesi boyutu: " + messages.size()); // Mesaj listesinin boyutunu günlüğe kaydet
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Mesajlar alınırken hata oluştu", ex);
        }
    }

    public Message getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(Message newMessage) {
        this.newMessage = newMessage;
    }

    public Message getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(Message selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
