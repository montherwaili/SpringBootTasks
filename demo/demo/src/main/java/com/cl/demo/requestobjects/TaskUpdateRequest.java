package com.cl.demo.requestobjects;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class TaskUpdateRequest {
    private UUID uuid;
    private String titleToUpdate;
    private String descriptionToUpdate;
    private String taskStatusToUpdate;
    private Date dueDateToUpdate;
    private Boolean isAssignedToUpdate;
}
