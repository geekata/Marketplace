package com.marketplace.servicefeedbacks.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FeedbackDTO {
    private long product;
    private long userId;
    private int rating;
    private Date date;
    private String text;
}
