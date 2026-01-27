package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Funcionario;
import br.ifs.cads.api.hotel.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    Optional<Funcionario> findByCpf(String cpf);
    List<Funcionario> findByCargo(Cargo cargo);
    List<Funcionario> findByAtivo(Boolean ativo);
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
}
