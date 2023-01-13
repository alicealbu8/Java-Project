package service;

import domain.weather;
import repository.Repository;

import java.util.Vector;

public class Service {
    private Repository repo;

    public Service(Repository repo) {
        this.repo = repo;
    }

    public void createSchema() {
        repo.createSchema();
    }

    public void openConnection() {
        repo.openConnection();
    }

    public void AddinSchema() {
        repo.AddInSchema();
    }

    public Vector<weather> getAll() {
        return repo.getAll();
    }
    public void updateSchema(String prec, String updatedPrec){
        repo.UpdateSchema(prec, updatedPrec);
    }
}
