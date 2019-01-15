package com.sbryan.internalservice.fault.data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "ValidationError",
        propOrder = {"validationCode", "input"}
)
@XmlRootElement(
        name = "ValidationError"
)
public class ValidationError {
    @XmlElement(
            name = "validationCode"
    )
    private String validationCode = null;
    @XmlElement(
            name = "Input"
    )
    private List<Input> input = new ArrayList();

    public ValidationError() {
    }

    public String getValidationCode() {
        return this.validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public List<Input> getInput() {
        return this.input;
    }

    public void setInput(List<Input> input) {
        this.input = input;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ValidationError {\n");
        sb.append("    validationCode: ").append(toIndentedString(this.validationCode)).append("\n");
        sb.append("    input: ").append(toIndentedString(this.input)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}

