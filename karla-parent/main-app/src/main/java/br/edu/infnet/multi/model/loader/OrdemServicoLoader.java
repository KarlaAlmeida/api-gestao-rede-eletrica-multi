package br.edu.infnet.multi.model.loader;


import br.edu.infnet.multi.model.dto.in.OrdemServicoRequestDTO;
import br.edu.infnet.multi.model.service.OrdemServicoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(4)
public class OrdemServicoLoader implements ApplicationRunner{

    private final OrdemServicoService ordemServicoService;

    public OrdemServicoLoader(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ordem-servico.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            OrdemServicoRequestDTO ordemServico = new OrdemServicoRequestDTO();

            ordemServico.setOcorrenciaId(Integer.valueOf(campos[0]));
            ordemServico.setCpfTecnico(campos[1]);
            ordemServico.setDescricaoServico(campos[2]);

            ordemServicoService.incluir(ordemServico);

            System.out.println(ordemServico);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ordemServicoService.obterLista().size());

        leitura.close();
    }
}
