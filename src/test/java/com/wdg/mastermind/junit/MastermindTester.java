package com.wdg.mastermind.junit;

import com.wdg.mastermind.Color;
import com.wdg.mastermind.Combination;
import com.wdg.mastermind.ParseException;
import com.wdg.mastermind.Result;
import junit5.MockitoExtension;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class MastermindTester {

    @Test
    @DisplayName("Generate a random color")
    public void randomColor() {

        Color color = Color.findAny();
        log.info("Generated color: " + color.name());
        Assertions.assertThat(color).isNotNull();
        Assertions.assertThat(Color.values()).contains(color);
    }

    @Test
    @DisplayName("Generate a random combination")
    public void randomCombination() {

        Combination combination = new Combination.RandomBuilder().build();
        log.info("Generated combination: " + combination.toString());
        Assertions.assertThat(combination).isNotNull();
        Assertions.assertThat(combination.getColors().length).isEqualTo(4);
    }

    @Test
    @DisplayName("Find color by code")
    public void findColorByCode() {

        Stream.of(Color.values()).
                forEach(color -> {
                    try {
                        Color colorFound = Color.findByCode(color.getCode());
                        log.info("Current color: " + color.name());
                        Assertions.assertThat(colorFound).isNotNull();
                        Assertions.assertThat(colorFound).isEqualTo(color);
                    } catch (ParseException e) {
                        Assertions.fail(e.getMessage());
                    }
                });
    }

    @Test
    @DisplayName("Throw Error when entered color is not defined")
    public void checkThrowException() {

        char badCode = 'A';
        Assertions.assertThatThrownBy(() -> Color.findByCode(badCode)).
                isInstanceOf(ParseException.class);
    }

    @Test
    @DisplayName("Parse a user's combination")
    public void userCombination() {

        String entry = "RONO";
        Combination combination = new Combination.Parser(entry).build();
        log.info("User combination: " + combination.toString());
        Assertions.assertThat(combination).isNotNull();
        Assertions.assertThat(combination.getColors().length).isEqualTo(4);

        Assertions.assertThat(combination.getColors()[0]).isEqualTo(Color.ROUGE);
        Assertions.assertThat(combination.getColors()[1]).isEqualTo(Color.ORANGE);
        Assertions.assertThat(combination.getColors()[2]).isEqualTo(Color.NOIR);
        Assertions.assertThat(combination.getColors()[3]).isEqualTo(Color.ORANGE);
    }

    @ParameterizedTest
    @CsvSource({
            "AONO",
            "RJ"
    })
    @DisplayName("Throw error when user enters bad color code")
    public void checkThrowError(String entry) {

        Assertions.assertThatThrownBy(() -> new Combination.Parser(entry).build()).
                isInstanceOf(Combination.CombinationException.class).
                hasCauseInstanceOf(ParseException.class);
    }

    @ParameterizedTest
    @CsvSource({
            "ROOJ, NBJV, 0, 1, false",
            "RJOO, NBJV, 0, 1, false",
            "RJOO, RJOO, 4, 0, true",
            "BRJO, RJBB, 0, 3, false",
            "RJBB, BRJO, 0, 3, false",
    })
    @DisplayName("Compare two combinations")
    public void compare(String entry1, String entry2, int numberOfCorrectPieces, int numberOfMismatchPieces, boolean isCombinationFound) {

        Combination combination1 = new Combination.Parser(entry1).build();
        Result result = combination1.compare(entry2);

        Assertions.assertThat(result.getNumberOfCorrectPieces()).isEqualTo(numberOfCorrectPieces);
        Assertions.assertThat(result.getNumberOfMismatchPieces()).isEqualTo(numberOfMismatchPieces);
        Assertions.assertThat(result.isCombinationFound()).isEqualTo(isCombinationFound);
    }
}
