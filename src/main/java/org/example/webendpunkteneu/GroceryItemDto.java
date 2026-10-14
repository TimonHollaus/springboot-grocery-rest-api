package org.example.webendpunkteneu;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
@Data //@Data kombiniert @Getter, @Setter, @EqualsAndHashCode, @ToString und @NoArgsConstructor

public class GroceryItemDto {

    @PositiveOrZero(message = "ID darf nicht negativ sein")
    private Long id;

    @NotNull(message = "Produkt muss Namen haben!")
    @NotBlank(message = "Produkt muss Namen haben!")
    @Size(max = 255, message = "Darf maximal 255 Zeichen lang sein")
    private String name;

    private int amount;

    private boolean collected;

    public void correctErrors() {
        if (name.startsWith(" ") || name.endsWith(" ")) {
            name = name.trim();
        }
        if(amount == 0) amount = 1;
        if(amount < 0) amount = Math.abs(amount);
    }
}
