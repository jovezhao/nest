package com.zhaofujun.nest.boot;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

public class NestBeanScanner extends ClassPathBeanDefinitionScanner {
    private Class<? extends Annotation> annotationClass;
    private Class<?> markerInterface;

    public NestBeanScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }


    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    private void registerFilters() {
        if (this.annotationClass != null)
            this.addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
        if (this.markerInterface != null)
            this.addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        registerFilters();
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
//        processBeanDefinition(beanDefinitions);
        return beanDefinitions;
    }

//    private void processBeanDefinition(Set<BeanDefinitionHolder> beanDefinitions) {
//        beanDefinitions.forEach(beanDefinitionHolder -> {
//            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
//            String beanClassName = beanDefinition.getBeanClassName();
//            try {
//                Class t = Thread.currentThread().getContextClassLoader().loadClass(beanClassName);
//                beanDefinition.setBeanClass(ApplicationServiceFactoryBean.class);
//                beanDefinition.getPropertyValues().add("appServiceType", t);
//                beanDefinition.getPropertyValues().add("commitor", commitor);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
//    }

}
