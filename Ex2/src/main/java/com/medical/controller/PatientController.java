package com.medical.controller;

import com.medical.entity.Patient;
import com.medical.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    /**
     * API tìm kiếm bệnh nhân
     * URL: GET /api/v1/patients/search?name=...
     */
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam(value = "name", required = false) String name) {
        log.info("[Controller] Nhận request tìm kiếm bệnh nhân với từ khóa: '{}'", name);
        List<Patient> patients = patientService.searchPatients(name);
        return ResponseEntity.ok(patients);
    }
}
