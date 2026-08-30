package com.cl.demo.requestobjects;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TaskCreateRequest {
    private String title;
    private String description;
    private Date dueDate;
    private Date startDate;
    private String taskStatus;
    private Boolean isAssigned;
}
