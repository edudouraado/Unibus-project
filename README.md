# 🚌 UniBus - Sistema de Gestão de Transporte Universitário

O **UniBus** é uma solução móvel completa para o monitoramento e gestão de frotas de transporte acadêmico. O sistema conecta em tempo real **Administradores**, **Motoristas** e **Alunos**, garantindo segurança no acesso, controle preciso de lotação via QR Code e visualização de rotas com mapas integrados.

---

## 🚀 Funcionalidades por Perfil

### 🛠️ Painel do Administrador
- **Gestão de Usuários:** Ativação e inativação de perfis (Alunos/Motoristas) em tempo real. Usuários inativados são impedidos de realizar login instantaneamente.
- **Dashboard Estratégico:** Contadores dinâmicos na tela inicial que exibem o total de alunos ativos, motoristas em viagem e chamados de suporte pendentes.
- **Relatórios de Auditoria & Filtros:**
    - **Tentativas Inválidas:** Registro real de falhas de login (e-mail, motivo do erro e horário) para monitoramento de segurança.
    - **Picos de Uso:** Agrupamento de acessos ao sistema por hora para identificação de horários de maior demanda.
    - **Acessibilidade:** Monitoramento de quantas vezes o QR Code de embarque foi utilizado por hora.
- **Central de Chamados:** Sistema para visualizar e responder mensagens enviadas pelos alunos através do suporte.

### 👨‍✈️ Visão do Motorista
- **Gestão de Viagem:** Fluxo completo para iniciar a rota (selecionando entre Papicu, Parangaba ou Messejana) e finalizar a viagem com popups de confirmação e sucesso.
- **Validação por QR Code:** Scanner integrado (ZXing) que utiliza a câmera para ler o código do aluno e incrementar a lotação do ônibus automaticamente no banco de dados.
- **Controle de Lotação:** Gráfico circular dinâmico que atualiza em tempo real a porcentagem de ocupação e o número de vagas restantes.
- **Mapa da Rota:** Navegação via OpenStreetMap (osmdroid) com foco em Fortaleza/CE e exibição da previsão de chegada (ETA).

### 👨‍🎓 Visão do Aluno
- **Mapa Interativo:** Visualização de pontos de ônibus específicos de Fortaleza/CE integrados ao OpenStreetMap.
- **Filtro Inteligente de Rotas:** O mapa exibe apenas os pontos pertencentes à linha selecionada pelo aluno, evitando poluição visual.
- **Barra de Pesquisa:** Busca funcional por nomes de paradas de ônibus cadastradas no Firebase.
- **Ticket Digital (QR Code):** Geração de um QR Code exclusivo baseado no UID do Firebase para validação rápida no momento do embarque.
- **Suporte Direto:** Canal de comunicação integrado para envio de mensagens em tempo real ao administrador.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **Arquitetura:** Android Views (XML) com foco total em fidelidade ao protótipo do **Figma**.
- **Backend:** 
    - **Firebase Authentication:** Gestão de login seguro por matrícula e e-mail.
    - **Cloud Firestore:** Banco de dados NoSQL com Real-time Listeners (Snapshot) para atualizações sem necessidade de atualizar a página.
- **Mapas:** osmdroid (OpenStreetMap) - Implementação gratuita e open-source.
- **Scanner:** ZXing Android Embedded para leitura de códigos.
- **UI/UX:** Material Design com CardViews personalizados, botões redondos e popups com fundos transparentes.

---

## 📂 Estrutura do Banco de Dados (Firestore)

As coleções foram estruturadas para suportar a sincronização entre perfis:

- **`usuarios`**: Dados de perfil, e-mail e o status `acessoAtivo` (Boolean).
- **`rotas`**: Gerencia a `lotacao_atual`, `capacidade_maxima`, status `ativa` e `previsao_chegada`.
- **`pontos`**: Localização das paradas usando o tipo **GeoPoint** e vinculadas a um ID de rota.
- **`tentativas_invalidas`**: Logs de segurança contendo e-mail e motivo da falha.
- **`avisos`**: Mensagens do sistema e chamados enviados pelos alunos.

---

## 🎨 Identidade Visual

O projeto segue rigorosamente o design planejado, utilizando:
- **Azul UniBus:** `#134B70` (Primária)
- **Bege Soft:** `#F8F4E1` (Background)
- **Status:** Cores dinâmicas (Verde para Ativo/Sucesso e Vermelho para Inativo/Erro).

---

## ⚙️ Instalação e Execução

1. Clone o repositório: `git clone https://github.com/edudouraado/Unibus-project.git`
2. Adicione o arquivo `google-services.json` (gerado no seu console Firebase) na pasta `/app`.
3. Certifique-se de habilitar as permissões de **Câmera** e **Internet** no dispositivo/emulador.
4. Sincronize o Gradle e execute o projeto no Android Studio.

---

## 👥 Equipe de Desenvolvimento

- **Eduardo Dourado:** Desenvolvimento de código, Prototipagem de interface (Figma), UI/UX Design, Arquitetura de Software e Integração com Firebase.
- **Leandro:** Prototipagem de interface (Figma), UI/UX Design e Desenvolvimento.
- **Filipe:** Apoio no desenvolvimento de telas em Kotlin.

---
