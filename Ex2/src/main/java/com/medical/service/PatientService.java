package com.medical.service;

import com.medical.entity.Patient;
import com.medical.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    /**
     * Tìm kiếm bệnh nhân theo tên (tìm kiếm tương đối)
     * Ghi log TRACE, DEBUG, INFO
     */
    @Transactional(readOnly = true)
    public List<Patient> searchPatients(String name) {
        log.trace("[TRACE] Bắt đầu thực thi phương thức searchPatients với từ khóa: '{}'", name);

        log.debug("[DEBUG] Đang chuẩn bị gửi câu truy vấn SQL tìm kiếm bệnh nhân xuống Database...");
        List<Patient> results;
        if (name == null || name.trim().isEmpty()) {
            results = patientRepository.findAll();
        } else {
            results = patientRepository.findByNameContainingIgnoreCase(name.trim());
        }

        log.debug("[DEBUG] Truy vấn hoàn tất, nhận được {} bản ghi từ Database.", results.size());
        log.info("[INFO] Tìm kiếm bệnh nhân thành công: từ khóa='{}', số lượng kết quả={}", name, results.size());
        log.trace("[TRACE] Kết thúc thực thi phương thức searchPatients.");

        return results;
    }

    @Transactional
    public Patient savePatient(Patient patient) {
        log.trace("[TRACE] Bắt đầu lưu thông tin bệnh nhân: {}", patient.getName());
        Patient saved = patientRepository.save(patient);
        log.info("[INFO] Đã lưu bệnh nhân thành công với ID: {}", saved.getId());
        return saved;
    }
}
