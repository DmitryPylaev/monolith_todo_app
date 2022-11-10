package ru.pylaev.toDoProject.presentLayer.ui;

import ru.pylaev.toDoProject.presentLayer.abstractions.IController;
import ru.pylaev.toDoProject.presentLayer.abstractions.RunnableUiFactory;

public class RunnableUI extends PlainUI implements Runnable {
    private final IController controller;
    public boolean active = true;

    public RunnableUI(RunnableUiFactory factory) {
        super(factory.getPlainUiFactory());
        this.controller = factory.getController();
        uiStateModel.addObserver(controller);
    }

    @Override
    public final void run() {
        view.show();
        while (active) controller.processUserInput(uiStateModel);
    }
}
