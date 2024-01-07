package dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutputDto {
    private String operationDesc;
    private String productId;
    private String productName;
    private List<String> offerNames;

    public OutputDto(String operationDesc, String productId, String productName, List<String> offerNames) {
        this.operationDesc = operationDesc;
        this.productId = productId;
        this.productName = productName;
        this.offerNames = offerNames;
    }


    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<String> getOfferNames(int i) {
        return offerNames;
    }

    public void setOfferNames(List<String> offerName) {
        this.offerNames = offerName;
    }
}
