package com.zhaofujun.nest.generator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;
import com.zhaofujun.nest.ddd.AggregateRoot;
import com.zhaofujun.nest.ddd.DomainService;
import com.zhaofujun.nest.ddd.Entity;
import com.zhaofujun.nest.ddd.Identifier;
import com.zhaofujun.nest.ddd.ValueObject;
import com.zhaofujun.nest.generator.model.NAggregateRoot;
import com.zhaofujun.nest.generator.model.NBaseClass;
import com.zhaofujun.nest.generator.model.NClass;
import com.zhaofujun.nest.generator.model.NEntity;
import com.zhaofujun.nest.generator.model.NField;
import com.zhaofujun.nest.generator.model.NIdentifier;
import com.zhaofujun.nest.generator.model.NMethod;
import com.zhaofujun.nest.generator.model.NModel;
import com.zhaofujun.nest.generator.model.NObject;
import com.zhaofujun.nest.generator.model.NService;
import com.zhaofujun.nest.generator.model.NValueObject;
import com.zhaofujun.nest.generator.model.NVoid;
import com.zhaofujun.nest.generator.parser.TypeUtil;

public class GeneratorContext {
    private Map<Class<?>, NModel> nModels = new HashMap<>();
    private List<NModel> nModelTree=new ArrayList<>();

    private NModel parseToModel(Class<?> aClass) {
        if (AggregateRoot.class.isAssignableFrom(aClass)) {
            return new NAggregateRoot(aClass.getPackageName(), aClass.getSimpleName());
        } else if (Entity.class.isAssignableFrom(aClass)) {
            return new NEntity(aClass.getPackageName(), aClass.getSimpleName());
        } else if (Identifier.class.isAssignableFrom(aClass)) {
            return new NIdentifier(aClass.getPackageName(), aClass.getSimpleName());
        } else if (ValueObject.class.isAssignableFrom(aClass)) {
            return new NValueObject(aClass.getPackageName(), aClass.getSimpleName());
        } else if (DomainService.class.isAssignableFrom(aClass)) {
            return new NService(aClass.getPackageName(), aClass.getSimpleName());
        } else {
            return null;
        }
    }

    private NClass parseToClass(Class<?> aClass) {
        NClass nClass = parseToModel(aClass);
        if (nClass == null) {
            if (TypeUtil.isPrimitive(aClass) || TypeUtil.isWrapper(aClass)) {
                nClass = new NBaseClass(aClass.getSimpleName());
            } else if (TypeUtil.isVoid(aClass)) {
                nClass = new NVoid();
            } else {
                nClass = new NObject(aClass.getPackageName(), aClass.getSimpleName());
            }
        }
        return nClass;
    }

    private NModel getModelByClass(Class<?> aClass) {
        NModel nModel = nModels.get(aClass);
        if (nModel == null) {
            nModel = parseToModel(aClass);
            if (nModel != null)
                nModels.put(aClass, nModel);
        }
        return nModel;
    }

    
    /**
     * 扫描指定包名下的所有类，并解析这些类的继承关系、属性和方法，最终构建一个类的模型树。
     * 
     * @param packageName 要扫描的包名
     */
    public void scan(String packageName) {
        // 使用Reflections库扫描指定包名下的所有类
        Reflections reflections = new Reflections(packageName);
        // 遍历所有扫描到的类
        reflections.getSubTypesOf(Object.class).forEach(aClass -> {
            // 将每个类解析为模型并放入nModels中
            nModels.put(aClass, parseToModel(aClass));
        });
        // 解析实体的关系
        nModels.forEach((aClass, nModel) -> {
            // 处理继承关系
            if (aClass.getSuperclass() != null) {
                // 获取父类的模型
                NModel superModel = getModelByClass(aClass.getSuperclass());
                // 设置当前模型的父模型
                nModel.setParent(superModel);
            }
            // 处理属性
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                // 过滤掉静态属性
                if (!Modifier.isStatic(field.getModifiers())) {
                    // 将属性类型解析为模型
                    NClass fieldClass = parseToClass(field.getType());
                    // 将属性添加到当前模型中
                    nModel.addField(new NField(field.getName(), fieldClass));
                }
            }
            // 处理方法
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                // 过滤掉静态方法
                if (!Modifier.isStatic(method.getModifiers())) {
                    // 将返回类型解析为模型
                    NClass returnType = parseToClass(method.getReturnType());
                    // 创建一个新的方法模型
                    NMethod nMethod = new NMethod(method.getName(), returnType);
                    // 遍历方法的参数
                    for (Parameter parameters : method.getParameters()) {
                        // 将参数类型解析为模型并添加到方法模型中
                        nMethod.addParameter(new NField(parameters.getName(), parseToClass(parameters.getType())));
                    }
                    // 将方法添加到当前模型中
                    nModel.addMethod(nMethod);
                }
            }
        });
        // 解析实体的子类关系
        nModels.forEach((aClass, nModel) -> {
            // 如果当前模型没有父模型，则将其添加到模型树的根节点
            if(nModel.getParent()==null)
                nModelTree.add(nModel);
            else
                // 如果当前模型有父模型，则将其添加到父模型的子节点列表中
                nModel.getParent().getChildren().add(nModel);
        });
    }
    
}
