package ru.pylaev.toDoProject.presentLayer.runnable.view;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.pylaev.toDoProject.ToDoMain;

import javax.swing.*;
import java.util.List;

@Component
public class WindowView extends View {
    @Getter private final JTextField textField = new JTextField(72);
    private final JPanel panel = new JPanel();
    private final JFrame mainFrame = new JFrame();
    @Getter private String userInput;

    {
        mainFrame.setTitle(ToDoMain.PROPERTIES.get("windowTitle"));
        mainFrame.setDefaultCloseOperation((WindowConstants.DISPOSE_ON_CLOSE));
        mainFrame.setBounds(300, 300, 900, 400);
        mainFrame.add(panel);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(e -> {
            userInput = textField.getText();
            textField.setText("");
        });
    }

    @Override
    public void setNull() {
        this.userInput = null;
    }

    @Override
    public void display(String content) {
        panel.removeAll();
        panel.add(stringToJScrollPane(content));
        panel.add(textField);
        panel.repaint();
        mainFrame.setVisible(true);
        textField.grabFocus();
    }

    private static JScrollPane stringToJScrollPane(String content) {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        dlm.addAll(List.of(content.split("\n")));
        JList<String> jList = new JList<>(dlm);
        jList.setFixedCellWidth(790);
        return new JScrollPane(jList);
    }
}