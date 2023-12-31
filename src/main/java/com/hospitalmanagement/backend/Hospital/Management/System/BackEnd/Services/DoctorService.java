package com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Services;

import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Models.Doctor;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Models.Patient;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Repositries.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository docRepo;

    public void addDoctorToDatabase(Doctor obj){
        String docId = "Doctor" + (docRepo.getOverAllDoctors()+1);
        obj.setDocID(docId);
        docRepo.addDoctorToDatabase(obj);
    }

    public Doctor getMinimumPatientdoctor(){
        int min = Integer.MAX_VALUE;
        HashMap<String, ArrayList<Patient>> docVSPatient = docRepo.getDocVsPatientDB();
        Doctor obj = null;
        // Key -> DoctorID
        for(String key : docVSPatient.keySet()){
            ArrayList<Patient> doctorsPatient = docVSPatient.get(key);
            int totalPatients = doctorsPatient.size();
            if(totalPatients < min){
                min = totalPatients;
                obj = docRepo.getDoctorByDocID(key);
            }
        }
        return obj;
    }

    public ArrayList<Patient> getDoctorPatients(String docId){
        return docRepo.getDoctorsPatients(docId);
    }

    public Doctor getDoctorByDocID(String docID){
        return docRepo.getDoctorByDocID(docID);
    }

    public void removeParticularPatientForParticularDoctor(String pId,String docId){
        docRepo.removeParticularPatientForParticularDoctor(pId,docId);
    }

    public void updateDocDetailsByID(String docID, Doctor obj){
        docRepo.updateDocDetailsByDocID(docID,obj);
    }
}
