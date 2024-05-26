package com.craft.domain.model;

import com.craft.domain.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlayerDetails {

    @NotNull(message = "PlayerId cannot be null")
    private String playerId;

    @NotNull(message = "Player Name connot be null")
    private String name;

    private Integer age;

    private GenderEnum gender;
}
