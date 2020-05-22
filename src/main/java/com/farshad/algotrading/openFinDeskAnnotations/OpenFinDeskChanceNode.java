package com.farshad.algotrading.openFinDeskAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OpenFinDeskChanceNode {
    int containerId();
    int nodeId();
    Class[] openfindeskStrategies();

    String[] timeFrames();


    boolean disabled() default false;

}
