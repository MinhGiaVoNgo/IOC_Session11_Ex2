package com.medical.controller;

import com.medical.entity.Patient;
import com.medical.service.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    @DisplayName("GET /api/v1/patients/search?name=Nguyễn - Tìm kiếm bệnh nhân và ghi log các cấp độ")
    void testSearchPatients() throws Exception {
        Patient patient = Patient.builder()
                .id(1L)
                .name("Nguyễn Văn A")
                .age(30)
                .gender("Nam")
                .phone("0987654321")
                .address("Hà Nội")
                .build();

        when(patientService.searchPatients("Nguyễn")).thenReturn(List.of(patient));

        mockMvc.perform(get("/api/v1/patients/search")
                        .param("name", "Nguyễn")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Nguyễn Văn A"));
    }
}
