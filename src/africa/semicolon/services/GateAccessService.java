package africa.semicolon.services;
import africa.semicolon.data.models.GatePass;
import africa.semicolon.data.models.Resident;
import africa.semicolon.data.models.Type;
import africa.semicolon.data.models.Visitor;
import africa.semicolon.dtos.requests.GenerateResidentEntryCodeRequest;
import africa.semicolon.dtos.requests.GenerateVisitorEntryCodeRequest;
import africa.semicolon.dtos.responses.GenerateExitCodeResponse;
import africa.semicolon.dtos.responses.GenerateResidentEntryCodeResponse;
import africa.semicolon.dtos.responses.GenerateVisitorEntryCodeResponse;
import africa.semicolon.dtos.responses.ValidateCodeResponse;
import africa.semicolon.data.repositories.GatePassRepo;
import africa.semicolon.data.repositories.ResidentRepo;
import africa.semicolon.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import africa.semicolon.utils.*;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class GateAccessService {
   private final GatePassRepo gatePassRepo;
   private final ResidentRepo residentRepo;

   public GenerateVisitorEntryCodeResponse generateVisitorEntryCode(GenerateVisitorEntryCodeRequest request){
      Resident resident = validateResidentIsActive(getResidentById(request.getResidentId()));
      Visitor savedVisitor = createVisitor(request);
      GatePass savedGatePass = createVisitorGatePass(request, savedVisitor, resident);
      return GenerateEntryVisitorCodeMapper.map(savedGatePass);
   }

   public GenerateResidentEntryCodeResponse generateResidentEntryCode(GenerateResidentEntryCodeRequest request){
      Resident resident = validateResidentIsActive(getResidentById(request.getResidentId()));
      GatePass savedGatePass = createResidentGatePass(request, resident);
      return GenerateResidentEntryCodeMapper.map(savedGatePass, resident);
   }

   public GenerateExitCodeResponse generateExitCode(String otp){
      GatePass gatePass = getGatePassByOtp(otp);
      gatePass.setOtp(RandomCodeGenerator.getOtp());
      gatePass.setCodeType(Type.EXIT);
      GatePass savedGatePass = gatePassRepo.save(gatePass);
      Resident resident = getResidentById(savedGatePass.getResidentId());
      return GenerateExitCodeMapper.map(savedGatePass, resident);
   }

   public ValidateCodeResponse validateCode(String otp){
      GatePass gatePass = getGatePassByOtp(otp);
      validateOtpGatePass(gatePass);
      Resident resident = getResidentById(gatePass.getResidentId());
      return ValidateCodeMapper.map(gatePass, resident);
   }

   private static void validateOtpGatePass(GatePass gatePass) {
      if (gatePass.isExpired()) throw new InvalidGatePassException("Gate pass has expired");
      if (!gatePass.isValid()) throw new InvalidGatePassException("Gate pass has been disabled");
   }

   public String extendTime(String gatePassId, LocalDateTime newExpirationDate){
      GatePass gatePass = getGatePassById(gatePassId);
      gatePass.setExpirationDate(newExpirationDate);
      gatePassRepo.save(gatePass);
      return "Time has been extended successfully";
   }

   public String disableCode(String gatePassId){
      GatePass gatePass = getGatePassById(gatePassId);
      gatePass.setValid(false);
      gatePassRepo.save(gatePass);
      return "Gate pass successfully disabled";
   }

   private Resident validateResidentIsActive(Resident resident) {
      if (resident.isSuspended()) throw new ResidentSuspendedException("Resident is suspended");
      return resident;
   }

   private GatePass getGatePassById(String id) {
      return gatePassRepo.findById(id).orElseThrow(() -> new GatePassDoesNotExistException("Gate pass not found"));
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
      gatePass.setId(RandomCodeGenerator.gateIdGenerator());
      gatePass.setOtp(RandomCodeGenerator.getOtp());
       return gatePassRepo.save(gatePass);
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
      gatePass.setId(RandomCodeGenerator.gateIdGenerator());
      gatePass.setOtp(RandomCodeGenerator.getOtp());
       return gatePassRepo.save(gatePass);
   }

   private Visitor createVisitor(GenerateVisitorEntryCodeRequest request) {
      Visitor visitor = new Visitor();
      visitor.setName(request.getVisitorName());
      visitor.setPhoneNumber(request.getVisitorPhoneNumber());
      visitor.setPurposeOfVisit(request.getPurposeOfVisit());
      return visitor;
   }

   private Resident getResidentById(String id) {
      return residentRepo.findById(id).orElseThrow(() -> new ResidentDoesNotExistException("Resident not found"));
   }
}
