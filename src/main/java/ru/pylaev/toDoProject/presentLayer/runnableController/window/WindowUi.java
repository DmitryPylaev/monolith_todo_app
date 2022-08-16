package ru.pylaev.toDoProject.presentLayer.runnableController.window;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.UiStateModel;
import ru.pylaev.toDoProject.presentLayer.runnableController.CustomController;
import ru.pylaev.toDoProject.presentLayer.runnableController.RunnableUI;
import ru.pylaev.toDoProject.presentLayer.view.View;

@Component
public class WindowUi extends RunnableUI {

    @Autowired
    public WindowUi(View view, UiStateModel uiStateModel, WindowPrinter windowPrinter) {
        super(view, uiStateModel);
        view.setPrinter(windowPrinter);
        setController(new CustomController(new WindowInputGetter(windowPrinter.getTextField())));
    }
}
