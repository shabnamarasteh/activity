package com.eshragh.activity;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;

public class MyBeanCopy extends BeanUtilsBean {
    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        System.out.println("---10----");
        if(value == null || value.toString().isEmpty()){
            return;
        }
        super.copyProperty(bean, name, value);
    }

}
