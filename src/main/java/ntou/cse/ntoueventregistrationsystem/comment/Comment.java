package ntou.cse.ntoueventregistrationsystem.comment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("comments")
public class Comment {
    @Id
    private String id;
    private String eventId;
//    private String userId;
    private String text;
//    private Integer likes;
    private int report;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }
}
