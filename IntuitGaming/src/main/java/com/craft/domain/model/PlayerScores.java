package com.craft.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerScores {

    @NotNull(message = "PlayerId cannot be null")
    private String playerId;

    @NotNull(message = "Player score cannot be null")
    @Min(value = 0, message = "Game Score cannot be less than 0")
    private Integer score;
}
