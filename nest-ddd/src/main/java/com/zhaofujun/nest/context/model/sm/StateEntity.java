//package com.zhaofujun.nest.context.model.sm;
//
//import com.zhaofujun.nest.context.model.BaseEntity;
//import com.zhaofujun.nest.context.model.AbstractIdentifier;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Target;
//import java.util.List;
//
//public abstract class StateEntity<T extends AbstractIdentifier> extends BaseEntity<T> {
//    private BaseState currentState;
//
//    //@State(source = {EnumState.A, EnumState.B})
//    public void stateConfig(StateConfiguration configuration) {
//        configuration.with(null)
//                .invoke("delete", currentState)
//                .invoke("currentState", currentState);
//        List<String> ss = null;
//
//        configuration.with(null);
//    }
//}
//
//@Target(ElementType.METHOD)
//@interface State {
//    EnumState[] source();
//
//    Enum success();
//
//    Enum fail();
//}
//
//enum EnumState {
//    A, B
//}
