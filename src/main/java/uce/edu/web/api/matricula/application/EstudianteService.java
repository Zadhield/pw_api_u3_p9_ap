package uce.edu.web.api.matricula.application;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.matricula.application.representation.EstudianteRepresentation;
import uce.edu.web.api.matricula.domain.Estudiante;
import uce.edu.web.api.matricula.infraestructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;
    @Inject
    private HijoService hijoService;

    public List<EstudianteRepresentation> listarTodos() {
        List<EstudianteRepresentation> list = new ArrayList<>();
        for (Estudiante estu : this.estudianteRepository.listAll()) {
            list.add(this.mapperToER(estu));
        }
        return list;
    }

    public EstudianteRepresentation consultarPorId(Integer id) {
        return this.mapperToER(this.estudianteRepository.findById(id.longValue()));
    }

    @Transactional
    public void crear(EstudianteRepresentation estu) {
        this.estudianteRepository.persist(this.mapperToEstudiante(estu));
    }

    @Transactional
    public void actualizar(Integer id, EstudianteRepresentation est) {
        Estudiante estuDB = this.estudianteRepository.findById(id.longValue());

        if (estuDB != null) {
            estuDB.setNombre(est.getNombre());
            estuDB.setApellido(est.getApellido());
            estuDB.setFechaNacimiento(est.getFechaNacimiento());
            estuDB.setProvincia(est.getProvincia());
            estuDB.setGenero(est.getGenero());
        }
    }

    @Transactional
    public void actualizarParcial(Integer id, EstudianteRepresentation estDto) {
        Estudiante estuDB = this.estudianteRepository.findById(id.longValue());

        if (estuDB != null) {

            if (estDto.getNombre() != null) {
                estuDB.setNombre(estDto.getNombre());
            }

            if (estDto.getApellido() != null) {
                estuDB.setApellido(estDto.getApellido());
            }

            if (estDto.getFechaNacimiento() != null) {
                estuDB.setFechaNacimiento(estDto.getFechaNacimiento());
            }

            if (estDto.getProvincia() != null) {
                estuDB.setProvincia(estDto.getProvincia());
            }
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        this.estudianteRepository.deleteById(id.longValue());
    }

    public List<Estudiante> buscarPorProvincia(String provincia) {
        return this.estudianteRepository.find("provincia", provincia).list();
    }

    public List<EstudianteRepresentation> buscarPorProvinciaGenero(String provincia, String genero) {
        List<EstudianteRepresentation> list = new ArrayList<>();
        for (Estudiante estu : this.estudianteRepository.find("provincia = ?1 and genero = ?2", provincia, genero)
                .list()) {
            list.add(this.mapperToER(estu));
        }
        return list;

    }

    private EstudianteRepresentation mapperToER(Estudiante est) {
        EstudianteRepresentation estuR = new EstudianteRepresentation();
        estuR.setId(est.getId());
        estuR.setNombre(est.getNombre());
        estuR.setApellido(est.getApellido());
        estuR.setFechaNacimiento(est.getFechaNacimiento());
        estuR.setGenero(est.getGenero());
        estuR.setProvincia(est.getProvincia());

        return estuR;
    }

    private Estudiante mapperToEstudiante(EstudianteRepresentation est) {
        Estudiante estuR = new Estudiante();
        estuR.setId(est.getId());
        estuR.setNombre(est.getNombre());
        estuR.setApellido(est.getApellido());
        estuR.setFechaNacimiento(est.getFechaNacimiento());
        estuR.setGenero(est.getGenero());
        estuR.setProvincia(est.getProvincia());

        return estuR;
    }
}