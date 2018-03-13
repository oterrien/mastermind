package com.wdg.mastermind;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Result {

    @Getter
    private int numberOfCorrectPieces = 0;

    @Getter
    private int numberOfMismatchPieces = 0;

    private final int numberOfPieces;

    public void incrementNumberOfCorrectPieces() {
        numberOfCorrectPieces++;
    }

    public void incrementNumberOfMisplacedPieces() {
        numberOfMismatchPieces++;
    }

    public boolean isCombinationFound() {
        return (numberOfCorrectPieces == numberOfPieces);
    }

}
