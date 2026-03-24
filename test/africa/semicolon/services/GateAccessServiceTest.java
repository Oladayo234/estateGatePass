package africa.semicolon.services;

import africa.semicolon.data.models.Resident;
import africa.semicolon.data.models.Type;
import africa.semicolon.data.repositories.GatePassRepo;
import africa.semicolon.data.repositories.ResidentRepo;
import africa.semicolon.dtos.requests.GenerateResidentEntryCodeRequest;
import africa.semicolon.dtos.requests.GenerateVisitorEntryCodeRequest;
import africa.semicolon.dtos.responses.GenerateExitCodeResponse;
import africa.semicolon.dtos.responses.GenerateResidentEntryCodeResponse;
import africa.semicolon.dtos.responses.GenerateVisitorEntryCodeResponse;
import africa.semicolon.dtos.responses.ValidateCodeResponse;
import africa.semicolon.exceptions.InvalidGatePassException;
import africa.semicolon.exceptions.ResidentSuspendedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class GateAccessServiceTest {

    @Autowired
    GateAccessService service;
    @Autowired
    ResidentRepo residentRepo;

    @Autowired
    GatePassRepo gatePassRepo;

    @BeforeEach
    public void setUp() {
        residentRepo.deleteAll();
        gatePassRepo.deleteAll();
    }

    @Test
    public void testThat_ICanGenerate_ResidentEntryCode() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);

        GenerateResidentEntryCodeResponse response = service.generateResidentEntryCode(request);

        assertNotNull(response.getOtpCode());
        assertEquals(resident.getId(), response.getResidentId());
    }

    @Test
    public void testThat_SuspendedResidentCannotGenerate_ResidentEntryCode() {
        Resident resident = new Resident();
        resident.setName("TeeSuspended");
        resident.setPhoneNumber("08087654321");
        resident.setSuspended(true);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);

        assertThrows(ResidentSuspendedException.class, () -> service.generateResidentEntryCode(request));
    }

    @Test
    public void testThat_ICanGenerate_VisitorEntryCode() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setVisitorName("John");
        request.setVisitorPhoneNumber("08011111111");
        request.setPurposeOfVisit("Delivery");
        request.setCodeType(Type.ENTRY);

        GenerateVisitorEntryCodeResponse response = service.generateVisitorEntryCode(request);

        assertNotNull(response.getOtpCode());
        assertEquals(resident.getId(), response.getResidentId());
    }

    @Test
    public void testThat_SuspendedResidentCannotGenerate_VisitorEntryCode() {
        Resident resident = new Resident();
        resident.setName("TeeSuspended");
        resident.setPhoneNumber("08087654321");
        resident.setSuspended(true);
        resident = residentRepo.save(resident) ;

        GenerateVisitorEntryCodeRequest request = new GenerateVisitorEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setVisitorName("John");
        request.setVisitorPhoneNumber("08011111111");
        request.setPurposeOfVisit("Delivery");
        request.setCodeType(Type.ENTRY);

        assertThrows(ResidentSuspendedException.class, () -> service.generateVisitorEntryCode(request));
    }

    @Test
    public void testThat_ICanGenerate_ExitCode() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);
        String otp = service.generateResidentEntryCode(request).getOtpCode();

        GenerateExitCodeResponse exitResponse = service.generateExitCode(otp);

        assertNotNull(exitResponse.getOtp());
        assertEquals(resident.getName(), exitResponse.getName());
    }

    @Test
    public void testThat_ICanValidate_CodeSuccessfully() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);
        String otp = service.generateResidentEntryCode(request).getOtpCode();

        ValidateCodeResponse response = service.validateCode(otp, Type.ENTRY);

        assertEquals(resident.getName(), response.getResidentName());
    }

    @Test
    public void testThat_ICannotValidate_ExpiredGatePass() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);
        request.setExpirationDate(LocalDateTime.now().minusHours(1));
        String otp = service.generateResidentEntryCode(request).getOtpCode();

        assertThrows(InvalidGatePassException.class, () -> service.validateCode(otp, Type.ENTRY));
    }

    @Test
    public void testThat_ICannotValidate_DisabledGatePass() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);
        String otp = service.generateResidentEntryCode(request).getOtpCode();

        String gatePassId = gatePassRepo.findByOtp(otp).getId();
        service.disableCode(gatePassId);

        assertThrows(InvalidGatePassException.class, () -> service.validateCode(otp, Type.ENTRY));
    }

    @Test
    public void testThat_ICanExtend_GatePassTime() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);
        String otp = service.generateResidentEntryCode(request).getOtpCode();

        String gatePassId = gatePassRepo.findByOtp(otp).getId();
        LocalDateTime newTime = LocalDateTime.now().plusHours(5);
        String result = service.extendTime(gatePassId, newTime);

        assertTrue(result.contains("Time has been extended"));
    }

    @Test
    public void testThat_ICanDisable_GatePass() {
        Resident resident = new Resident();
        resident.setName("Tee");
        resident.setPhoneNumber("08012345678");
        resident.setSuspended(false);
        resident = residentRepo.save(resident) ;

        GenerateResidentEntryCodeRequest request = new GenerateResidentEntryCodeRequest();
        request.setResidentId(resident.getId());
        request.setCodeType(Type.ENTRY);
        String otp = service.generateResidentEntryCode(request).getOtpCode();

        String gatePassId = gatePassRepo.findByOtp(otp).getId();
        String result = service.disableCode(gatePassId);

        assertTrue(result.contains("successfully disabled"));
    }
}