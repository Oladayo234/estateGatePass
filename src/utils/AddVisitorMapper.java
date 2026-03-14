package utils;

import data.models.Visitor;
import dtos.requests.AddVisitorRequest;
import dtos.responses.AddVisitorResponse;

public class AddVisitorMapper {
    public static Visitor map(AddVisitorRequest request) {
        Visitor visitor = new Visitor();
        visitor.setName(request.getName());
        visitor.setPhoneNumber(request.getPhoneNumber());
        visitor.setPurposeOfVisit(request.getPurposeOfVisit());
        return visitor;
    }

    public static AddVisitorResponse map(Visitor visitor) {
        AddVisitorResponse response = new AddVisitorResponse();
        response.setName(visitor.getName());
        response.setPhoneNumber(visitor.getPhoneNumber());
        response.setPurposeOfVisit(visitor.getPurposeOfVisit());
        response.setId(visitor.getId());
        return response;

        }
}
