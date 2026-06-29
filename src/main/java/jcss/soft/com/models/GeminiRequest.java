package jcss.soft.com.models;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeminiRequest {
    private String model;
    private String input;
}
