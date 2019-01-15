package com.sbryan.internalservice.fault.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "Input",
        propOrder = {"field", "value"}
)
@XmlRootElement(
        name = "Input"
)
public class Input {
    @XmlElement(
            name = "field"
    )
    private String field = null;
    @XmlElement(
            name = "value"
    )
    private String value = null;

    public Input() {
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Input {\n");
        sb.append("    field: ").append(toIndentedString(this.field)).append("\n");
        sb.append("    value: ").append(toIndentedString(this.value)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private static String toIndentedString(Object o) {
        return o == null ? "null" : o.toString().replace("\n", "\n    ");
    }
}

