package ru.pylaev.toDoProject.businessLogicLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.pylaev.toDoProject.dataAccessLayer.DAO;
import ru.pylaev.toDoProject.dataAccessLayer.Task;
import ru.pylaev.util.HeadlessSpringBootContextLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = HeadlessSpringBootContextLoader.class)
class TaskRepositoryTest {
    @MockBean
    private DAO tasksDAO;

    @Autowired
    TaskRepository taskRepository;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        var task1 = new Task(3L, "помидоры (tomatoes)", "Wed Mar 25 16:01", "WAIT");
        var task2 = new Task(11L, "гречка (buckwheat)", "Wed Mar 24 16:01", "WAIT");
        var task3 = new Task(14L, "помидоры (tomatoes)", "Thu Mar 23 16:01", "DONE");

        tasks.clear();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        Mockito.when(tasksDAO.findByOwner("user")).thenReturn(tasks);
        Mockito.when(tasksDAO.findById(3L)).thenReturn(Optional.of(task1));
        Mockito.when(tasksDAO.findById(11L)).thenReturn(Optional.of(task2));
        Mockito.when(tasksDAO.findById(14L)).thenReturn(Optional.of(task3));
    }

    @Test
    void getActualTasks() {
        assertEquals(tasks, taskRepository.getAll("user"));
    }

    @Test
    void saveNewIsOk() {
        assertEquals(1, taskRepository.saveNewTask("user", "гречка (buckwheat)", "Wed Mar 25 16:01", "WAIT"));
    }

    @Test
    void saveNewIsRejected() {
        assertEquals(0, taskRepository.saveNewTask("user", "BACK", "Wed Mar 25 16:01", "WAIT"));
    }

    @Test
    void changeStatusOK() {
        assertEquals(1, taskRepository.updateTask("user", "DONE", 1));
    }

    @Test
    void changeStatusBack() {
        assertEquals(1, taskRepository.updateTask("user", "BACK", 1));
    }

    @Test
    void changeStatusReject() {
        assertEquals(-1, taskRepository.updateTask("user", "invalidStatus", 1));
    }
}