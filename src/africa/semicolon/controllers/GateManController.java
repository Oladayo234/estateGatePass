package africa.semicolon.controllers;

import africa.semicolon.data.models.Type;
import africa.semicolon.dtos.responses.ApiResponse;
import africa.semicolon.services.GateAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gate-man")
@RequiredArgsConstructor
public class GateManController {
    private final GateAccessService gateAccessService;

    @GetMapping("/validate-code/{otp}/{type}")
    public ResponseEntity<ApiResponse> validateCode(@PathVariable String otp, @PathVariable Type type) {
        return ResponseEntity.ok(new ApiResponse("success", "Code is valid", gateAccessService.validateCode(otp, type)));
    }
}
