package ml.northwestwind.guadrianfishing.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingHook.class)
public class MixinFishingHook {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z", ordinal = 0), method = "retrieve")
    public boolean replaceEntity(Level instance, Entity entity) {
        if (instance.random.nextDouble() < 0.1) {
            Entity newEntity;
            double chance = instance.random.nextDouble();
            if (chance > 0.4) {
                if (instance.random.nextBoolean()) newEntity = new Cod(EntityType.COD, instance);
                else newEntity = new Salmon(EntityType.SALMON, instance);
            } else if (chance > 0.2) newEntity = new Boat(EntityType.BOAT, instance);
            else if (chance > 0.1) newEntity = new Squid(EntityType.SQUID, instance);
            else if (chance > 0.025) newEntity = new Guardian(EntityType.GUARDIAN, instance);
            else newEntity = new ElderGuardian(EntityType.ELDER_GUARDIAN, instance);
            newEntity.setPos(entity.position());
            newEntity.setDeltaMovement(entity.getDeltaMovement());
            return instance.addFreshEntity(newEntity);
        }
        return instance.addFreshEntity(entity);
    }
}
