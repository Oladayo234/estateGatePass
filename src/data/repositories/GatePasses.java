package data.repositories;
import data.models.GatePass;
import utils.RandomCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class GatePasses implements GatePassRepo {
    private List<GatePass> gatePasses = new ArrayList<>();

    @Override
    public List<GatePass> findAll() {
        return new ArrayList<>(gatePasses);
    }

    @Override
    public GatePass findById(String id) {
        for (GatePass pass : gatePasses) {
            if (pass.getId().equalsIgnoreCase(id)) {
                return pass;
            }
        }
        return null;
    }

    @Override
    public GatePass findByOtp(String otp) {
        for (GatePass pass : gatePasses) {
            if (pass.getOtp().equalsIgnoreCase(otp)) {
                return pass;
            }
        }
        return null;
    }

    @Override
    public GatePass save(GatePass pass) {
        if (pass.getId() == null) {
            pass.setId(RandomCodeGenerator.gateIdGenerator());
        }
        if (!gatePasses.contains(pass)) {
            gatePasses.add(pass);
        }
        return pass;
    }

    @Override
    public void delete(GatePass pass) {
        gatePasses.remove(pass);
    }

    @Override
    public void deleteById(String id) {
        GatePass pass = findById(id);
        if (pass != null) {
            gatePasses.remove(pass);
        }
    }

    @Override
    public void deleteAll() {
        gatePasses.clear();
    }

    public long count() {
        return gatePasses.size();
    }
}