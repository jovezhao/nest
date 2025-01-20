package com.zhaofujun.nest.generator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NAggregateRoot extends NEntity{

    public NAggregateRoot(String packageName, String name ) {
        super(packageName, name);
    }
}
