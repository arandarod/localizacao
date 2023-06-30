package com.github.arandarod.localizacao.service.impl;

import com.github.arandarod.localizacao.domain.entity.Cidade;
import com.github.arandarod.localizacao.domain.repository.CidadeRepository;
import com.github.arandarod.localizacao.domain.repository.projections.CidadeProjection;
import com.github.arandarod.localizacao.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.github.arandarod.localizacao.domain.repository.specs.CidadeSpecs.*;

@Service
@RequiredArgsConstructor
public class CidadeServiceImpl implements CidadeService {
    private final CidadeRepository cidadeRepository;

    @Transactional
    public void salvarCidade(Cidade cidade) {
        cidadeRepository.save(cidade);
    }

    public Page<Cidade> listarCidades(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }

    public Page<Cidade> listarCidadePorNome(String nomeRegex, Pageable pageable) {
        return cidadeRepository.findByNomeLike(nomeRegex, pageable);
    }

    public Page<Cidade> listarCidadePorHabitantes(Long habitantes, Pageable pageable) {
        return cidadeRepository.findByHabitantesGreaterThanEqual(habitantes, pageable);
    }

    public Page<Cidade> listarCidadePorNomeHabitante(Long habitantes, String nomeRegex, Pageable pageable) {
        return cidadeRepository.findByHabitantesGreaterThanEqualAndNomeLike(habitantes, nomeRegex, pageable);
    }

    public List<Cidade> filtroDinamico(Cidade cidade) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example<Cidade> example = Example.of(cidade, exampleMatcher);

        return cidadeRepository.findAll(example);
    }

    public void listarCidadesByNomeSpec() {
        Specification<Cidade> specs = nomeEqual("São Paulo").or(habitantesGreaterThan(2000000L));
        cidadeRepository.findAll(specs).forEach(System.out::println);
    }

    public void listarCidadesSpecsFiltroDinamico(Cidade filtro) {
        Specification<Cidade> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (filtro.getId() != null) {
            specs = specs.and(idEqual(filtro.getId()));
        }

        if (StringUtils.hasText(filtro.getNome())) {
            specs = specs.and(nomeLike(filtro.getNome()));
        }

        if (filtro.getHabitantes() != null) {
            specs = specs.and(habitantesGreaterThan(filtro.getHabitantes()));
        }

        cidadeRepository.findAll(specs).forEach(System.out::println);
    }

    public Page<CidadeProjection> listarPorNomeProjection(String nomeRegex, Pageable pageable) {
        return cidadeRepository.findByNomeLikeProjection(nomeRegex, pageable);
    }
}
