-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 26/08/2025 às 21:18
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `logistica`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `acessa_coordenador_estoque`
--

CREATE TABLE `acessa_coordenador_estoque` (
  `id_coordenador` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `acessa_instrutor_estoque`
--

CREATE TABLE `acessa_instrutor_estoque` (
  `id_instrutor` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `alternativa`
--

CREATE TABLE `alternativa` (
  `id_alternativa` int(11) NOT NULL,
  `id_questao` int(11) DEFAULT NULL,
  `letra` char(1) DEFAULT NULL,
  `texto` text DEFAULT NULL,
  `correta` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `alternativa`
--

INSERT INTO `alternativa` (`id_alternativa`, `id_questao`, `letra`, `texto`, `correta`) VALUES
(26, 6, 'B', '2', 1),
(27, 6, 'E', '87', 0),
(28, 6, 'A', '3', 0),
(29, 6, 'C', '666', 0),
(30, 6, 'D', '1', 0),
(31, 7, 'E', '7', 0),
(32, 7, 'D', '6', 1),
(33, 7, 'B', '4', 0),
(34, 7, 'C', '5', 0),
(35, 7, 'A', '3', 0),
(36, 8, 'D', '4', 0),
(37, 8, 'B', '2', 0),
(38, 8, 'C', '3', 0),
(39, 8, 'A', '1', 0),
(40, 8, 'E', '5', 1),
(41, 9, 'A', 'Logistica', 0),
(42, 9, 'D', 'ADS', 0),
(43, 9, 'B', 'Logistica 2', 0),
(44, 9, 'C', 'TI', 0),
(45, 9, 'E', 'JAVASCRIPT', 1),
(46, 10, 'E', '2', 0),
(47, 10, 'C', '3', 0),
(48, 10, 'B', '4', 1),
(49, 10, 'D', '1', 0),
(50, 10, 'A', '6', 0),
(51, 11, 'B', '22', 0),
(52, 11, 'C', '34', 0),
(53, 11, 'D', '4', 0),
(54, 11, 'A', '1', 1),
(55, 11, 'E', '5', 0),
(56, 12, 'B', '2', 0),
(57, 12, 'D', '3', 0),
(58, 12, 'A', '4', 1),
(59, 12, 'E', '5', 0),
(60, 12, 'C', '1', 0),
(61, 13, 'E', '180', 1),
(62, 13, 'C', '4', 0),
(63, 13, 'A', '2', 0),
(64, 13, 'D', '5', 0),
(65, 13, 'B', '3', 0),
(66, 14, 'C', '3', 0),
(67, 14, 'E', '5', 0),
(68, 14, 'B', '2', 0),
(69, 14, 'A', '18', 1),
(70, 14, 'D', '4', 0),
(71, 15, 'A', '8', 1),
(72, 15, 'C', '12', 0),
(73, 15, 'E', '14', 0),
(74, 15, 'B', '11', 0),
(75, 15, 'D', '13', 0),
(76, 16, 'B', '2', 0),
(77, 16, 'A', '14', 1),
(78, 16, 'D', '12', 0),
(79, 16, 'E', '22', 0),
(80, 16, 'C', '31', 0),
(81, 17, 'D', '3', 0),
(82, 17, 'B', '2', 0),
(83, 17, 'C', '1', 0),
(84, 17, 'A', '5', 1),
(85, 17, 'E', '7', 0),
(86, 18, 'D', '3', 0),
(87, 18, 'A', '20', 1),
(88, 18, 'E', '2', 0),
(89, 18, 'C', '4', 0),
(90, 18, 'B', '5', 0),
(91, 19, 'B', '12', 0),
(92, 19, 'A', '24', 1),
(93, 19, 'C', '23', 0),
(94, 19, 'D', '22', 0),
(95, 19, 'E', '21', 0),
(96, 20, 'D', '32', 0),
(97, 20, 'A', '10', 1),
(98, 20, 'E', '2', 0),
(99, 20, 'C', '4', 0),
(100, 20, 'B', '5', 0),
(101, 21, 'E', '5', 0),
(102, 21, 'B', '1', 0),
(103, 21, 'D', '3', 0),
(104, 21, 'C', '2', 0),
(105, 21, 'A', '4', 1),
(106, 22, 'D', '22', 0),
(107, 22, 'E', '11', 0),
(108, 22, 'B', '12', 0),
(109, 22, 'A', '40', 1),
(110, 22, 'C', '32', 0),
(111, 23, 'C', '4', 0),
(112, 23, 'E', '6', 1),
(113, 23, 'B', '3', 0),
(114, 23, 'A', '2', 0),
(115, 23, 'D', '5', 0),
(116, 24, 'B', '43', 1),
(117, 24, 'A', '6', 0),
(118, 24, 'C', '2', 0),
(119, 24, 'E', '1', 0),
(120, 24, 'D', '3', 0),
(126, 26, 'B', 'Adquirir produtos, mercadorias e/ou serviços indispensáveis ao funcionamento da empresa, diretamente\ncom o fabricante, evitando a ação de intermediadores que encarecem o preço final.', 0),
(127, 26, 'E', 'Obter materiais, agregar-lhes valor de acordo com a concepção dos clientes e dos consumidores e\ndisponibilizar os produtos para o lugar e data que os clientes e os consumidores desejarem.', 0),
(128, 26, 'A', 'Competir com empresas que obtêm seus insumos por meio de extrativismo direto ou se abastecem em\nmercados onde não há concorrência.', 0),
(129, 26, 'D', 'Possuir técnicas de precificação ajustadas ao mercado financeiro, tornando os produtos e serviços mais\ncompetitivos.', 1),
(130, 26, 'C', 'Comprar matérias-primas com fornecedores previamente cadastrados, sempre com desconto e prazos de\npagamento mais longos, além de não fixar quantidades mínimas para aquisição.', 0),
(131, 27, 'D', '6', 0),
(132, 27, 'E', '7', 0),
(133, 27, 'C', '5', 0),
(134, 27, 'B', '4', 1),
(135, 27, 'A', '3', 0),
(136, 28, 'E', 'Obter materiais, agregar-lhes valor de acordo com a concepção dos clientes e dos consumidores e\ndisponibilizar os produtos para o lugar e data que os clientes e os consumidores desejarem.', 0),
(137, 28, 'D', 'Possuir técnicas de precificação ajustadas ao mercado financeiro, tornando os produtos e serviços mais\ncompetitivos.', 1),
(138, 28, 'B', 'Adquirir produtos, mercadorias e/ou serviços indispensáveis ao funcionamento da empresa, diretamente\ncom o fabricante, evitando a ação de intermediadores que encarecem o preço final.', 0),
(139, 28, 'C', 'Comprar matérias-primas com fornecedores previamente cadastrados, sempre com desconto e prazos de\npagamento mais longos, além de não fixar quantidades mínimas para aquisição.', 0),
(140, 28, 'A', 'Competir com empresas que obtêm seus insumos por meio de extrativismo direto ou se abastecem em\nmercados onde não há concorrência.', 0);

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno`
--

CREATE TABLE `aluno` (
  `id_aluno` int(11) NOT NULL,
  `nome_completo` varchar(120) NOT NULL,
  `data_nascimento` date NOT NULL,
  `sexo` enum('MASCULINO','FEMININO') NOT NULL,
  `telefone` varchar(30) NOT NULL,
  `email` varchar(80) NOT NULL,
  `turma` varchar(20) NOT NULL,
  `numero` int(11) NOT NULL,
  `bairro` varchar(50) NOT NULL,
  `cep` varchar(11) NOT NULL,
  `rua` varchar(80) NOT NULL,
  `nacionalidade` varchar(30) NOT NULL,
  `cidade` varchar(90) NOT NULL,
  `UF` enum('AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO') NOT NULL,
  `senha` varchar(100) NOT NULL,
  `foto_perfil` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `aluno`
--

INSERT INTO `aluno` (`id_aluno`, `nome_completo`, `data_nascimento`, `sexo`, `telefone`, `email`, `turma`, `numero`, `bairro`, `cep`, `rua`, `nacionalidade`, `cidade`, `UF`, `senha`, `foto_perfil`) VALUES
(2, 'ASDDSADSAADSaa', '2025-01-05', 'MASCULINO', 'ADSSDASDA', 'ericmellofernandes@gmail.com', 'DSADSASDA', 213, 'ADSADSDSA', 'SADDSADSA', 'adssadsda', 'SDASDA', 'SADDASDSA', 'MT', '43991373769yHABC', NULL),
(3, 'Eric Mello Fernandes', '2024-03-01', 'MASCULINO', '21969376184', 'abc', '23', 23, 'Ab', '21241-320', '23', 'BR', 'AS', 'MT', 'a', 'C:\\Users\\Eric\\Pictures\\Imagens\\Eric.png'),
(4, 'Eric Mello Fernandes', '2005-05-01', 'MASCULINO', '21969376184', 'ericmellofernando2@gmail.com', 'NOITE', 422, 'Vigário Geral', '21241320', 'Xavier Pinheiro', 'Brasileiro', 'Rio de Janeiro', 'RJ', '43991373769yH!', 'C:\\Users\\Eric\\Pictures\\Imagens\\Eric.jpg'),
(5, 'Kelven Nascimento', '2005-04-01', 'MASCULINO', '214343433432', 'kelvennascimento@gmail.com', 'NOITE', 422, 'Rio de Janeiro', '21241320', 'Rio de Janeiro', 'Brasileiro', 'Rio de Janeiro', 'RJ', 'kelven', NULL),
(6, 'Matheus dos Santos Carvalho', '2025-02-01', 'MASCULINO', '21969376184', 'mateusdossantos@gmail.com', 'NOITE', 422, 'Vigário Geral', '21241-320', 'Xavier Pinheiro', 'Brasil', 'Rio de Janeiro', 'RJ', '43991373769yH', NULL),
(7, 'Eric Melloso', '2005-05-01', 'MASCULINO', '21969376184', 'ericmellinho@gmail.com', 'NOITE', 422, 'Vigário Geralzinho', '21241-320', 'Xavier Pinheiro', 'Angola', 'Rio de Janeirinho', 'BA', '43991373769yH', NULL),
(8, 'Taltal da Silva', '2025-05-01', 'MASCULINO', '21969382921', 'taltaltal', 'NOITE', 32121, 'dsadsa', '22222-222', 'dsasda', 'Brasil', 'dsasda', 'RJ', 'taltaltal', NULL),
(9, 'Eric Mello Fernandes', '2005-05-01', 'MASCULINO', '21969376184', 'ericmellofernandesteste@gmail.com', 'NOITE', 422, 'Vigário Geral', '21241-320', 'Xavier Pinheiro', 'Brasil', 'Rio de Janeiro', 'RJ', '43991373769yH!', 'C:\\Users\\Eric\\Pictures\\Imagens\\Eric.png'),
(10, 'Denka da Silva', '2005-05-01', 'MASCULINO', '21949392912', 'denka@gmail.com', 'NOITE', 321, 'Vigarinho', '21222-222', '3232', 'Anguila', 'Rio de Janeiro', 'BA', 'abc', NULL),
(11, 'Denka da Silva', '2001-02-01', 'MASCULINO', '21943939192', 'denkasilva@gmail.com', 'MANHÃ', 323, 'Vigarioo', '21321-312', '3232', 'Antígua e Barbuda', 'Rio de Janeirinho', 'AC', 'abc', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `avaliacao_prova`
--

CREATE TABLE `avaliacao_prova` (
  `id_prova` int(11) NOT NULL,
  `id_desempenho` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `nome` varchar(120) NOT NULL,
  `documento` varchar(20) DEFAULT NULL,
  `telefone` varchar(30) DEFAULT NULL,
  `email` varchar(80) DEFAULT NULL,
  `cidade` varchar(90) DEFAULT NULL,
  `uf` char(2) DEFAULT NULL,
  `endereco` varchar(140) DEFAULT NULL,
  `prioridade_nivel` tinyint(4) DEFAULT 3,
  `janela_ini` time DEFAULT NULL,
  `janela_fim` time DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nome`, `documento`, `telefone`, `email`, `cidade`, `uf`, `endereco`, `prioridade_nivel`, `janela_ini`, `janela_fim`, `latitude`, `longitude`) VALUES
(1, 'Cliente Alpha', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Rua A, 100', 2, '08:00:00', '17:00:00', -22.902800, -43.207500),
(2, 'Cliente Beta', NULL, NULL, NULL, 'Duque de Caxias', 'RJ', 'Av. B, 200', 3, '09:00:00', '16:00:00', -22.785100, -43.304900),
(3, 'Cliente Copacabana', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Av. Atlântica, 1700', 2, '08:00:00', '17:00:00', -22.971100, -43.182200),
(4, 'Cliente Botafogo', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Praia de Botafogo, 300', 3, '09:00:00', '18:00:00', -22.949300, -43.182600),
(5, 'Cliente Barra', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Av. das Américas, 5000', 2, '10:00:00', '16:00:00', -23.000000, -43.365000),
(6, 'Cliente Niterói', NULL, NULL, NULL, 'Niterói', 'RJ', 'Rua Moreira César, 200', 3, '08:30:00', '17:30:00', -22.883200, -43.103400),
(7, 'Cliente Tijuca', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Rua Conde de Bonfim, 900', 2, '09:00:00', '17:00:00', -22.925400, -43.248800),
(8, 'Cliente Campo Grande', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Rua Augusto de Vasconcelos, 100', 3, '08:00:00', '15:00:00', -22.902100, -43.562800),
(9, 'Cliente Nova Iguaçu', NULL, NULL, NULL, 'Nova Iguaçu', 'RJ', 'Av. Gov. Roberto Silveira, 120', 3, '08:00:00', '17:00:00', -22.755000, -43.460000),
(10, 'Cliente Madureira', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Rua Carvalho de Souza, 250', 2, '09:00:00', '18:00:00', -22.875400, -43.336800),
(11, 'Cliente Méier', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Rua Dias da Cruz, 400', 2, '08:00:00', '17:00:00', -22.902000, -43.277000),
(12, 'Cliente Ilha do Governador', NULL, NULL, NULL, 'Rio de Janeiro', 'RJ', 'Estr. do Galeão, 3000', 3, '09:00:00', '16:00:00', -22.812000, -43.196000);

-- --------------------------------------------------------

--
-- Estrutura para tabela `coordenador`
--

CREATE TABLE `coordenador` (
  `id_coordenador` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `coordenador`
--

INSERT INTO `coordenador` (`id_coordenador`, `nome`, `login`, `senha`) VALUES
(1, 'Jonathan Mattos', 'admin', 'admin123'),
(2, 'Luan Mendes', 'luan', 'luan123');

-- --------------------------------------------------------

--
-- Estrutura para tabela `decisao_reposicao`
--

CREATE TABLE `decisao_reposicao` (
  `id_decisao` int(11) NOT NULL,
  `id_material` int(11) NOT NULL,
  `data_decisao` datetime DEFAULT current_timestamp(),
  `acao` enum('COMPRAR','AGUARDAR','REDISTRIBUIR') NOT NULL,
  `qtd_sugerida` int(11) NOT NULL,
  `motivo` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `desempenho`
--

CREATE TABLE `desempenho` (
  `id_desempenho` int(11) NOT NULL,
  `nota_global` decimal(5,2) DEFAULT NULL,
  `percentual_acerto` decimal(5,2) DEFAULT NULL,
  `data_avaliacao` date DEFAULT NULL,
  `observacao` varchar(90) DEFAULT NULL,
  `status` varchar(40) DEFAULT NULL,
  `id_aluno` int(11) DEFAULT NULL,
  `id_prova` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `desempenho`
--

INSERT INTO `desempenho` (`id_desempenho`, `nota_global`, `percentual_acerto`, `data_avaliacao`, `observacao`, `status`, `id_aluno`, `id_prova`) VALUES
(1, 16.00, 160.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 7),
(2, 0.00, 0.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 6),
(3, 0.00, 0.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 8),
(4, 0.00, 0.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 8),
(5, 10.00, 100.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 8),
(6, 10.00, 100.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(7, 15.00, 150.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(8, 20.00, 200.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(9, 5.00, 50.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(10, 8.00, 80.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(11, 4.00, 40.00, '2025-08-06', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(12, 0.00, 0.00, '2025-08-07', 'Prova finalizada pelo sistema', 'finalizada', 3, 10),
(13, 2.00, 20.00, '2025-08-10', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(14, 2.00, 20.00, '2025-08-10', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(15, 4.00, 40.00, '2025-08-10', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(16, 7.00, 70.00, '2025-08-10', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(17, 0.00, 0.00, '2025-08-10', 'Prova finalizada pelo sistema', 'finalizada', 3, 11),
(18, 10.00, 100.00, '2025-08-10', 'Prova finalizada pelo sistema', 'finalizada', 3, 11),
(19, 4.00, 40.00, '2025-08-11', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(20, 5.00, 50.00, '2025-08-11', 'Prova finalizada pelo sistema', 'finalizada', 3, 9),
(21, 10.00, 100.00, '2025-08-11', 'Prova finalizada pelo sistema', 'finalizada', 9, 9),
(22, 0.00, 0.00, '2025-08-12', 'Prova finalizada pelo sistema', 'finalizada', 9, 13),
(23, 0.00, 0.00, '2025-08-12', 'Prova finalizada pelo sistema', 'finalizada', 9, 13),
(24, 0.00, 0.00, '2025-08-12', 'Prova finalizada pelo sistema', 'finalizada', 9, 13);

-- --------------------------------------------------------

--
-- Estrutura para tabela `elabora_prova`
--

CREATE TABLE `elabora_prova` (
  `id_instrutor` int(11) NOT NULL,
  `id_prova` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `elabora_relatorio`
--

CREATE TABLE `elabora_relatorio` (
  `id_coordenador` int(11) NOT NULL,
  `id_relatorio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `entrega`
--

CREATE TABLE `entrega` (
  `id_entrega` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_rota` int(11) DEFAULT NULL,
  `id_veiculo` int(11) DEFAULT NULL,
  `sequencia` int(11) DEFAULT NULL,
  `status` enum('PENDENTE','CARREGADO','EM_TRANSITO','ENTREGUE','FALHA') DEFAULT 'PENDENTE',
  `distancia_km` decimal(10,2) DEFAULT 0.00,
  `tempo_previsto_min` int(11) DEFAULT 0,
  `tempo_real_min` int(11) DEFAULT NULL,
  `confirmado_em` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `entrega`
--

INSERT INTO `entrega` (`id_entrega`, `id_pedido`, `id_rota`, `id_veiculo`, `sequencia`, `status`, `distancia_km`, `tempo_previsto_min`, `tempo_real_min`, `confirmado_em`) VALUES
(1, 4, 2, 1, 1, 'CARREGADO', 0.00, 0, NULL, NULL),
(2, 5, 2, 2, 2, 'EM_TRANSITO', 0.00, 0, NULL, NULL),
(3, 6, 2, 2, 3, 'ENTREGUE', 12.50, 45, 42, '2025-08-22 16:05:00'),
(4, 9, 2, 3, 3, 'CARREGADO', 0.00, 0, NULL, NULL),
(5, 10, 2, 3, 4, 'EM_TRANSITO', 0.00, 0, NULL, NULL),
(6, 11, 2, 1, 5, 'ENTREGUE', 9.80, 35, 33, '2025-08-22 11:20:00'),
(7, 2, 4, 1, 1, 'CARREGADO', 0.00, 0, NULL, NULL),
(8, 12, 4, 1, 2, 'CARREGADO', 0.00, 0, NULL, NULL),
(9, 1, 4, 1, 3, 'CARREGADO', 0.00, 0, NULL, NULL),
(10, 4, 4, 1, 4, 'CARREGADO', 0.00, 0, NULL, NULL),
(11, 3, 4, 1, 5, 'CARREGADO', 0.00, 0, NULL, NULL),
(12, 9, 4, 1, 6, 'CARREGADO', 0.00, 0, NULL, NULL),
(13, 8, 4, 1, 7, 'CARREGADO', 0.00, 0, NULL, NULL),
(14, 2, 5, 1, 1, 'CARREGADO', 0.00, 0, NULL, NULL),
(15, 12, 5, 1, 2, 'CARREGADO', 0.00, 0, NULL, NULL),
(16, 1, 5, 1, 3, 'CARREGADO', 0.00, 0, NULL, NULL),
(17, 4, 5, 1, 4, 'CARREGADO', 0.00, 0, NULL, NULL),
(18, 3, 5, 1, 5, 'CARREGADO', 0.00, 0, NULL, NULL),
(19, 9, 5, 1, 6, 'CARREGADO', 0.00, 0, NULL, NULL),
(20, 8, 5, 1, 7, 'CARREGADO', 0.00, 0, NULL, NULL),
(21, 2, 6, 2, 1, 'CARREGADO', 0.00, 0, NULL, NULL),
(22, 12, 6, 2, 2, 'CARREGADO', 0.00, 0, NULL, NULL),
(23, 1, 6, 2, 3, 'CARREGADO', 0.00, 0, NULL, NULL),
(24, 4, 6, 2, 4, 'CARREGADO', 0.00, 0, NULL, NULL),
(25, 3, 6, 2, 5, 'CARREGADO', 0.00, 0, NULL, NULL),
(26, 9, 6, 2, 6, 'CARREGADO', 0.00, 0, NULL, NULL),
(27, 8, 6, 2, 7, 'CARREGADO', 0.00, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `estoque`
--

CREATE TABLE `estoque` (
  `id_estoque` int(11) NOT NULL,
  `descricao` varchar(120) DEFAULT NULL,
  `localizacao` varchar(50) DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estoque`
--

INSERT INTO `estoque` (`id_estoque`, `descricao`, `localizacao`, `latitude`, `longitude`) VALUES
(1, 'Depósito A', 'Sala 01', -22.785100, -43.304900),
(2, 'Depósito B', 'Bloco 2', -22.902800, -43.207500),
(3, 'Depósito C', 'Bloco 3', -22.910000, -43.180000),
(4, 'Almoxarifado Central', 'Prédio 1', NULL, NULL),
(5, 'Picking 01', 'Rua 01', NULL, NULL),
(6, 'Picking 02', 'Rua 02', NULL, NULL),
(7, 'Picking 03', 'Rua 03', NULL, NULL),
(8, 'Picking 04', 'Rua 04', NULL, NULL),
(9, 'Cross-docking', 'Docas 01', NULL, NULL),
(10, 'Quarentena', 'Sala QA', NULL, NULL),
(11, 'Devoluções', 'Docas 02', NULL, NULL),
(12, 'Expedição', 'Docas 03', NULL, NULL),
(13, 'Recebimento', 'Docas 00', NULL, NULL),
(14, 'WIP Produção', 'Linha 2', NULL, NULL),
(15, 'Materiais Perigosos', 'Galpão HAZ', NULL, NULL),
(16, 'Itens de Alto Valor', 'Cofre', NULL, NULL),
(17, 'Frigorificado', 'Câmara Fria', NULL, NULL),
(18, 'Mezanino', 'MZN-1', NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `estoque_material`
--

CREATE TABLE `estoque_material` (
  `id_material` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `estoque_movimentacao`
--

CREATE TABLE `estoque_movimentacao` (
  `id_estoque_movimentacao` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `data_movimentacao` date DEFAULT NULL,
  `motivo` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `estoque_snapshot`
--

CREATE TABLE `estoque_snapshot` (
  `id_snapshot` int(11) NOT NULL,
  `id_material` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL,
  `data_ref` date NOT NULL,
  `saldo` int(11) NOT NULL,
  `custo_medio` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `estoque_snapshot`
--

INSERT INTO `estoque_snapshot` (`id_snapshot`, `id_material`, `id_estoque`, `data_ref`, `saldo`, `custo_medio`) VALUES
(1, 36, 3, '2025-07-23', 40, 20.00),
(2, 36, 3, '2025-08-22', 28, 20.00),
(3, 33, 3, '2025-08-22', 300, 1.20);

-- --------------------------------------------------------

--
-- Estrutura para tabela `instrutor`
--

CREATE TABLE `instrutor` (
  `id_instrutor` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `sobrenome` varchar(50) NOT NULL,
  `login` varchar(30) NOT NULL,
  `senha` varchar(40) NOT NULL,
  `foto_perfil` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `instrutor`
--

INSERT INTO `instrutor` (`id_instrutor`, `nome`, `sobrenome`, `login`, `senha`, `foto_perfil`) VALUES
(1, '', '', '', '', NULL),
(2, 'Jonathan', 'Mattos', 'JonDSilva', '21z35x69', NULL),
(3, 'Max', 'Esteves', 'mefernandes', '21z35x69', 'C:\\Users\\Eric\\Pictures\\Imagens\\Max Redondo.png');

-- --------------------------------------------------------

--
-- Estrutura para tabela `inventario_fisico`
--

CREATE TABLE `inventario_fisico` (
  `id_inventario` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL,
  `data_execucao` date NOT NULL,
  `responsavel` varchar(80) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `inventario_fisico_item`
--

CREATE TABLE `inventario_fisico_item` (
  `id_item` int(11) NOT NULL,
  `id_inventario` int(11) NOT NULL,
  `id_material` int(11) NOT NULL,
  `qtd_sistema` int(11) NOT NULL,
  `qtd_fisica` int(11) NOT NULL,
  `divergencia` int(11) GENERATED ALWAYS AS (`qtd_fisica` - `qtd_sistema`) VIRTUAL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `material`
--

CREATE TABLE `material` (
  `id_material` int(11) NOT NULL,
  `nome` varchar(30) DEFAULT NULL,
  `valor_total` decimal(10,2) DEFAULT NULL,
  `peso_kg` decimal(10,3) DEFAULT NULL,
  `volume_m3` decimal(10,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `material`
--

INSERT INTO `material` (`id_material`, `nome`, `valor_total`, `peso_kg`, `volume_m3`) VALUES
(1, 'Pallet PBR', 0.00, 25.000, 0.1800),
(2, 'Caixa Plástica 60L', 0.00, NULL, NULL),
(3, 'Etiqueta RFID', 0.00, NULL, NULL),
(4, 'Pallet Euro', 0.00, NULL, NULL),
(5, 'Caixa Plástica 30L', 0.00, NULL, NULL),
(6, 'Caixa Plástica 80L', 0.00, NULL, NULL),
(7, 'Contentor Gradeado', 0.00, NULL, NULL),
(8, 'Big Bag 1t', 0.00, NULL, NULL),
(9, 'Saco Ráfia', 0.00, NULL, NULL),
(10, 'Filme Stretch', 0.00, NULL, NULL),
(11, 'Fita PP 12mm', 0.00, NULL, NULL),
(12, 'Fita PET 16mm', 0.00, NULL, NULL),
(13, 'Cantoneira Papelão', 0.00, NULL, NULL),
(14, 'Lacre Segurança', 0.00, NULL, NULL),
(15, 'Etiqueta RFID UHF', 0.00, NULL, NULL),
(16, 'Etiqueta Código Barras', 0.00, NULL, NULL),
(17, 'Envelope Manifesto', 0.00, NULL, NULL),
(18, 'Plástico Bolha', 0.00, NULL, NULL),
(19, 'Dessecante Sílica', 0.00, NULL, NULL),
(20, 'Cartucho Térmico Zebra', 0.00, NULL, NULL),
(21, 'Tinta Inkjet Linerless', 0.00, NULL, NULL),
(22, 'Colete EPI', 0.00, NULL, NULL),
(23, 'Luva Nitrílica', 0.00, NULL, NULL),
(24, 'Capacete EPI', 0.00, NULL, NULL),
(25, 'Protetor Auricular', 0.00, NULL, NULL),
(26, 'Parafuso M8', 0.00, NULL, NULL),
(27, 'Porca M8', 0.00, NULL, NULL),
(28, 'Arruela M8', 0.00, NULL, NULL),
(29, 'Kit Embalagem E-commerce', 0.00, NULL, NULL),
(30, 'Manual Operacional', 0.00, NULL, NULL),
(31, 'Kit Picking Lista', 0.00, NULL, NULL),
(32, 'Tubo PVC 50mm', 0.00, NULL, NULL),
(33, 'Abraçadeira Nylon', 0.00, 0.050, 0.0002),
(34, 'Rolo Stretch Mini', 0.00, NULL, NULL),
(35, 'Cinta Cat.6', 0.00, NULL, NULL),
(36, 'Caixa Papelão 40x30x20', 0.00, 2.200, 0.0240),
(37, 'Caixa Papelão 60x40x40', 0.00, 3.000, 0.0960),
(38, 'Tampa Caixa 60L', 0.00, NULL, NULL),
(39, 'Divisória Caixa', 0.00, NULL, NULL),
(40, 'Bobina Papel Kraft', 0.00, NULL, NULL),
(41, 'Bobina POF', 0.00, NULL, NULL),
(42, 'Selo Metálico 16mm', 0.00, NULL, NULL),
(43, 'Talão CTE', 0.00, NULL, NULL),
(44, 'Formulário Devolução', 0.00, NULL, NULL),
(45, 'Seladora Manual', 0.00, NULL, NULL),
(46, 'Etiquetadora Manual', 0.00, NULL, NULL),
(47, 'Porta Pallet Reposição', 0.00, NULL, NULL),
(48, 'Marcador Chão 5cm', 0.00, NULL, NULL),
(49, 'Sinalização Doca', 0.00, NULL, NULL),
(50, 'Calço de Roda', 0.00, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `movimentacao_material`
--

CREATE TABLE `movimentacao_material` (
  `id_mov` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL,
  `id_material` int(11) NOT NULL,
  `tipo` enum('E','S') NOT NULL,
  `quantidade` int(11) NOT NULL,
  `custo_unitario` decimal(10,2) DEFAULT NULL,
  `data_movimentacao` datetime NOT NULL DEFAULT current_timestamp(),
  `motivo` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `movimentacao_material`
--

INSERT INTO `movimentacao_material` (`id_mov`, `id_estoque`, `id_material`, `tipo`, `quantidade`, `custo_unitario`, `data_movimentacao`, `motivo`) VALUES
(8, 3, 36, 'E', 1, 0.00, '2025-08-11 02:15:04', 'Entrada por NF'),
(9, 3, 36, 'E', 1, 20.00, '2025-08-11 02:15:04', 'Entrada por NF'),
(11, 3, 36, 'S', 12, 20.00, '2025-08-22 23:16:13', 'Saída separação'),
(12, 3, 36, 'E', 20, 20.00, '2025-08-22 23:16:13', 'Reposição NF'),
(13, 3, 33, 'S', 100, 1.20, '2025-08-22 23:16:13', 'Consumo picking');

-- --------------------------------------------------------

--
-- Estrutura para tabela `nota_fiscal`
--

CREATE TABLE `nota_fiscal` (
  `id_nota_fiscal` int(11) NOT NULL,
  `nome` varchar(80) DEFAULT NULL,
  `descricao` varchar(120) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `nota_fiscal`
--

INSERT INTO `nota_fiscal` (`id_nota_fiscal`, `nome`, `descricao`) VALUES
(1, 'NF Treino 2025-08-11T02:00:07.023628700', ''),
(2, 'as', 'asa');

-- --------------------------------------------------------

--
-- Estrutura para tabela `nota_fiscal_item`
--

CREATE TABLE `nota_fiscal_item` (
  `id_item` int(11) NOT NULL,
  `id_nota_fiscal` int(11) NOT NULL,
  `id_material` int(11) NOT NULL,
  `id_estoque` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `custo_unitario` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `nota_fiscal_item`
--

INSERT INTO `nota_fiscal_item` (`id_item`, `id_nota_fiscal`, `id_material`, `id_estoque`, `quantidade`, `custo_unitario`) VALUES
(1, 1, 1, 1, 1, 0.00),
(2, 2, 36, 3, 1, 0.00),
(3, 2, 36, 3, 1, 20.00);

-- --------------------------------------------------------

--
-- Estrutura para tabela `nota_fiscal_material`
--

CREATE TABLE `nota_fiscal_material` (
  `id_nota_fiscal` int(11) NOT NULL,
  `id_material` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `ocorrencia_entrega`
--

CREATE TABLE `ocorrencia_entrega` (
  `id_ocorrencia` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `tipo` enum('ATRASO','PRODUTO_ERRADO','AVARIA','ENDERECO_INCORRETO','CLIENTE_AUSENTE','OUTROS') NOT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `gravidade` enum('BAIXA','MEDIA','ALTA') DEFAULT 'BAIXA',
  `criada_em` datetime DEFAULT current_timestamp(),
  `resolvida_em` datetime DEFAULT NULL,
  `resolucao` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `participacao_simulacao`
--

CREATE TABLE `participacao_simulacao` (
  `id_simulacao` int(11) NOT NULL,
  `id_material` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido`
--

CREATE TABLE `pedido` (
  `id_pedido` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_estoque_origem` int(11) NOT NULL,
  `data_criacao` datetime NOT NULL DEFAULT current_timestamp(),
  `data_prometida` datetime DEFAULT NULL,
  `canal` enum('B2B','B2C','INTERNO') DEFAULT 'B2C',
  `status` enum('ABERTO','SEPARACAO','EXPEDIDO','EM_TRANSITO','ENTREGUE','CANCELADO') DEFAULT 'ABERTO',
  `observacao` varchar(200) DEFAULT NULL,
  `peso_total_kg` decimal(12,3) DEFAULT NULL,
  `volume_total_m3` decimal(12,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pedido`
--

INSERT INTO `pedido` (`id_pedido`, `id_cliente`, `id_estoque_origem`, `data_criacao`, `data_prometida`, `canal`, `status`, `observacao`, `peso_total_kg`, `volume_total_m3`) VALUES
(1, 1, 4, '2025-08-22 20:55:38', '2025-08-23 20:55:38', 'B2C', 'ABERTO', 'Pedido teste', NULL, NULL),
(2, 2, 1, '2025-08-22 08:30:00', '2025-08-23 16:00:00', 'B2C', 'ABERTO', 'Pedido Beta', NULL, NULL),
(3, 3, 1, '2025-08-22 09:10:00', '2025-08-23 17:00:00', 'B2C', 'SEPARACAO', 'Separação em curso', NULL, NULL),
(4, 4, 2, '2025-08-22 09:40:00', '2025-08-23 14:00:00', 'B2C', 'EXPEDIDO', 'Aguardando carregamento', NULL, NULL),
(5, 5, 2, '2025-08-22 10:00:00', '2025-08-23 13:30:00', 'B2C', 'EM_TRANSITO', 'Saiu para entrega', NULL, NULL),
(6, 6, 1, '2025-08-21 10:15:00', '2025-08-22 15:00:00', 'B2B', 'ENTREGUE', 'Concluído', NULL, NULL),
(7, 7, 4, '2025-08-22 11:00:00', '2025-08-24 10:00:00', 'B2C', 'CANCELADO', 'Cliente cancelou', NULL, NULL),
(8, 8, 4, '2025-08-22 11:30:00', '2025-08-24 18:00:00', 'B2C', 'ABERTO', 'Janela reduzida', NULL, NULL),
(9, 9, 1, '2025-08-22 12:00:00', '2025-08-23 12:00:00', 'B2C', 'EXPEDIDO', 'Rota Zona Oeste', NULL, NULL),
(10, 10, 1, '2025-08-22 12:40:00', '2025-08-23 12:30:00', 'B2C', 'EM_TRANSITO', 'Circuito Subúrbio', NULL, NULL),
(11, 11, 2, '2025-08-21 13:00:00', '2025-08-22 11:00:00', 'B2C', 'ENTREGUE', 'Assinado c/ restrição', NULL, NULL),
(12, 12, 1, '2025-08-22 13:20:00', '2025-08-23 17:30:00', 'B2C', 'SEPARACAO', 'Conferência final', NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `pedido_item`
--

CREATE TABLE `pedido_item` (
  `id_pedido_item` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_material` int(11) NOT NULL,
  `quantidade` int(11) NOT NULL,
  `status` enum('PENDENTE','SEPARADO','FALTANTE','AVARIADO') DEFAULT 'PENDENTE',
  `reservado_estoque` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `pedido_item`
--

INSERT INTO `pedido_item` (`id_pedido_item`, `id_pedido`, `id_material`, `quantidade`, `status`, `reservado_estoque`) VALUES
(1, 1, 36, 2, 'PENDENTE', 0),
(2, 1, 33, 5, 'PENDENTE', 0),
(3, 1, 1, 3, 'PENDENTE', 0),
(4, 2, 36, 3, 'PENDENTE', 0),
(5, 2, 33, 50, 'PENDENTE', 0),
(6, 3, 36, 4, 'PENDENTE', 2),
(7, 3, 1, 1, 'PENDENTE', 0),
(8, 4, 37, 2, 'SEPARADO', 2),
(9, 4, 33, 80, 'SEPARADO', 80),
(10, 5, 36, 6, 'SEPARADO', 6),
(11, 5, 33, 40, 'SEPARADO', 40),
(12, 6, 36, 2, 'SEPARADO', 2),
(13, 6, 1, 1, 'SEPARADO', 1),
(14, 7, 33, 60, 'PENDENTE', 0),
(15, 8, 36, 5, 'PENDENTE', 0),
(16, 8, 37, 3, 'PENDENTE', 0),
(17, 9, 37, 4, 'SEPARADO', 4),
(18, 10, 36, 3, 'SEPARADO', 3),
(19, 10, 33, 30, 'SEPARADO', 30),
(20, 11, 36, 2, 'SEPARADO', 2),
(21, 11, 33, 20, 'SEPARADO', 20),
(22, 12, 36, 4, 'PENDENTE', 1),
(23, 12, 33, 40, 'PENDENTE', 10);

-- --------------------------------------------------------

--
-- Estrutura para tabela `politica_estoque`
--

CREATE TABLE `politica_estoque` (
  `id_material` int(11) NOT NULL,
  `estoque_min` int(11) NOT NULL,
  `estoque_max` int(11) NOT NULL,
  `lead_time_dias` int(11) DEFAULT 3
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `politica_estoque`
--

INSERT INTO `politica_estoque` (`id_material`, `estoque_min`, `estoque_max`, `lead_time_dias`) VALUES
(1, 5, 50, 7),
(33, 10, 100, 5),
(36, 5, 50, 3),
(37, 8, 120, 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `possui_simulacao_material`
--

CREATE TABLE `possui_simulacao_material` (
  `id_simulacao` int(11) NOT NULL,
  `id_material` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `prova`
--

CREATE TABLE `prova` (
  `id_prova` int(11) NOT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  `data_aplicacao` date DEFAULT NULL,
  `id_instrutor` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `prova`
--

INSERT INTO `prova` (`id_prova`, `descricao`, `data_aplicacao`, `id_instrutor`) VALUES
(6, 'Fundamentos da Administração', '2025-08-08', 1),
(7, 'Gestão da Cadeia de Suprimentos', '2024-03-01', 1),
(8, 'Gestão de Transportes e Abastecimento', '2025-06-08', 1),
(9, 'Gestão de Estoque e Armazenagem', '2025-06-08', 1),
(10, 'Legislação Aduaneira e Comércio Internacional', '2025-02-03', 1),
(11, 'Logística Reversa', '2025-04-05', 1),
(13, 'Logística GRAFICA (2)', '2025-12-08', 1),
(14, 'Matemática Básica', '2025-08-11', 1),
(16, 'Prova com Imagem', '2025-08-23', 1);

-- --------------------------------------------------------

--
-- Estrutura para tabela `provas_realizada`
--

CREATE TABLE `provas_realizada` (
  `id_aluno` int(11) NOT NULL,
  `id_prova` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `questao`
--

CREATE TABLE `questao` (
  `id_questao` int(11) NOT NULL,
  `id_prova` int(11) DEFAULT NULL,
  `numero_questao` int(11) DEFAULT NULL,
  `enunciado` text DEFAULT NULL,
  `imagem` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `questao`
--

INSERT INTO `questao` (`id_questao`, `id_prova`, `numero_questao`, `enunciado`, `imagem`) VALUES
(6, 6, 1, 'Quanto é 2 + 2 ?', NULL),
(7, 7, 3, 'sdasdasda', NULL),
(8, 7, 2, 'sadsdasad', NULL),
(9, 7, 5, 'sqdasadsdasda', NULL),
(10, 7, 4, 'sdasdasdasda', NULL),
(11, 7, 1, 'dsasdasdasda', NULL),
(12, 8, 1, 'Quanto é 2 + 2 ?', NULL),
(13, 9, 8, 'Quanto é 90 + 90 ?', NULL),
(14, 9, 5, 'Quanto é 9 + 9 ?', NULL),
(15, 9, 9, 'Quanto é 4 + 4 ?', NULL),
(16, 9, 6, 'Quanto é 7 + 7 ?', NULL),
(17, 9, 4, 'Quanto é 2 + 3 ?', NULL),
(18, 9, 3, 'Quanto é 10 + 10 ?', NULL),
(19, 9, 10, 'Quanto é 12 + 12 ?', NULL),
(20, 9, 2, 'Quanto é 5 + 5 ?', NULL),
(21, 9, 1, 'Quanto é 2 + 2 ?', NULL),
(22, 9, 7, 'Quanto é 20 + 20 ?', NULL),
(23, 10, 1, 'sdasdadsa', NULL),
(24, 11, 1, 'Quanto é 3 + 3', NULL),
(26, 13, 1, '“O perfeito entendimento da cadeia de abastecimento integrada tem\nsido reconhecidamente um fator de vantagem competitiva para as\norganizações que efetivamente entendem o seu papel estratégico”\n(BERTAGLIA, 2009). \n\nA frase indica que a cadeia de abastecimento deve ser foco de análise e ações por parte das organizações e consiste\nem um conjunto de processos requeridos para:', NULL),
(27, 14, 1, 'Quanto é 2 + 2', NULL),
(28, 16, 1, 'A frase indica que a cadeia de abastecimento deve ser foco de análise e ações por parte das organizações e consiste\nem um conjunto de processos requeridos para:', 0x89504e470d0a1a0a0000000d49484452000001f80000007e0806000000c8b6030e000000017352474200aece1ce90000000467414d410000b18f0bfc6105000000097048597300000ec300000ec301c76fa86400004eb749444154785eed9d875714d9d6f6bfffe47def55c94d936990ac80282a06608ca018d0115430ce887146cc718451744445143382611c50318023a8804a94a0929ad44127bd373cdfdad581a6abbbba11d0414fadf55b4ba9ae734e9d50cf897bff3fb08b5dec6217bbd8c5aecfeefa7ffa7f6017bbd8c52e76b18b5d43ff6202cf2e76b18b5dec62d7677831816717bbd8c52e76b1eb33bc98c0b38b5dec6217bbd8f5195e4ce0d9c52e76b18b5decfa0c2f26f0ec6217bbd8c52e767d8617137876b18b5dec6217bb3ec36bd004febffffd2f8ffffce73f0c0683c1603074d0d74a6220ae011378fdc4e926fedffffeb7967ffdeb5fbdf8bffffb3f0683c16030be08f43550a38da644ff43ae7e0bbc3141d724feafbffe0f7ffef917fefcf34ffcf1c79ff8fdf73f387efbfd77fcf65b0fef7ffb8dc16030188ccf125dbdfbfd7742a585a48ba48fa493ba1d007dd1ff10a1ff6081d717768da85302fffaeb2fad98bf7fff1bdebd7f0f85f21de40a25647205643239babb651c5d0c0683c1607c2168b48f74502e5770baa87cf70eefde53474025fc24f8a4a31ac1d715fbbe087d9f055e48d829511a515792a0cb15dc8b747476a1bdbd136d6ded686995a2b9a5154dcd2ade36b530180c0683f1d9a3d13da2b9a50dadad52b449dbd1ded189cece6e4e2f39c157bee3745423f61f2af47d12785d71ef2dec7f710979f7ee3d140aa54ad43b3ad1dad6cebdc89bb74d68687c83da57f5b87bf71e7272aee1e2c5cbb870fe22c7f9f317180c0683c1f8ac21ddbb7a3517f9b7efa0aaba16afea1a5057dfc8e9e39bb7cddce09706c23428eeee96737a4a23fbdfff20a1578de875d7ec4d89bcd9026f68d4ae9a8a578dd829215d5ddd686fefe07a26afdf34a1a6f6154ea49f44c2b2044c18170a3b2b6b787b8ec4b83121983c310c93c32631180c0683f16530318cd33fd241d2c3f16343b12c7e298ea61dc38b9795a8a9ad437dc36b4e3f5b5ada383da5697d5ae2269da5a56fd25d43a37943975902af2fee9ae978da2c4011d3889da6e069b4def8fa0d8a8b9f607dd206b83bbb624ad824ecdfb71f7979f95cef44f9ee3d83c16030185f34a487a48ba48f24fe6ece2ef866ed37b877ff212aab6ab819efd76fde7203669a11a7357b7a4e336d6f8ec89b1478dd6979cd943cf522a837416b05b46ed0da26e57a1c1595d558bb7a2dc4b622242e4f4049c913de4b31180c0683c1e80de965e2b204886dedb02261058a4b9e729a4ad3f8a4af34754fb3e4345b4ebbf2356bf342d3f58202af2fee9addf1b42640bbe16963008dda694ae1dab5eb18e5e78f39b3a3d0d0d0c84b3c83c16030180c61483fa36747c1dfc70fe7ce5d4059f90b6ebd9ed6e969d44febf37285821b641b1279ddcba4c0eb4ecb6bc55d2687b4bd93db1440bd8b5d3b77c359ec80ccd399bcc432180c0683c1e81ba4a74e62076cd9fc1d9e3c2dc5cb8a2a4e6f69373e0daee9949ab191bce6322af006c5fddd7b95b84b3bd49be8eab07ddb768c7497a0b2b28a97400683c16030181f06e9aaa7bb041bd66fc4e3e227287ffe925b9b7fdbd4cc6dc0e3445ebdf94e774d5e23f206055e57dce921ea21706bee7205a4ed2a71afae79856dc94cdc190c0683c1182caaaaaa3991a78deb8f7e2dd68a3ccda07323798512bf712379d5ee7add4d778202af5977e776cb2b945c6014288ddcd3d34fc0ddc595893b83c16030188308893ce9ed81fd3ff412799aaeefecec8242a9e476d7935eeb8ee27902af3f354f0f91551dda2d4f1bea28d0a2478fe1eeec825f7ec9e32584c16030180cc6c0427aebeae48c9cdc1b9cc83f7f51c1adc9d3313a3a2b4fbfd19faa372af0ba53f3dd323977148e76cbd361fc98e83958bd72152f010c0683c160300687552b5661c657d371e7ee3dee181d6dbca3ddf574848ef6c7912d7b8d211c9ec01b1abd7353f3ed9ddcba3b1dbe3f75ea34464a3c2095b6f3226730180c0683313890ee92fed2547dc1bd8778faac8c3b42474be7747c8e0ccf917559cdae7a8302af19bd9317b8ae2e1977f68ea6e64bcb9e635ac457389a7694173183c16030188cc185f4972cdffd7c2b0f8545bf72ebf164cfbea5b58d9b6d579d8f57adc51b14f8dea3f70e34be7ecb59d4b971e36788ed446cf4ce6030180cc62780f497acdd1d4f3f85db775453f534bb4eb3ec64d256338aa7813a4fe049f5690e9f5b7bef96730bf89ad1fbf2a5cbf0cd9ab5bc08190c0683c1607c1cd6ae5e839839f370fdc62d3c78f888b376471beec81d3badc5d3c937d2f15e02af597fa79d78b4739e7a031a1bf374c8decfdb17858545bcc8180c0683c1607c1c8a8a1e711ee92e5fc9e146f1254f9e69d7e2e9c41bfd8696d979024fc3fadf7fff9d336a433bf3686e9fe6f8f36f17c0dac252bb1d9fc16030180cc6c78774d87a84054e9e3a839b3fe77147d7e9841beda8276374b4bc4ed3f43c815719b6a1e9791977ee9d8cda901ddc23478e21242898171183c16030188c8f4bf0e8406cf96e2baee65ce776d43f2b2de796d369599d36dbd1347d2f81a7e979da7d4736e769cbbdeef4fcda35df6065e20a5e240c0683c160303e2e2b125760e182585cbc74959b61d74cd393753b722b4bfbe80c08fc9fdc2e3cda3d4fc37d1af6d356fcd805b1d8b7771f2f120683c16030181f17d2e3e9d36620ebdc25fc7c2b9fb36e47866fe8d41bed9fa3dff0049e36d891f17acdfa3bedcebbffa008317363909a92ca8b84c16030180cc6c785f438223c12199959dc6efa87858f38f3b56471963cbed240bd97c0ab36d8fd01995cc16db7a7f97c9ad7a7f9fde8d9d13872f8082f12c67b28e51d686eee804cffef0c0683c1600c026947d23065f2149c3895899cdc9bb8ffa0507b5caeb5ad9d1ba81b14785aa027eb75b4c18e4ce1ddb97b1f51b3a23ebac04b1f9dc68f39b5e82cbd80d48bcf7b04b4ab160f2ea5e3c0774958b769378edf788e5603cf0f3e72bccc5a89f1620bd8da8dc2fadbc64f18c8eaeee1dcc522342af8f73e4f5a707a9e334236dd83f29d02af0acee162e11bc879bf1baae8be9ffebd8f89f9e9188c3ad89c311f6e419b51d0cdbfd76fda2f22de35004979aa633f7f1be4f528c8ba84c2d74afebda1c0904dbff975fd4b8013f8495370fc4406b2afd246bb07da8d763440a793703c81ffedf7dfd53be85b389fefb4707ffb4ec1c71778453dd2a3029098db80cb4bfc117ba699fb7b77c539248eb68724743136eedc8b9d498b10ea6403dff9e978d665209cc1a4f91296383922ea6805bade292137fae154e2d5f159108b6271a18dfedf85e2539bb0f5ec8b4f3aeaef28cec0a6efb3f05ccebfd77f9a913ec30a3e6befa8ca72a6088e0b2f7ce48ed860e6b3cefbf1ee7d4ccc4d877e1d1c189a8ecf84edc86f707b50043e0bf3ad2558f5f34009fcc0d407795d3aa2ececb1e8bc94776f50e92c41c6c6ad38fb5cc1bfd707fa96fe81c9b381c1dcba2ec000e5e1df0112f8c9619371ecf8495cbe92cb0dc469404e0373da494f066ff802ff1b5fe06987deec99b33faac0cb2a5310e13017a72b3231cf29023fd628a19495e1e06411243119a890f5fcb6abfc18a29cac31616709ba0d84355874177e8720cb081ca937a7272c4747a75cfdef37383edd1a819b8b3e6a7af569fc69066c03b6a050272f070ebdc628eb42c7a0c423c460e6f3007c6c0684bea443b70e0e0c434be007ae3e747776f53b8c3ef3fa38665a8dc27785fd2f43f3d33f7079d67ffa52d78d308079f8a9d108fcd19f4ee0d2e5ab9cc11b3ad24eba4d47dc6926be97c0930d7a12787230435bed69cb3dd9b9cdbf7df7230bbc120de7d660faf25328b9b11551b187512c7b8faebbebe067118addcff47b5f723cde1a042baf6f853f346d39581fbe0227f332f0edb42078b97923745612b24a3bd4bf91a1227b1b164f0d86bf873742a3be477695aa22b45e4d42e4ea0c5c4b598a88c07864161d475c901b6cfe610fbfb1e311bef1063a049eefbcb10591b3f7e05e6703b2564c80afdd3f61eb1a8489136660e75d9ada57a0a120152ba68d85bfc40b215362b1f5e20b74e8bf030fe371b65d5d8fc8c4e3c83d9280087f093c7da622f17809a4ef94a8cb5a89302f114658ba614ce844ccda51f08161f5a4a32a671b164ef0c748f7004426a46053b8a5ba3176e3e6e60844efbe8f2e4d58abd291bd7f11267ab9c32b38067bf31b5171690366057ac2d3670a566494a15313767725b293172122c8075e5e6331f7bbaba8e23a0bedc8498ac0ca9f7291b63c1ca3dd25f09f9280f4e2762815039fcfc6dfef3d64e5e7b13e7a02467bb8c1c36722961c28c01ba3333a1a94682a3c81a43913304ae20e2fbf095899550ba5fc39ceaf9b83307f4f48dc7c3169d141dc7dabe9440aa743a80cb57590da88601cc6e9aacec5f6791331ca5d82d1e18948d910011badc01b8fdb2842e9e004de1dcb8f9ec6fae9c15c7b1d37fd5b643e53fbc2107ab6ad084797852350e206efa099d873a7ca487d104ab391f2e9ba89efc2a3b0e71e3dafaa836b8e5fc5c1d8f1f073f3c4d8b97b71bba11297d7cfc0180f09fc2727e274694f27a5abe22ab6c78623c4db13be21d1d89a5dad124f690e3684afc0f19c34244e0dc048775f442c4f47b1f43de4f5e7b06abc37c4ffb08224703c264ddfc985256bb887d484e908f5f1804fd0542cfafe129e771ac8675d0ca4bf6f6d4828cf64a8bebe138bc2fce1e51e8088c49f702a790e961c2985f29d1457d74560eda9eb488d9f8a317167d12c548626ea7a5fdb9cb13c34f57de94bd97e4c48e027854d46dab174eea81ccdb4d3805c25f02ddc405d40e09bb5029f977fe7230bbc2114a8383009d64644bcf34622242322904a237ddeb36aa4a731d7c20e2ea3a2b0fdfc439495e6212566246c3c57e07acb7b48ef6e44b05b24f6dc6e4047572d725707c1796a0acae5ef21cd98035b2709422236223dbb08352d0d7892110faf1193b1e3ceaf28ae6846abd0f367e741e492806b5d323496dfc1f6304bf82dcf42e1e327a86a51425ab00521763ef83aed1eaa1a6b71ffe862f8597963f5f516fe7be82098e6d3736063e588d0254771bba40437f74541623906db1fc9d1d5f81cb79327c1c62b01670b8b5152d5fac161513a5af3be4580953716a6e4a3acba1cb7f645c373d8307563ecc0d9181b48965fe7445b15963326af3c83c2d22264c4fbc2dac91b13e76c477661096eed9e0e57bb481caea08e5c3bee6e088447c46edcaeef42674d2ed6063a20e210edc9684766b425ec1cc6223eed364a8a6fe2c06c37d8066fc323d9c0e6b3f0fbbd87bce10172f3caf1a6b30b75799b31c1c6074977841b7ee7e33d986ce78c884d6750505a8d8a923b28ac9443a968c4fd9c7c94bded42477d3eb68cb385ffb777b9bc33950ec132d4d6415a02331e877e3ab5b4e5639daf357ce7a522afac0665b7f6638efb7058a8055e286e5e581a84d241026f350262ff18ecba5884f2d23ca4ce1b091b4902729b859e95e3c98e71104fd88687af5b51f7e4219ebeee325c1f04d26cb47cb88e872b12af51f9aaeba05318566716e159e1692cf3b1809bd778c46ccb4661f12fd833cd19e288c3a8a07c901660d368774cdb7507759ddda8c9598331e270a4942ba0946622c6c21a2e2171389aff04c537f623dad50a63937f4577f76b94dfde86c916de483c5b84c725d550b6dfc3774122f8c71ec5bdead7a8be7f0c4bbcace1b7f2069af5f3591743e9ef4b1b12c8b3b6fc248cb6f6c1a2c3b7515e5d8e9bbb67c3cbc20263934bb8b84e475bc1cd3d18d336a423bbb016dd46cbd0745def739b339487e67c5fcc2d5bfdf8061995c04f42dad1745cb898ad3d0b2f28f0ef7ffb4d2bf0e4a14623f0b33eb9c0cb51bc3518d641c9dc685eff7e574112fc4784e1c04bfdd1bd0edc07c30ad38fd46a377bc91b4e61ae9d185f5f6cc4a545ce18b52e1f6f5a5ab94d86cd4ff762b295aad3401f47db11539152d9133e17a7e54ca437d1ffdb4d3eaffdb8f2a6bda4b810eb00c7b9993d1ba0146f9039d71ef65119fcf7d0623a4e5beb6864bc55ffbebb00495e96883854cbfdbff7147d7fc26ac5f905f6709873ba27fdf232ec19676158e0cfce839d6801ceb5a87edbfd74274247f8627d817a93627b0e9639d9616156079452dae7e08fa4bc26559a5a5af0744f18ecc25351a3a070ad218acac01b4d99dc4d829fc55475476fa0f2d9d4fb697ed78dc6b2fbf8f9ca612ce1f2a6c640581ada91bdc419e2e9c7506d64d4d1d9588e7b3f67e3f0226fd84c494195c2543a4c97614f1d3416073f1d1a9acf2d84a3682e321a359d68054a7785c29a1378e1b8f5c3d2c7603ab8f66a8d59c71a74da6b0662b8f5e31e8f96fc67e528fa3e08b6fe89b8f452d7f3a57e7d104a739bf1f2e92590aa3ae830ff9c5a54e578ba632cac7d92b41b0fa55797c2cd763eceb5bf47dbc5c570f54b42de5b757ccdcfb077a23522536a21e7ded706734f35a9e392e1ee3ad53b7169d09b5e6e3dbf08cea218646acb4389c6d33170b4d169a78630907ef3db90509eb5e2c242319c62cea0511397ec576c1d6d89d0ed4fb571594f4941a59e20f2cbd0545dd73cdb9736c7cf4373be2fe6962d2fae414623f0478e1eefbbc093d17a8dc0ff92f777107825ea8e7c055bc778641bc8cce6cc1888ede6e3ac5a340ca29ef25b7943a797272bc2f7015688fc211fbbc70d87ad9317fcbd7d7af08b466aa95cf571744dc40d9d8f632f819797638fa9e78d093cf7ac2542b616ebac73c9519c1c0c9b31dbf9efa1a14f71aa1ac8eeb15698b0bb8cfb7f2f81ef4f585cc3d34fbfee7a195fe0ed3dd6204f1d96bce647445a8560c71375a3ebbe8f8dbed6883ad10259d91e4c18660dd7913a69f2f6c1a8a8143c93f50e979e9595eec278cb50ec29a58ed840e5b3a9f77b8f8e6727b13c4402ef71b3f0f5f23884bb5968f3d920949650fd30d57496e2e4d2b118e9198aa8d804c44f7687f5b83d28ed32918ebe94a1b1388c8e4454626e13948cc73a1d6ced1a7c8770dcfcf04cbc2ba543a0bd4690200a3c2b6f7a8094858170b4704268dc113c6c32205642f9f5e499f1f2e109a42dbc56e7abeba01235a911b01db31d4fd4f9d4756f0302ac66e364b30265bb436169e50c1fddf8bcfd31e75019ba7b85abcaf3673bc7c156931fbdc44915967e7974176f4388854e5b32042ffd7d6d430279a65f3fe59538186685d01dcf7ae24ab8d9335364ac0c4dd5f50f6973849ec09bfebe985bb606e21a647405fefc852b435de0a930f663b2a50b169f7bddfbb895a20e27a3c410cf4ac72bfddeb62e5cc57644dc65cd9a3bf5e0b211ef2442ec9997488bb4c6b86d4ff80d5a2d4a22f795f8d998c02b1a4c3faf2ff09b8ab835699a6a3c36cd1aee4b7374d6b4db7135de05b6d38ef1c2d2d2a738d5a23c4e4fe0fd37a3907aa3fd098b7bd60a92841b3de95754e3d024e3237861817f804d6a8197d7a7613add2b31f4c132fc719aa0ff711a907c16783f452d8e4eb383dfca9baa9e3edd9b6c2dfcb151a745b234076dbdee295193361d62af55b8c97d3494a8fa618aea23df6d2a1de696a1401c46055eddc1764dc4756d079b9e9bac1ac1770ac76d1813e9d0b6579d51b8f40ae29dec119bd52afcac3afce6d22c24fa531d7868a03e08a4d968f91812481322707fa35a0454796837663b4a0ccc42f60e97fe46021faa27f001d85248335d4ad41f9d0e5be765c891f68441234a77abe938d620306bc24b7f5fda90709ed16fdde3aef6e499ec5724075af61278cf15b77af2ca58199aaceb1fd0e6885e7948ebf2a6be2fe696adfeb3830f5fe0ef6a059e33573bd4049e7a7037d704c0d67506f6e6d542aa780fb9b40a37b745c0d5763c7614ea08b721b829b07fc239f2001ed1485fd18c07db2743ecb410598d32541c990607d7681c296e553f23475b9b2a4c93024f7b044c3daf15c80e5c582082cbc2f378cbfd4e89eae35170124dc59e22d5d195e6873b31c5ce11312755d3e986e94b9c7c81979e5f08b1632ccef739fdfa6129f032350262fb08ec2ba467e578756d2d42ac86c37b753eef23d21781a71140da57f690441d41b1667646d68e564e644c7d9c062e9f05df8f9bcdb044e0a6422e1d9da5a998211aaefea8e987a5419d16ab60accdae52a55f2e45ab54356ab309d88c87943f5d6548fdca1e96213bf15466221d6697a1501cfae9ec41f6f2477c652b46e4de22eea3da5d771ddf0459c3d2730df2bb84e3368c8974b49fc53ccbe17089dc87426ecdbd19f7922741ecbc08e71b6502cf2ad14ad6bcb8389a9035df0992e5d70cd407a1341b2b1f430269be08c82ad23043e486b9874bb4ebe4dd52a92aada604befd0262ed9cb0e89ceac8b0bc261dd1f6f688d8fd4875fcb4b9103bc34470893e851a93031d7305be6f79f6eaf43cb8da8e45d2e517686ea9c19d43f3e1354cd316f4055ea8fc4dd4f50f6a73fc3c34fd7d31bf6c79710d3286049ef47a080b3c657e35ae7e371bfe76c3612d728283952d3cc62dc20f77f446f586e02ab633a67d1d8360774f7839db42e431033b6ea9d7f8e435c8de108191367670f7f283bfbb13bce22e720dd1b4c09bf1bc5620957893b306a3680391cb14ec2ca28d55f5b8b5230a7e2227f8fa7bc3c5611416eebb8d06a186daa738f545f93de46f73b1d6cf1a5676ae08dff1a85f6129bb2b706155289c8659c1d9c50dbee19bb127de0723136ef23e227d1278fae054676353b804f6b62ef0f5f581a7a327965ea07ba63e4e0398cf82efa740d5d978f8dbdac32f28186326276079b83d02924c18e45034e046f24cf8db5942e4248187d819b3d36a21abcac2321f3b387a052324700a129746c0d177bd6af7bb603accaf0f8271e8a7538b0c2fcfafc604fbe1b015bb42e215812dbb96c2df5dbd742510373f2c1582e9683989289ba958bf7b3e463bbbc1c3c11ab61e33b12baf916bafc69f6d457682375cdc0230c6d7056ea3e391514e75cb407d104ab391f2e10b645f444081aa2b1b11e96e0bb1b33702bc2570f188c745ba674ae0154dc85ded0fdbe1224826d30e70255eddda89682f7bb87805c0d7c11181f3f6e3b6d0e89de0a5bf8f6d4830cf9af120351e93bd9de1e0e88b696b0f2169bc35c2f6be3020f0426568aaae7f689bd3cf4353df97be94edc7e5f314780d5dcda87959897aa989caac0b57b1d5e76a65cda8a979830e031f7679fb1b54bdac40d5eb0ed39d060398fb7c774b3d2a6b9ab5959d43d6829aca7ab40a8ca40c616e9c3c64ad7855598bb73a1d970f0e8b1a4b4b1d2a6a9a55d379038a12d2d7d578f1b2068ded7d28f301ce67a1f793491b5051a91939f60145071aab2a50d3a4b3d62c6f471d398e30b0df84104a076156199a88c328f256bcaaa8459391ce805971f70acf743a646df57859f99adf5e8d3eab80f4752daaea5a797964a83e08a6d950f9f41775982faadf703391bcfb4691a3b9ae0ad5bdd22247734d155eb5199a661e18fa9c671ada6f61954484f96735a37d03182d43154275fdc3da9ca13cfcf0efcba7e2f316f80f4157e0f5ef31180c06a37f74fe8a8b27aea1f0791d5e553c40e6aab110bbc5e1b2193616187d8309bc3e9c818768ec7f68dc663c83c160303e0c79c30d6c9f3311819e6e90788ec2a439eb91f944f7a82263a06002cf6030180cc6670813780683c160303e4398c033180c0683f119c2049ec1f81b216b6f4173bb8029650683c13093cf52e06575f770ee62518f7d623d9a33e6c32d68b3d656f050477a210e129fb5bdcedff71b793d0ab22ea1f0b5e19dad9f5b1e7e72641538b7621c9c4758411c9064d059d227a5f93416380563b3c61700e38bc5d4f7d5d47dc6c7e3331478255e1d9f05b1281617daf4efa91854ffd49f009e819901405e978e28ce6187ca9a9b3e7fbf3cec42f1a94dd87af60564bc7b7f7f9ac9c188c36c1c7b298352a1347e6698ec6b176760d3f759786ed464ec20d0948ed9965ef8f6f6df53e03f499e7c11e8b72bfdefaba9fb8c4fc96728f0841c1d9dc60d3afcfdc4a97f0c86c013dd9d5d7c5bd26afe7e79a8efd16a282147e196d1b00d3f823a33463dbd3dfc7d24fee602ff49f2e48bc050bbd2fdbe9abacff8940c718197a2306d3922fc3d20f10cc6ecddf7d0c1f97ddf82c8d97bb42635bbaa73b17dde448c729760747822523644c046479c640df7909a301da13e1ef0099a8a45df5fc2f34efdb80cd37a350991ab33702d65292202e371b6f93dba2aae627b6c3842bc3de11b128dadd9d5daca2f6f2ac289757311e6e7010f0f7f84259e53d9849637a220251133427ce1353218110bb7e2d2f32e553cd21c6c085f81e33969489c1a8091eebe88589e8e62b5030995c0c7e148c646cc9be0072f9f29483c5ea263b949868aec6d583c3518fe1ede088dfa1ed9559a06a84453e10924cd998051127778f94dc0caac5ab53d8028ecb9a7faa00be6a1fc39ceaf9b83307f4f48dc7c3169d141dc551bad68bbba1e91abd291bd7f11267ab9c32b38067bf31b5171690366057ac2d3670a566494f558bfeaae4476f2224404f9c0cb6b2ce67e771555dc47bb1d39491158f9532ed2968763b4bb04fe5312905edcce990bcd5a3101be76ff84ad6b10264e98819d7729dd42ef6d02692e364e9d879462cdefe57874300611492ac7227d7e2fa328f0fca7250871b5c4089137c6874ec1a6eb5d90959fc7fae80918ede1060f9f895872a0006f144ad465ad44989708232cdd3026742266ed28e0c2682848c58a6963e12ff142c894586cbdf8826b0bd446aeae8bc0da53d7911a3f1563e2ce0a9a85d5d25d83dce4f998e427c148ff08ac38b41191163d022f54c78568beb619d3a625e317adf313399e1d598488e5a751d565a21e251e47ee910444f84be0e933555dc78de489409da47a517d7d271685f9c3cb3d0011893fe154f21c2c3952aabd6facde0c5cb9ab5134a3303d0931e3fde1e5e68951e357208b73c12a54a6aab6b0e6f8551c8c1d0f3f374f8c9dbb17b71b2a7179fd0c8cf190c07f72224e97aa8d75b5e5607df80a9ccccbc0b7d382e0e5e68dd05949c82aedf10160b03c8db42bedf7b5d3c4fdd7d7b139623a927fe939e3de5d9a86c553129051dd8df273f4de01f0727587ff84c53878b74970e6aa07559d5e79221f196ba7638cc41d7e21b3b13eab4cfdcd53180f9bcb8b35c8b8968a655382b0f44c8b91b6a61fa761849e6d2d3c8a84a9948691089941f9c57f7eb019d202dffd6427c6db4dc4f6876fd05cff140f9ebee10ab1d788b62d1feb7cade13b2f1579653528bbb51f73dc87c342234eedf7f05d9008feb14771affa35aaef1fc3122f6bf8adbc61d687509a3107b64e1284446c447a76116a5a0ab069b43ba6edba83bace6ed4e4acc118713852ca1550761563cf4411dca66cc699823254553cc1edc22a74bfeb40c1e66088bd17e168410d1a6b1ee0d8226fd879adc275b25f2ccd448c85355c42e27034ff098a6fec47b4ab15c626ffca7d54e97ded86d9636cfc51dc2e29c1cd7db321b10cc1f647aa8f92f4ee4604bb4562cfed067474d5227775109ca7a6a05cfe1e9d8ff760b29d3322369d414169352a4aeea0b052dedb16b5a93c5434e27e4e3ecade76a1a33e1f5bc6d9c2ffdbbb2a9bf2a7e7c0c6ca1993579e4161691132e27d61ede48d8973b623bbb004b7764f87ab5d240e57d0c6b276dcdd10088f88ddb85ddf85ce9a5cac0d7440c4a1e790bd6b4766b425ec1cc6223eed364a8a6fe2c06c37d8066fc323990c8de577b03dcc127ecbb350f8f809aa5a9482efad5f8e3c5a4e21dad21b49773423d66edc5e3312b6b34eaa6cb6f7e9bd0c84af5b87ea9fe2749c07ac276dc79d4725a8685242def000b979e578d3d985babccd9860e383a43bdde86a7c8edbc99360e39580b385c528a96a85b4600b42ec7cf075da3d5435d6e2fed1c5f0b3f2c6eaeb642bbb1da7a3ade0e61e8c691bd2915d586b86104b91f78d1fecbce62335af1c5565bfe040940496ff540bbc54a08ef3c2ea8dbcf13462442e88bfa25efa9195607bb03526ef7b0e99c97ae488d0259a3a1e0589e518ae8e1bca13a13ad9969f84d1d63e5874f836caabcb7173f76c785958606c72099726a17a3390e54e75eaf1ee30889da66273e63d945655a2e476112a65ef4d9629d7169cc2b03ab308cf0a4f63998f05dcbcc623665b360a8b7fc19e69ce10471c4605d575e969ccb5b083cba8286c3fff1065a579488919091bcf15b84ece538c9667a7e176a5fdbe1a69779afb1daf717a8e3ddc9764ab1cdd706e97c7c06ee27e94cb95a8bf9f8bbcb226747436206f5328ecbdd7e18e59b390edc888b284d86134a2932fe04159197e39340fde1623b1f23a99bb15085b7a1a3196ce181914894dc7afa2b0466eb4adf1e3e563f459d953ec1c2bc2a4e44234b634e0c983679f644fc2d016f8a2ad1863198015172b7ad919d615f8e6730be1289a8b8c464def5de5b39a735fd9fd1eade717c15914834ced7d251a4fc7c0d1261a196ff971ea4371d98e988a944a55836ea3b554bf24e4bd6d45734b2b9a9b9f61ef446b44a6d4a2e54a1c5ced66e018d743d709a7ed02168bed31ef748f331cf9eb4ccc13d962eea926b5873b1bd5bfb9fb32dc5de70d9b2929a856a805de7a0e32340e6dbaef22c9cb121187c8fb593b2e2d72c6a875f97843e9219eeec564ab08a4d6b4217b8933c4d38f71e1f44a938ec09bca43cd339d8de5b8f773360e2f52a5ad4a9336d1029c537b61ea7eba13a1237cb15eb359ab3d07cb9cecb030ab034ae9252c71f247525e932a9d2d2d78ba270c76e1a9a8519053076b88a232f0461d5fd7dd24f8594c452a979ffa538542ef6d78e3602f4c097c5fde4b3f6c1e3214acf385ed8c7434f5fa7b371acbeee3e72b87b1842bcf1aeeefbda7a3a5b810eb00c7b9993d1f10c51b64ceb5877d5486da198635aca7a4a0d29c8e0dd1721eb1227bc464f4d44759e96e4c18a11278a13a6e7a04d6828b8b9ce1bae892cadbdca3ad18a3d7268cd5235b6b9d36d95da053c7f5f3a4077e58525c58288653cc19346a7e27fb155b475b2274fb5393f56640cb5ded867ad651fd7c33af4c1de69f530f42e478ba632cac7d92b49b5ec925ac9bed7c9c23dbeddcf7c30ad38ff4c4236f3885b976627c7db1dd4479eab72bfd2541e1fbb4b7c4cd69b1ca510ee573a0083375dfb7eb35caeedd42f68f8b556db9da8cb6a97e7fdbc8b41e8f788a469c8a12c129f6528f1b5a4361737961818843557a7b750cb735f330f0acec11b6065a2130e1125ee8b8eafdd80c6981a7e9adfb8762112cb2844b483c8e3c6cd61bc1ab84c82628198f751a7ecffab10265bbf9f7bb8bb721c442c77399005c5cae6a4f59ef54e1595a39c3c7db07fe5afc31e7d0533cdd3d9e1717212bdb830916c1d8f658273e1ad9045962dcb6a7463c488dd37a90eadde0d45edcc6aabdb8c9cbb167dc70d83a79e9a4c707fe7ed1487df20c7b422d11b2b5983faad3c6d961220fc9b674294e2e1d8b919ea1888a4d40fc647758eba44dd82bdc7d6c547b85e3f26198355c47eae69d0f4645a5e099ccb047abf1fa3ea9351f1aa1f72e355dae1a815f2720f0e6be172f6c1e7c81ef787612cb4324f01e370b5f2f8f43b89b85d62b5f2f31e3de53bf0c69a4140c9b31db7b3c8125dc347bda9813738b6024ebd647ed1a7ca7401d2fe3d72303486f2462a4c3029c6b92a1704b205ce79f5375da4cd423a3755c3f4f84c2ea32905ff24a1c0cb352b91135516f06b2dca9be4fd4cf672e3d06d2c82b53131eccee6de8f160c6b56577acbca1332a9515e1fb002b44a654e19960790a0b38afdde9df97dec40a7747c46635a3abf03b043b2d4016d749ebc2b313cb10ea3e1213662e42e29229f018a169cbfcbcea8daa4e7b24ead669dac7320ab6dc6040206c2e2fdcb042272f84da9a298c3fab44d3fd142c1a6d0f1bc7b1587ab8104dfa83a88fc0d0167835f296329c5d1e005bffcd78d0ad5bc194a83bf2156c5d13715deb854889aa1f26ab479f4ad41f9d0e5be765c8d1e96551efd7dd6a3a8e9972a9a8a9cc5a17b1aaf8ecc66c47096fb38f3a2ed7de7111f286639861e582e55775ec314b73b0ccd91a338e361a11f81e1791063f7e1a37ad8a06a4455a63dcb627fc8fafa211c7a65943b254b5aedceb9e36ce4e93795893361d62af55b8c9b943a47b537aa54df883d8e3f6555e9f86e974afc490001b16f81e9795ea0fcda622954729a1f73607e959ccb3724162ae7a1fc4bb6e5c4f70838dd902dfdb9dad307a02afa8c5d16976f05b7953ed5eb31a87265bf71633ffcd28542f8f5019ba2fcdd199c56ac7d57817d84e3b66d0fda629b872b074c38aeb3da35079d5214ce546f05d0275dc4cba0ab0dec711b199bf60f3280f24e652bd375d8f8cd671fd3c110aabbb91ab27ee71577beabcec5724075aaa04de44bd19c87257b57bb7deed9e30ab4c4d08bcae8b52ae2d3b22eeb2ceac827af62036abd54479eab52b7d013779bf1b77d7f9c265fe19dcda341a5ecbaf71f92eaf398a19b6de587d43954f54bfc2adfa26f02e5f5fe9953fdc6c24cd6a0885adef4ccc445b13c49c6715ad78763601815601d8fce0e36f501dd2022f6f6bd4ba0f7c93b5002eae09b8d6d9bb82c95efe88af6cc588dc5ba49a12acbb8e6f82ac61e9b906f95d54d1d2116d6f8f88dd8f54eb44cd85d81926824bf4a99ee91f017a0bfc7bc82ad23043e486b9874bb46bf8dd522957115571592364f5555472bf57a0b58d3689d5227db6180e53f6a0883b5ad282873b2641ec3007a768eab23f02ff4e818a23d3e0e01a8d23c51a778c72b4b5516357a2fa78149cac82b136bb4af5b1904bd14a1d109d3885f350359b6013b0190f29feae32a47e650fcb909d784a6b897df920ca2b91f6953d24514750ac9e0255cadad1ca95b12981efc0850522b82c3c8fb7ea3c32fedebdcbd020dc47df023ef1d9a8a729e2d2742c701f0eabe9e9dc68b34fefa51f360f3d81e746a79608dc54c8bd6b67692a668886ab0488e23ebf1062c7589ce79664d465289a8a3d45aa75ede6873b31c5ce11312769fabaef02af9457e0c7703b3885ef43219583ac1ed7d604c376d848accdeb16ace3bcb00c22c7a3ef83e11214027fff2414a8db82a97a64bc8eebe78950584abc3a3d0faeb6639174f9059a5b6a70e7d07c780dd3e4af70bd19d07257b77bdba035c8ae54b56d591be5a33965da4781b7fa279c230fe0119527f963df3e1962a785c86a549a284ffd76a55f16a6eeab9661421c8231ce3b00eb0bd4ef49b36f16a3b0e521fdbf1ba529d3e0306c2c763e35d4b9d74735453f42fc150e3e52adb9373dd881a976ce88cd7a8d2ea1b0f505de445b1344e85985140d8dea6fcddb73887574d3192c7c3c86b4c0376727c2cfd11da303fde0ee1488a5a79e6b379df55430195e9e5f8d09f6c3612b7685c42b025b762d85bfbb665a5d8957b77622dacb1e2e5e01f0757044e0bcfdb86dc6e89dd01778fa40545dd98848775b889dbd11e02d818b47bc6a0d8a46f837b661b6b70836d6ce18e9e600d71947b9f56f79dd2fd839db1b8e8ede18ede5089780f9d89fdfa85aabea97c0d3ff6b90bd2102236dece0eee5077f772778c55d54f73a1b70237926fced2c217292c043ec8cd969b57a710ae7a1ac2a0bcb7cece0e8158c90c029485c1a0147dff5dc2986be7e1065d5d9d8142e81bdad0b7c7d7de0e9e889a517e89e298157e24dce1a8cb21a01b1cb14ec2c920bbfb7499468cc4dc238bb61b0b2738064f422ec4b0a875df88f5cc7afafef258cfe14bd025567e3e16f6b0fbfa0608c999c80e5e1f60848baa78aeb6d2ed6fa59c3cace15e13b1e41a9a8c7ad1d51f01339c1d7df1b2e0ea3b070df6d34701dd40f107812889717b0669c1896960e90b87a2372d36e2cf396a8a73685eab879c84af7226cf8f05e236553f548a88eebe78950589cc0a5c663b2b7331c1c7d316ded21248db746d8de17eab08dd79b812d779a2db989ed337c20b6b081abbb3b9c9d66e22875ea4d96691f05deda19d3be8e41b0bb27bc9c6d21f298811db71ad46be142e5c96f57bdcbc2d47d5559ed1d3f0296bab304f26a9c8df38558e48d90a020842f5f8648911f369865484955a7dd229660de6809bc3c5c606fed8959db7f511d33150a5b5fe04db43561049e6db98a155e4ef0f00f4280b33382e332506e7086647019d2024fc8dadfa0baaa1ecd3a9bbd0c226fc5ab8a5a3419fd9d1ccd355578d5664e0fd20c141d68acaac08bea3790f2660294903656e3654d33efa3dbdd5c8bca3aa9c1e9c1fe226f7f83aa9715a87addc1df0ca54e6f4d93c0ee51a13c94b7a3aea24a3ba3d23f9490beaec68b9735686c37afa3a5a1bba51e957af92af8de2690491b5151d108a9b91bd4061099b4011595af0d8f8c65ad7855598bb7da8e25fdad053595f5681db00f8902cdaf2a51dd64e4a32b58c73f90fed423fd3c3137acf65b58251161fe59cd885d457fea4d5f91b7bf46d54bbdf22406a24c75454dd68c9a9a37e830545e02e569a85df5e5be611468abaf44a566a46b36ba9d56399a6a6ad1d8a9ff9de85bd8826dcd04469f9577a0b1a61aaf5a8cb49f8fc09017780683c1e8139dbfe2e2896b287c5e8757150f90b96a2cc46e71b8ac3d27ff99c11bb50e753e6c56ea4b84093c83c1f8a29037dcc0f6391311e8e90689e7284c9ab31e994ff436ba7d4e7046aba2b1ffe1a71b490e2cddb8b9390273f7156a37f6310cc3049ec16030188ccf1026f00c0683c1607c863081670c4998df740683c1108609fc0031a83e9015cd28ceb984bc972da82fb88cbc9a2f58d8fae337dd848f7bc6a7a205a7e739236493394793189f12e98538487cd6f2fe3e5468bdb3178b637723bfc99c6f8002af0acee162a1cac709ffbe39b4e0fef164a4dca841f1996dd877b9f2a3bab366023f200cae0fe4eee2edf86ad2622c9f198a51a31723f3953995f3f3a43f7ed34df9b8ff7ba1ef67fbd3a29f97034b33d26758c167ed1d03f7187f273467dcf5ff3e90f4afae09b49bf687d83a2e082bae68ceff9b40518ff49922382ebca07696f30174ddc44a891f92f21f606b90846fb5709061023f600ca20f64593bda78e73cbf44faef375dc8c7fddf0bbe8def4f89a1bc1c3898c00f153e86c0f7afae196f37f2c612dc2c505bec341759173a3e281d2ac8afc984e0ef5158790433fcd7e096299b0c03cc901778215fee867cb52bbb6b717d472c26fb7960a47f38561e3b85edd18b91f68c2c9f09f89036e193bdc707bd022f8fc76372e8784cd4612af911170adf985f7641bfd0f43ec6fca7f7cf1f7157d575ec5c188600cefffb4aa41fff1eb3a3f7e3215915cbd980889814146b2abeec57fc30772a36687ba7c6fd699b85c17732ec37ddf06f8df808d7f171df2fbfe47df1076e307d74ef23fbb7e730fe7c9ffdad7f50583de9a8cad9868513fc31d23d00910929d8146ed923f026f2cc6c3fe86622f40d318bb6221c5d168e40891bbc8366624f81da24a9c07be4ae0fc7fc43255a11ea7ef403e64d59cff3536114133ede05fd94d37731f124f24e7d8319811ef0f01a8ba875e750aa151fe365db7781371e163fdf3a0cd7359e0ff7b786fdbd7f50bb91a1fafa4e2c0af387977b0022127fc2a9e4395872a4547b1c2f7af77df5713c63df6863754889ba336bb1e64c2d9a73b720f158197f56619019da026fc2973bcf57bbac1db7d70540e41d8b23f9cf515576137b6679c066c4186c2f960bfa9036c727bbc63ca3b4fa57dcbd7d07b76fdfc12f19ab116223c1e2ac5ac805c237e697dd945f68a3fed3fbe38f589a8f247f1b1dffef071033d212966a139dcd27a360ebb50e7734ebdf5db7f18da795d63ca7903f6d5e5c3c8cbf532bcf6f7a9bd1dfb61bf211ae637eb77f7ec9cdf5076efc5d3eba7f7b13e5d2577feb1f1a16a5a335ef5b04587963614a3ecaaacb716b5f343c870d530bbc197966ae1f740379c0c3c43784f77b1e723cd9310ee209dbf0f0752bea9e3cc4536e8f87d07bb4e2d46c2bf87d7b577b8ebb337f2dbc2dd5a665797118c0848f77a37ecaa90396110d1b5b4704cdde860b0fcaf1ec9714ccf7b48477a2fabb2954b67d1478e36119ce3743758defc3bddb88bff7beb79bb6fc248cb6f6c1a2c3b7515e5d8e9bbb67c3cbc20263934b78e6b18d7da3fb5f87068f212df0a67cb95365d4f5d54e7ed717d98b313ff38d360c7284106c31b6976b58be0f698dc306619fecbdec2f132d77b1658c3d82d7e5f5f2f3cd0f5fed0989e797dd845f6821ffe95d1fee8fb8f57c2c9c443138ade3ff9d5c8472dee34c0abcb03f6dfdb87808bd9342cf66bbe06f0d4cf5f5b2afffe17ec9cdf6072e983e95c38c8fe6dfdec4f35c5b31dbdf7a7fc26ac5f905f6709873baa74ecbcbb0679c854ae0cdc833b3fda0f3f2808fa96f88feeff9c851f47d106cfd1371e9a5ae3748a1f7180081e7be47c67dbcab7e67c04fb97a30626bf915d2b4f546898693d110db2fc245a9e9b2355fe085c2ea369c6fbcbaa67957033edc0df97bef53bb69c585856238c59c41a3264cf2593fda12a1db9fea09bcb16ff440d4a1c163080bbc695fee5c65d4fa6aeff1bbaeeb7f59567110532cd59e868cf990a6d18041872f023ed915f5b818e705e7c9fb51ac99ee33163ef9a836e497dd845f6861ffe91fea8f98f295efb75ed7ffbb4ae0bf352cf026fc69f3e3eb8df03bf51678e1df1af950e894e187fa25177636f237f56f6fe2795efd15f2b7de9fb03831d7afd33d6bf0a6f3cc8493155d3fe8fa79c0c3f43784ff0c1f79d303a42c0c84a3851342e38ee06193d2c47ba804def79b7e0abc511fefb5901af553aefe56b9adc04d9dc14877e17708b48c406a25958fe9b2e5a5c71026ea89a17ca3e70cb75b5d1fee02fedefbd26e9e3ce3d74579250e8659a9bdc9e9b4510ac7d0377a80ead060318405deb42f77ae32ea787a93371cc72c2b172ccbeee931763f4ac6180b12f86ee33ea48d0abc318f6e72941d990137b779385da539d226eca3fad8342bbe5f76137ea185fda76bc2e8ab3f62255e1d89e4fb7f3f3415366a81979e89819db3ca352f77bfeb0656b85aaa04de843f6d5308bf536f8117feadbe8f700365f8817ec98505fe6feadfdec4f30645d998bff5fe84c53d6b0549c28d9e3a4d7eb427a946f0a6f3cc84c0eb7a51e33daf8fe96f08ff196328d15c9a85447f2ab38726dea31d67e75ac37df935ed5e9aceeb899058989b6e4d5d36e2e3fd4cb9a09f72ae7c1c96e08ace2c47db9538b8dad1cc947965abff778398a8272a7ae71bfdcd70bbedb1a52fec4bbe0fed46d1c8fdd63dee6acf779773136dc91778f5b798f78d1ed03a34f00c618137edcb5d5fe069549d19e30c714812ae3c6f4173f55da4cef38425e72bb84bc087b401711010f8b6fbc998200e44d2ad669de318c23eaa797ed915ed68959af00b2de43fbd1ffe88652f531169eb8069fb7fe5f2b5b3e61abe0db480855ae0b94ed1082f2cbb42ee6cdbf1ecf87c780e1b8159c769f942d89fb64984de49dfadaae06ff57d841b2ac30ff34b6eaec00ba7cf94c0ebfbd9ee67be9a785e50947979d99fb01478991a01b17d04f615d2b372bcbab6162156c3e1bd3adf8c3c1b488137fd0dd1ff3d1f255a1b349ec49a9035df0992e5d74cbc871c8fb606c17ae45264930048cb903e4f02cbe133906eee942e376d6dc4c77bdd33e37eca3553f4ff70c0f4fdbf721d0079d343ec982482db827368549857b6bcf41844282c23f9c6ab6b7c8117f625df9776a3c4abd3f3e06a3b1649975fa0b9a506770ecd87d7304d5ee9b65103df68b914add281a84383c7901678ae80047cb9f3045e5d997f8c9b047f277bb8f844e2db1fd621cc6a02f63d5708fb90e689831181efa8435a840586fdcf3f60f1cf615a6ca6a6e0658540f88ad7b8bd3b0681f6561039b9432272c4cc23d526fc420bf84fef973f62195e9c5b8509f62360e7e80c17490436254ed68ee029add7d68540fccf11108b5d111cbb0f1ba65a233255b55e2be44f9b1f171fa3efa42ff082bfe5fb08e797e187f925375be005d3674ae0f97eb6fb9baf42cf0b8bb281bcec4758caee0a5c58150aa76156707671836ff866ec89f7c1c8849b66e4d9c00abca96f88695a919de00d17b7008cf17581dbe8786494abea82f1f7a0235bd7903446048be12238bb0462f1def588b08ac08f66eda7d07c8f8cf97817f053ae291fa748c4cd0dc4480f4fb8dbd8c06bfa0edcaa57c76d46d9f2d2630ca36119cf375e5dd31378417fef7d6d37d4314a8dc7646f673838fa62dada43481a6f8db0bd2ff86d54d1801bc933e16f67099193041e6267cc4ea36f5e7febd0e031c4055ec387fb7297febc125e76f37056d3cb36d787f48762227c69deb7981c731425afbb7a1b6310f40b6dc47f7a7ffd11937f6dceffbbb2d71abceabe02d2864a5434b41b3dfad13f7fda46dec92002bfd5f7116e2e26caa96f08a4cf0486fc6cf72f5ffbf1bc81bcfce0b048005bea5051d36cc423d887e7d987f1e1df10ae2dbcae45555dab817711780f793bea2b2a512ffd00cb94baa267c4c7bb313fe5ba039feea65a54eb7f6bd4f4a76cf5311c9640be19a86bbd11f6f7fec1eda6fd16564944987f5633da3780a2038d5515a869d2d9ffc0d19f3a34387c26026f3e1d8f2ee1646e119ebfaa47c5fd4cac0911c163c965ed19d14f4debcb9bd835dd1f4bb43b61ff1ef0059ec1607c32f447b57dc0d0cce6174be7afb878e21a0a9fd7e155c50364ae1a0bb15b1c2e6bed930c6dbe308157a2fefa36c44c0880b7ab1bbc0326222629134ffa788c6cd0e8ba873d7323111199808ce71fd0ab1f44c8384644cca15e3b45190cc627a21f3ede39a35c512aa355faf7be34e40d37b07dce44047aba41e2390a93e6ac47e693bfd7e0aa3f7c6102cf6030180cc6970113780683c160303e4398c033180c0683f119c2049ec16030188ccf90cf40e0e5283bbe12df9cae844c5e8bdb270e61cf8e6d48deba03fb8f5cc4fd573a679e5fe6e2c77dfbb06f6f0f074edc45bde23d6435779171681f7626272339793752336ea2b4997652caf0e4e2c15ecf68d99782ec323a12a1404dde711cd87f068546cedf76bf7e86eb1929d8f5dd666cfa6e17524e69c257a5eb706a0edfffb1bc02b93ffe881cbd0d77b29a7c1cdf7700670a55676acd4301697d191edcb98fa7f5060cde7437e379d1233c6f32b4694786b7cf1fa1e8b981234d8a0e349416a1b0b4b1c7c39dac1c2756ae41c6cbbfd746410683c1f89218f2022f7f750231fe5fe3227970eabc8aa50ed618139b8c5ddbbfc79ae85170108dc7b6bb2a2b701dd9f170b1f0c18cb86558be5445e2d6cba892abef598e46ec773bb1e3bbb55818e20cbb918b71b6ba1d777f588ef8257188ff3a1a63ed87c36ddc7cd5ffe356208d76b1cacbb16f8208b65622c464bcd63b6329c5e3a34b102412c177d27c247eb31e1bbf49c082f058a472064dd4718b97e08abe8b4a7a1f4707c45dd6156405caf64e84d8ca1ae2681d871d42b4ddc18e7077d88b3ce0efe504db116e98b1af506d72916cd6efc72c7707f806054262eb82881d055ae329642ffac074099cbd8211ec6a07f7a93b7157dd89e97a7e0eabc7ba41121486095ef6701eb316d99ca10e25ea32e621605e26d779e2a587c160301883ce10177872bc320e81ebeea90c1ae80ba2a21ec7a65bc325ee2a37ba342aa486ee49ef609d8f85d6bc2307e752d40ae1876a7a89b8ac740f268aa3b1656d101ca34e69adcc71168ece2fc6481b326f58c31ffd1a8b5b83fefb10e4ac23d40173367d8331a2689c32c75a92bc12b7731ee10d3743d08d17c7a2e1623d1dc7eac84ce62f58eb658ff07d4fb93c6cbeb31e4156c1d8c6b9f66cc7add5de7098bc1f4fe8cc6ccb5d6c18658d90adbfa25b5e81d470110256dd54d910e82e476a843d24f157559d83f65fb0c6374c6d95cd409a180c068331a80c6d81973fc7be899e58715d2d80fa8228af466ab80d679399c4d5a8901abac73968b0e24c986a7f6750e055266bc55fa5a1b2600346d9cdc6098de892e38220b209fdb09745257d78716bd07f1f8d3953bb6948abbc878d7e2244a5933d787e9842c81b8e62baa52f67dab1236739dc453138a3b181adf6f6c5996eedcc4582b33de6656a5ce42a50ba2b94f3645752478e7b546168c26d3a15cd79efbbce19d0e8c0d53809220f55f3e26730180cc6e033b405befd3c62c55f214d63439913446b84c6efc6de1d5bb0627a1082a3f6e0b6da2a1127a4d6e390b8ef200e1e207ec091dc179ca9555d91ed6aaec29dc30be0ef320369653a235043022f2bc5aeb122cc20bfcc6487dc4f84d93f913d68f2729482f01123f14dbeb0b529f3055ece3942a1ce04f946bfbfc11fe219c751d7c76970e9add5f0b18bc6c906199eef9ba8e7eab0031763ede1b8e812da9eefc7243df7bad28b8be0205a8c4bb5e9986de58e15d77b4c454a2f7d0d678b3938cd190e52a23a250292b81c5efc0c0683c1187c86b4c0cb5f1d46a4fd029cd7d80b570b7c48ec166cddb00671917e90f844637f81caab9b6a0dde173397276245828aa4e32affbedcbd7f8e80bd4804bb61ffc070eb106cced3dbc46640e0bb9fec40a8dd0c1c7ba5da90f7605300c4d38fe19542ed1378441092b9e96efabd0cf7360573ebe7b65636f05dad72ae61b6c0cb9e62478808338fd671f1773dd88cd1b69ab8f9f96390ce62ec091363f4b7f9687b27c7e3ad41b00d3b880aed06bf6e5c5bee0adbb967d1fc3819632c27e160454f27a7f35a0224563138db568b8cb9ce100727e2d8b5bbc8bf78004b82ec61298e43b6fa3dc8adace3ac13fc3430180c0663d019da024f9ebc7802af3be26dc59d75a360e3bf190fba0584545f64db2b9015e703fb71bb50ac6b9a9527f072946c0b81e53f2d2116d9c391b01e8ee1565f21ad460979c34f986925c6d73a76e5e5b26e74b437e3dc427bce4b112f6edd74e9bd4f77c9768c1b360c36b6eab84436b0fc5f6b4ca7d90303f9c3435e8b4bcbfce03a71271e70a36c055eee9f045bbd11fcf9f9767089cb41dbcb03986ca937823fb70062c7785ca5b44a9fe1ecf771888e9c8185abf6e3e4e64910851dc44b7567a12d33060eb34ef2d3c16030188c4167480bbc527a0e0b459138d26b8abef79a751b3956b09a87b3ed02426a406465343d6d1984addad1b70181e7fe6f8d89bb9ea2adbd0352a229172bdcadf1d58f3590cb2bf163b80d1ca6a769454f45172e2f12f751e06943e118d886eec613a93aaef666e4264860177e1855a6a6e9e5b5c85e1904f790f5b8a1b331aff3462224767391f15af38e8fb075b415a61ca880aceb2656b8d2c98037eadfcb51f47d2037e2effd3eef217f7d1dab7cec31edc74ab5773925aa0e85c3239e4dd133180cc6a760680b3c6d081b2f4142aee14d76d2aa6bd832c11e2e51e93d47e1c48b71492a47b74c8ddcc851b5ee426cf1b74284ee26313d81ef2e4e4688e504ecd55da77fd7859f5748601b9ecac5d9767f1b26dada2070710a7e2ed5b8746cc6d918fd11bc8174e9be8fac04db82ac10b6a7bc977bd68e9f5762a4550452ab1450b6dfc5be05f3b12b4fcf5982ac1a17960740326e03aebd92a9e35040466969cfc7b7de22846d2f82948eb75d8a878f5d047ee4ceb077207fad0fc4e377a050fa1ef2facb58365284af522b74d22043e3e3b3581fe60c97f08328d67652da91bd4482699c8f7825de14a461cbf6cb3a4b010c0683c1184c86b6c073a3dab108589daff279dc9983658ec360237283c4c91e62912726c6eec12df5089f13d2fffd5f0cfb1f1d86cf447a9301817fd7855f567b423cfd784f7cbd045e8e47b47e1dba17657aa2d5f1f32a78594dc1216eed5a89e64727b16ec668b88cf8272cad681adf0a62b760cc4f2d319e2eabf9bd04befb11ad878fd7eb4cd03bdfc26a8935c27fa844d7c3cd08a4a3738dbdd7e4df9e8a869d6ed81cc311b657b5c1b0b5f010e67a8ae0ecea0cb12808cb4f3fef39d2d756844351232116bbc1cdd61e639666a25ced85aae34a22fc9d4470701f8b980d677b7be593dec42adf2938584ee995e1de467f588a16e27c4befbc6230180cc6e030c4059e36da9d448ccf7c9c514f3bcbbbdad1f4fa351a9b3bb8cd73fabfffa4c8daf1a6a1118d6d86acc5f517052a0e4e8564fe39bce1dd33872e345456a151b39f418f8e862a5436f6ec9827e42d757859d362e07cbf12af4ecec5e8d8733d867814ad6868fc7cdc3032180cc6df1d43025ff2e4d9d011781ac5979ffa0649999ab5df2f9576dcdab502870b0d98a1fdd8c89e2363ed7a64d1b281fe3d0683c1607c1454023f5947e00bb402dfd4dc826ec302ff3b27f06f9b9a51555dcb097c5efe1d44cf8e46eaa1145e240c0683c160303e2ea4c753264de104fec2c56cf304fe3723021f33771ef6eed9cb8b84c16030180cc6c785f4387c6a04d28ea69b27f0fffad7bf7404be452bf034b79fb87c05e7c0453f120683c16030181f97a571f1888e9a8bb463e9b878e92a6edf3153e0e906fd807e480f50cf60efde03080c18c58b84c16030180cc6c725c0d70fab56afc5d19f4ee0d26512f87b78f2b4542df0addc9169bec0fffe3b774357e0a9679075ee22ac4758a0a3f36fb0d18bc16030188c2f94f68e4e588db0c08e9d7b70ecf8495cbe928b3b77efe3e9b332d4d4d6a1b9a50d324302fffbef7f7037e807f4437ae06ec17d2e005f2f1fdc2bb8c78b8cc16030180cc6c7817478a48727f61d3884f413a7917df53a0aee3dc4b3d272d4beaa474bab1472b9c2b0c0d30dfa01fd901ea00729807931f3b18cadc333180c0683f1c9581abf1411e191d87f3005274f9d414eee4ddc7f5084b2f217a8ab6f445b5b3be40a656f81fff7bfff8d3ffef8130a8592fb01fd901e78f0b008b9d76ee2c7c34721b2b1455353332f420683c1603018830be9af9db50d92366cc60f2987713af31caedfb885c2a25ff1fc4505ea1b5e432aed8042f98e2ff07ffef92794ca77686fef4443e31bbc785989a2478f71e3661ece9cbd80b0096138b0ff002f520683c1603018830be9ef98a031d8b16b2f7e3c720c59e72ee1d62fb7f1ebe312bcaca842e3ebb7e8e850ed95eb25f0fff9cf7ff0e79f7fe11d7922ebecd25ab37b5cfc843b0b4fd672b67cb7152e0e8edc3979fd88190c0683c1600c0ea4bbce6207c42f4dc09e7d0771eca793bd8ec8d1d176ce4c6d5737debfff8d2ff07ffd45c66e7e531f956bd5d968f70057b2af710bfa74b87ee1bc05bcc8190c0683c1600c0e0be6cd47e8d8506cddb613070ffdc8adbf5fcdb9817bf70b515af69cdb37471be4e9241c9d88e309bc76a31dadc34b55ebf0e5cf5fe261e1afb871f317649e3d8f033fa4c2ddc515972f5de62580c16030180cc6c0427aebe2e8846f933662e7ee7d389cf613373dfff3ad3c6e199d96d36959bdbd9dd6df95f8e38f3ff802af5d87272f639d5d78fda649eb74862cdad174c04fe9a7b066cdb77014d9a3a4e4092f210c0683c160300606d25907913d162c5c8cef937770836c9a4d579d7f5719b8d14ccf77767573cbecb4dcde4be0fffbdfff7202ffd75f7f715ee568984fc3fd57750ddc6efafb0f0a71edfacfc83c731ea9878f223676319cecc54ce4190c0683c11804485f1d45624c9f3e131b377f8f5d7bf673a3f7b35917b959f587858fb859769a6d6f6d9342265770166949c779024fa378723a43d3f434cca7ddf4b42bafa2b29adb6c47666b2f5dcec1899399dc1ac08205b19cc83f7e5ccc4b1883c16030188c0f83749566caa74d9b81f51bb760db8edd38947a042733ce20fbea356ef4aed95c479be2b9ddf3ca77dcf43c2db71b1478ba41d3f4b40b8f36dbe98ee2a9b74047e6ce9dbfccd9c0ddbbff07cc9f1f0b7b5b3bcebb0df51ef413c96030180c06c33c4847f7eede037b1b5b4edcd7addfc46dac23c336b4447efee215fc7c2b1f85458f7b46efad52ce0a2dcdbed3e89d66e30d0a3cddd01dc5777474e2cddb26ae974073fd05f71e70866fe85cfc91b4e3dc76fd356bd7c1c7cb0763c784a0acac9c97600683c1603018c2907e8e1b13022f0f4f2c5e1cc7893badbb93595a1a54d3d4fcb5ebb7b82573b2344b3e6368ed9df313c38ddeffe406e93c81d71579d528fe2f6e144fbd82b63629b7438f0ed2d3543d9dbb23f3b5644587d60348e4bfdbba1d5f454ee3acdd4d8bfc0a376fdce4ace9e8bf0083c16030180c15a493a497d3222221b2b6c5a4b0c958b9fa1b6e5a9e46ee24ee478e1ee706d5649696fcc3d0c6775a3aa725743af1a6bbf64ee24e3a6e54e035a3789acb57be7bc7edccd34cd5d394c0a35f8bb95df574369e449e46f2345d9fbc7d1792d66f4264e434485cdd3152e28135abd7e04ce619ae67c2049fc16030185f32a483a487a48ba48fa493ee2e6e9832792a121257e19b751bb80d75b4e64ed3f2e4f39d8ea8e7e4dee0dcc2d2209b8ec5d1d47c4b6b1bbaba65dcce79ddd1bb4181a74b77144fbd01ea15907d7a9aaaa7a9003a4c4f224f67ef48e4afe65ce77a16347d401befe88c1e8de6a9f741dbfac3264cc2e8805170767084b585259cc58ef07073872783c16030185f08a47b64894ea5830e18e51f80f1a1133077ee3cac5af32d27ec1b367dc74dc9d36ef9432947f0d3f153dcb4bc4adc0b3893b4a4bf34d82663746475963a0cb4a4ae3b7a271d171478faa1ee863bf23247bbea69b71e59b8d38ce429d2dc6b3f731bef68773dd9c7a55e07d9caa5846edab295338c4f894f5cb90671f1cbb9b585d8454bb070d1d70c0683c1607cf62c5a1c87afe3966179c24aac5ebb0e6bbe49e20cd7d060987492a6e369807ce0602ab7f44dbbe52f5cbcc23993217fef1a71a74136b7eeded1c919a5d34ccd935e93760b0a3c5dfca97a95c8d33cbfb4bd831379cd489ea60b68e3ddcd9ff3b883f7349a3f7e22033f1e3ec61dc8a7f579127b9abea791fde6ef92b997a12908eaad30180c0683f1b9439a4790fe910e921e922e923e924ed20c38093b19b1a1513b1d85a3ddf2f7ee3fe4d6dc695a9e46ee24eeed24ee7205a7cb3408d79d9a27fda6cba8c0d3a52ff2bd46f2eae97a8a8c36ded1ee7a3a4247e7e46987dfa52b395c02c9562e4dddd3a89eceef91e0efdb7f887b99dd7b0f60f79e03dc540483c16030189f33a477a47ba47fb4718ef49074f1f0919f70ecf8496ec49e75ee223750a6513badb7d35238f983a10d75b4e64ed3f2b4635eae507047e24897499ff5c59d2e93026f54e4150aee507d734b2bb7bb9e8ed0d139791acd93ff789ab6272b3bb4d39eccdb9e3d7791db8c47824fbd133acb479e7048fc398e9de0361230180c0683f13941faa6d13ad23dd23fd2411274b20c4b36e54927c9710ccd8493b0d3809946ed344b4e47e168b73c6da8ebecece6a6e54d893b5d82024f975191ffed376ee31db9a56b6b6be7cec9d3689eecd693d053c2c8013d4dddd3a89ea619a847425bfc49f4af64e7e2f2951cce2adea5cb57b997bb78299bc16030188ccf8cab9cce91de91ee91fe910e921e5ebff10b7ebe759b1b14d3543c8dd8c93a1de928e9298dda69499c8ec2d16e79da50476bee86c4bdcf024f9746e0f5459e76edd1967f3a274f8bfd748ceef59bb75aa17ffea2823b884f624f9bf1a8474287f30bee3de4cef191993deaa9f450c07506180c0683c1f81c205dd3d539da2c47fa473a487a480361da3c47a24e2e5f699d9d66c44947c9d95b4bab949b92a7fd6f74144eb35b5e77cdddd0e89d2eb3049e2e7d91d71ca1d36cbea35e05f52e68973d4d23508fa3bee135b7118f124bebf424f8d42b21d1a735055ab7a7976230180c06e34b80748ff48f7490f4907491f45123eab4e44dfac9097b4727672e9e2cd4fdf6db6f9cdeea8bbba191bbe6325be0e9d29faeef19cdff85dffff803efd4424f5ee8e86c1e4d29d0a8fe6d5333b77e40824fd30d24fa74cc8ed615180c0683c1f85220ed2348cc490f49d0491f69d33a0d8ea5d20e6e9d9d66c649d86900ad1176dd297953e24e579f049e2e5d91d71dcd6ba6edc9f21df5342861b4114025f6dd5c4f8412dedad6cef54c68731eed066430180c06e34b82f48f7490f4908e9dd3143ced67235127ff2f34154febeca4a73480d608bbbeb89bbafe3fca2699d9d62c4cf60000000049454e44ae426082);

-- --------------------------------------------------------

--
-- Estrutura para tabela `relatorio`
--

CREATE TABLE `relatorio` (
  `id_relatorio` int(11) NOT NULL,
  `descricao` varchar(120) DEFAULT NULL,
  `tipo_relatorio` varchar(20) DEFAULT NULL,
  `data_criacao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `resposta_aluno`
--

CREATE TABLE `resposta_aluno` (
  `id_resposta` int(11) NOT NULL,
  `id_aluno` int(11) DEFAULT NULL,
  `id_questao` int(11) DEFAULT NULL,
  `id_alternativa_escolhida` int(11) DEFAULT NULL,
  `data_resposta` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `resposta_aluno`
--

INSERT INTO `resposta_aluno` (`id_resposta`, `id_aluno`, `id_questao`, `id_alternativa_escolhida`, `data_resposta`) VALUES
(3, 2, 6, 27, '2025-08-06 10:27:09'),
(9, 2, 7, NULL, NULL),
(10, 2, 8, NULL, NULL),
(11, 2, 9, NULL, NULL),
(12, 2, 10, NULL, NULL),
(13, 2, 11, NULL, NULL),
(14, 3, 7, NULL, NULL),
(15, 3, 8, NULL, NULL),
(16, 3, 9, NULL, NULL),
(17, 3, 10, NULL, NULL),
(18, 3, 11, NULL, NULL),
(19, 3, 11, 51, '2025-08-06 12:13:50'),
(20, 3, 9, 45, '2025-08-06 12:13:51'),
(21, 3, 10, 46, '2025-08-06 12:13:52'),
(22, 3, 7, 34, '2025-08-06 12:13:54'),
(23, 3, 8, 40, '2025-08-06 12:13:56'),
(24, 3, 7, 34, '2025-08-06 12:29:03'),
(25, 3, 9, 45, '2025-08-06 12:42:16'),
(26, 3, 10, 49, '2025-08-06 12:42:18'),
(27, 3, 10, 50, '2025-08-06 12:42:41'),
(28, 3, 11, 53, '2025-08-06 12:42:43'),
(29, 3, 7, 34, '2025-08-06 12:42:45'),
(30, 3, 8, 39, '2025-08-06 12:42:47'),
(31, 2, 6, 29, '2025-08-06 12:44:30'),
(32, 3, 11, 52, '2025-08-06 12:57:25'),
(33, 3, 10, 48, '2025-08-06 12:57:26'),
(34, 3, 8, 37, '2025-08-06 12:57:27'),
(35, 3, 7, 32, '2025-08-06 12:57:28'),
(36, 3, 9, 44, '2025-08-06 12:57:29'),
(37, 3, 9, 45, '2025-08-06 13:00:15'),
(38, 3, 11, 54, '2025-08-06 13:00:17'),
(39, 3, 8, 38, '2025-08-06 13:00:18'),
(40, 3, 7, 31, '2025-08-06 13:00:18'),
(41, 3, 10, 49, '2025-08-06 13:00:20'),
(42, 3, 8, 36, '2025-08-06 13:16:44'),
(43, 3, 10, 49, '2025-08-06 13:16:45'),
(44, 3, 11, 55, '2025-08-06 13:16:46'),
(45, 3, 9, 43, '2025-08-06 13:16:47'),
(46, 3, 7, 33, '2025-08-06 13:16:48'),
(47, 3, 8, 40, '2025-08-06 13:28:50'),
(48, 3, 11, 51, '2025-08-06 13:28:50'),
(49, 3, 9, 42, '2025-08-06 13:28:51'),
(50, 3, 10, 50, '2025-08-06 13:28:53'),
(51, 3, 7, 34, '2025-08-06 13:28:53'),
(52, 3, 6, 29, '2025-08-06 13:29:16'),
(54, 2, 12, NULL, NULL),
(55, 3, 12, NULL, NULL),
(56, 3, 12, 56, '2025-08-06 13:31:00'),
(57, 3, 12, 58, '2025-08-06 13:31:24'),
(68, 2, 13, NULL, NULL),
(69, 2, 14, NULL, NULL),
(70, 2, 15, NULL, NULL),
(71, 2, 16, NULL, NULL),
(72, 2, 17, NULL, NULL),
(73, 2, 18, NULL, NULL),
(74, 2, 19, NULL, NULL),
(75, 2, 20, NULL, NULL),
(76, 2, 21, NULL, NULL),
(77, 2, 22, NULL, NULL),
(78, 3, 13, NULL, NULL),
(79, 3, 14, NULL, NULL),
(80, 3, 15, NULL, NULL),
(81, 3, 16, NULL, NULL),
(82, 3, 17, NULL, NULL),
(83, 3, 18, NULL, NULL),
(84, 3, 19, NULL, NULL),
(85, 3, 20, NULL, NULL),
(86, 3, 21, NULL, NULL),
(87, 3, 22, NULL, NULL),
(88, 3, 15, 71, '2025-08-06 13:35:47'),
(89, 3, 18, 87, '2025-08-06 13:35:48'),
(90, 3, 20, 97, '2025-08-06 13:35:50'),
(91, 3, 14, 69, '2025-08-06 13:36:04'),
(92, 3, 21, 105, '2025-08-06 13:36:07'),
(93, 3, 13, 61, '2025-08-06 13:36:16'),
(94, 3, 17, 84, '2025-08-06 13:36:18'),
(95, 3, 22, 109, '2025-08-06 13:36:21'),
(96, 3, 16, 77, '2025-08-06 13:36:23'),
(97, 3, 19, 92, '2025-08-06 13:36:25'),
(98, 3, 19, 92, '2025-08-06 13:36:38'),
(99, 3, 17, 84, '2025-08-06 13:36:40'),
(100, 3, 22, 109, '2025-08-06 13:36:43'),
(101, 3, 13, 61, '2025-08-06 13:36:45'),
(102, 3, 15, 71, '2025-08-06 13:36:46'),
(103, 3, 16, 79, '2025-08-06 13:36:47'),
(104, 3, 18, 86, '2025-08-06 13:36:49'),
(105, 3, 14, 68, '2025-08-06 13:36:50'),
(106, 3, 20, 99, '2025-08-06 13:36:53'),
(107, 3, 21, 103, '2025-08-06 13:36:55'),
(108, 3, 13, 61, '2025-08-06 13:41:57'),
(109, 3, 20, 97, '2025-08-06 13:41:59'),
(110, 3, 17, 84, '2025-08-06 13:42:01'),
(111, 3, 16, 77, '2025-08-06 13:42:03'),
(112, 3, 21, 105, '2025-08-06 13:42:05'),
(113, 3, 19, 91, '2025-08-06 13:42:08'),
(114, 3, 18, 86, '2025-08-06 13:42:10'),
(115, 3, 22, 108, '2025-08-06 13:42:11'),
(116, 3, 14, 70, '2025-08-06 13:42:15'),
(117, 3, 15, 74, '2025-08-06 13:42:18'),
(118, 3, 21, 105, '2025-08-06 13:47:23'),
(119, 3, 15, 71, '2025-08-06 13:47:25'),
(120, 3, 16, 77, '2025-08-06 13:47:26'),
(121, 3, 19, 92, '2025-08-06 13:47:27'),
(122, 3, 14, 69, '2025-08-06 13:47:29'),
(123, 3, 22, 108, '2025-08-06 13:47:30'),
(124, 3, 20, 98, '2025-08-06 13:47:32'),
(125, 3, 13, 65, '2025-08-06 13:47:34'),
(126, 3, 17, 82, '2025-08-06 13:47:36'),
(127, 3, 18, 89, '2025-08-06 13:47:38'),
(128, 3, 21, 105, '2025-08-06 13:47:50'),
(129, 3, 22, 109, '2025-08-06 13:47:53'),
(130, 3, 19, 92, '2025-08-06 13:47:55'),
(131, 3, 20, 97, '2025-08-06 13:47:57'),
(132, 3, 14, 69, '2025-08-06 13:47:59'),
(133, 3, 13, 61, '2025-08-06 13:48:01'),
(134, 3, 15, 71, '2025-08-06 13:48:02'),
(135, 3, 16, 77, '2025-08-06 13:48:05'),
(136, 3, 17, 83, '2025-08-06 13:48:06'),
(137, 3, 18, 88, '2025-08-06 13:48:08'),
(138, 3, 20, 97, '2025-08-06 19:00:10'),
(139, 3, 22, 109, '2025-08-06 19:00:13'),
(140, 3, 17, 84, '2025-08-06 19:00:14'),
(141, 3, 14, 69, '2025-08-06 19:00:17'),
(142, 3, 21, 104, '2025-08-06 19:00:18'),
(143, 3, 15, 75, '2025-08-06 19:00:21'),
(144, 3, 18, 90, '2025-08-06 19:00:23'),
(145, 3, 19, 91, '2025-08-06 19:00:25'),
(146, 3, 13, 63, '2025-08-06 19:00:27'),
(147, 3, 16, 76, '2025-08-06 19:00:29'),
(148, 3, 14, 68, '2025-08-06 21:44:11'),
(149, 2, 23, NULL, NULL),
(150, 3, 23, NULL, NULL),
(151, 4, 23, NULL, NULL),
(152, 3, 23, 115, '2025-08-07 15:35:09'),
(153, 3, 20, 97, '2025-08-09 14:56:24'),
(154, 3, 15, 72, '2025-08-09 14:56:25'),
(155, 3, 21, 102, '2025-08-09 14:56:26'),
(156, 3, 16, 80, '2025-08-09 14:56:28'),
(157, 3, 20, 100, '2025-08-10 18:21:48'),
(158, 3, 18, 86, '2025-08-10 18:21:51'),
(159, 3, 17, 84, '2025-08-10 18:24:56'),
(160, 3, 15, 73, '2025-08-10 18:41:44'),
(161, 3, 18, 88, '2025-08-10 18:41:45'),
(162, 3, 19, 91, '2025-08-10 18:41:48'),
(163, 3, 19, 91, '2025-08-10 18:56:32'),
(164, 3, 16, 76, '2025-08-10 18:56:34'),
(165, 3, 13, 61, '2025-08-10 18:56:37'),
(166, 3, 19, 93, '2025-08-10 18:56:44'),
(167, 3, 16, 78, '2025-08-10 18:59:12'),
(168, 3, 20, 97, '2025-08-10 19:02:56'),
(169, 3, 20, 97, '2025-08-10 19:03:03'),
(170, 3, 19, 91, '2025-08-10 19:03:04'),
(171, 3, 16, 77, '2025-08-10 19:03:06'),
(172, 3, 21, 105, '2025-08-10 19:03:08'),
(173, 3, 13, 65, '2025-08-10 19:03:10'),
(174, 3, 18, 86, '2025-08-10 19:03:11'),
(175, 3, 14, 68, '2025-08-10 19:03:12'),
(176, 3, 17, 83, '2025-08-10 19:03:13'),
(177, 3, 15, 73, '2025-08-10 19:03:14'),
(178, 3, 16, 77, '2025-08-10 19:05:19'),
(179, 3, 21, 105, '2025-08-10 19:05:21'),
(180, 3, 22, 109, '2025-08-10 19:05:22'),
(181, 3, 14, 69, '2025-08-10 19:05:24'),
(182, 3, 19, 92, '2025-08-10 19:05:25'),
(183, 3, 13, 61, '2025-08-10 19:05:28'),
(184, 3, 15, 71, '2025-08-10 19:05:31'),
(185, 3, 18, 89, '2025-08-10 19:05:32'),
(186, 3, 17, 82, '2025-08-10 19:05:34'),
(187, 3, 20, 100, '2025-08-10 19:05:37'),
(188, 2, 24, NULL, NULL),
(189, 3, 24, NULL, NULL),
(190, 4, 24, NULL, NULL),
(191, 5, 24, NULL, NULL),
(192, 3, 24, 116, '2025-08-10 19:57:20'),
(193, 3, 24, 116, '2025-08-10 20:11:24'),
(194, 3, 19, 94, '2025-08-11 11:49:43'),
(195, 3, 19, 94, '2025-08-11 11:49:46'),
(196, 3, 13, 61, '2025-08-11 11:49:48'),
(197, 3, 16, 77, '2025-08-11 11:49:50'),
(198, 3, 21, 101, '2025-08-11 11:49:52'),
(199, 3, 14, 69, '2025-08-11 11:49:54'),
(200, 3, 21, 105, '2025-08-11 11:49:57'),
(201, 3, 14, 69, '2025-08-11 11:49:59'),
(202, 3, 15, 75, '2025-08-11 11:50:02'),
(203, 3, 22, 107, '2025-08-11 11:50:04'),
(204, 3, 20, 99, '2025-08-11 11:50:05'),
(205, 3, 17, 83, '2025-08-11 11:50:07'),
(206, 3, 18, 88, '2025-08-11 11:50:09'),
(213, 2, 26, NULL, NULL),
(214, 3, 26, NULL, NULL),
(215, 4, 26, NULL, NULL),
(216, 5, 26, NULL, NULL),
(217, 6, 26, NULL, NULL),
(218, 7, 26, NULL, NULL),
(219, 3, 21, 102, '2025-08-11 18:23:43'),
(220, 3, 21, 102, '2025-08-11 18:23:47'),
(221, 3, 17, 81, '2025-08-11 18:23:48'),
(222, 3, 21, 105, '2025-08-11 18:23:56'),
(223, 3, 17, 84, '2025-08-11 18:23:59'),
(224, 3, 15, 71, '2025-08-11 18:24:00'),
(225, 3, 16, 77, '2025-08-11 18:24:02'),
(226, 3, 22, 109, '2025-08-11 18:24:03'),
(227, 3, 14, 67, '2025-08-11 18:24:04'),
(228, 3, 19, 93, '2025-08-11 18:24:07'),
(229, 3, 18, 90, '2025-08-11 18:24:09'),
(230, 3, 20, 99, '2025-08-11 18:24:10'),
(231, 3, 13, 62, '2025-08-11 18:24:12'),
(232, 2, 27, NULL, NULL),
(233, 3, 27, NULL, NULL),
(234, 4, 27, NULL, NULL),
(235, 5, 27, NULL, NULL),
(236, 6, 27, NULL, NULL),
(237, 7, 27, NULL, NULL),
(238, 3, 17, 84, '2025-08-11 20:31:28'),
(239, 3, 17, 82, '2025-08-11 20:31:30'),
(240, 3, 18, 87, '2025-08-11 20:31:32'),
(241, 3, 21, 105, '2025-08-11 20:31:33'),
(242, 9, 16, 77, '2025-08-11 23:50:08'),
(243, 9, 20, 97, '2025-08-11 23:50:10'),
(244, 9, 22, 109, '2025-08-11 23:50:12'),
(245, 9, 13, 61, '2025-08-11 23:50:13'),
(246, 9, 18, 87, '2025-08-11 23:50:16'),
(247, 9, 17, 84, '2025-08-11 23:50:19'),
(248, 9, 19, 92, '2025-08-11 23:50:21'),
(249, 9, 21, 105, '2025-08-11 23:50:22'),
(250, 9, 15, 71, '2025-08-11 23:50:24'),
(251, 9, 14, 69, '2025-08-11 23:50:26'),
(252, 9, 26, 126, '2025-08-12 00:56:40'),
(253, 9, 26, 128, '2025-08-12 00:58:02'),
(254, 9, 26, 128, '2025-08-12 01:01:22'),
(255, 2, 28, NULL, NULL),
(256, 3, 28, NULL, NULL),
(257, 4, 28, NULL, NULL),
(258, 5, 28, NULL, NULL),
(259, 6, 28, NULL, NULL),
(260, 7, 28, NULL, NULL),
(261, 8, 28, NULL, NULL),
(262, 9, 28, NULL, NULL),
(263, 10, 28, NULL, NULL),
(264, 11, 28, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `rota`
--

CREATE TABLE `rota` (
  `id_rota` int(11) NOT NULL,
  `data_rota` date NOT NULL,
  `id_estoque_origem` int(11) NOT NULL,
  `objetivo` enum('MENOR_TEMPO','MENOR_CUSTO') DEFAULT 'MENOR_TEMPO',
  `algoritmo` enum('NEAREST_NEIGHBOR','SAVINGS','MANUAL') DEFAULT 'NEAREST_NEIGHBOR',
  `custo_previsto` decimal(12,2) DEFAULT 0.00,
  `tempo_total_previsto_min` int(11) DEFAULT 0,
  `distancia_total_prevista_km` decimal(10,2) DEFAULT 0.00,
  `criado_por` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `rota`
--

INSERT INTO `rota` (`id_rota`, `data_rota`, `id_estoque_origem`, `objetivo`, `algoritmo`, `custo_previsto`, `tempo_total_previsto_min`, `distancia_total_prevista_km`, `criado_por`) VALUES
(1, '2025-08-22', 1, 'MENOR_TEMPO', 'NEAREST_NEIGHBOR', 0.00, 0, 0.00, 'tela-roteirizacao'),
(2, '2025-08-23', 1, 'MENOR_TEMPO', 'NEAREST_NEIGHBOR', 0.00, 0, 0.00, 'seed'),
(3, '2025-08-25', 1, 'MENOR_TEMPO', 'MANUAL', 0.00, 0, 0.00, 'tela-roteirizacao'),
(4, '2025-08-25', 1, 'MENOR_TEMPO', 'MANUAL', 277.18, 204, 86.62, 'tela-roteirizacao'),
(5, '2025-08-25', 1, 'MENOR_TEMPO', 'MANUAL', 277.18, 204, 86.62, 'tela-roteirizacao'),
(6, '2025-08-25', 1, 'MENOR_CUSTO', 'MANUAL', 372.46, 218, 86.62, 'tela-roteirizacao');

-- --------------------------------------------------------

--
-- Estrutura para tabela `rota_parada`
--

CREATE TABLE `rota_parada` (
  `id_parada` int(11) NOT NULL,
  `id_rota` int(11) NOT NULL,
  `id_pedido` int(11) DEFAULT NULL,
  `id_cliente` int(11) NOT NULL,
  `sequencia` int(11) NOT NULL,
  `janela_ini` time DEFAULT NULL,
  `janela_fim` time DEFAULT NULL,
  `latitude` decimal(9,6) DEFAULT NULL,
  `longitude` decimal(9,6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `rota_parada`
--

INSERT INTO `rota_parada` (`id_parada`, `id_rota`, `id_pedido`, `id_cliente`, `sequencia`, `janela_ini`, `janela_fim`, `latitude`, `longitude`) VALUES
(1, 1, 1, 1, 1, NULL, NULL, -22.902800, -43.207500),
(2, 2, 4, 4, 1, NULL, NULL, -22.949300, -43.182600),
(3, 2, 5, 5, 2, NULL, NULL, -23.000000, -43.365000),
(4, 2, 9, 9, 3, NULL, NULL, -22.755000, -43.460000);

-- --------------------------------------------------------

--
-- Estrutura para tabela `simulacao`
--

CREATE TABLE `simulacao` (
  `id_simulacao` int(11) NOT NULL,
  `descricao` varchar(150) DEFAULT NULL,
  `objetivo` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura para tabela `veiculo`
--

CREATE TABLE `veiculo` (
  `id_veiculo` int(11) NOT NULL,
  `placa` varchar(10) DEFAULT NULL,
  `capacidade_peso_kg` int(11) DEFAULT NULL,
  `capacidade_vol_m3` decimal(8,2) DEFAULT NULL,
  `custo_hora` decimal(10,2) DEFAULT 0.00,
  `custo_km` decimal(10,2) DEFAULT 0.00,
  `velocidade_media_kmh` decimal(6,2) DEFAULT 40.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `veiculo`
--

INSERT INTO `veiculo` (`id_veiculo`, `placa`, `capacidade_peso_kg`, `capacidade_vol_m3`, `custo_hora`, `custo_km`, `velocidade_media_kmh`) VALUES
(1, 'ABC1D23', 1500, 10.50, 35.00, 2.20, 35.00),
(2, 'EFG4H56', 2500, 18.50, 48.00, 2.80, 32.00),
(3, 'IJX1L89', 3500, 22.00, 55.00, 3.20, 28.00),
(4, 'MN0P2L3', 800, 4.50, 22.00, 1.80, 38.00);

-- --------------------------------------------------------

--
-- Estrutura stand-in para view `vw_acuracidade_inventario`
-- (Veja abaixo para a visão atual)
--
CREATE TABLE `vw_acuracidade_inventario` (
`id_inventario` int(11)
,`estoque` varchar(120)
,`acuracidade_pct` decimal(29,2)
);

-- --------------------------------------------------------

--
-- Estrutura stand-in para view `vw_giro_estoque_mensal`
-- (Veja abaixo para a visão atual)
--
CREATE TABLE `vw_giro_estoque_mensal` (
`id_material` int(11)
,`mes` varchar(7)
,`saidas` decimal(32,0)
,`estoque_medio` decimal(14,4)
,`giro` decimal(39,2)
);

-- --------------------------------------------------------

--
-- Estrutura stand-in para view `vw_nivel_servico`
-- (Veja abaixo para a visão atual)
--
CREATE TABLE `vw_nivel_servico` (
`dia` date
,`pedidos_total` bigint(21)
,`pedidos_entregues` decimal(23,0)
,`nivel_servico_pct` decimal(29,2)
);

-- --------------------------------------------------------

--
-- Estrutura stand-in para view `vw_ruptura_excesso`
-- (Veja abaixo para a visão atual)
--
CREATE TABLE `vw_ruptura_excesso` (
`id_material` int(11)
,`id_estoque` int(11)
,`saldo` int(11)
,`estoque_min` int(11)
,`estoque_max` int(11)
,`situacao` varchar(7)
);

-- --------------------------------------------------------

--
-- Estrutura para view `vw_acuracidade_inventario`
--
DROP TABLE IF EXISTS `vw_acuracidade_inventario`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_acuracidade_inventario`  AS SELECT `i`.`id_inventario` AS `id_inventario`, `e`.`descricao` AS `estoque`, round(100 * sum(`ifi`.`qtd_sistema` = `ifi`.`qtd_fisica`) / count(0),2) AS `acuracidade_pct` FROM ((`inventario_fisico` `i` join `inventario_fisico_item` `ifi` on(`ifi`.`id_inventario` = `i`.`id_inventario`)) join `estoque` `e` on(`e`.`id_estoque` = `i`.`id_estoque`)) GROUP BY `i`.`id_inventario`, `e`.`descricao` ;

-- --------------------------------------------------------

--
-- Estrutura para view `vw_giro_estoque_mensal`
--
DROP TABLE IF EXISTS `vw_giro_estoque_mensal`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_giro_estoque_mensal`  AS SELECT `m`.`id_material` AS `id_material`, date_format(`mm`.`data_movimentacao`,'%Y-%m') AS `mes`, sum(case when `mm`.`tipo` = 'S' then `mm`.`quantidade` else 0 end) AS `saidas`, avg(`s`.`saldo`) AS `estoque_medio`, round(ifnull(sum(case when `mm`.`tipo` = 'S' then `mm`.`quantidade` else 0 end) / nullif(avg(`s`.`saldo`),0),0),2) AS `giro` FROM ((`material` `m` left join `movimentacao_material` `mm` on(`mm`.`id_material` = `m`.`id_material`)) left join `estoque_snapshot` `s` on(`s`.`id_material` = `m`.`id_material` and date_format(`s`.`data_ref`,'%Y-%m') = date_format(`mm`.`data_movimentacao`,'%Y-%m'))) GROUP BY `m`.`id_material`, date_format(`mm`.`data_movimentacao`,'%Y-%m') ;

-- --------------------------------------------------------

--
-- Estrutura para view `vw_nivel_servico`
--
DROP TABLE IF EXISTS `vw_nivel_servico`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_nivel_servico`  AS SELECT cast(`p`.`data_criacao` as date) AS `dia`, count(0) AS `pedidos_total`, sum(`p`.`status` = 'ENTREGUE') AS `pedidos_entregues`, round(100 * sum(`p`.`status` = 'ENTREGUE') / nullif(count(0),0),2) AS `nivel_servico_pct` FROM `pedido` AS `p` GROUP BY cast(`p`.`data_criacao` as date) ;

-- --------------------------------------------------------

--
-- Estrutura para view `vw_ruptura_excesso`
--
DROP TABLE IF EXISTS `vw_ruptura_excesso`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vw_ruptura_excesso`  AS SELECT `s`.`id_material` AS `id_material`, `s`.`id_estoque` AS `id_estoque`, `s`.`saldo` AS `saldo`, `pe`.`estoque_min` AS `estoque_min`, `pe`.`estoque_max` AS `estoque_max`, CASE WHEN `s`.`saldo` < `pe`.`estoque_min` THEN 'RUPTURA' WHEN `s`.`saldo` > `pe`.`estoque_max` THEN 'EXCESSO' ELSE 'OK' END AS `situacao` FROM (`estoque_snapshot` `s` join `politica_estoque` `pe` on(`pe`.`id_material` = `s`.`id_material`)) WHERE `s`.`data_ref` = (select max(`s2`.`data_ref`) from `estoque_snapshot` `s2` where `s2`.`id_material` = `s`.`id_material` AND `s2`.`id_estoque` = `s`.`id_estoque`) ;

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `acessa_coordenador_estoque`
--
ALTER TABLE `acessa_coordenador_estoque`
  ADD PRIMARY KEY (`id_coordenador`,`id_estoque`),
  ADD KEY `id_estoque` (`id_estoque`);

--
-- Índices de tabela `acessa_instrutor_estoque`
--
ALTER TABLE `acessa_instrutor_estoque`
  ADD PRIMARY KEY (`id_instrutor`,`id_estoque`),
  ADD KEY `id_estoque` (`id_estoque`);

--
-- Índices de tabela `alternativa`
--
ALTER TABLE `alternativa`
  ADD PRIMARY KEY (`id_alternativa`),
  ADD KEY `id_questao` (`id_questao`);

--
-- Índices de tabela `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`id_aluno`);

--
-- Índices de tabela `avaliacao_prova`
--
ALTER TABLE `avaliacao_prova`
  ADD PRIMARY KEY (`id_prova`,`id_desempenho`),
  ADD KEY `id_desempenho` (`id_desempenho`);

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `idx_cliente_geo` (`latitude`,`longitude`);

--
-- Índices de tabela `coordenador`
--
ALTER TABLE `coordenador`
  ADD PRIMARY KEY (`id_coordenador`);

--
-- Índices de tabela `decisao_reposicao`
--
ALTER TABLE `decisao_reposicao`
  ADD PRIMARY KEY (`id_decisao`),
  ADD KEY `fk_dec_mat` (`id_material`);

--
-- Índices de tabela `desempenho`
--
ALTER TABLE `desempenho`
  ADD PRIMARY KEY (`id_desempenho`),
  ADD KEY `fk_desempenho_aluno` (`id_aluno`),
  ADD KEY `fk_desempenho_prova` (`id_prova`);

--
-- Índices de tabela `elabora_prova`
--
ALTER TABLE `elabora_prova`
  ADD PRIMARY KEY (`id_instrutor`,`id_prova`),
  ADD KEY `id_prova` (`id_prova`);

--
-- Índices de tabela `elabora_relatorio`
--
ALTER TABLE `elabora_relatorio`
  ADD PRIMARY KEY (`id_coordenador`,`id_relatorio`),
  ADD KEY `id_relatorio` (`id_relatorio`);

--
-- Índices de tabela `entrega`
--
ALTER TABLE `entrega`
  ADD PRIMARY KEY (`id_entrega`),
  ADD KEY `fk_ent_ped` (`id_pedido`),
  ADD KEY `fk_ent_rota` (`id_rota`),
  ADD KEY `fk_ent_vei` (`id_veiculo`);

--
-- Índices de tabela `estoque`
--
ALTER TABLE `estoque`
  ADD PRIMARY KEY (`id_estoque`);

--
-- Índices de tabela `estoque_material`
--
ALTER TABLE `estoque_material`
  ADD PRIMARY KEY (`id_material`,`id_estoque`),
  ADD KEY `id_estoque` (`id_estoque`);

--
-- Índices de tabela `estoque_movimentacao`
--
ALTER TABLE `estoque_movimentacao`
  ADD PRIMARY KEY (`id_estoque_movimentacao`,`id_estoque`),
  ADD KEY `id_estoque` (`id_estoque`);

--
-- Índices de tabela `estoque_snapshot`
--
ALTER TABLE `estoque_snapshot`
  ADD PRIMARY KEY (`id_snapshot`),
  ADD UNIQUE KEY `uk_snap` (`id_material`,`id_estoque`,`data_ref`),
  ADD KEY `fk_snap_est` (`id_estoque`);

--
-- Índices de tabela `instrutor`
--
ALTER TABLE `instrutor`
  ADD PRIMARY KEY (`id_instrutor`);

--
-- Índices de tabela `inventario_fisico`
--
ALTER TABLE `inventario_fisico`
  ADD PRIMARY KEY (`id_inventario`),
  ADD KEY `fk_inv_est` (`id_estoque`);

--
-- Índices de tabela `inventario_fisico_item`
--
ALTER TABLE `inventario_fisico_item`
  ADD PRIMARY KEY (`id_item`),
  ADD KEY `fk_invi_inv` (`id_inventario`),
  ADD KEY `fk_invi_mat` (`id_material`);

--
-- Índices de tabela `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id_material`);

--
-- Índices de tabela `movimentacao_material`
--
ALTER TABLE `movimentacao_material`
  ADD PRIMARY KEY (`id_mov`),
  ADD KEY `fk_mm_estoque` (`id_estoque`),
  ADD KEY `fk_mm_material` (`id_material`);

--
-- Índices de tabela `nota_fiscal`
--
ALTER TABLE `nota_fiscal`
  ADD PRIMARY KEY (`id_nota_fiscal`);

--
-- Índices de tabela `nota_fiscal_item`
--
ALTER TABLE `nota_fiscal_item`
  ADD PRIMARY KEY (`id_item`),
  ADD KEY `fk_ni_nf` (`id_nota_fiscal`),
  ADD KEY `fk_ni_mat` (`id_material`),
  ADD KEY `fk_ni_estoque` (`id_estoque`);

--
-- Índices de tabela `nota_fiscal_material`
--
ALTER TABLE `nota_fiscal_material`
  ADD PRIMARY KEY (`id_nota_fiscal`,`id_material`),
  ADD KEY `id_material` (`id_material`);

--
-- Índices de tabela `ocorrencia_entrega`
--
ALTER TABLE `ocorrencia_entrega`
  ADD PRIMARY KEY (`id_ocorrencia`),
  ADD KEY `fk_oc_ped` (`id_pedido`);

--
-- Índices de tabela `participacao_simulacao`
--
ALTER TABLE `participacao_simulacao`
  ADD PRIMARY KEY (`id_simulacao`,`id_material`),
  ADD KEY `id_material` (`id_material`);

--
-- Índices de tabela `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `fk_ped_cli` (`id_cliente`),
  ADD KEY `fk_ped_est` (`id_estoque_origem`),
  ADD KEY `idx_pedido_status` (`status`);

--
-- Índices de tabela `pedido_item`
--
ALTER TABLE `pedido_item`
  ADD PRIMARY KEY (`id_pedido_item`),
  ADD KEY `fk_pi_ped` (`id_pedido`),
  ADD KEY `fk_pi_mat` (`id_material`);

--
-- Índices de tabela `politica_estoque`
--
ALTER TABLE `politica_estoque`
  ADD PRIMARY KEY (`id_material`);

--
-- Índices de tabela `possui_simulacao_material`
--
ALTER TABLE `possui_simulacao_material`
  ADD PRIMARY KEY (`id_simulacao`,`id_material`),
  ADD KEY `id_material` (`id_material`);

--
-- Índices de tabela `prova`
--
ALTER TABLE `prova`
  ADD PRIMARY KEY (`id_prova`),
  ADD KEY `id_instrutor` (`id_instrutor`);

--
-- Índices de tabela `provas_realizada`
--
ALTER TABLE `provas_realizada`
  ADD PRIMARY KEY (`id_aluno`,`id_prova`),
  ADD KEY `id_prova` (`id_prova`);

--
-- Índices de tabela `questao`
--
ALTER TABLE `questao`
  ADD PRIMARY KEY (`id_questao`),
  ADD KEY `id_prova` (`id_prova`);

--
-- Índices de tabela `relatorio`
--
ALTER TABLE `relatorio`
  ADD PRIMARY KEY (`id_relatorio`);

--
-- Índices de tabela `resposta_aluno`
--
ALTER TABLE `resposta_aluno`
  ADD PRIMARY KEY (`id_resposta`),
  ADD KEY `id_aluno` (`id_aluno`),
  ADD KEY `id_questao` (`id_questao`),
  ADD KEY `id_alternativa_escolhida` (`id_alternativa_escolhida`);

--
-- Índices de tabela `rota`
--
ALTER TABLE `rota`
  ADD PRIMARY KEY (`id_rota`),
  ADD KEY `fk_rota_est` (`id_estoque_origem`);

--
-- Índices de tabela `rota_parada`
--
ALTER TABLE `rota_parada`
  ADD PRIMARY KEY (`id_parada`),
  ADD KEY `fk_rp_cli` (`id_cliente`),
  ADD KEY `fk_rp_ped` (`id_pedido`),
  ADD KEY `idx_rota_parada_rota_seq` (`id_rota`,`sequencia`);

--
-- Índices de tabela `simulacao`
--
ALTER TABLE `simulacao`
  ADD PRIMARY KEY (`id_simulacao`);

--
-- Índices de tabela `veiculo`
--
ALTER TABLE `veiculo`
  ADD PRIMARY KEY (`id_veiculo`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `alternativa`
--
ALTER TABLE `alternativa`
  MODIFY `id_alternativa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT de tabela `aluno`
--
ALTER TABLE `aluno`
  MODIFY `id_aluno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `coordenador`
--
ALTER TABLE `coordenador`
  MODIFY `id_coordenador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `decisao_reposicao`
--
ALTER TABLE `decisao_reposicao`
  MODIFY `id_decisao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `desempenho`
--
ALTER TABLE `desempenho`
  MODIFY `id_desempenho` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de tabela `entrega`
--
ALTER TABLE `entrega`
  MODIFY `id_entrega` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de tabela `estoque`
--
ALTER TABLE `estoque`
  MODIFY `id_estoque` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de tabela `estoque_snapshot`
--
ALTER TABLE `estoque_snapshot`
  MODIFY `id_snapshot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `instrutor`
--
ALTER TABLE `instrutor`
  MODIFY `id_instrutor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `inventario_fisico`
--
ALTER TABLE `inventario_fisico`
  MODIFY `id_inventario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `inventario_fisico_item`
--
ALTER TABLE `inventario_fisico_item`
  MODIFY `id_item` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `material`
--
ALTER TABLE `material`
  MODIFY `id_material` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT de tabela `movimentacao_material`
--
ALTER TABLE `movimentacao_material`
  MODIFY `id_mov` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de tabela `nota_fiscal`
--
ALTER TABLE `nota_fiscal`
  MODIFY `id_nota_fiscal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `nota_fiscal_item`
--
ALTER TABLE `nota_fiscal_item`
  MODIFY `id_item` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `ocorrencia_entrega`
--
ALTER TABLE `ocorrencia_entrega`
  MODIFY `id_ocorrencia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `pedido_item`
--
ALTER TABLE `pedido_item`
  MODIFY `id_pedido_item` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de tabela `prova`
--
ALTER TABLE `prova`
  MODIFY `id_prova` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de tabela `questao`
--
ALTER TABLE `questao`
  MODIFY `id_questao` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de tabela `relatorio`
--
ALTER TABLE `relatorio`
  MODIFY `id_relatorio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `resposta_aluno`
--
ALTER TABLE `resposta_aluno`
  MODIFY `id_resposta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=265;

--
-- AUTO_INCREMENT de tabela `rota`
--
ALTER TABLE `rota`
  MODIFY `id_rota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `rota_parada`
--
ALTER TABLE `rota_parada`
  MODIFY `id_parada` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `simulacao`
--
ALTER TABLE `simulacao`
  MODIFY `id_simulacao` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `veiculo`
--
ALTER TABLE `veiculo`
  MODIFY `id_veiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `acessa_coordenador_estoque`
--
ALTER TABLE `acessa_coordenador_estoque`
  ADD CONSTRAINT `acessa_coordenador_estoque_ibfk_1` FOREIGN KEY (`id_coordenador`) REFERENCES `coordenador` (`id_coordenador`),
  ADD CONSTRAINT `acessa_coordenador_estoque_ibfk_2` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `acessa_instrutor_estoque`
--
ALTER TABLE `acessa_instrutor_estoque`
  ADD CONSTRAINT `acessa_instrutor_estoque_ibfk_1` FOREIGN KEY (`id_instrutor`) REFERENCES `instrutor` (`id_instrutor`),
  ADD CONSTRAINT `acessa_instrutor_estoque_ibfk_2` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `alternativa`
--
ALTER TABLE `alternativa`
  ADD CONSTRAINT `alternativa_ibfk_1` FOREIGN KEY (`id_questao`) REFERENCES `questao` (`id_questao`);

--
-- Restrições para tabelas `avaliacao_prova`
--
ALTER TABLE `avaliacao_prova`
  ADD CONSTRAINT `avaliacao_prova_ibfk_1` FOREIGN KEY (`id_prova`) REFERENCES `prova` (`id_prova`),
  ADD CONSTRAINT `avaliacao_prova_ibfk_2` FOREIGN KEY (`id_desempenho`) REFERENCES `desempenho` (`id_desempenho`);

--
-- Restrições para tabelas `decisao_reposicao`
--
ALTER TABLE `decisao_reposicao`
  ADD CONSTRAINT `fk_dec_mat` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `desempenho`
--
ALTER TABLE `desempenho`
  ADD CONSTRAINT `fk_desempenho_aluno` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_desempenho_prova` FOREIGN KEY (`id_prova`) REFERENCES `prova` (`id_prova`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Restrições para tabelas `elabora_prova`
--
ALTER TABLE `elabora_prova`
  ADD CONSTRAINT `elabora_prova_ibfk_1` FOREIGN KEY (`id_instrutor`) REFERENCES `instrutor` (`id_instrutor`),
  ADD CONSTRAINT `elabora_prova_ibfk_2` FOREIGN KEY (`id_prova`) REFERENCES `prova` (`id_prova`);

--
-- Restrições para tabelas `elabora_relatorio`
--
ALTER TABLE `elabora_relatorio`
  ADD CONSTRAINT `elabora_relatorio_ibfk_1` FOREIGN KEY (`id_coordenador`) REFERENCES `coordenador` (`id_coordenador`),
  ADD CONSTRAINT `elabora_relatorio_ibfk_2` FOREIGN KEY (`id_relatorio`) REFERENCES `relatorio` (`id_relatorio`);

--
-- Restrições para tabelas `entrega`
--
ALTER TABLE `entrega`
  ADD CONSTRAINT `fk_ent_ped` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`),
  ADD CONSTRAINT `fk_ent_rota` FOREIGN KEY (`id_rota`) REFERENCES `rota` (`id_rota`),
  ADD CONSTRAINT `fk_ent_vei` FOREIGN KEY (`id_veiculo`) REFERENCES `veiculo` (`id_veiculo`);

--
-- Restrições para tabelas `estoque_material`
--
ALTER TABLE `estoque_material`
  ADD CONSTRAINT `estoque_material_ibfk_1` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`),
  ADD CONSTRAINT `estoque_material_ibfk_2` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `estoque_movimentacao`
--
ALTER TABLE `estoque_movimentacao`
  ADD CONSTRAINT `estoque_movimentacao_ibfk_1` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `estoque_snapshot`
--
ALTER TABLE `estoque_snapshot`
  ADD CONSTRAINT `fk_snap_est` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`),
  ADD CONSTRAINT `fk_snap_mat` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `inventario_fisico`
--
ALTER TABLE `inventario_fisico`
  ADD CONSTRAINT `fk_inv_est` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `inventario_fisico_item`
--
ALTER TABLE `inventario_fisico_item`
  ADD CONSTRAINT `fk_invi_inv` FOREIGN KEY (`id_inventario`) REFERENCES `inventario_fisico` (`id_inventario`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_invi_mat` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `movimentacao_material`
--
ALTER TABLE `movimentacao_material`
  ADD CONSTRAINT `fk_mm_estoque` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`),
  ADD CONSTRAINT `fk_mm_material` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `nota_fiscal_item`
--
ALTER TABLE `nota_fiscal_item`
  ADD CONSTRAINT `fk_ni_estoque` FOREIGN KEY (`id_estoque`) REFERENCES `estoque` (`id_estoque`),
  ADD CONSTRAINT `fk_ni_mat` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`),
  ADD CONSTRAINT `fk_ni_nf` FOREIGN KEY (`id_nota_fiscal`) REFERENCES `nota_fiscal` (`id_nota_fiscal`);

--
-- Restrições para tabelas `nota_fiscal_material`
--
ALTER TABLE `nota_fiscal_material`
  ADD CONSTRAINT `nota_fiscal_material_ibfk_1` FOREIGN KEY (`id_nota_fiscal`) REFERENCES `nota_fiscal` (`id_nota_fiscal`),
  ADD CONSTRAINT `nota_fiscal_material_ibfk_2` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `ocorrencia_entrega`
--
ALTER TABLE `ocorrencia_entrega`
  ADD CONSTRAINT `fk_oc_ped` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`);

--
-- Restrições para tabelas `participacao_simulacao`
--
ALTER TABLE `participacao_simulacao`
  ADD CONSTRAINT `participacao_simulacao_ibfk_1` FOREIGN KEY (`id_simulacao`) REFERENCES `simulacao` (`id_simulacao`),
  ADD CONSTRAINT `participacao_simulacao_ibfk_2` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `fk_ped_cli` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  ADD CONSTRAINT `fk_ped_est` FOREIGN KEY (`id_estoque_origem`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `pedido_item`
--
ALTER TABLE `pedido_item`
  ADD CONSTRAINT `fk_pi_mat` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`),
  ADD CONSTRAINT `fk_pi_ped` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE CASCADE;

--
-- Restrições para tabelas `politica_estoque`
--
ALTER TABLE `politica_estoque`
  ADD CONSTRAINT `fk_pol_mat` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `possui_simulacao_material`
--
ALTER TABLE `possui_simulacao_material`
  ADD CONSTRAINT `possui_simulacao_material_ibfk_1` FOREIGN KEY (`id_simulacao`) REFERENCES `simulacao` (`id_simulacao`),
  ADD CONSTRAINT `possui_simulacao_material_ibfk_2` FOREIGN KEY (`id_material`) REFERENCES `material` (`id_material`);

--
-- Restrições para tabelas `prova`
--
ALTER TABLE `prova`
  ADD CONSTRAINT `prova_ibfk_1` FOREIGN KEY (`id_instrutor`) REFERENCES `instrutor` (`id_instrutor`);

--
-- Restrições para tabelas `provas_realizada`
--
ALTER TABLE `provas_realizada`
  ADD CONSTRAINT `provas_realizada_ibfk_1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  ADD CONSTRAINT `provas_realizada_ibfk_2` FOREIGN KEY (`id_prova`) REFERENCES `prova` (`id_prova`);

--
-- Restrições para tabelas `questao`
--
ALTER TABLE `questao`
  ADD CONSTRAINT `questao_ibfk_1` FOREIGN KEY (`id_prova`) REFERENCES `prova` (`id_prova`);

--
-- Restrições para tabelas `resposta_aluno`
--
ALTER TABLE `resposta_aluno`
  ADD CONSTRAINT `resposta_aluno_ibfk_1` FOREIGN KEY (`id_aluno`) REFERENCES `aluno` (`id_aluno`),
  ADD CONSTRAINT `resposta_aluno_ibfk_2` FOREIGN KEY (`id_questao`) REFERENCES `questao` (`id_questao`),
  ADD CONSTRAINT `resposta_aluno_ibfk_3` FOREIGN KEY (`id_alternativa_escolhida`) REFERENCES `alternativa` (`id_alternativa`);

--
-- Restrições para tabelas `rota`
--
ALTER TABLE `rota`
  ADD CONSTRAINT `fk_rota_est` FOREIGN KEY (`id_estoque_origem`) REFERENCES `estoque` (`id_estoque`);

--
-- Restrições para tabelas `rota_parada`
--
ALTER TABLE `rota_parada`
  ADD CONSTRAINT `fk_rp_cli` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  ADD CONSTRAINT `fk_rp_ped` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`),
  ADD CONSTRAINT `fk_rp_rota` FOREIGN KEY (`id_rota`) REFERENCES `rota` (`id_rota`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
