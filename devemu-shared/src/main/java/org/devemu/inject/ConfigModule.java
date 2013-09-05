package org.devemu.inject;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Blackrush
 */
public class ConfigModule extends AbstractModule {
    public static ConfigModule of(Config config) {
        return new ConfigModule(config);
    }

    private final Config config;

    public ConfigModule(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(Config.class).toInstance(config);
        bindListener(Matchers.any(), fieldInjector);
    }

    private <I> List<Field> findFields(TypeLiteral<I> type) {
        List<Field> fields = Lists.newArrayList();

        for (Field field : type.getRawType().getFields()) {
            if (field.isAnnotationPresent(InjectConfig.class)) {
                fields.add(field);
            }
        }

        return fields;
    }

    private <I> void inject(I instance, List<Field> fields) {
        for (Field field : fields) {
            InjectConfig annotation = field.getAnnotation(InjectConfig.class);

            ConfigValue value = config.getValue(annotation.value());
            try {
                field.set(instance, value.unwrapped());
            } catch (IllegalAccessException e) {
                addError(e);
            }
        }
    }

    private final TypeListener fieldInjector = new TypeListener() {
        @Override
		public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
            final List<Field> fields = findFields(type);

            encounter.register(new MembersInjector<I>() {
                @Override
				public void injectMembers(I instance) {
                    inject(instance, fields);
                }
            });
        }
    };
}
