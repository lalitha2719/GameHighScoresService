package com.craft.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name="Score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "score_uuid")
    private Integer scoreUuid;

    @Column(name = "score")
    private Integer score;

    @ManyToOne
    @JoinColumn(name = "player_uuid")
    private Player player;

    @CreatedDate
    @Column(name = "created_datetime")
    private Instant createdDatetime;

    @Version
    @Column(name = "concurrency_version")
    private Integer concurrencyVersion;


}
