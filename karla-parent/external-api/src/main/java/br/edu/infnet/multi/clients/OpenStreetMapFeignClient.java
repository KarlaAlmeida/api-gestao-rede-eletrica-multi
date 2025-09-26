package br.edu.infnet.multi.clients;

import br.edu.infnet.multi.model.domain.entities.Geolocalizacao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "openstreetmap", url = "${api.openstreetmap.url}")
public interface OpenStreetMapFeignClient {

    @GetMapping("/search")
    List<Geolocalizacao> search(@RequestParam("q") String query,
                                @RequestParam("format") String format,
                                @RequestParam("limit") int limit);
}
