package data.repositories;
import data.models.Visitor;
import utils.RandomCodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class Visitors implements VisitorRepo {
    private List<Visitor> visitors = new ArrayList<>();

    @Override
    public List<Visitor> findAll() {
        return new ArrayList<>(visitors);
    }

    @Override
    public Visitor findById(String id) {
        for (Visitor visitor : visitors) {
            if (visitor.getId().equalsIgnoreCase( id)) {
                return visitor;
            }
        }
        return null;
    }

    @Override
    public Visitor save(Visitor visitor) {
        if (visitor.getId() == null) {
            visitor.setId(RandomCodeGenerator.visitorIdGenerator());
        }
        if (!visitors.contains(visitor)) {
            visitors.add(visitor);
        }
        return visitor;
    }

    @Override
    public void delete(Visitor visitor) {
        visitors.remove(visitor);
    }

    @Override
    public void deleteById(String id) {
        Visitor visitor = findById(id);
        if (visitor != null) {
            visitors.remove(visitor);
        }
    }

    @Override
    public void deleteAll() {
        visitors.clear();
    }

    public long count() {
        return visitors.size();
    }
}