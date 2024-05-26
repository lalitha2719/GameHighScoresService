package com.craft.domain.model;

import com.craft.domain.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @Min(value = 0, message = "Age should not be less than 0")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer age;

    private GenderEnum gender;
}
