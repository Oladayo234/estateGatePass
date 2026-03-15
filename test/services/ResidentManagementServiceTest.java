package services;

import dtos.requests.OnboardResidentRequest;
import dtos.responses.OnboardResidentResponse;
import exceptions.ResidentAlreadyRegisteredException;
import exceptions.ResidentDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResidentManagementServiceTest {

    ResidentManagementService service;

    @BeforeEach
    public void setUp() {
        service = new ResidentManagementService();
    }

    @Test
    public void testThat_ICan_OnboardResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Tee");
        request.setPhoneNumber("08012345678");

        OnboardResidentResponse response = service.onboardResident(request);

        assertNotNull(response);
        assertEquals("Tee", response.getName());
    }

    @Test
    public void testThat_ICanOnboard_MultipleResidents() {
        OnboardResidentRequest request1 = new OnboardResidentRequest();
        request1.setName("Tee");
        request1.setPhoneNumber("08011111111");
        service.onboardResident(request1);

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request2.setName("John");
        request2.setPhoneNumber("08022222222");
        service.onboardResident(request2);

        List<OnboardResidentResponse> residents = service.viewAllResident();
        assertEquals(2, residents.size());
    }

    @Test
    public void testThat_ICannotOnboard_ResidentWithSame_PhoneNumber() {
        OnboardResidentRequest request1 = new OnboardResidentRequest();
        request1.setName("Tee");
        request1.setPhoneNumber("08012345678");
        service.onboardResident(request1);

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request2.setName("John");
        request2.setPhoneNumber("08012345678");

        assertThrows(ResidentAlreadyRegisteredException.class, () -> service.onboardResident(request2));
    }

    @Test
    public void testThat_ICannotOnboard_ResidentWithBlankName() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("");
        request.setPhoneNumber("08012345678");

        assertThrows(IllegalArgumentException.class, () -> service.onboardResident(request));
    }

    @Test
    public void testThat_ICannotOnboard_ResidentWithBlankPhone() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Tee");
        request.setPhoneNumber("");

        assertThrows(IllegalArgumentException.class, () -> service.onboardResident(request));
    }

    @Test
    public void testThat_ICanViewResidentById() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Tee");
        request.setPhoneNumber("08012345678");

        OnboardResidentResponse response = service.onboardResident(request);

        OnboardResidentResponse foundResident = service.viewResident(response.getId());
        assertEquals("Tee", foundResident.getName());
    }

    @Test
    public void testThat_ICannotViewResident_ThatDoesNotExist() {
        assertThrows(ResidentDoesNotExistException.class, () -> service.viewResident("invalid-id"));
    }

    @Test
    public void testThat_ICanView_AllResidents() {
        OnboardResidentRequest request1 = new OnboardResidentRequest();
        request1.setName("Tee");
        request1.setPhoneNumber("08011111111");
        service.onboardResident(request1);

        OnboardResidentRequest request2 = new OnboardResidentRequest();
        request2.setName("John");
        request2.setPhoneNumber("08022222222");
        service.onboardResident(request2);

        List<OnboardResidentResponse> residents = service.viewAllResident();
        assertEquals(2, residents.size());
    }

    @Test
    public void testThat_ICan_DeleteResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Tee");
        request.setPhoneNumber("08012345678");

        OnboardResidentResponse response = service.onboardResident(request);
        service.deleteResident(response.getId());

        assertThrows(ResidentDoesNotExistException.class, () -> service.viewResident(response.getId()));
    }

    @Test
    public void testThat_ICannotDeleteResident_ThatDoesNotExist() {
        assertThrows(ResidentDoesNotExistException.class, () -> service.deleteResident("invalid-id"));
    }

    @Test
    public void testThat_ICan_DisableResident() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Tee");
        request.setPhoneNumber("08012345678");

        OnboardResidentResponse response = service.onboardResident(request);
        String result = service.disableResident(response.getId());

        assertTrue(result.contains(" has been suspended"));
    }

    @Test
    public void testThat_DisabledResident_IsSuspended() {
        OnboardResidentRequest request = new OnboardResidentRequest();
        request.setName("Tee");
        request.setPhoneNumber("08012345678");

        OnboardResidentResponse response = service.onboardResident(request);
        service.disableResident(response.getId());

        OnboardResidentResponse disabledResident = service.viewResident(response.getId());
        assertTrue(disabledResident.isSuspended(), "Resident should be suspended after disableResident");
    }
}