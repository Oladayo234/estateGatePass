package africa.semicolon.controllers;

import africa.semicolon.dtos.requests.GenerateResidentEntryCodeRequest;
import africa.semicolon.dtos.requests.GenerateVisitorEntryCodeRequest;
import africa.semicolon.dtos.responses.ApiResponse;
import africa.semicolon.services.GateAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/residents")
@RequiredArgsConstructor
public class ResidentController {
    private final GateAccessService gateAccessService;

    @PostMapping("/resident-entry-code")
    public ResponseEntity<ApiResponse> generateResidentEntryCode(@RequestBody GenerateResidentEntryCodeRequest request) {
        try {
            ApiResponse response = new ApiResponse("success", "Resident code processed successfully", gateAccessService.generateResidentEntryCode(request));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/visitor-entry-code")
    public ResponseEntity<ApiResponse> generateVisitorEntryCode(@RequestBody GenerateVisitorEntryCodeRequest request) {
        try {
            ApiResponse response = new ApiResponse("success", "Visitor code processed successfully", gateAccessService.generateVisitorEntryCode(request));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/exit-code/{otp}")
    public ResponseEntity<ApiResponse> generateExitCode(@PathVariable String otp) {
        try {
            ApiResponse response = new ApiResponse("success", "Exit code processed successfully", gateAccessService.generateExitCode(otp));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PatchMapping("/extend-time/{gatePassId}")
    public ResponseEntity<ApiResponse> extendTime(@PathVariable String gatePassId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newExpirationDate) {
        try {
            ApiResponse response = new ApiResponse("success", "Time extended successfully", gateAccessService.extendTime(gatePassId, newExpirationDate));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PatchMapping("/disable-code/{gatePassId}")
    public ResponseEntity<ApiResponse> disableCode(@PathVariable String gatePassId) {
        try {
            ApiResponse response = new ApiResponse("success", "code disabled successfully", gateAccessService.disableCode(gatePassId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
