package com.github.arandarod.localizacao.domain.repository;

import com.github.arandarod.localizacao.domain.entity.Cidade;
import com.github.arandarod.localizacao.domain.repository.projections.CidadeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {
    List<Cidade> findByNome(String nome);
    @Query(value = "select c.id_cidade id, c.nome from tb_cidade c where regexp_like(c.nome, ?1, 'i')", nativeQuery = true)
    Page<CidadeProjection> findByNomeLikeProjection(@Param("nome") String nome, Pageable pageable);
    @Query(value = "select * from tb_cidade c where regexp_like(c.nome, ?1, 'i')", nativeQuery = true)
    Page<Cidade> findByNomeLike(@Param("nome") String nome, Pageable pageable);
    List<Cidade> findByNomeStartingWith(String nome);
    List<Cidade> findByNomeEndingWith(String nome);
    List<Cidade> findByNomeContaining(String nome);
    List<Cidade> findByHabitantes(Long habitantes);
    List<Cidade> findByHabitantesGreaterThan(Long habitantes);
    List<Cidade> findByHabitantesLessThan(Long habitantes);
    Page<Cidade> findByHabitantesGreaterThanEqual(Long habitantes, Pageable pageable);
    List<Cidade> findByHabitantesLessThanEqual(Long habitantes);
    @Query(value = "select * from tb_cidade c where c.qtd_habitantes = ?1 and regexp_like(c.nome, ?2, 'i')", nativeQuery = true)
    Page<Cidade> findByHabitantesGreaterThanEqualAndNomeLike(Long habitantes, String nome, Pageable pageable);
}
