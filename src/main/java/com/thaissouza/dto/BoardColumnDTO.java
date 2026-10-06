package com.thaissouza.dto;

import com.thaissouza.persistence.entity.BoardColumnKindEnum;

public record BoardColumnDTO(Long id,
                             String name,
                             int order,
                             BoardColumnKindEnum kind,
                             int cardsAmount) {
}
