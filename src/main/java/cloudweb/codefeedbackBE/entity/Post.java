package cloudweb.codefeedbackBE.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        this.messages.add(message);
        message.setPost(this);
    }

    @Column(length = 40, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    @Setter(AccessLevel.NONE)
    private LocalDateTime modifiedAt;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;
}
