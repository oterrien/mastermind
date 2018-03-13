package com.wdg.mastermind.cucumber;

import com.wdg.mastermind.Combination;
import com.wdg.mastermind.Result;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;

public class StepDefinition {

    private Combination combination;
    private Result result;

    @Given("a combination set to '(.*)'")
    public void givenACombination(String combination) {
        this.combination = new Combination.Parser(combination).build();
    }

    @When("user type '(.*)'")
    public void whenUserTypeCombination(String userCombination) {
        this.result = this.combination.compare(userCombination);
    }

    @Then("number of (correct|misplaced) pieces: (.*)")
    public void thenCheckNumberofPieces(String correctOrMisplaced, int num) {
        if (correctOrMisplaced.equals("correct")) {
            Assertions.assertThat(result.getNumberOfCorrectPieces()).isEqualTo(num);
        } else {
            Assertions.assertThat(result.getNumberOfMismatchPieces()).isEqualTo(num);
        }
    }


}
