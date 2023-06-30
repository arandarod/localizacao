package com.github.arandarod.localizacao.service;

import com.github.arandarod.localizacao.domain.entity.Cidade;
import com.github.arandarod.localizacao.domain.repository.projections.CidadeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CidadeService {
    void salvarCidade(Cidade cidade);
    Page<Cidade> listarCidades(Pageable pageable);
    Page<Cidade> listarCidadePorNome(String nomeRegex, Pageable pageable);
    Page<Cidade> listarCidadePorHabitantes(Long habitantes, Pageable pageable);
    Page<Cidade> listarCidadePorNomeHabitante(Long habitantes, String nomeRegex, Pageable pageable);
    List<Cidade> filtroDinamico(Cidade cidade);
    void listarCidadesByNomeSpec();
    void listarCidadesSpecsFiltroDinamico(Cidade filtro);
    Page<CidadeProjection> listarPorNomeProjection(String nomeRegex, Pageable pageable);
}
