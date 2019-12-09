package com.jindanupajit.jedi.plugins.jedidemo.jedi;

import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention.DLP;
import com.jindanupajit.jedi.plugins.jedidemo.jedi.security.DataLossPrevention.DiodeMode;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Just Enough Data and Information (JEDI)
 *
 * <p>Filter data by @annotation and return new entity</p>
 */
public class Jedi<ENTITY>   {

    private ENTITY entity;

    private Jedi(Class entityClass) {
        try {
            this.entity = (ENTITY) Objects.requireNonNull(entityClass.newInstance());
        } catch (InstantiationException | IllegalAccessException ignored) {
        }
    }

    private ENTITY get() {
        return entity;
    };

    public static <E> E readFrom(E entity) {
        return Jedi.toJedi(entity).toEntity();
    }

    public Object getId() {

        Class getterClass = entity.getClass();

        for (Method getter : getterClass.getMethods()) {
            String methodName = getter.getName();

            if (methodName.length()>3 && methodName.startsWith("get") && getter.getParameterCount()==0) {


                Method setter;
                String variableName = methodName.substring(3);
                String setterName = "set"+variableName;
                Field getterField = null;

                variableName = variableName.substring(0,1).toLowerCase()
                        +(variableName.length()>1?variableName.substring(1):"");
                try {
                    if (getter.isAnnotationPresent(Id.class)) {
                        return getter.invoke(entity);
                    }


                    getterField = getterClass.getDeclaredField(variableName);
                    if (getterField.isAnnotationPresent(Id.class)) {
                        return getter.invoke(entity);
                    }
                } catch (NoSuchFieldException ignored) {
                    // No field but maybe getter/setter

                } catch (IllegalAccessException | InvocationTargetException ignored) {

                }
                try {
                    setter = getterClass.getMethod(setterName, getter.getReturnType());

                    if (setter.isAnnotationPresent(Id.class)) {
                        return getter.invoke(entity);
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {

                }

            }
        }
        return null;
    }

    /**
     * Prepare entity for CREATE TO DATABASE
     * @return the ENTITY of this JEDI
     */
    public ENTITY toEntity() {
        Class setterClass = this.get().getClass();
        ENTITY entity = null;
        try {
            entity = (ENTITY) setterClass.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        Class getterClass = this.get().getClass();

        for (Method setter : setterClass.getMethods()) {
            String methodName = setter.getName();

            if (methodName.length()>3 && methodName.startsWith("set") && setter.getParameterCount()==1) {
                Method getter;
                String variableName = methodName.substring(3);
                try {
                    Field setterField = setterClass.getField(variableName.substring(0,1).toLowerCase()
                            +(variableName.length()>1?variableName.substring(1):""));

                    if (!DiodeMode.canWriteOnce(setterField))
                        continue;
                } catch (NoSuchFieldException ignored) {
                    // No field but maybe getter/setter
                }



                try {
                    getter = getterClass.getMethod("get"+variableName);
                } catch (NoSuchMethodException ignored) {
                    // No getter
                    continue;
                }

                if (!DiodeMode.canWriteOnce(setter) || !DiodeMode.canWriteOnce(getter))
                    // Not writeable
                    continue;

                if (getter.getReturnType() != setter.getParameterTypes()[0])
                    // Mismatch parameter type
                    continue;

                try {
                    setter.invoke(entity, getter.invoke(this.get()) );
                } catch (IllegalAccessException | InvocationTargetException ignored) {
                    // Write error
                }

            }
        }

        return entity;
    }

    /**
     * Prepare entity for MERGE TO DATABASE


     */
    public <E> ENTITY writeTo(E entity) {

        Class getterClass = get().getClass();

        Class setterClass = entity.getClass();

        for (Method setter : setterClass.getMethods()) {
            String methodName = setter.getName();

            if (methodName.length()>3 && methodName.startsWith("set") && setter.getParameterCount()==1) {
                System.out.println(methodName);
                Method getter;
                String variableName = methodName.substring(3);
                String getterName = "get"+variableName;
                Field getterField = null;
                Field setterField = null;
                variableName = variableName.substring(0,1).toLowerCase()
                        +(variableName.length()>1?variableName.substring(1):"");
                try {
                    getterField = getterClass.getDeclaredField(variableName);
                    setterField = setterClass.getDeclaredField(variableName);
                    if (!DiodeMode.canWrite(setterField))
                        continue;

                } catch (NoSuchFieldException ignored) {
                    // No field but maybe getter/setter
                }



                try {
                    getter = getterClass.getMethod(getterName);
                } catch (NoSuchMethodException ignored) {
                    continue;
                }

                if (!DiodeMode.canWrite(setter) || !DiodeMode.canWrite(getter)) {
                    // Not writeable
                    continue;
                }

                if (getter.getReturnType() != setter.getParameterTypes()[0])
                    // Mismatch parameter type
                    continue;

                try {

                    if ( (DLP.getDiode(setterField) == DiodeMode.WRITE_ENCODED)
                            || (DLP.getDiode(setter) == DiodeMode.WRITE_ENCODED)
                            || (DLP.getDiode(getter) == DiodeMode.WRITE_ENCODED) ) {
                        Class encoderClass = DLP.getEncoder(getter, getterField, setter);
                        PasswordEncoder encoder = (PasswordEncoder) encoderClass.newInstance();
                        setter.invoke(get(), encoder.encode((CharSequence) getter.invoke(entity)));
                    } else {
                        setter.invoke(get(), getter.invoke(entity));
                    }
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException ignored) {
                    // Write error
                }

            }
        }

        return toEntity();
    }



    /**
     * Prepare jedi
     * @param entity entity to be converted to JEDI
     * @return JEDI
     */
    public static <E> Jedi<E> toJedi(E entity) {

        Class getterClass = entity.getClass();

        Jedi<E> jedi = new Jedi<E> (entity.getClass());

        Class setterClass = jedi.get().getClass();

        System.out.printf("From Entity: %s(%s) -> %s\n", getterClass.getSimpleName(), entity, jedi.get().getClass().getSimpleName());

        for (Method getter : getterClass.getMethods()) {
            String methodName = getter.getName();

            if (methodName.length()>3 && methodName.startsWith("get") && getter.getParameterCount()==0) {
                System.out.println(methodName);
                Method setter;
                String variableName = methodName.substring(3);
                String setterName = "set"+variableName;
                Field getterField = null;
                Field setterField = null;
                variableName = variableName.substring(0,1).toLowerCase()
                        +(variableName.length()>1?variableName.substring(1):"");
                try {
                    getterField = getterClass.getDeclaredField(variableName);
                    setterField = setterClass.getDeclaredField(variableName);
                    if (!DiodeMode.canRead(getterField))
                        continue;

                } catch (NoSuchFieldException ignored) {
                    // No field but maybe getter/setter
                    System.out.printf("No field %s\n",variableName);
                }



                try {
                    setter = setterClass.getMethod(setterName,getter.getReturnType() );
                } catch (NoSuchMethodException ignored) {
                    System.out.printf("No Setter set%s\n", variableName);
                    continue;
                }

                if (!DiodeMode.canRead(setter) || !DiodeMode.canRead(getter)) {
                    System.out.printf("Type mismatch %s\n", variableName);
                    // Not writeable
                    continue;
                }

                if (getter.getReturnType() != setter.getParameterTypes()[0])
                    // Mismatch parameter type
                    continue;

                try {
                    System.out.printf("Setting %s\n", variableName);

                    if ( (DLP.getDiode(getterField) == DiodeMode.READ_ENCODED)
                            || (DLP.getDiode(setter) == DiodeMode.READ_ENCODED)
                            || (DLP.getDiode(getter) == DiodeMode.READ_ENCODED) ) {
                        Class encoderClass = DLP.getEncoder(getter, getterField, setter);
                        PasswordEncoder encoder = (PasswordEncoder) encoderClass.newInstance();
                        setter.invoke(jedi.get(), encoder.encode((CharSequence) getter.invoke(entity)));
                    } else {
                        setter.invoke(jedi.get(), getter.invoke(entity));
                    }
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException ignored) {
                    // Write error
                }

            }
        }

        System.out.println("To Jedi: "+jedi.get());
        return jedi;
    }

    public static <E> Jedi<E> asJedi(E entity) {
        Class getterClass = entity.getClass();

        Jedi<E> jedi = new Jedi<E> (entity.getClass());

       jedi.entity = entity;

       return jedi;
    }

}
