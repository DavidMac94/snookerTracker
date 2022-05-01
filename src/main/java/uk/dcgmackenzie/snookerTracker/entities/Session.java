package uk.dcgmackenzie.snookerTracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @NotNull
    private List<Integer> results;

    @NotNull
    private LocalDateTime timestamp;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "drill_name", nullable = false)
    private Drill drill;
}
