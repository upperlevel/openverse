package xyz.upperlevel.opencraft.client.physic;

import lombok.NonNull;

import java.util.*;

public class PhysicBondManager {

    private Map<String, PhysicBond> bonds = new HashMap<>();

    private Map<String, PhysicBond> enabled = new HashMap<>();

    public PhysicBondManager() {
    }

    public void register(@NonNull PhysicBond bond) {
        bonds.put(bond.getId(), bond);
    }

    public boolean unregister(String id) {
        return bonds.remove(id) != null;
    }

    public boolean unregister(@NonNull PhysicBond bond) {
        return bonds.remove(bond.getId()) != null;
    }

    public PhysicBond getBond(String id) {
        return bonds.get(id);
    }

    public Collection<PhysicBond> getBonds() {
        return bonds.values();
    }

    public void enable(@NonNull PhysicBond bond) {
        enabled.put(bond.getId(), bond);
    }

    public void enableOne(@NonNull PhysicBond bond) {
        enabled.clear();
        enable(bond);
    }

    public boolean disable(String id) {
        return enabled.remove(id) != null;
    }

    public boolean disable(@NonNull PhysicBond bond) {
        return enabled.remove(bond.getId()) != null;
    }

    public Collection<PhysicBond> getEnabled() {
        return enabled.values();
    }
}