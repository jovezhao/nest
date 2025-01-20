// package com.zhaofujun.nest.generator.parser;

// import com.zhaofujun.nest.generator.model.*;

// import java.lang.reflect.Field;
// import java.lang.reflect.Modifier;

// public class ClassParser {

//     protected NModel parseModel(Class<?> clazz) {
//         String packageName = clazz.getPackage().getName();
//         String className = clazz.getSimpleName();
//         NIdentifier nIdentifier = null;
//         boolean isAbstract = Modifier.isAbstract(clazz.getModifiers());
//         if (TypeUtil.isAggregateRoot(clazz)) {
//             return new NAggregateRoot(packageName, className, nIdentifier, isAbstract);
//         } else if (TypeUtil.isEntity(clazz)) {
//             return new NEntity(packageName, className, nIdentifier, isAbstract);
//         } else if (TypeUtil.isIdentifier(clazz)) {
//             return new NIdentifier(packageName, className);
//         } else if (TypeUtil.isValueObject(clazz)) {
//             return new NValueObject(packageName, className);
//         } else {
//             return new NObject(packageName, className);
//         }
//     }

//     protected NField parseFiled(Field field) {
//         String fieldName = field.getName();
//         Class<?> fieldType = field.getType();
//         NClass fieldClass = parseModel(fieldType);
//         if (fieldClass == null) {
//             //不是模型类型，需要解析是否是基础类型和包装类型
//             if (TypeUtil.isPrimitive(fieldType) || TypeUtil.isWrapper(fieldType)) {
//                 fieldClass = new NClass(null, fieldType.getSimpleName());
//             } else {
//                 //其它可能为对象类型
//                 fieldClass = new NObject(fieldType.getPackageName(), fieldType.getSimpleName());
//             }
//         }

//         return new NField(fieldName, fieldClass);
//     }

// }

