package br.edu.infnet.multi.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "openstreetmap", url = "${api.openstreetmap.url}")
public interface OpenStreetMapFeignClient {

    @GetMapping("/search")
    List<OpenStreetMapResponse> search(@RequestParam("q") String query,
                                @RequestParam("format") String format,
                                @RequestParam("limit") int limit);

    class OpenStreetMapResponse {

        private String lat;
        private String lon;

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }



}
