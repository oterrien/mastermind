package com.wdg.mastermind;

import lombok.Getter;

import java.util.Random;
import java.util.stream.Stream;

public enum Color {

    ROUGE('R'),
    JAUNE('J'),
    BLEU('B'),
    ORANGE('O'),
    VERT('V'),
    NOIR('N');

    @Getter
    private char code;

    @Override
    public String toString(){
        return String.valueOf(this.code);
    }

    Color(char code) {
        this.code = code;
    }

    public static Color findByCode(char code) throws ParseException {
        return Stream.of(Color.values()).
                filter(p -> p.code == code).
                findAny().
                orElseThrow(() -> new ParseException());
    }

    public static Color findAny() {
        Random random = new Random();
        return Color.values()[random.nextInt(Color.values().length)];
    }
}
