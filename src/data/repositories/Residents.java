package data.repositories;

import data.models.Resident;
import utils.RandomCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class Residents implements ResidentRepo {
    private List<Resident> residents = new ArrayList<>();

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(residents);
    }

    @Override
    public Resident findById(String id) {
        for (Resident resident : residents) {
            if (resident.getId().equalsIgnoreCase(id)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident findByName(String name) {
        for (Resident resident : residents) {
            if (resident.getName().equalsIgnoreCase(name)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident findByEmail(String email) {
        for (Resident resident : residents) {
            if (resident.getEmail().equalsIgnoreCase(email)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident findByPhone(String phoneNumber) {
        for (Resident resident : residents) {
            if (resident.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident save(Resident resident) {
        if (resident.getId() == null) {
            resident.setId(RandomCodeGenerator.residentIdGenerator());
        }
        if (!residents.contains(resident)) {
            residents.add(resident);
        }
        return resident;
    }

    @Override
    public void delete(Resident resident) {
        residents.remove(resident);
    }

    @Override
    public void deleteById(String id) {
        Resident resident = findById(id);
        if (resident != null) {
            residents.remove(resident);
        }
    }

    @Override
    public void deleteAll() {
        residents.clear();
    }

    public long count() {
        return residents.size();
    }
}