package uk.dcgmackenzie.snookerTracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"results"})
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDateTime timestamp;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "drill_name", nullable = false)
    private Drill drill;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
    private List<Result> results;
}
