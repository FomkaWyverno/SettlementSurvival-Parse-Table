package ua.wyverno.localization;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LangList(@JsonProperty("Lang")Lang lang) {
}
