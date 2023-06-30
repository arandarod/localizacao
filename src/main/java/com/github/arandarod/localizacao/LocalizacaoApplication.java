package com.github.arandarod.localizacao;

import com.github.arandarod.localizacao.domain.entity.Cidade;
import com.github.arandarod.localizacao.domain.repository.projections.CidadeProjection;
import com.github.arandarod.localizacao.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootApplication
@RequiredArgsConstructor
public class LocalizacaoApplication implements CommandLineRunner {
	private final CidadeService cidadeService;

	@Override
	public void run(String... args) throws Exception {
		Pageable pageable = PageRequest.of(0, 10);

		System.out.println("######### LISTANDO CIDADES POR NOME #########");
		cidadeService.listarCidadePorNome("^(rio|bra)", pageable).forEach(System.out::println);
		System.out.println("#############################################");
		System.out.println();
		System.out.println("######### LISTANDO CIDADES POR HABITANTES #########");
		cidadeService.listarCidadePorHabitantes(2521564L, pageable).forEach(System.out::println);
		System.out.println("###################################################");
		System.out.println();
		System.out.println("######### LISTANDO CIDADES POR NOME E HABITANTES #########");
		cidadeService.listarCidadePorNomeHabitante(2521564L, "^belo", pageable).forEach(System.out::println);
		System.out.println("##########################################################");
		System.out.println();
		System.out.println("######### LISTANDO CIDADES POR NOME OU HABITANTES - QUERY DINÂMICA #########");
		var cidade = Cidade.builder().nome("curitiba").build();
		cidadeService.filtroDinamico(cidade).forEach(System.out::println);
		var cidade2 = Cidade.builder().habitantes(1948626L).build();
		cidadeService.filtroDinamico(cidade2).forEach(System.out::println);
		System.out.println("############################################################################");
		System.out.println();
		System.out.println("######### LISTANDO CIDADES POR SPECIFICATIONS #########");
		cidadeService.listarCidadesByNomeSpec();
		System.out.println("#######################################################");
		System.out.println();
		System.out.println("######### LISTANDO CIDADES POR FILTRO DINÂMICO #########");
		var cidade3 = Cidade.builder().id(1L).nome("São").habitantes(100L).build();
		cidadeService.listarCidadesSpecsFiltroDinamico(cidade3);
		System.out.println("#######################################################");
		System.out.println();
		System.out.println("######### LISTANDO CIDADES POR NOME - PROJECTIONS #########");
		cidadeService.listarPorNomeProjection("São Paulo", pageable)
				.stream()
				.map(cidadeProjection -> new Cidade(cidadeProjection.getId(), cidadeProjection.getNome(), null))
				.forEach(System.out::println);
	}

	public static void main(String[] args) {
		SpringApplication.run(LocalizacaoApplication.class, args);
	}
}
