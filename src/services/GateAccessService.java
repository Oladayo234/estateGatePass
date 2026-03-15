package services;

import data.models.GatePass;
import data.models.Resident;
import data.models.Type;
import data.models.Visitor;
import data.repositories.*;
import dtos.requests.GenerateResidentEntryCodeRequest;
import dtos.requests.GenerateVisitorEntryCodeRequest;
import dtos.responses.GenerateResidentEntryCodeResponse;
import dtos.responses.GenerateVisitorEntryCodeResponse;
import exceptions.*;
import utils.GenerateEntryVisitorCodeMapper;
import utils.GenerateResidentEntryCodeMapper;
import utils.RandomCodeGenerator;

import java.time.LocalDateTime;

public class GateAccessService {
   private GatePassRepo gatePassRepo = new GatePasses();
   private ResidentRepo residentRepo = new Residents();
   private VisitorRepo visitorRepo = new Visitors();

   GenerateVisitorEntryCodeResponse generateVisitorEntryCode(GenerateVisitorEntryCodeRequest request){
      Resident resident = getResidentById(request.getResidentId());
      Visitor savedVisitor = createVisitor(request);
      GatePass savedGatePass = createVisitorGatePass(request, savedVisitor, resident);
      return GenerateEntryVisitorCodeMapper.map(savedGatePass);
   }

   GenerateResidentEntryCodeResponse generateResidentEntryCode(GenerateResidentEntryCodeRequest request){
      Resident resident = getResidentById(request.getResidentId());
      GatePass savedGatePass = createResidentGatePass(request, resident);
      return GenerateResidentEntryCodeMapper.map(savedGatePass, resident);
   }

   public String generateExitCode(String otp){
      GatePass gatePass = getGatePassByOtp(otp);
      gatePass.setOtp(RandomCodeGenerator.getOtp());
      gatePass.setCodeType(Type.EXIT);
      GatePass savedGatePass = gatePassRepo.save(gatePass);
      return "Exit Code generated successfully";
   }

   public String validateCode(String otp){
      GatePass gatePass = getGatePassByOtp(otp);
      if (gatePass.isExpired()) throw new InvalidGatePassException("Gate pass has expired");
      if (!gatePass.isValid()) throw new InvalidGatePassException("Gate pass has been disabled");
      return "Access granted";
   }

   public String extendTime(String gatePassId, LocalDateTime newExpirationDate){
      GatePass gatePass = getGatePassById(gatePassId);
      gatePass.setExpirationDate(newExpirationDate);
      gatePassRepo.save(gatePass);
      return "Time has been extended successfully";
   }

   public  String disableCode(String gatePassId){
      GatePass gatePass = getGatePassById(gatePassId);
      gatePass.setValid(false);
      gatePassRepo.save(gatePass);
      return "Gatepass succesfully disabled";
   }

   private GatePass getGatePassById(String id) {
      GatePass gatePass = gatePassRepo.findById(id);
      if (gatePass == null) throw new GatePassDoesNotExistException("Gate pass not found");
      return gatePass;
   }

   private GatePass getGatePassByOtp(String otp) {
      GatePass gatePass = gatePassRepo.findByOtp(otp);
      if (gatePass == null) throw new GatePassOtpNotExistException("Otp not found");
      return gatePass;
   }

   private GatePass createResidentGatePass(GenerateResidentEntryCodeRequest request, Resident resident) {
      GatePass gatePass = new GatePass();
      if (request.getExpirationDate() == null) {
         gatePass.setExpirationDate(LocalDateTime.now().plusHours(24));
      } else {
         gatePass.setExpirationDate(request.getExpirationDate());
      }
      gatePass.setResidentId(resident.getId());
      gatePass.setCodeType(request.getCodeType());
      gatePass.setOtp(RandomCodeGenerator.getOtp());
      GatePass savedGatePass = gatePassRepo.save(gatePass);
      return savedGatePass;
   }

   private GatePass createVisitorGatePass(GenerateVisitorEntryCodeRequest request, Visitor savedVisitor, Resident resident) {
      GatePass gatePass = new GatePass();
      if (request.getExpirationDate() == null) {
         gatePass.setExpirationDate(LocalDateTime.now().plusHours(24));
      } else {
         gatePass.setExpirationDate(request.getExpirationDate());
      }
      gatePass.setCodeType(request.getCodeType());
      gatePass.setVisitor(savedVisitor);
      gatePass.setResidentId(resident.getId());
      gatePass.setOtp(RandomCodeGenerator.getOtp());
      GatePass savedGatePass = gatePassRepo.save(gatePass);
      return savedGatePass;
   }

   private Visitor createVisitor(GenerateVisitorEntryCodeRequest request) {
      Visitor visitor = new Visitor();
      visitor.setName(request.getVisitorName());
      visitor.setPhoneNumber(request.getVisitorPhoneNumber());
      visitor.setPurposeOfVisit(request.getPurposeOfVisit());
      return visitorRepo.save(visitor);
   }

   private Visitor getVisitorById(String id) {
      Visitor visitor = visitorRepo.findById(id);
      if (visitor == null) throw new VisitorDoesNotExistException("visitor not found");
      return visitor;
   }

   private Resident getResidentById(String id) {
      Resident resident = residentRepo.findById(id);
      if (resident == null) throw new ResidentDoesNotExistException("Resident not found");
      return resident;
   }

   private void validateCheckDuplicateFor(Visitor visitor) {
      if (visitorRepo.findByPhone(visitor.getPhoneNumber()) != null)
         throw new VisitorAlreadyRegisteredException("Visitor already registered");
   }

   private void validateCheckDuplicateFor(Resident resident) {
      if (residentRepo.findByPhone(resident.getPhoneNumber()) != null)
         throw new ResidentAlreadyRegisteredException("Resident already registered");
   }
}
