package com.example.mixin;


import com.example.AntiCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;


@Mixin(Explosion.class)
public abstract class ExplosionMixin {
	@Unique
	private static final int CIRCLE = 0;
	@Unique
	private static final int RECTANGLE = 1;

	@Final
	@Shadow private Entity entity;


	@Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z"))
	private boolean addWithException(Set<BlockPos> instance, Object o) {
		if (getProtectedAreasEnabled() && entity.getType() == EntityType.CREEPER && isProtected((BlockPos) o)) {
			return false;
		}
		return instance.add((BlockPos) o);
	}

	@Unique
	private static boolean isProtected(BlockPos pos) {
		boolean isProtected = false;

		for (int i = 0; i < AntiCreeper.getConfig().getAreas().size() && !isProtected; i++) {
			float x = pos.getX();
			float z = pos.getZ();

			float X = getX(i);
			float Z = getZ(i);
			float R = getR(i);
			float LX = getLX(i);
			float LZ = getLZ(i);

			if (getType(i) == CIRCLE) {
				isProtected = ((x - X) * (x - X) + (z - Z) * (z - Z)) <= (R * R);
			} else if (getType(i) == RECTANGLE) {
				isProtected = ((x >= X) && (x <= X + LX)) && ((z >= Z) && (z <= Z + LZ));
			}
		}

		return isProtected;
	}

	@Unique
	private static boolean getProtectedAreasEnabled() {
		return AntiCreeper.getConfig().getProtectAreas();
	}
	@Unique
	private static float getX1(int i) {
		return Float.parseFloat(AntiCreeper.getConfig().getAreas().get(i)[0]);
	}
	@Unique
	private static float getX2(int i) {
		return Float.parseFloat(AntiCreeper.getConfig().getAreas().get(i)[2]);
	}
	@Unique
	private static float getZ1(int i) {
		return Float.parseFloat(AntiCreeper.getConfig().getAreas().get(i)[1]);
	}
	@Unique
	private static float getZ2(int i) {
		return Float.parseFloat(AntiCreeper.getConfig().getAreas().get(i)[3]);
	}
	@Unique
	private static float getX(int i) { // relevant x coordinate for the shape
		float x1 = getX1(i);
		float x2 = getX2(i);

		if (getType(i) == CIRCLE) {
			return x1 + (x2-x1)/2;
		}
		if (getType(i) == RECTANGLE) {
			return Math.min(x1, x2);
		}

		return x1;
	}
	@Unique
	private static float getZ(int i) { // relevant z coordinate for the shape
		float z1 = getZ1(i);
		float z2 = getZ2(i);

		if (getType(i) == CIRCLE) {
			return z1 + (z2-z1)/2;
		}
		if (getType(i) == RECTANGLE) {
			return Math.min(z1, z2);
		}

		return z1;
	}
	@Unique
	private static float getR(int i) { // radius of the circle
		float X = getX(i);
		float Z = getZ(i);

		return (float) Math.sqrt(X*X+Z*Z);
	}
	@Unique
	private static float getLX(int i) { // length of the rectangle along the x-axis
		return Math.abs(getX2(i)-getX1(i));
	}
	@Unique
	private static float getLZ(int i) { // length of the rectangle along the z-axis
		return Math.abs(getZ2(i)-getZ1(i));
	}
	@Unique
	private static int getType(int i) {
		return switch (AntiCreeper.getConfig().getAreas().get(i)[4]) {
			case "circle" -> 0;
			case "rectangle" -> 1;
			default -> -1;
		};
	}
}