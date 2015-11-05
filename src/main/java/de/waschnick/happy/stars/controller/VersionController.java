package de.waschnick.happy.stars.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class VersionController {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class VersionInfo {
        String version;
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public VersionInfo getVersion() {
        return new VersionInfo("alive!");
    }
}
