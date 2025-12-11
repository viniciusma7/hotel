package br.ifs.cads.api.hotel.repository;

import br.ifs.cads.api.hotel.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade,Long> {}
