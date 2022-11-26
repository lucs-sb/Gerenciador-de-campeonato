package api.championship.manager.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_group_information")
@Getter
@Setter
public class GroupInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Team first_place;
    @ManyToOne
    private Team second_place;
    @ManyToOne
    private Team third_place;
    @ManyToOne
    private Team fourth_place;
    private int first_place_points;
    private int second_place_points;
    private int third_place_points;
    private int fourth_place_points;
    private LocalDateTime deletionDate;

    public GroupInformation(){}
}
