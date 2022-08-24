package ru.pylaev.toDoProject.businessLogicLayer;

import ru.pylaev.toDoProject.ToDoMain;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.util.InputChecker;

import java.util.List;

public enum LogicStep {
    ASK_OWNER(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askOwner")),
    ASK_NUMBER(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNumber")) {
        @Override
        void manageState(String userInput, UiStateModel uiStateModel, TaskRepository taskRepository) {
            List<Task> tasksList = taskRepository.getAll(uiStateModel.getOwner());
            int index = validateIndex(userInput, tasksList.size());
            if (index == 0) {
                uiStateModel.setStep(LogicStep.ASK_NEW);
            }
            else if (index > 0) {
                uiStateModel.setStep(LogicStep.ASK_STATUS);
                uiStateModel.setCurrentTaskIndex(index);
            }
        }
    },
    ASK_NEW(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askNew")) {
        @Override
        void manageState(String userInput, UiStateModel uiStateModel, TaskRepository taskRepository) {
            taskRepository.saveNewTask(uiStateModel.getOwner(), userInput);
            uiStateModel.setStep(LogicStep.ASK_NUMBER);
        }
    },
    ASK_STATUS(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("askStatus")) {
        @Override
        void manageState(String userInput, UiStateModel uiStateModel, TaskRepository taskRepository) {
            int changeStatusResult = taskRepository.updateTask(uiStateModel.getOwner(), userInput, uiStateModel.getCurrentTaskIndex());
            if (changeStatusResult>0) {
                uiStateModel.setStep(LogicStep.ASK_NUMBER);
            }
            else if (changeStatusResult==0) {
                uiStateModel.setStep(LogicStep.ASK_NEW);
            }
        }
    };

    private final String content;

    LogicStep(String content) {
        this.content = content;
    }

    void manageState (String userInput, UiStateModel uiStateModel, TaskRepository taskRepository) {}

    @Override
    public String toString() {
        return content;
    }

    private static int validateIndex(String userInput, int size) {
        if (size==0 || userInput.equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("commandNew"))) {
            return 0;
        }
        else if (!userInput.equals(ToDoMain.CUSTOM_PROPERTIES.getPropertyContent("commandBack"))) {
            var taskIndex = InputChecker.isValidIndex(userInput, size);
            if (taskIndex>-1) return taskIndex;
        }
        return -1;
    }
}
