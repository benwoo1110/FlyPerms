package dev.benergy10.flyperms.utils;

import dev.benergy10.flyperms.Constants.FlyState;
import dev.benergy10.flyperms.FlyPerms;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyStateContextCalculator implements ContextCalculator<Player> {

    private static final String KEY = "flyperms:state";
    private final FlyPerms plugin;

    public FlyStateContextCalculator(FlyPerms plugin) {
        this.plugin = plugin;
    }

    @Override
    public void calculate(@NotNull Player target, @NotNull ContextConsumer consumer) {
        FlyState state = FlyState.NO; // this.plugin.getCheckManager().calculateFlyState(target);
        consumer.accept(KEY, state.name());
    }

    @Override
    public ContextSet estimatePotentialContexts() {
        ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
        for (FlyState state : FlyState.values()) {
            builder.add(KEY, state.name());
        }
        return builder.build();
    }
}
