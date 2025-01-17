package com.zhaofujun.nest.generator.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NMethod{
    private String name;
    private NClass returnType;
    private List<NField> parameters;

    public NMethod(String name, NClass returnType) {
        this.name = name;
        this.returnType = returnType;
        parameters = new ArrayList<NField>();
    }
    public void addParameter(NField parameter) {
        parameters.add(parameter);
    }
    public void addParameters(NField... parameters) {
        for (NField parameter : parameters) {
            addParameter(parameter);
        }
    }

}
