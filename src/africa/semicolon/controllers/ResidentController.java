package africa.semicolon.controllers;

import africa.semicolon.dtos.requests.GenerateResidentEntryCodeRequest;
import africa.semicolon.dtos.requests.GenerateVisitorEntryCodeRequest;
import africa.semicolon.dtos.responses.ApiResponse;
import africa.semicolon.services.GateAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
        return ResponseEntity.ok(new ApiResponse("success", "Resident code processed successfully", gateAccessService.generateResidentEntryCode(request)));
    }

    @PostMapping("/visitor-entry-code")
    public ResponseEntity<ApiResponse> generateVisitorEntryCode(@RequestBody GenerateVisitorEntryCodeRequest request) {
        return ResponseEntity.ok(new ApiResponse("success", "Visitor code processed successfully", gateAccessService.generateVisitorEntryCode(request)));
    }

    @PostMapping("/exit-code/{otp}")
    public ResponseEntity<ApiResponse> generateExitCode(@PathVariable String otp) {
        return ResponseEntity.ok(new ApiResponse("success", "Exit code processed successfully", gateAccessService.generateExitCode(otp)));
    }

    @PatchMapping("/extend-time/{gatePassId}")
    public ResponseEntity<ApiResponse> extendTime(@PathVariable String gatePassId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newExpirationDate) {
        return ResponseEntity.ok(new ApiResponse("success", "Time extended successfully", gateAccessService.extendTime(gatePassId, newExpirationDate)));
    }

    @PatchMapping("/disable-code/{gatePassId}")
    public ResponseEntity<ApiResponse> disableCode(@PathVariable String gatePassId) {
        return ResponseEntity.ok(new ApiResponse("success", "Code disabled successfully", gateAccessService.disableCode(gatePassId)));
    }
}
