package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

    @Mock //mock para o repositorio de tarefas
    private TaskRepo taskRepo; //repositorio de tarefas

    @InjectMocks //injeta o mock no controller
    private TaskController controller; //controller de tarefas  

    @Before //inicializa o mock antes de cada teste
    public void setup() { //inicializa o mock
        MockitoAnnotations.initMocks(this); //inicializa o mock
    }

    private String message = "Deveria ter lançado uma ValidationException";

    @Test
    public void naoDeveSalvarTaskSemDescricao() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail(message);
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskSemData() {
        Task todo = new Task();
        todo.setTask("Descrição 01");
        try {
            controller.save(todo);
            Assert.fail(message);
        } catch (ValidationException e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTaskComDataPassada() {
        Task todo = new Task();
        todo.setTask("Descrição 02");
        todo.setDueDate(LocalDate.of(2010, 1, 1));
        try {
            controller.save(todo);
            Assert.fail(message);
        } catch (ValidationException e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTaskComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setTask("Descrição 03");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo); //verifica se o metodo save foi chamado
    }

    @Test
    public void deveRemoverTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setTask("Descrição 04");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        controller.delete(todo.getId());
        Mockito.verify(taskRepo).deleteById(todo.getId());
    }
}
