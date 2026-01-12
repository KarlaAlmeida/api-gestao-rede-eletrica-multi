package br.edu.infnet.multi.model.loader;


import br.edu.infnet.multi.model.dto.in.OcorrenciaRequestDTO;
import br.edu.infnet.multi.model.service.OcorrenciaService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(3)
public class OcorrenciaLoader implements ApplicationRunner{

    private final OcorrenciaService ocorrenciaService;

    public OcorrenciaLoader(OcorrenciaService ocorrenciaService) {
        this.ocorrenciaService = ocorrenciaService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ocorrencia.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            OcorrenciaRequestDTO ocorrencia = new OcorrenciaRequestDTO();

            ocorrencia.setAtivoId(Integer.valueOf(campos[0]));
            ocorrencia.setDescricaoOcorrencia(campos[1]);
            ocorrencia.setPrioridadeOcorrencia(campos[2]);

            ocorrenciaService.incluir(ocorrencia);

            System.out.println(ocorrencia);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ocorrenciaService.obterLista().size());

        leitura.close();
    }
}
