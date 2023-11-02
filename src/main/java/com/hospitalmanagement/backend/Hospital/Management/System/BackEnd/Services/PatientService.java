package com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Services;

import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Models.Bill;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Models.Doctor;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Models.Patient;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Repositries.DoctorRepository;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Repositries.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepo;

    @Autowired
    DoctorService docService;

    @Autowired
    DoctorRepository docRepo;

    @Autowired
    HospitalService hospitalService;

    public void addPatientToDatabase(Patient obj){
        String pId = "Patient" + (patientRepo.getOverAllPatients() + 1);
        obj.setpId(pId);
        int bedNumber = hospitalService.getFirstEmptyBedNumber();
        hospitalService.assignPatientABed(bedNumber,obj);
        Doctor doc = docService.getMinimumPatientdoctor();
        patientRepo.assignPatientToDoctor(pId,doc);
        docRepo.assignPatientToDoctor(doc.getDocID(),obj);
        patientRepo.addPatientToDatabase(obj);
    }

    public Doctor getPatientsDoctor(String pId){
        return patientRepo.getPatientsDoctor(pId);
    }

    public Bill dischargePatient(String pId, String dischargeDate){
        // dd-mm-yy
        Patient obj = patientRepo.getPatientByID(pId);
        String admitDate = obj.getPatientAdmitDate();
        String[] admitDateArray = admitDate.split("-");
        String[] dischargeDateArray = dischargeDate.split("-");
        int diff = Integer.parseInt(dischargeDateArray[0]) - Integer.parseInt(admitDateArray[0]);
        Doctor docObj = patientRepo.getPatientsDoctor(pId);
        int docFee = docObj.getDocFee();
        int bedFee = hospitalService.getBedFee();
        int totalBill = diff * (docFee + bedFee);
        Bill billObj = new Bill(docFee,bedFee,totalBill);
        patientRepo.dischargePatientByPatientId(pId);
        return billObj;
    }

    public Patient getPatientById(String pId){
        return patientRepo.getPatientByID(pId);
    }
}
