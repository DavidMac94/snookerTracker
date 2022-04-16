package uk.dcgmackenzie.snookerTracker.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@RequiredArgsConstructor
@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"sessions"})
public class Drill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 3)
    private String name;
    @NotNull
    @Size(min = 10)
    private String description;
    private Integer maxScore;
    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_name", nullable = false)
    private User user;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drill")
    private List<Session> sessions;
}