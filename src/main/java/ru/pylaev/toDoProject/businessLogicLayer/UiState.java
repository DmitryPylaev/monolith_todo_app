package ru.pylaev.toDoProject.businessLogicLayer;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static ru.pylaev.util.InputChecker.inputInArray;

@Component
@Scope("prototype")
public class UiState {
    public static final String[] INVALID_SYMBOLS = new String[] {" ", "\\", "|", "/", ":", "?", "\"", "<", ">"};

    private Step step = Step.ASK_OWNER;
    private int currentTaskIndex;
    private String owner;

    public Step getStep( ) {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public int getCurrentTaskIndex( ) {
        return currentTaskIndex;
    }

    public void setCurrentTaskIndex(int currentTaskIndex) {
        this.currentTaskIndex = currentTaskIndex;
    }

    public String getOwner ( ) {
        return owner;
    }

    public void setCorrectOwner(String owner) {
        if ((step.equals(Step.ASK_OWNER)) && (inputInArray(owner, INVALID_SYMBOLS) < 0)) {
            this.owner = owner;
            step = Step.ASK_NUMBER;
        }
    }

    public void reset() {
        this.step = Step.ASK_OWNER;
        this.owner = null;
        this.currentTaskIndex = 0;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UiState uiState = (UiState) o;

        if (currentTaskIndex != uiState.currentTaskIndex) return false;
        if (!Objects.equals(step, uiState.step)) return false;
        return Objects.equals(owner, uiState.owner);
    }

    @Override
    public int hashCode ( ) {
        int result = step != null ? step.hashCode() : 0;
        result = 31 * result + currentTaskIndex;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}