package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.camunda.bpm.engine.task.Task;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private String id;
    private String name;
    private Boolean flag;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.flag = null;
    }
}
