package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private EventType type;

    @Column(columnDefinition="TEXT")
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name ="user_id")
    private User author;

    public Event() {
    }

    public Event(Long id, String name, EventType type, String description, LocalDateTime startDate, LocalDateTime endDate, User author) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.description = description;
        this.endDate = endDate;
        this.author = author;
        this.type = type;
    }
    public Event(String name, EventType type, String description, LocalDateTime startDate, LocalDateTime endDate, User author) {
        this.name = name;
        this.startDate = startDate;
        this.description = description;
        this.endDate = endDate;
        this.author = author;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", author=" + author +
                '}';
    }
}
