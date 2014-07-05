package com.w00tmast3r.skquery.elements.packet;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.w00tmast3r.skquery.api.Patterns;
import com.w00tmast3r.skriptaddon.skaddonlib.util.Reflection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


@Patterns("display credits to %player%")
public class EffShowCredits extends Effect {

    private Expression<Player> player;

    @Override
    protected void execute(Event event) {
        Player p = player.getSingle(event);
        if(p == null) return;
        try {
            Constructor packetConstructor = Reflection.nmsClass("PacketPlayOutGameStateChange").getConstructor(int.class, int.class);
            Object packet = packetConstructor.newInstance(4, 0);
            Reflection.sendPacket(packet, p);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "credits";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) expressions[0];
        return true;
    }
}
