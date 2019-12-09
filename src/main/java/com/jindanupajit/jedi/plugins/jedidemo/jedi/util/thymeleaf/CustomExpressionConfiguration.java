package com.jindanupajit.jedi.plugins.jedidemo.jedi.util.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Configuration
public class CustomExpressionConfiguration {

    @Bean
    public CustomExpressionObjectDialect customExpressionObjectDialect() {
        return new CustomExpressionObjectDialect();
    }


    private static class CustomExpressionObjectDialect implements IExpressionObjectDialect {

        private final IExpressionObjectFactory EXPRESSION_OBJECT_FACTORY = new CustomExpressionObjectFactory();

        @Override
        public String getName() {
            return "Info";
        }

        @Override
        public IExpressionObjectFactory getExpressionObjectFactory() {
            return EXPRESSION_OBJECT_FACTORY;
        }
    }

    private static class CustomExpressionObjectFactory implements IExpressionObjectFactory {

        protected static final Set<String> ALL_EXPRESSION_OBJECT_NAMES =
                Collections.unmodifiableSet(
                        new LinkedHashSet<>(
                                Collections.singletonList(Info.expressionObjectName)
                        )
                );

        @Override
        public boolean isCacheable(String expressionObjectName) {
            return true;
        }

        @Override
        public Set<String> getAllExpressionObjectNames() {
            return ALL_EXPRESSION_OBJECT_NAMES;
        }

        @Override
        public Object buildObject(IExpressionContext context, String expressionObjectName) {
            if (Info.expressionObjectName.equals(expressionObjectName)) {
                return new Info();
            }
            return null;
        }

    }


}

