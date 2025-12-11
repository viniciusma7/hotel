package br.ifs.cads.api.hotel.service;

import br.ifs.cads.api.hotel.dto.EstadoDto;
import br.ifs.cads.api.hotel.entity.Estado;
import br.ifs.cads.api.hotel.exception.BusinessRuleException;
import br.ifs.cads.api.hotel.repository.CidadeRepository;
import br.ifs.cads.api.hotel.repository.EstadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {
    private final EstadoRepository estadoRepository;
    private final CidadeRepository cidadeRepository;

  public EstadoService(EstadoRepository estadoRepository, CidadeRepository cidadeRepository) {
      this.estadoRepository = estadoRepository;
      this.cidadeRepository = cidadeRepository;
  }

  private EstadoDto toDto(Estado estado) {
    // Todo: Implement cities list
      return new EstadoDto(
            estado.getId(),
            estado.getUf()
      );
  }

  private Estado fromDto(EstadoDto estadoDto) {
      Estado estado = new Estado();
      estado.setId(estadoDto.id());
      estado.setUf(estadoDto.uf());

      // Todo: Implement cities list

      return estado;
  }

  public List<EstadoDto> findAll() {
        List<Estado> estados = estadoRepository.findAll();

        return estados.stream()
                .map(this::toDto)
                .toList();
  }

  public EstadoDto findById(Long id) {
      Optional<Estado> estado = estadoRepository.findById(id);

      EstadoDto estadoDto = null;
      if (estado.isPresent()) {
            estadoDto = toDto(estado.get());
      }

      return estadoDto;
  }

  public EstadoDto findByUf(String uf) {
      Optional<Estado> estado = estadoRepository.findByUf(uf);

      EstadoDto estadoDto = null;
      if (estado.isPresent()) {
          estadoDto = toDto(estado.get());
      }

      return estadoDto;
  }

  @Transactional
  public EstadoDto save(EstadoDto estadoDto) {
      String uf = estadoDto.uf();

      Optional<Estado> existingEstado = estadoRepository.findByUf(uf);
      if (existingEstado.isEmpty()) {
          throw new BusinessRuleException("State with UF " + uf + " already exists.");
      }

      Estado estado = fromDto(estadoDto);
      Estado savedEstado = estadoRepository.save(estado);

      return toDto(savedEstado);
  }

  @Transactional
    public Estado update(Long id, Estado estado) {
    estado.setId(id);

    return estadoRepository.save(estado);
  }
}
