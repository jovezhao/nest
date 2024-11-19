package com.zhaofujun.nest.ddd.event;

import java.time.LocalDateTime;

import com.zhaofujun.nest.ddd.AggregateRoot;

public class EventProcessRecordModel extends AggregateRoot<ProcessIdentifier> {

    private ProcessState processState;
    private LocalDateTime processTime;

    public EventProcessRecordModel(ProcessIdentifier processIdentifier) {
        super(processIdentifier);
        this.processState = ProcessState.processing;
    }

    public void process() {
        processState = ProcessState.completed;
        processTime = LocalDateTime.now();
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public LocalDateTime getProcessTime() {
        return processTime;
    }

}