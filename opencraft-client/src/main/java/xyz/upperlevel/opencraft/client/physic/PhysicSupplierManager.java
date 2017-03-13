package xyz.upperlevel.opencraft.client.physic;

import lombok.NonNull;

import java.util.*;

public class PhysicSupplierManager {

    private Map<String, PhysicSupplier> suppliers = new HashMap<>();

    private Map<String, PhysicSupplier> enabled = new HashMap<>();

    public PhysicSupplierManager() {
    }

    public void register(@NonNull PhysicSupplier supplier) {
        suppliers.put(supplier.getId(), supplier);
    }

    public boolean unregister(String id) {
        return suppliers.remove(id) != null;
    }

    public boolean unregister(@NonNull PhysicSupplier supplier) {
        return suppliers.remove(supplier.getId()) != null;
    }

    public PhysicSupplier getSupplier(String id) {
        return suppliers.get(id);
    }

    public Collection<PhysicSupplier> getSuppliers() {
        return suppliers.values();
    }

    public void enable(@NonNull PhysicSupplier supplier) {
        enabled.put(supplier.getId(), supplier);
    }

    public void enableOne(@NonNull PhysicSupplier supplier) {
        enabled.clear();
        enable(supplier);
    }

    public boolean disable(String id) {
        return enabled.remove(id) != null;
    }

    public boolean disable(@NonNull PhysicSupplier supplier) {
        return enabled.remove(supplier.getId()) != null;
    }

    public Collection<PhysicSupplier> getEnabled() {
        return enabled.values();
    }
}