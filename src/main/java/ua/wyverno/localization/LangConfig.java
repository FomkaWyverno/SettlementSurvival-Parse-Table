package ua.wyverno.localization;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LangConfig(@JsonProperty("ID")String id, @JsonProperty("LangList")LangList langList) {
}
