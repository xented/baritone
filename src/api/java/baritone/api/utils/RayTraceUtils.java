/*
 * This file is part of Baritone.
 *
 * Baritone is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Baritone is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Baritone.  If not, see <https://www.gnu.org/licenses/>.
 */

package baritone.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

/**
 * @author Brady
 * @since 8/25/2018
 */
public final class RayTraceUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    private RayTraceUtils() {}

    /**
     * Performs a block raytrace with the specified rotations. This should only be used when
     * any entity collisions can be ignored, because this method will not recognize if an
     * entity is in the way or not. The local player's block reach distance will be used.
     *
     * @param rotation The rotation to raytrace towards
     * @return The calculated raytrace result
     */
    public static RayTraceResult rayTraceTowards(Entity entity, Rotation rotation, double blockReachDistance) {
        Vec3d start = entity.getPositionEyes(1.0F);
        Vec3d direction = RotationUtils.calcVec3dFromRotation(rotation);
        Vec3d end = start.add(
                direction.x * blockReachDistance,
                direction.y * blockReachDistance,
                direction.z * blockReachDistance
        );
        return mc.world.rayTraceBlocks(start, end, false, false, true);
    }

    /**
     * Returns the block that the crosshair is currently placed over. Updated once per render tick.
     *
     * @return The position of the highlighted block
     */
    public static Optional<BlockPos> getSelectedBlock() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
            return Optional.of(mc.objectMouseOver.getBlockPos());
        }
        return Optional.empty();
    }

    /**
     * Returns the entity that the crosshair is currently placed over. Updated once per render tick.
     *
     * @return The entity
     */
    public static Optional<Entity> getSelectedEntity() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            return Optional.of(mc.objectMouseOver.entityHit);
        }
        return Optional.empty();
    }
}
