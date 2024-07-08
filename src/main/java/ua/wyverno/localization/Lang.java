package ua.wyverno.localization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public record Lang(@JsonProperty("LangType") String langType,
                   @JsonProperty("LangValue") String langValue) {
}
