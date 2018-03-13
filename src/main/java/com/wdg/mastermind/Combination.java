package com.wdg.mastermind;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Combination {

    private static final int NumberOfCorrectPieces = 4;

    @Getter
    private Color[] colors = new Color[NumberOfCorrectPieces];

    public Result compare(String other) {

        Combination otherCombination = new Combination.Parser(other).build();

        Result result = new Result(colors.length);

        IntStream.range(0, colors.length).
                forEach(i -> {
                    Color colorAtCurrentIndex = colors[i];
                    // Check current color is correct
                    if (colorAtCurrentIndex == otherCombination.colors[i]) {
                        result.incrementNumberOfCorrectPieces();
                        // remove to prevent checking again
                        otherCombination.colors[i] = null;
                    } else {
                        int index = otherCombination.getColorIndex(colorAtCurrentIndex);
                        if (index > -1) {
                            // Check currentColor exist in otherCombination
                            result.incrementNumberOfMisplacedPieces();
                            // remove to prevent checking again
                            otherCombination.colors[index] = null;
                        }
                    }
                });

        return result;
    }

    private int getColorIndex(Color color) {
        for (int i = 0; i < colors.length; i++) {
            if (color == colors[i]) {
                return i;
            }
        }
        return -1;

    }

    @Override
    public String toString() {
        return IntStream.range(0, colors.length).
                mapToObj(i -> colors[i].toString()).
                collect(Collectors.joining());
    }

    @NoArgsConstructor
    public static class RandomBuilder {

        public Combination build() {

            Combination combination = new Combination();
            IntStream.range(0, combination.colors.length).
                    forEach(i -> combination.colors[i] = Color.findAny());
            return combination;
        }
    }

    @RequiredArgsConstructor
    public static class Parser {

        private final String entry;

        public Combination build() {

            Combination combination = new Combination();
            char[] entryAsChar = entry.toCharArray();
            IntStream.range(0, combination.colors.length).
                    forEach(i -> {
                        try {
                            combination.colors[i] = getColor(entryAsChar, i);
                        } catch (ParseException e) {
                            throw new CombinationException(entry, e);
                        }
                    });
            return combination;
        }

        private Color getColor(char[] entryAsChar, int index) throws ParseException {
            if (index < 0 || index >= entryAsChar.length) {
                throw new ParseException();
            }
            return Color.findByCode(entryAsChar[index]);
        }
    }

    public static class CombinationException extends RuntimeException {
        public CombinationException(String combination, Throwable cause) {
            super("The combination " + combination + " is not correct", cause);
        }
    }


}
