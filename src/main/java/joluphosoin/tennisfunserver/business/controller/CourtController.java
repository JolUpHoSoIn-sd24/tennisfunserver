package joluphosoin.tennisfunserver.business.controller;

import joluphosoin.tennisfunserver.business.service.CourtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business/courts")
public class CourtController {
    private final CourtService courtService;


}
