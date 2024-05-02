package com.example.mixin;


import com.example.AntiCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(CreeperEntity.class)
public class CreeperMixin {
    @Redirect(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    public Explosion createExplosion(World world, @Nullable Entity entity, double x, double y, double z, float power, World.ExplosionSourceType explosionSourceType) {
        return world.createExplosion(entity, x, y, z, power, getCreeperExplosionSourceType());
    }

    @Unique
    private static World.ExplosionSourceType getCreeperExplosionSourceType() {
        return AntiCreeper.getConfig().getDestroy() ? World.ExplosionSourceType.MOB : World.ExplosionSourceType.NONE;
    }
}
