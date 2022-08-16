package ru.pylaev.toDoProject.presentLayer.runnableController.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.businessLogicLayer.UiStateModel;
import ru.pylaev.toDoProject.presentLayer.runnableController.CustomController;
import ru.pylaev.toDoProject.presentLayer.runnableController.RunnableUI;
import ru.pylaev.toDoProject.presentLayer.view.View;

@Component
public class TelegramUi extends RunnableUI {

    @Autowired
    public TelegramUi(View view, UiStateModel uiStateModel, TelegramBot bot) {
        super(view, uiStateModel);
        view.setPrinter(bot::send);
        setController(new CustomController(new TelegramInputGetter(bot)));
    }
}
