create table tb_cidade(
    id_cidade bigint not null primary key,
    nome varchar(50) not null,
    qtd_habitantes bigint
);

insert into tb_cidade(id_cidade, nome, qtd_habitantes)
values
(1, 'São Paulo', 12325232),
(2, 'Rio de Janeiro', 6747815),
(3, 'Brasília', 3055149),
(4, 'Salvador', 2886698),
(5, 'Fortaleza', 2686612),
(6, 'Belo Horizonte', 2521564),
(7, 'Manaus', 2219580),
(8, 'Curitiba', 1948626),
(9, 'Recife', 1653461),
(10, 'Goiânia', 1536097),
(11, 'Belém', 1499641);
