package com.craft.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_uuid")
    private Integer playerUuid;

    @Column(name = "playerId")
    private String playerId;

    @Column(name = "player_name")
    private String playerName;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Score> scores = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_datetime", updatable = false)
    private Instant createdDatetime;

    @LastModifiedDate
    @Column(name = "updated_datetime", insertable = false)
    private Instant updatedDatetime;

    @Version
    @Column(name = "concurrency_version")
    private Integer concurrencyVersion;

}
