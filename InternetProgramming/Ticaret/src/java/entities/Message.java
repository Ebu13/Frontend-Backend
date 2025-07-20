package entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "send_date")
    private Timestamp sendDate;

    @Column(name = "is_read")
    private boolean isRead;

    public Message(int messageId, User sender, User receiver, String messageText, Timestamp sendDate, boolean isRead) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.messageText = messageText;
        this.sendDate = sendDate;
        this.isRead = isRead;
    }

    public Message(User sender, User receiver, String messageText, Timestamp sendDate, boolean isRead) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageText = messageText;
        this.sendDate = sendDate;
        this.isRead = isRead;
    }

    public Message() {
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getSenderId() {
        return this.getSender().getUserId();
    }

    public int getReceiverId() {
        return this.getReceiver().getUserId();
    }

}
