package africa.semicolon.controllers;

import africa.semicolon.dtos.requests.OnboardResidentRequest;
import africa.semicolon.dtos.responses.ApiResponse;
import africa.semicolon.dtos.responses.OnboardResidentResponse;
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
    public ResponseEntity<?> onboardResident(@RequestBody OnboardResidentRequest request) {
        try {
            OnboardResidentResponse response = residentManagementService.onboardResident(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("success", "Resident onboarded successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("error", e.getMessage(), null));
        }
    }
    @GetMapping("/residents/{id}")
    public ResponseEntity<?> viewResident(@PathVariable String id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", "View resident successfully", residentManagementService.viewResident(id)));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("error", e.getMessage(), null));
        }
    }

    @GetMapping("/residents")
    public ResponseEntity<?> viewAllResident() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", "View all resident successfully", residentManagementService.viewAllResident()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/residents/{id}")
    public ResponseEntity<?> deleteResident(@PathVariable String id){
        try{
            residentManagementService.deleteResident(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", "Resident was deleted successfully", null));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("error", e.getMessage(), null));
        }
    }

    @PatchMapping("/residents/{id}/disable")
    public ResponseEntity<?> disableResident(@PathVariable String id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("success", "Resident disabled successfully",  residentManagementService.disableResident(id)));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("error", e.getMessage(), null));
        }
    }

    @PatchMapping("/residents/{id}/reactivate")
    public ResponseEntity<?> reactivateResident(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("success", "Resident reactivated successfully", residentManagementService.reactivateResident(id)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("error", e.getMessage(), null));
        }
    }
}


