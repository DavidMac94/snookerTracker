package uk.dcgmackenzie.snookerTracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Validated
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;
}
