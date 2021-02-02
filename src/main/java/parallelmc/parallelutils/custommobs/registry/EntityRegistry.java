package parallelmc.parallelutils.custommobs.registry;

import net.minecraft.server.v1_16_R3.Entity;
import parallelmc.parallelutils.Parallelutils;
import parallelmc.parallelutils.custommobs.nmsmobs.EntityPair;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

public class EntityRegistry {

	private HashMap<String, EntityPair> entities;

	private static EntityRegistry registry;

	private EntityRegistry() {
		entities = new HashMap<>();
	}

	public static EntityRegistry getInstance() {
		if (registry == null) {
			registry = new EntityRegistry();
		}

		return registry;
	}

	public void registerEntity(String uuid, String type, Entity entity) {
		Parallelutils.log(Level.INFO, "Registering entity " + uuid);
		entities.put(uuid, new EntityPair(type, entity));
	}

	public EntityPair getEntity(String uuid) {
		return entities.get(uuid);
	}

	public Collection<String> getUUIDs() {
		return entities.keySet();
	}

	public Collection<EntityPair> getEntities() {
		return entities.values();
	}

	public EntityPair removeEntity(String uuid) {
		Parallelutils.log(Level.INFO, "Removing entity " + uuid);
		return entities.remove(uuid);
	}

	public boolean containsEntity(String uuid) {
		Parallelutils.log(Level.ALL, "Does contain " + uuid);
		return entities.containsKey(uuid);
	}
}
