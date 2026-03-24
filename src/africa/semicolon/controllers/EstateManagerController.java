package africa.semicolon.controllers;

import africa.semicolon.dtos.requests.OnboardResidentRequest;
import africa.semicolon.dtos.responses.ApiResponse;
import africa.semicolon.services.ResidentManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/estate-manager")
@RequiredArgsConstructor
public class EstateManagerController {
    private final ResidentManagementService residentManagementService;

    @PostMapping("/residents")
    public ResponseEntity<ApiResponse> onboardResident(@RequestBody OnboardResidentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("success", "Resident onboarded successfully", residentManagementService.onboardResident(request)));
    }

    @GetMapping("/residents/{id}")
    public ResponseEntity<ApiResponse> viewResident(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse("success", "View resident successfully", residentManagementService.viewResident(id)));
    }

    @GetMapping("/residents")
    public ResponseEntity<ApiResponse> viewAllResident() {
        return ResponseEntity.ok(new ApiResponse("success", "View all resident successfully", residentManagementService.viewAllResident()));
    }

    @DeleteMapping("/residents/{id}")
    public ResponseEntity<ApiResponse> deleteResident(@PathVariable String id) {
        residentManagementService.deleteResident(id);
        return ResponseEntity.ok(new ApiResponse("success", "Resident was deleted successfully", null));
    }

    @PatchMapping("/residents/{id}/disable")
    public ResponseEntity<ApiResponse> disableResident(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse("success", "Resident disabled successfully", residentManagementService.disableResident(id)));
    }

    @PatchMapping("/residents/{id}/reactivate")
    public ResponseEntity<ApiResponse> reactivateResident(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse("success", "Resident reactivated successfully", residentManagementService.reactivateResident(id)));
    }
}


