package com.jindanupajit.jedi.plugins.jedidemo.jedi.util.thymeleaf;

import com.jindanupajit.starter.util.Verbose;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class Info {
    public static final String expressionObjectName = "info";


    public static final ActionType PERSIST = ActionType.PERSIST;
    public static final ActionType MERGE = ActionType.MERGE;
    public static final ActionType DELETE = ActionType.DELETE;
    public static final ActionType LOGIN = ActionType.LOGIN;
    public static final ActionType LIST = ActionType.LIST;
    public static final ActionType VIEW = ActionType.VIEW;
    public static final ActionType SEARCH = ActionType.SEARCH;

    public String classNameOf(Object o) {
        return o.getClass().getName();
    }

    public String typeOf(Object o) {
        String type = classNameOf(o).replace(".","-");
        Verbose.printlnf("#info::typeOf(...) = '%s'", type);
        return type;
    }

    public List<FieldInfo> getAllSettableFieldOf(Object o) {
        Verbose.printlnf("Lookup settable fields of '%s'", o.getClass().getName());
        HashSet<FieldInfo> settableFields = new HashSet<>();
        for(Method method : o.getClass().getDeclaredMethods()){
            String name = method.getName();

            if (!name.startsWith("set") || (name.length() < 4) || (method.getParameterTypes().length != 1))
                continue;

            Class type = method.getParameterTypes()[0];

            String fieldName = name.substring(3, 4).toLowerCase() + name.substring(4);

            try {
                Field field = o.getClass().getDeclaredField(fieldName);
                if (field.getType() != type)
                    continue;

                if (Modifier.isPublic(method.getModifiers())) {
                    FieldInfo fieldInfo = new FieldInfo(fieldName, type.getName().replace(".","-"));

                    // JPA
                    fieldInfo.setId(field.isAnnotationPresent(Id.class));
                    fieldInfo.setGeneratedValue(field.isAnnotationPresent(GeneratedValue.class));

                    // Validation
                    fieldInfo.setNotNull(field.isAnnotationPresent(NotNull.class));
                    fieldInfo.setNotEmpty(field.isAnnotationPresent(NotEmpty.class));
                    fieldInfo.setNotBlank(field.isAnnotationPresent(NotBlank.class));

                    if  (field.isAnnotationPresent(Min.class)) {
                        fieldInfo.setMin(true);
                        fieldInfo.setMinValue(field.getDeclaredAnnotation(Min.class).value());
                    }

                    if  (field.isAnnotationPresent(Max.class)) {
                        fieldInfo.setMax(true);
                        fieldInfo.setMaxValue(field.getDeclaredAnnotation(Max.class).value());
                    }

                    if  (field.isAnnotationPresent(Size.class)) {
                        fieldInfo.setSize(true);
                        fieldInfo.setSizeMinValue(field.getDeclaredAnnotation(Size.class).min());
                        fieldInfo.setSizeMaxValue(field.getDeclaredAnnotation(Size.class).max());
                    }

                    if (field.isAnnotationPresent(UserInput.class)) {
                        UserInput userInput = field.getDeclaredAnnotation(UserInput.class);
                        fieldInfo.setOrdinal(userInput.Ordinal());
                        fieldInfo.setLabel(userInput.Label());
                        fieldInfo.setPlaceHolder(userInput.PlaceHolder());
                        fieldInfo.setSecret(userInput.Secret());
                    }
                    Verbose.printlnf("Found '%s' with type '%s'", fieldInfo.getName(), fieldInfo.getType());
                    settableFields.add(fieldInfo);
                }
            } catch (NoSuchFieldException ignored) { }
        }

        ArrayList<FieldInfo> sortedSettableFields = new ArrayList<>(settableFields);
        Collections.sort(sortedSettableFields);
        Verbose.printlnf("Lookup end.");
        return sortedSettableFields;
    }

    public ActionMapping getActionHandler(Object obj, ActionType actionType) {
        if (obj == null)
            return null;
        ActionMapping[] actionMappings = obj.getClass().getDeclaredAnnotationsByType(ActionMapping.class);

        Verbose.printlnf("ActionHandler lookup '%s' for '%s'", actionType.name(), obj.getClass().getName());
        Verbose.printlnf("The ActionMapping has %d record(s). ",actionMappings.length);
        for (ActionMapping actionMapping : actionMappings) {

            List<ActionType> actionTypes = Arrays.asList(actionMapping.Action());
            if (actionTypes.contains(actionType)) {
                Verbose.printlnf("Found ActionHandler for '%s' at '%s' (%s)",
                        actionType.name(),
                        actionMapping.Url(), actionMapping.Method());
                return actionMapping;
            }

        }
        Verbose.printlnf("No ActionHandler for '%s' found!", actionType.name());
        return null;
    }

    private String typeResolver(Class cls) {
        if (String.class.equals(cls)) {
            return "String";
        } else if (Boolean.class.equals(cls)) {
            return "Boolean";
        } else if (Character.class.equals(cls)) {
            return "Character";
        } else if (Byte.class.equals(cls)) {
            return "Byte";
        } else if (Integer.class.equals(cls)) {
            return "Integer";
        } else if (Long.class.equals(cls)) {
            return "Long";
        } else if (Double.class.equals(cls)) {
            return "Double";
        } else if (Float.class.equals(cls)) {
            return "Float";
        } else if (Short.class.equals(cls)) {
            return "Short";
        }
        return cls.getName();
    }

}
