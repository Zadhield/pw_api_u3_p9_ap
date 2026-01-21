package uce.edu.web.api.matricula.application;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.domain.Materia;
import uce.edu.web.api.matricula.infraestructure.MateriaRepository;

@ApplicationScoped
public class MateriaService {

    @Inject
    private MateriaRepository materiaRepository;

    public List<Materia> listarTodos() {
        return this.materiaRepository.listAll();
    }

    public Materia consultarPorId(Integer id) {
        return this.materiaRepository.findById(id.longValue());
    }

    @Transactional
    public void crear(Materia materia) {
        this.materiaRepository.persist(materia);
    }

    @Transactional
    public void actualizar(Integer id, Materia materiaInfo) {
        Materia materia = this.consultarPorId(id);
        if (materia != null) {
            materia.setNombre(materiaInfo.getNombre());
            materia.setCreditos(materiaInfo.getCreditos());
            materia.setSemestre(materiaInfo.getSemestre());
        }
    }

    @Transactional
    public void actualizarParcial(Integer id, Materia materiaInfo) {
        Materia materia = this.consultarPorId(id);
        if (materia != null) {
            if (materiaInfo.getNombre() != null)
                materia.setNombre(materiaInfo.getNombre());
            if (materiaInfo.getCreditos() != null)
                materia.setCreditos(materiaInfo.getCreditos());
            if (materiaInfo.getSemestre() != null)
                materia.setSemestre(materiaInfo.getSemestre());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        this.materiaRepository.deleteById(id.longValue());
    }

    public List<Materia> reportarMateriasAltaCarga() {
        return this.materiaRepository.buscarConCreditosMayoresAlPromedio();
    }

    public List<Materia> reportarMateriasFinCarrera() {
        return this.materiaRepository.buscarMateriasDelUltimoSemestre();
    }
}