package parallelmc.parallelutils.modules.custommobs.nmsmobs;

import net.minecraft.server.v1_16_R3.EntityInsentient;
import net.minecraft.server.v1_16_R3.PathfinderGoalSelector;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * A helper class with some common methods for managing NMS entities
 */
public class CustomEntityHelper {

	/**
	 * Clears the pathfinding goals for an entity
	 * @param entity The entity to clear the pathfinding goals of
	 */
	public static void clearGoals(EntityInsentient entity) {
		Map goalC = (Map) getPrivateField("c", PathfinderGoalSelector.class, entity.goalSelector);
		goalC.clear();
		Set goalD = (Set) getPrivateField("d", PathfinderGoalSelector.class, entity.goalSelector);
		goalD.clear();
		Map targetC = (Map) getPrivateField("c", PathfinderGoalSelector.class, entity.targetSelector);
		targetC.clear();
		Set targetD = (Set) getPrivateField("d", PathfinderGoalSelector.class, entity.targetSelector);
		targetD.clear();
	}

	/**
	 * Returns the object associated with a private field of an object using reflection.
	 * @param fieldName The name of the private field to get
	 * @param clazz The class containing the private field
	 * @param object The object to pull the private field from
	 * @return Returns the object associated with the given private field
	 */
	public static Object getPrivateField(String fieldName, Class clazz, Object object) {
		Field field;
		Object o = null;

		try {
			field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			o = field.get(object);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
}