package com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Services;

import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Models.Patient;
import com.hospitalmanagement.backend.Hospital.Management.System.BackEnd.Repositries.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalService {
    @Autowired
    HospitalRepository hospitalRepo;

    public int getFirstEmptyBedNumber(){
        Patient[] beds = hospitalRepo.getAllBeds();
        for(int i=0;i<beds.length;i++){
            if(beds[i] == null){
                return i; // Got First Empty Bed
            }
        }
        return -1; // No Bed is Empty
    }

    public void assignPatientABed(int bedNumber, Patient obj){
        hospitalRepo.assignPatientToBedNumber(bedNumber,obj);
    }

    public Patient getPatientFromBedNumber(int bedNumber){
        return hospitalRepo.getPatientAtParticularBedNumber(bedNumber);
    }

    public void deAllocatePatientFromBed(String pId){
        hospitalRepo.deAllocatePatientFromBed(pId);
    }

    public int getBedFee(){
        return hospitalRepo.getBedFee();
    }
}
