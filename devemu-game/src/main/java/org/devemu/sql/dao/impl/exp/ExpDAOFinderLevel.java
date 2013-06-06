package org.devemu.sql.dao.impl.exp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.devemu.sql.DAOFinder;
import org.devemu.sql.entity.ExpStep;

public class ExpDAOFinderLevel implements DAOFinder<ExpStep> {
    
    private final Map<Long, ExpStep> expSteps = new HashMap<Long, ExpStep>(512);
    private final Collection<ExpStep> synchronizedexpSteps = Collections.synchronizedCollection(Collections.unmodifiableCollection(expSteps.values()));

    @Override
    public ExpStep find(Object o) {
        if (o instanceof Long) {
            return expSteps.get(o);
        }
        throw new IllegalArgumentException("expStepDAOFinderId.find(Object o) : o must be a Long.");
    }

    @Override
    public Collection<ExpStep> findAll() {
        return synchronizedexpSteps;
    }

    @Override
    public ExpStep add(ExpStep expStep) {
        if (expStep == null) {
            return null;
        }
        
        long id = expStep.getLevel();
        if (!expSteps.containsKey(id)) {
            expSteps.put(id, expStep);
        }
        return expStep;
    }

    @Override
    public void remove(ExpStep expStep) {
        if (expStep == null) {
            return;
        }
        
        long id = expStep.getLevel();
        if (expSteps.containsKey(id)) {
            expSteps.remove(id);
        }
    }

    @Override
    public void unload() {
        expSteps.clear();
    }
    
}
