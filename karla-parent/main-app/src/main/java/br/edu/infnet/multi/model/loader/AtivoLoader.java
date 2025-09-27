package br.edu.infnet.multi.model.loader;

import br.edu.infnet.multi.model.dto.in.AtivoRequestDTO;
import br.edu.infnet.multi.model.dto.out.AtivoResponseDTO;
import br.edu.infnet.multi.model.service.AtivoService;
import br.edu.infnet.multi.model.service.EnderecoGeorreferenciadoService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;

@Component
@Order(2)
public class AtivoLoader implements ApplicationRunner{

    private final AtivoService ativoService;
    private final EnderecoGeorreferenciadoService enderecoGeorreferenciadoService;

    public AtivoLoader(AtivoService ativoService,
                       EnderecoGeorreferenciadoService enderecoGeorreferenciadoService) {
        this.ativoService = ativoService;
        this.enderecoGeorreferenciadoService = enderecoGeorreferenciadoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        FileReader arquivo = new FileReader("ativo.txt");
        BufferedReader leitura = new BufferedReader(arquivo);

        String linha = leitura.readLine();

        String[] campos = null;

        while (linha != null) {

            campos = linha.split(";");

            AtivoRequestDTO ativoRequestDTO = new AtivoRequestDTO();

            ativoRequestDTO.setCep(campos[0]);
            ativoRequestDTO.setTipoAtivo(campos[1]);
            ativoRequestDTO.setDataInstalacao(LocalDate.parse(campos[2]));

            AtivoResponseDTO ativo = ativoService.incluir(ativoRequestDTO);

            System.out.println(ativo);

            linha = leitura.readLine();
        }

        System.out.println(" - " + ativoService.obterLista().size());

        leitura.close();
    }
}
