package uni.project.online.shop.model.revolut;

import com.google.gson.annotations.SerializedName;

public class PaymentMethods {
    private String id;
    private String type;
    private String labelName;
    @SerializedName("saved_for")
    private String savedFor;
    @SerializedName("method_details")
    private MethodDetails methodDetails;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSavedFor() {
        return savedFor;
    }

    public void setSavedFor(String savedFor) {
        this.savedFor = savedFor;
    }

    public MethodDetails getMethodDetails() {
        return methodDetails;
    }

    public void setMethodDetails(MethodDetails methodDetails) {
        this.methodDetails = methodDetails;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
