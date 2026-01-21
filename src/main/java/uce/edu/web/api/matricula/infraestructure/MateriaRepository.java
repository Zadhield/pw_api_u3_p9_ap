package uce.edu.web.api.matricula.infraestructure;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.matricula.domain.Materia;

@ApplicationScoped
public class MateriaRepository implements PanacheRepository<Materia> {

    
    public List<Materia> buscarConCreditosMayoresAlPromedio() {
        return list("creditos > (SELECT AVG(m.creditos) FROM Materia m)");
    }
    public List<Materia> buscarMateriasDelUltimoSemestre() {
        return list("semestre = (SELECT MAX(m.semestre) FROM Materia m)");
    }
}