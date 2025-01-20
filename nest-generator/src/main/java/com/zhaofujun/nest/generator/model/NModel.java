package com.zhaofujun.nest.generator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NModel extends NClass {
    private final List<NMethod> methods;
    private final List<NField> fields;
    private NModel parent;
    private final List<NModel> children;

    public NModel(String packageName, String name ) {
        super(packageName, name);
        this.methods = new ArrayList<NMethod>();
        this.fields = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void addMethod(NMethod method) {
        methods.add(method);
    }

    public void addMethods(NMethod... methods) {
        for (NMethod method : methods) {
            addMethod(method);
        }
    }

    public void addField(NField field) {
        fields.add(field);
    }

    public void addFields(NField... fields) {
        for (NField field : fields) {
            addField(field);
        }
    }

}
