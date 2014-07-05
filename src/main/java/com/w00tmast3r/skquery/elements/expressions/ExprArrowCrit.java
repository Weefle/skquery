package com.w00tmast3r.skquery.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.w00tmast3r.skquery.api.PropertyPatterns;
import com.w00tmast3r.skriptaddon.skaddonlib.util.Collect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;


@PropertyPatterns(
        fromType = "entities",
        property = "critical arrow (state|ability|mode)"
)
public class ExprArrowCrit extends SimplePropertyExpression<Entity, Boolean> {
    @Override
    protected String getPropertyName() {
        return "crit";
    }

    @Override
    public Boolean convert(Entity entity) {
        return entity instanceof Arrow ? ((Arrow) entity).isCritical() : null;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) return Collect.asArray(Boolean.class);
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        boolean b = delta != null && (Boolean) delta[0];
        switch (mode) {
            case SET:
                for (Entity en : getExpr().getAll(e))  {
                    if (en instanceof Arrow) ((Arrow) en).setCritical(b);
                }
                break;
            case RESET:
                for (Entity en : getExpr().getAll(e))  {
                    if (en instanceof Arrow) ((Arrow) en).setCritical(false);
                }
            case ADD:
            case REMOVE:
            case REMOVE_ALL:
            case DELETE:
                assert false;
        }
    }
}
