package br.edu.infnet.multi.model.loader;

import br.edu.infnet.multi.model.dto.in.TecnicoRequestDTO;
import br.edu.infnet.multi.model.service.TecnicoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
@Order(1)
public class TecnicoLoader implements ApplicationRunner{

    private final TecnicoService tecnicoService;

    public TecnicoLoader(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("tecnico.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            TecnicoRequestDTO tecnico = new TecnicoRequestDTO();
            tecnico.setNome(campos[0]);
            tecnico.setCpf(campos[1]);
            tecnico.setEmail(campos[2]);
            tecnico.setTelefone(campos[3]);
            tecnico.setUltimoSalario(Double.valueOf(campos[4]));
            tecnico.setAtivo(Boolean.valueOf(campos[5]));
            tecnico.setEspecialidade(campos[6]);
            tecnico.setDisponivel(Boolean.valueOf(campos[7]));
            tecnico.setCep(campos[8]);

            tecnicoService.incluir(tecnico);

            System.out.println(tecnico);

            linha = leitura.readLine();
        }

        System.out.println(" - " + tecnicoService.obterLista().size());

        leitura.close();
    }
}
